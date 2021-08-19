package org.id2k1149.project_v10.service;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        assert infoRepo.findById(id).isPresent() : id + " does not exist";
        return infoRepo.getById(id);
    }

    public void addInfo(Info newInfo, Long answerId) {
        assert answerRepo.findById(answerId).isPresent() : answerId + " does not exist";
        newInfo.setAnswer(answerRepo.getById(answerId));
        infoRepo.save(newInfo);
    }

    public void updateInfo(Info info, Long id) {
        assert infoRepo.findById(id).isPresent() : id + " does not exist";
        Info infoToUpdate = infoRepo.findById(id).get();
        infoToUpdate.setAnswer(info.getAnswer());
        infoToUpdate.setDate(info.getDate());
        infoToUpdate.setDetails(info.getDetails());
        infoRepo.save(infoToUpdate);
    }

    public void deleteInfo(Long id) {
        assert infoRepo.findById(id).isPresent() : id + " does not exists";
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

    public List<AnswerTo> vote() {
        return AnswerUtil
                .getAnswersTo(getAnswersInfoByDate(LocalDate.now()), getByDate(LocalDate.now()));
    }

    public void everyDayUpdate(LocalDate date) {
        // TODO vote check
        List<Info> optionalInfo = infoRepo.findAllByDate(date);
        if (optionalInfo.size() > 0) {
            return;
        } else {
            List<Answer> allAnswers = answerRepo.findAll();
            List<Answer> answers = new ArrayList<>();
            IntStream.range(0, new Random().nextInt((allAnswers.size() - 2) + 1) + 2)
                    .map(i -> new Random().nextInt(allAnswers.size())).forEach(randomIndex -> {
                Answer randomAnswer = allAnswers.get(randomIndex);
                allAnswers.remove(randomIndex);
                answers.add(randomAnswer);
            });
            answers.forEach(answer -> {
                Info newInfo = new Info();
                newInfo.setDate(date);
                newInfo.setAnswer(answer);
                Map<String, BigDecimal> descriptionMap = new HashMap<>();
                IntStream.range(0, new Random().nextInt(4) + 2)
                        .mapToObj(i -> new Faker().food().dish()).forEach(stringInfo -> {
                    BigDecimal digitalInfo = BigDecimal.valueOf(Double.parseDouble(new Faker().commerce().price(10, 100)));
                    descriptionMap.put(stringInfo, digitalInfo);
                });
                newInfo.setDetails(descriptionMap);
                infoRepo.save(newInfo);
            });
        }
    }
}