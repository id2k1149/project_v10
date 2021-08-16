package org.id2k1149.project_v10.repo;

import com.github.javafaker.Faker;
import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.model.Info;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class InfoRepoTest {
    private final Faker faker = new Faker();
    @Autowired
    private InfoRepo testInfoRepo;
    @Autowired
    private AnswerRepo testAnswerRepo;

    @AfterEach
    void tearDown() {
        testAnswerRepo.deleteAll();
        testInfoRepo.deleteAll();
    }

    public LocalDate getRandomDate() {
        long minDay = LocalDate.of(2021, 8, 1).toEpochDay();
        long maxDay = LocalDate.of(2021, 8, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    public Answer getRandomAnswer() {
        Answer answer = new Answer();
        answer.setTitle(faker.beer().name());
        testAnswerRepo.save(answer);
        return answer;
    }

    @Test
    void findAllDate() {
        LocalDate ld = getRandomDate();
        List<Info> list1 = new ArrayList<>();

        Random random = new Random();
        int bound = random.nextInt(4) + 2;
        IntStream.range(0, bound).mapToObj(i -> new Info()).forEach(info -> {
            info.setDate(ld);
            info.setAnswer(getRandomAnswer());
            list1.add(info);
            testInfoRepo.save(info);
        });

        random = new Random();
        bound = random.nextInt(4) + 2;
        IntStream.range(0, bound).mapToObj(i -> new Info()).forEach(info -> {
            info.setDate(getRandomDate());
            info.setAnswer(getRandomAnswer());
            testInfoRepo.save(info);
        });

        List<Info> list2 = testInfoRepo.findAllByDate(ld);
        assertThat(list2).isEqualTo(list1);
    }

    @Test
    void findAllByAnswer() {
        Answer answer1 = getRandomAnswer();
        List<Info> list1 = new ArrayList<>();

        Random random = new Random();
        int bound = random.nextInt(4) + 2;
        IntStream.range(0, bound).mapToObj(i -> new Info()).forEach(info -> {
            info.setDate(getRandomDate());
            info.setAnswer(answer1);
            list1.add(info);
            testInfoRepo.save(info);
        });

        random = new Random();
        bound = random.nextInt(4) + 2;
        IntStream.range(0, bound).mapToObj(i -> new Info()).forEach(info -> {
            info.setDate(getRandomDate());
            info.setAnswer(getRandomAnswer());
            testInfoRepo.save(info);
        });

        List<Info> list2 = testInfoRepo.findAllByAnswer(answer1);
        assertThat(list2).isEqualTo(list1);
    }
}