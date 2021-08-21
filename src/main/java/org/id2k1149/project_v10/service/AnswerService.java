package org.id2k1149.project_v10.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.project_v10.exception.NotFoundException;
import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.model.Info;
import org.id2k1149.project_v10.repo.AnswerRepo;
import org.id2k1149.project_v10.repo.InfoRepo;
import org.id2k1149.project_v10.to.AnswerTo;
import org.id2k1149.project_v10.to.InfoTo;
import org.id2k1149.project_v10.util.InfoUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AnswerService {
    private final AnswerRepo answerRepo;
    private final InfoRepo infoRepo;

    public List<Answer> getAnswers() {
        log.info("Find all answers in DB");
        return answerRepo.findAll();
    }

    public Answer getAnswer(Long id) {
        if (answerRepo.existsById(id)) return answerRepo.getById(id);
        else {
            log.error("Id {} does not exist in DB", id);
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    public Answer addAnswer(Answer newAnswer) {
        Optional<Answer> optionalAnswer = Optional.ofNullable(answerRepo.findAnswerByTitle(newAnswer.getTitle()));
        assert optionalAnswer.isEmpty() : "The name " + newAnswer.getTitle() + " is already used";
        answerRepo.save(newAnswer);
        return newAnswer;
    }

    public void updateAnswer(Answer answer,
                             Long id) {
        if (answerRepo.existsById(id)) {
            Answer answerToUpdate = answerRepo.getById(id);
            if (answer.getTitle() != null) {
                answerToUpdate.setTitle(answer.getTitle());
                answerRepo.save(answerToUpdate);
            } else {
                log.error("Id {} does not exist in DB", id);
                throw new NotFoundException("Id " + id + " does not exists");
            }
        }
    }

    public void deleteAnswer(Long id) {
        if (answerRepo.existsById(id)) answerRepo.deleteById(id);
        else {
            log.error("Id {} does not exist in DB", id);
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    public AnswerTo getAllInfoForAnswer(Long id) {
        List<Info> infoList = infoRepo.findAllByAnswer(getAnswer(id));
        List<InfoTo> infoToList = InfoUtil.getInfoTo(getAnswer(id), infoList);
        return new AnswerTo(id, getAnswer(id).getTitle(), infoToList);
    }

    public AnswerTo getTodayInfoForAnswer(Long id) {
        List<Info> infoList = infoRepo.findAllByDate(LocalDate.now());
        List<InfoTo> infoToList = InfoUtil.getInfoTo(getAnswer(id), infoList);
        return new AnswerTo(id, getAnswer(id).getTitle(), infoToList);
    }
}