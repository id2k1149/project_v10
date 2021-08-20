package org.id2k1149.project_v10.repo;

import com.github.javafaker.Faker;
import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.model.Counter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CounterRepoTest {

    @Autowired
    private CounterRepo testCounterRepo;

    @Autowired
    private AnswerRepo testAnswerRepo;

    public Answer getRandomAnswer() {
        Answer answer = new Answer();
        answer.setTitle(new Faker().beer().name());
        testAnswerRepo.save(answer);
        return answer;
    }

    public LocalDate getRandomDate() {
        long minDay = LocalDate.of(2021, 8, 1).toEpochDay();
        long maxDay = LocalDate.of(2021, 8, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    @BeforeEach
    void setUp() {
        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> new Counter()).forEach(counter -> {
            counter.setAnswer(getRandomAnswer());
            counter.setDate(getRandomDate());
            testCounterRepo.save(counter);
        });
    }

    @AfterEach
    void tearDown() {
        testAnswerRepo.deleteAll();
        testCounterRepo.deleteAll();
    }

    @Test
    void findByDateAndAnswer() {
        Answer answer = getRandomAnswer();
        LocalDate date = getRandomDate();
        Counter counter = new Counter();
        counter.setAnswer(answer);
        counter.setDate(date);
        testCounterRepo.save(counter);

        Optional<Counter> counter2 = testCounterRepo.findByDateAndAnswer(date, answer);

        assertThat(counter2.get()).isEqualTo(counter);
    }

    @Test
    void findAllByDate() {
        LocalDate date = getRandomDate();
        List<Counter> list = new ArrayList<>();
        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> new Counter()).forEach(counter -> {
            counter.setAnswer(getRandomAnswer());
            counter.setDate(date);
            list.add(counter);
            testCounterRepo.save(counter);
        });

        List<Counter> list2 = testCounterRepo.findAllByDate(date);

        assertThat(list2).isEqualTo(list);
    }
}