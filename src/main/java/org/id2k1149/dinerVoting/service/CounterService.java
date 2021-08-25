package org.id2k1149.dinerVoting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.dinerVoting.exception.NotFoundException;
import org.id2k1149.dinerVoting.exception.TimeException;
import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.Counter;
import org.id2k1149.dinerVoting.model.Menu;
import org.id2k1149.dinerVoting.repo.DinerRepo;
import org.id2k1149.dinerVoting.repo.CounterRepo;
import org.id2k1149.dinerVoting.repo.MenuRepo;
import org.id2k1149.dinerVoting.util.DinerUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CounterService {
    private final CounterRepo counterRepo;
    private final DinerRepo dinerRepo;
    private final MenuRepo menuRepo;
    private final VoterService voterService;

    public List<Counter> getCounters() {
        log.info("Find all counters in DB");
        return counterRepo.findAll();
    }

    public Counter getCounter(Long id) {
        if (counterRepo.existsById(id)) {
            return counterRepo.getById(id);
        } else {
            log.error("Id {} does not exist in DB", id);
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    public Counter addCounter(Counter newCounter) {
        counterRepo.save(newCounter);
        return newCounter;
    }

    public void updateCounter(Long id, Counter counter) {
        if (counterRepo.existsById(id)) {
            Counter counterToUpdate = counterRepo.getById(id);
            counterToUpdate.setDiner(counter.getDiner());
            counterToUpdate.setDate(counter.getDate());
            counterToUpdate.setVotes(counter.getVotes());
            counterRepo.save(counterToUpdate);
        } else {
            log.error("Id {} does not exist in DB", id);
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    public void deleteCounter(Long id) {
        if (counterRepo.existsById(id)) {
            counterRepo.deleteById(id);
        } else {
            log.error("Id {} does not exist in DB", id);
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

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
        voterService.checkVoter(newDiner);
    }

    public List<Counter> getAllResults() {
        List<Counter> counterList = counterRepo.findAllByDate(LocalDate.now());
        return (counterList.size() == 0 ? counterList : counterList.stream()
                .sorted(Comparator.comparingInt(Counter::getVotes).reversed())
                .collect(Collectors.toList()));
    }

    public Counter getBestResult() {
        List<Counter> sortedList = getAllResults();
        return sortedList
                .stream()
                .max(Comparator.comparing(Counter::getVotes))
                .orElseThrow(NoSuchElementException::new);
    }


    public void voteForDiner(Long id) {
        checkTime();
        checkTodayDinerList();
        Counter counter = new Counter();
        counter.setDate(LocalDate.now());
        counter.setDiner(dinerRepo.getById(id));
        addVoiceToCounter(counter);
    }

    private void checkTime() {
        if (LocalTime.now().getHour() > 23) {
            throw new TimeException("You can't vote today. Vote tomorrow.");
        }
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

    public boolean checkTodayCounter() {
        return counterRepo.getFirstByDate(LocalDate.now()).isEmpty();
    }
}