package org.id2k1149.project_v10.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.project_v10.exception.NotFoundException;
import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.model.Counter;
import org.id2k1149.project_v10.repo.CounterRepo;
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
@Transactional
@Slf4j
public class CounterService {
    private final CounterRepo counterRepo;
    private final VoterService voterService;

    public List<Counter> getCounters() {
        return counterRepo.findAll();
    }

    public Counter getCounter(Long id) {
        if (counterRepo.findById(id).isEmpty()) {
            throw new NotFoundException(id + " does not exist");
        }
        return counterRepo.getById(id);
    }

    public void addCounter(Counter newCounter) {
        counterRepo.save(newCounter);
    }

    public void updateCounter(Long id, Counter counter) {
        if (counterRepo.findById(id).isEmpty()) {
            throw new NotFoundException(id + " does not exist");
        }
        Counter counterToUpdate = counterRepo.findById(id).get();
        counterToUpdate.setAnswer(counter.getAnswer());
        counterToUpdate.setDate(counter.getDate());
        counterToUpdate.setVotes(counter.getVotes());
        counterRepo.save(counterToUpdate);
    }

    public void deleteCounter(Long id) {
        if (counterRepo.findById(id).isEmpty()) {
            throw new NotFoundException(id + " does not exists");
        }
        counterRepo.deleteById(id);
    }

    public void vote(Counter counter) {
        Counter newCounter = new Counter();
        Answer newAnswer = counter.getAnswer();
        int votes = 0;
        Optional<Counter> optionalVotesCounter = counterRepo
                .findByDateAndAnswer(LocalDate.now(), newAnswer);
        if (optionalVotesCounter.isPresent()) {
            newCounter = optionalVotesCounter.get();
            votes = newCounter.getVotes();
        }
        votes += 1;
        newCounter.setAnswer(newAnswer);
        newCounter.setVotes(votes);
        counterRepo.save(newCounter);
        voterService.checkVoter(newAnswer);
    }

    public List<Counter> getResult() {
        List<Counter> counterList = counterRepo.findByDate(LocalDate.now());
        if (counterList.size() == 0) return counterList;

        List<Counter> sortedList = counterList.stream()
                .sorted(Comparator.comparingInt(Counter::getVotes).reversed())
                .collect(Collectors.toList());

        Counter bestResult = sortedList
                .stream()
                .max(Comparator.comparing(Counter::getVotes))
                .orElseThrow(NoSuchElementException::new);

        return sortedList;
    }
}