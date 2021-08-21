package org.id2k1149.project_v10.service;

import org.id2k1149.project_v10.model.User;
import org.id2k1149.project_v10.model.Voter;
import org.id2k1149.project_v10.repo.CounterRepo;
import org.id2k1149.project_v10.repo.VoterRepo;
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
import static org.id2k1149.project_v10.service.UserServiceTest.getRandomUser;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class VoterServiceTest {
    private VoterService voterService;
    private AutoCloseable autoCloseable;

    @Mock
    private VoterRepo voterRepo;
    @Mock
    private CounterRepo counterRepo;
    @Mock
    private UserService userService;

    public Voter getRandomVoter() {
        Voter voter = new Voter();
        voter.setId((long) new Random().nextInt(10));
        voter.setDate(LocalDate.now().minusDays(voter.getId()));
        voter.setUser(getRandomUser());
        voter.setAnswer(getRandomAnswer());
        return voter;
    }

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        voterService = new VoterService(voterRepo, counterRepo, userService);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getVoters() {
        voterService.getVoters();
        verify(voterRepo).findAll();
    }

    @Test
    void getVoter() {
        long id = getRandomVoter().getId();
        given(voterRepo.existsById(id)).willReturn(true);
        voterService.getVoter(id);
        verify(voterRepo).getById(id);
    }

    @Test
    void addVoter() {
        Voter voter1 = getRandomVoter();
        Voter voter2 = voterService.addVoter(voter1);
        assertThat(voter2).isEqualTo(voter1);
    }

    @Test
    void updateVoter() {
        Voter voter = getRandomVoter();
        long id = voter.getId();
        given(voterRepo.existsById(id)).willReturn(true);
        Voter voterToUpdate = getRandomVoter();
        voterToUpdate.setId(id);
        doReturn(voterToUpdate).when(voterRepo).getById(id);
        voterService.updateVoter(id, voter);
        assertThat(voterToUpdate.getDate()).isEqualTo(voter.getDate());
    }

    @Test
    void deleteVoter() {
        long id = getRandomVoter().getId();
        given(voterRepo.existsById(id)).willReturn(true);
        voterService.deleteVoter(id);
        verify(voterRepo).deleteById(id);
    }

    @Test
    void checkVoter() {
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void checkUser() {
        User user = getRandomUser();
        doReturn(user).when(userService).findCurrentUser();
        voterService.checkUser();
        verify(voterRepo).findByUserAndDate(user, LocalDate.now());
    }
}