package org.id2k1149.project_v10.repo;

import com.github.javafaker.DateAndTime;
import com.github.javafaker.Faker;
import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.model.Counter;
import org.id2k1149.project_v10.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CounterRepoTest {
    private final Faker faker = new Faker();

    @Autowired
    private CounterRepo testCounterRepo;

    @Autowired
    private AnswerRepo testAnswerRepo;

    @Test
    void findByDateAndAnswer() {
        Answer answer1 = new Answer();
        String testTitle = faker.beer().name();
        answer1.setTitle(testTitle);
        testAnswerRepo.save(answer1);

        Counter counter1 = new Counter();
        counter1.setAnswer(answer1);
        counter1.setDate(LocalDate.now());
        testCounterRepo.save(counter1);

        Optional<Counter> counter2 = testCounterRepo.findByDateAndAnswer(LocalDate.now(), answer1);

        assertThat(counter2.get()).isEqualTo(counter1);
    }

    @Test
    void findByDate() {
        Random random = new Random();
        int bound = random.nextInt(4) + 2;
        LocalDate ld = new java.sql.Date(new Date().getTime()).toLocalDate();
        List<Counter> list1 = new ArrayList<>();
        IntStream.range(0, bound).mapToObj(i -> new Counter()).forEach(counter -> {
            Answer answer = new Answer();
            answer.setTitle(faker.beer().name());
            testAnswerRepo.save(answer);
            counter.setAnswer(answer);
            counter.setDate(new java.sql.Date(new Date().getTime()).toLocalDate());
            list1.add(counter);
            testCounterRepo.save(counter);
        });

        List<Counter> list2 = testCounterRepo.findByDate(ld);

        assertThat(list2).isEqualTo(list1);
    }
}