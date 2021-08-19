package org.id2k1149.project_v10.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.project_v10.exception.NotFoundException;
import org.id2k1149.project_v10.exception.TimeException;
import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.model.Counter;
import org.id2k1149.project_v10.model.Info;
import org.id2k1149.project_v10.repo.AnswerRepo;
import org.id2k1149.project_v10.repo.CounterRepo;
import org.id2k1149.project_v10.repo.InfoRepo;
import org.id2k1149.project_v10.util.AnswerUtil;
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
    private final AnswerRepo answerRepo;
    private final InfoRepo infoRepo;
    private final VoterService voterService;

    public List<Counter> getCounters() {
        return counterRepo.findAll();
    }

    public Counter getCounter(Long id) {
        assert counterRepo.findById(id).isPresent() : id + " does not exist";
        return counterRepo.getById(id);
    }

    public void addCounter(Counter newCounter) {
        counterRepo.save(newCounter);
    }

    public void updateCounter(Long id, Counter counter) {
        assert counterRepo.findById(id).isPresent() : id + " does not exist";
        Counter counterToUpdate = counterRepo.findById(id).get();
        counterToUpdate.setAnswer(counter.getAnswer());
        counterToUpdate.setDate(counter.getDate());
        counterToUpdate.setVotes(counter.getVotes());
        counterRepo.save(counterToUpdate);
    }

    public void deleteCounter(Long id) {
        assert counterRepo.findById(id).isPresent() : id + " does not exists";
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


    public void voteForAnswer(Long id) {
        checkTime();
        checkTodayAnswerList();
        Counter counter = new Counter();
        counter.setDate(LocalDate.now());
        counter.setAnswer(answerRepo.getById(id));
        vote(counter);
    }

    private void checkTime() {
        if (LocalTime.now().getHour() > 22) throw new TimeException("You can't vote today. Vote tomorrow.");
    }

    private void checkTodayAnswerList() {
        if (AnswerUtil.getAnswersTo(
                infoRepo
                        .findAllByDate(LocalDate.now())
                        .stream()
                        .map(Info::getAnswer)
                        .collect(Collectors.toList()),
                infoRepo
                        .findAllByDate(LocalDate.now())).size() == 0) throw new NotFoundException("Empty vote list");
    }
}