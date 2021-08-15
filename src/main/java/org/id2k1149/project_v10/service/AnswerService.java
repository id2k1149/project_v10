package org.id2k1149.project_v10.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.project_v10.exception.BadRequestException;
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
        return answerRepo.findAll();
    }

    public Answer getAnswer(Long id) {
        if (answerRepo.findById(id).isEmpty()) {
            throw new NotFoundException(id + " does not exist");
        }
        return answerRepo.getById(id);
    }

    public void addAnswer(Answer newAnswer) {
        Optional<Answer> optionalAnswer = Optional.ofNullable(answerRepo.findAnswerByTitle(newAnswer.getTitle()));
        if (optionalAnswer.isPresent()) {
            throw new BadRequestException("The name " + newAnswer.getTitle() + " is already used");
        }
        answerRepo.save(newAnswer);
    }

    public void updateAnswer(Answer answer,
                             Long id) {
        Answer answerToUpdate;
        Optional<Answer> optionalAnswer = answerRepo.findById(id);
        if (optionalAnswer.isEmpty()) {
            throw new NotFoundException(id + " does not exist");
        } else {
            answerToUpdate = optionalAnswer.get();
        }

        String newTitle = answer.getTitle();
        if (newTitle != null) {
            answerToUpdate.setTitle(newTitle);
        }

        answerRepo.save(answerToUpdate);
    }

    public void deleteAnswer(Long id) {
        if (answerRepo.findById(id).isEmpty()) {
            throw new NotFoundException(id + " does not exists");
        }
        answerRepo.deleteById(id);
    }

    public AnswerTo getAllInfoForAnswer(Long id) {
        Answer answer = getAnswer(id);
        List<Info> infoList = infoRepo.getByAnswer(answer);
        List<InfoTo> infoToList = InfoUtil.getInfoTo(answer, infoList);
        return new AnswerTo(id, answer.getTitle(), infoToList);
    }
}