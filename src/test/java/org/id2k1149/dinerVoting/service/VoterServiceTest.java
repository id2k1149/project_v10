package org.id2k1149.dinerVoting.service;

import org.id2k1149.dinerVoting.model.User;
import org.id2k1149.dinerVoting.model.Voter;
import org.id2k1149.dinerVoting.repo.CounterRepo;
import org.id2k1149.dinerVoting.repo.VoterRepo;
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
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.id2k1149.dinerVoting.service.DinerServiceTest.getRandomDiner;
import static org.id2k1149.dinerVoting.service.UserServiceTest.getRandomUser;
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
    private UserService userService;

    public Voter getRandomVoter() {
        Voter voter = new Voter();
        voter.setId((long) new Random().nextInt(10));
        voter.setDate(LocalDate.now().minusDays(voter.getId()));
        voter.setUser(getRandomUser());
        voter.setDiner(getRandomDiner());
        return voter;
    }

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        voterService = new VoterService(voterRepo, userService);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }


    @Test
    void addVoter() {
        Voter voter1 = getRandomVoter();
        Voter voter2 = voterService.addVoter(voter1);
        assertThat(voter2).isEqualTo(voter1);
    }

    @Test
    void deleteVoter() {
        Voter voter = getRandomVoter();
        long id = voter.getId();
        given(voterRepo.findById(id)).willReturn(Optional.of(voter));
        voterService.deleteVoter(id);
        verify(voterRepo).deleteById(id);
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void getVoterByUserAndDate() {
        User user = getRandomUser();
        doReturn(user).when(userService).findCurrentUser();
        voterService.getVoterByUserAndDate();
        verify(voterRepo).findByUserAndDate(user, LocalDate.now());
    }
}