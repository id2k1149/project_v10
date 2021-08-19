package org.id2k1149.project_v10.repo;

import com.github.javafaker.Faker;
import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.model.Counter;
import org.id2k1149.project_v10.model.Info;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private InfoRepo testInfoRepo;
    @Autowired
    private AnswerRepo testAnswerRepo;

    @BeforeEach
    void setUp() {
        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> new Info()).forEach(info -> {
            info.setAnswer(getRandomAnswer());
//            info.setDate(getRandomDate());
            testInfoRepo.save(info);
        });
    }

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
        answer.setTitle(new Faker().beer().name());
        testAnswerRepo.save(answer);
        return answer;
    }

    @Test
    void findAllDate() {
        LocalDate date = getRandomDate();
        List<Info> list = new ArrayList<>();

        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> new Info()).forEach(info -> {
            info.setDate(date);
            info.setAnswer(getRandomAnswer());
            list.add(info);
            testInfoRepo.save(info);
        });

        List<Info> list2 = testInfoRepo.findAllByDate(date);
        assertThat(list2).isEqualTo(list);
    }

    @Test
    void findAllByAnswer() {
        Answer answer = getRandomAnswer();
        List<Info> list = new ArrayList<>();

        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> new Info()).forEach(info -> {
            info.setDate(getRandomDate());
            info.setAnswer(answer);
            list.add(info);
            testInfoRepo.save(info);
        });

        List<Info> list2 = testInfoRepo.findAllByAnswer(answer);
        assertThat(list2).isEqualTo(list);
    }

    @Test
    void findByDateAndAnswer() {
        LocalDate date = getRandomDate();
        Answer answer = getRandomAnswer();
        Info info = new Info();
        info.setDate(date);
        info.setAnswer(answer);
        testInfoRepo.save(info);

        Info info2 = testInfoRepo.findByDateAndAnswer(date, answer).get();
        assertThat(info2).isEqualTo(info);
    }
}