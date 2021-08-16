package org.id2k1149.project_v10.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.project_v10.exception.NotFoundException;
import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.model.Info;
import org.id2k1149.project_v10.repo.AnswerRepo;
import org.id2k1149.project_v10.repo.InfoRepo;
import org.id2k1149.project_v10.to.AnswerTo;
import org.id2k1149.project_v10.util.AnswerUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InfoService {
    private final InfoRepo infoRepo;
    private final AnswerRepo answerRepo;

    public List<Info> getAllInfo() {
        return infoRepo.findAll();
    }

    public Info getInfo(Long id) {
        if (infoRepo.findById(id).isEmpty()) {
            throw new NotFoundException(id + " does not exist");
        }
        return infoRepo.getById(id);
    }

    public void addInfo(Info newInfo, Long answerId) {
        if (answerRepo.findById(answerId).isEmpty()) {
            throw new NotFoundException(answerId + " does not exist");
        }
        newInfo.setAnswer(answerRepo.getById(answerId));
        infoRepo.save(newInfo);
    }

    public void updateInfo(Info info, Long id) {
        if (infoRepo.findById(id).isEmpty()) {
            throw new NotFoundException(id + " does not exist");
        }
        Info infoToUpdate = infoRepo.findById(id).get();
        infoToUpdate.setAnswer(info.getAnswer());
        infoToUpdate.setDate(info.getDate());
        infoToUpdate.setDetails(info.getDetails());
        infoRepo.save(infoToUpdate);
    }

    public void deleteInfo(Long id) {
        if (infoRepo.findById(id).isEmpty()) {
            throw new NotFoundException(id + " does not exists");
        }
        infoRepo.deleteById(id);
    }

    public List<Info> getByDate(LocalDate date) {
        return infoRepo.findAllByDate(date);
    }

    public List<Answer> getTodayAnswersInfo() {
        return infoRepo
                .findAllByDate(LocalDate.now())
                .stream()
                .map(Info::getAnswer)
                .collect(Collectors.toList());
    }

    public List<AnswerTo> checkTime() {
        List<AnswerTo> answerToList = new ArrayList<>();
        if (LocalTime.now().getHour() < 24) answerToList = AnswerUtil.getAnswersTo(getTodayAnswersInfo(),
                getByDate(LocalDate.now()));
        return answerToList;
    }
}