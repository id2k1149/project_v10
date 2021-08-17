package org.id2k1149.project_v10.repo;

import com.github.javafaker.Faker;
import org.id2k1149.project_v10.model.Answer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AnswerRepoTest {
    private final Faker faker = new Faker();

    @Autowired
    private AnswerRepo testAnswerRepo;

    public Answer getRandomAnswer() {
        Answer answer = new Answer();
        answer.setTitle(faker.beer().name());
        testAnswerRepo.save(answer);
        return answer;
    }

    @BeforeEach
    void setUp() {
        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> getRandomAnswer())
                .forEach(answer -> testAnswerRepo.save(answer));
    }

    @AfterEach
    void tearDown() {
        testAnswerRepo.deleteAll();
    }

    @Test
    void findAnswerByTitle() {
        Answer answer = getRandomAnswer();
        String testTitle = answer.getTitle();
        testAnswerRepo.save(answer);

        Answer answer2 = testAnswerRepo.findAnswerByTitle(testTitle);
        assertThat(answer2).isEqualTo(answer);
    }
}