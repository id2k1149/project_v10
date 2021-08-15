package org.id2k1149.project_v10.repo;

import com.github.javafaker.Faker;
import org.id2k1149.project_v10.model.Answer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.stream.IntStream;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Rollback(false)
public class AnswerRepoTest {

    private final Faker faker = new Faker();

    @Autowired
    private AnswerRepo answerRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void createAnswers() {
        IntStream.range(0, 4)
                .mapToObj(i -> new Answer(faker.beer().name())).forEach(answer -> entityManager.persist(answer));
    }

    @Test
    public void deleteAllAnswers() {
        answerRepo.deleteAll();
    }















}
