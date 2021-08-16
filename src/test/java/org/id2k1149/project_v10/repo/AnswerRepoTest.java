package org.id2k1149.project_v10.repo;

import com.github.javafaker.Faker;
import org.id2k1149.project_v10.model.Answer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AnswerRepoTest {
    private final Faker faker = new Faker();

    @Autowired
    private AnswerRepo testRepo;

    @Test
    void findAnswerByTitle() {
        Answer answer1 = new Answer();
        String testTitle = faker.beer().name();
        answer1.setTitle(testTitle);
        testRepo.save(answer1);

        Answer answer2 = testRepo.findAnswerByTitle(testTitle);

        assertThat(answer2).isEqualTo(answer1);
    }
}