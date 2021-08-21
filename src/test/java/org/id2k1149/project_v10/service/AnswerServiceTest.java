package org.id2k1149.project_v10.service;

import com.github.javafaker.Faker;
import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.repo.AnswerRepo;
import org.id2k1149.project_v10.repo.InfoRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {
    private AnswerService answerService;
    private AutoCloseable autoCloseable;

    @Mock
    private AnswerRepo answerRepo;
    @Mock
    private InfoRepo infoRepo;

    public static Answer getRandomAnswer() {
        Answer answer = new Answer();
        answer.setId((long) new Random().nextInt(10));
        answer.setTitle(new Faker().company().name());
        return answer;
    }

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        answerService = new AnswerService(answerRepo, infoRepo);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAnswers() {
        answerService.getAnswers();
        verify(answerRepo).findAll();
    }

    @Test
    void getAnswer() {
        long id = getRandomAnswer().getId();
        given(answerRepo.existsById(id)).willReturn(true);
        answerService.getAnswer(id);
        verify(answerRepo).getById(id);
    }

    @Test
    void addAnswer() {
        Answer answer1 = getRandomAnswer();
        Answer answer2 = answerService.addAnswer(answer1);
        assertThat(answer2).isEqualTo(answer1);
    }

    @Test
    void updateAnswer() {
        Answer answer = getRandomAnswer();
        long id = answer.getId();
        given(answerRepo.existsById(id)).willReturn(true);
        Answer answerToUpdate = getRandomAnswer();
        answerToUpdate.setId(id);
        doReturn(answerToUpdate).when(answerRepo).getById(id);
        answerService.updateAnswer(answer, id);
        assertThat(answerToUpdate.getTitle()).isEqualTo(answer.getTitle());
    }

    @Test
    void deleteAnswer() {
        long id = getRandomAnswer().getId();
        given(answerRepo.existsById(id)).willReturn(true);
        answerService.deleteAnswer(id);
        verify(answerRepo).deleteById(id);
    }

    @Test
    void getAllInfoForAnswer() {
    }

    @Test
    void getTodayInfoForAnswer() {
    }
}