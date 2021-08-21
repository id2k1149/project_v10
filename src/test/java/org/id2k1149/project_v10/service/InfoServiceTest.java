package org.id2k1149.project_v10.service;

import org.id2k1149.project_v10.model.Info;
import org.id2k1149.project_v10.repo.AnswerRepo;
import org.id2k1149.project_v10.repo.CounterRepo;
import org.id2k1149.project_v10.repo.InfoRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.id2k1149.project_v10.service.AnswerServiceTest.getRandomAnswer;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class InfoServiceTest {
    private InfoService infoService;
    private AutoCloseable autoCloseable;

    @Mock
    private InfoRepo infoRepo;
    @Mock
    private AnswerRepo answerRepo;
    @Mock
    private CounterRepo counterRepo;

    public static Info getRandomInfo() {
        Info info = new Info();
        info.setId((long) new Random().nextInt(10));
        info.setDate(LocalDate.now().minusDays(info.getId()));
        info.setAnswer(getRandomAnswer());
        return info;
    }

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        infoService = new InfoService(infoRepo, answerRepo, counterRepo);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllInfo() {
        infoService.getAllInfo();
        verify(infoRepo).findAll();
    }

    @Test
    void getInfo() {
        long id = getRandomInfo().getId();
        given(infoRepo.existsById(id)).willReturn(true);
        infoService.getInfo(id);
        verify(infoRepo).getById(id);
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void addInfo() {
        Info info1 = getRandomInfo();
        long id = info1.getAnswer().getId();
        given(answerRepo.existsById(id)).willReturn(true);
        Info info2 = infoService.addInfo(info1, id);
        assertThat(info2).isEqualTo(info1);
    }

    @Test
    void updateInfo() {
        Info info = getRandomInfo();
        System.out.println("info ->" + info);
        long id = info.getId();
        given(infoRepo.existsById(id)).willReturn(true);
        Info infoToUpdate = getRandomInfo();
        infoToUpdate.setId(id);
        doReturn(infoToUpdate).when(infoRepo).getById(id);
        infoService.updateInfo(info, id);
        assertThat(infoToUpdate.getDate()).isEqualTo(info.getDate());
    }

    @Test
    void deleteInfo() {
        long id = getRandomInfo().getId();
        given(infoRepo.existsById(id)).willReturn(true);
        infoService.deleteInfo(id);
        verify(infoRepo).deleteById(id);
    }

    @Test
    void getByDate() {
        LocalDate localDate = LocalDate.now();
        infoService.getByDate(localDate);
        verify(infoRepo).findAllByDate(localDate);
    }

    @Test
    void getAnswersInfoByDate() {
        LocalDate localDate = LocalDate.now();
        infoService.getAnswersInfoByDate(localDate);
        verify(infoRepo).findAllByDate(localDate);

    }
}