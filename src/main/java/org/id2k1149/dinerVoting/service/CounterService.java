package org.id2k1149.dinerVoting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.dinerVoting.exception.NoResultsException;
import org.id2k1149.dinerVoting.exception.NotFoundException;
import org.id2k1149.dinerVoting.model.Counter;
import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.Menu;
import org.id2k1149.dinerVoting.model.Voter;
import org.id2k1149.dinerVoting.repo.CounterRepo;
import org.id2k1149.dinerVoting.repo.DinerRepo;
import org.id2k1149.dinerVoting.repo.MenuRepo;
import org.id2k1149.dinerVoting.util.DinerUtil;
import org.id2k1149.dinerVoting.util.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CounterService {
    private final CounterRepo counterRepo;
    private final DinerRepo dinerRepo;
    private final MenuRepo menuRepo;
    private final VoterService voterService;

    @Transactional
    public void addVoiceToCounter(Counter counter) {
        Counter newCounter = new Counter();
        Diner newDiner = counter.getDiner();
        int votes = 0;
        Optional<Counter> optionalVotesCounter = counterRepo
                .findByDateAndDiner(LocalDate.now(), newDiner);
        if (optionalVotesCounter.isPresent()) {
            newCounter = optionalVotesCounter.get();
            votes = newCounter.getVotes();
        }
        votes += 1;
        newCounter.setDiner(newDiner);
        newCounter.setVotes(votes);
        counterRepo.save(newCounter);
        voterService.saveVoterForDiner(newDiner);
    }

    public List<Counter> getTodayAllResults() {
        List<Counter> counterList = counterRepo.findAllByDate(LocalDate.now());
        return (counterList.size() == 0 ? counterList : counterList.stream()
                .sorted(Comparator.comparingInt(Counter::getVotes).reversed())
                .collect(Collectors.toList()));
    }

    public Counter getTodayBestResult() {
        List<Counter> sortedList = getTodayAllResults();
        if (sortedList.size() == 0) {
            throw new NoResultsException("There are no votes today.");
        }
        return sortedList
                .stream()
                .max(Comparator.comparing(Counter::getVotes))
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void voteForDiner(Long id) {
        checkTodayDinerList();
        if (voterService.userVotedToday()) {
            TimeUtil.checkTime();
            Voter voter = voterService.getVoterByUserAndDate().get();
            Diner voterDiner = voter.getDiner();
            Counter oldCounter = counterRepo
                    .findByDateAndDiner(LocalDate.now(), voterDiner)
                    .get();
            int votes = oldCounter.getVotes() - 1;
            if (votes == 0) {
                counterRepo.delete(oldCounter);
            } else {
                oldCounter.setVotes(votes);
                counterRepo.save(oldCounter);
            }
            voterService.deleteVoter(voter.getId());
        }
        Counter counter = new Counter();
        counter.setDate(LocalDate.now());
        counter.setDiner(dinerRepo.getById(id));
        addVoiceToCounter(counter);
    }

    private void checkTodayDinerList() {
        if (DinerUtil.getDinersTo(
                menuRepo
                        .findAllByDate(LocalDate.now())
                        .stream()
                        .map(Menu::getDiner)
                        .collect(Collectors.toList()),
                menuRepo
                        .findAllByDate(LocalDate.now())).size() == 0) throw new NotFoundException("Empty vote list");
    }

    public boolean getTodayCounter() {
        return counterRepo.getFirstByDate(LocalDate.now()).isEmpty();
    }
}