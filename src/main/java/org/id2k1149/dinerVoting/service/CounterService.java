package org.id2k1149.dinerVoting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.dinerVoting.exception.NotFoundException;
import org.id2k1149.dinerVoting.exception.TimeException;
import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.VoiceCounter;
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

    public List<VoiceCounter> getCounters() {
        log.info("Find all counters in DB");
        return counterRepo.findAll();
    }

    public VoiceCounter getCounter(Long id) {
        if (counterRepo.existsById(id)) return counterRepo.getById(id);
        else {
            log.error("Id {} does not exist in DB", id);
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    public VoiceCounter addCounter(VoiceCounter newVoiceCounter) {
        counterRepo.save(newVoiceCounter);
        return newVoiceCounter;
    }

    public void updateCounter(Long id, VoiceCounter voiceCounter) {
        if (counterRepo.existsById(id)) {
            VoiceCounter voiceCounterToUpdate = counterRepo.getById(id);
            voiceCounterToUpdate.setDiner(voiceCounter.getDiner());
            voiceCounterToUpdate.setDate(voiceCounter.getDate());
            voiceCounterToUpdate.setVotes(voiceCounter.getVotes());
            counterRepo.save(voiceCounterToUpdate);
        } else {
            log.error("Id {} does not exist in DB", id);
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    public void deleteCounter(Long id) {
        if (counterRepo.existsById(id)) counterRepo.deleteById(id);
        else {
            log.error("Id {} does not exist in DB", id);
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    public void vote(VoiceCounter voiceCounter) {
        VoiceCounter newVoiceCounter = new VoiceCounter();
        Diner newDiner = voiceCounter.getDiner();
        int votes = 0;
        Optional<VoiceCounter> optionalVotesCounter = counterRepo
                .findByDateAndDiner(LocalDate.now(), newDiner);
        if (optionalVotesCounter.isPresent()) {
            newVoiceCounter = optionalVotesCounter.get();
            votes = newVoiceCounter.getVotes();
        }
        votes += 1;
        newVoiceCounter.setDiner(newDiner);
        newVoiceCounter.setVotes(votes);
        counterRepo.save(newVoiceCounter);
        voterService.checkVoter(newDiner);
    }

    public List<VoiceCounter> getAllResults() {
        List<VoiceCounter> voiceCounterList = counterRepo.findAllByDate(LocalDate.now());
        return (voiceCounterList.size() == 0 ? voiceCounterList : voiceCounterList.stream()
                .sorted(Comparator.comparingInt(VoiceCounter::getVotes).reversed())
                .collect(Collectors.toList()));
    }

    public VoiceCounter getBestResult() {
        List<VoiceCounter> sortedList = getAllResults();
        return sortedList
                .stream()
                .max(Comparator.comparing(VoiceCounter::getVotes))
                .orElseThrow(NoSuchElementException::new);
    }


    public void voteForDiner(Long id) {
        checkTime();
        checkTodayDinerList();
        VoiceCounter voiceCounter = new VoiceCounter();
        voiceCounter.setDate(LocalDate.now());
        voiceCounter.setDiner(dinerRepo.getById(id));
        vote(voiceCounter);
    }

    private void checkTime() {
        if (LocalTime.now().getHour() > 23) throw new TimeException("You can't vote today. Vote tomorrow.");
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