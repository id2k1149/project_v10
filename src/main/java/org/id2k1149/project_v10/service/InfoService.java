package org.id2k1149.project_v10.service;

import com.github.javafaker.Faker;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public List<Answer> getAnswersInfoByDate(LocalDate date) {
        return infoRepo
                .findAllByDate(date)
                .stream()
                .map(Info::getAnswer)
                .collect(Collectors.toList());
    }

    public List<AnswerTo> checkTime(LocalDate date) {
        List<AnswerTo> answerToList = new ArrayList<>();
        if (LocalTime.now().getHour() < 23) answerToList = AnswerUtil.getAnswersTo(getAnswersInfoByDate(date),
                getByDate(LocalDate.now()));
        return answerToList;
    }

    public void getRandomInfo(LocalDate date) {
//        LocalDate date = LocalDate.now().minusDays(4);

        List<Info> optionalInfo = infoRepo.findAllByDate(date);

        if (optionalInfo.size() > 0) {
            return;
        } else {
            Random random = new Random();

            List<Answer> allAnswers = answerRepo.findAll();
            int max = allAnswers.size();
            int min = 2;
            int numberOfElements = random.nextInt((max - min) + 1) + min;

            List<Answer> answers = new ArrayList<>();
            for (int i = 0; i < numberOfElements; i++) {
                int randomIndex = random.nextInt(allAnswers.size());
                Answer randomAnswer = allAnswers.get(randomIndex);
                allAnswers.remove(randomIndex);
                answers.add(randomAnswer);
            }

            for (Answer todayAnswer : answers) {
                Info newInfo = new Info();
                newInfo.setDate(date);
                newInfo.setAnswer(todayAnswer);

                max = 5;
                min = 2;
                numberOfElements = random.nextInt((max - min) + 1) + min;
                Map<String, BigDecimal> descriptionMap = new HashMap<>();
                for (int i = 0; i < numberOfElements; i++) {
                    String stringInfo = new Faker().food().dish();
                    BigDecimal digitalInfo = BigDecimal.valueOf(Double.valueOf(new Faker().commerce().price(10, 100)));
                    descriptionMap.put(stringInfo, digitalInfo);
                }
                newInfo.setDetails(descriptionMap);
                infoRepo.save(newInfo);
            }
        }
    }
}