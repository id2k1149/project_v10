package org.id2k1149.dinerVoting.service;

import org.id2k1149.dinerVoting.model.VoiceCounter;
import org.id2k1149.dinerVoting.repo.DinerRepo;
import org.id2k1149.dinerVoting.repo.CounterRepo;
import org.id2k1149.dinerVoting.repo.MenuRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.id2k1149.dinerVoting.service.DinerServiceTest.getRandomDiner;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class VoiceCounterServiceTest {
    private CounterService counterService;
    private AutoCloseable autoCloseable;

    @Mock
    private CounterRepo counterRepo;
    @Mock
    private DinerRepo dinerRepo;
    @Mock
    private MenuRepo menuRepo;
    @Mock
    private VoterService voterService;

    public static VoiceCounter getRandomCounter() {
        VoiceCounter voiceCounter = new VoiceCounter();
        voiceCounter.setId((long) new Random().nextInt(10));
        voiceCounter.setDate(LocalDate.now().minusDays(voiceCounter.getId()));
        voiceCounter.setDiner(getRandomDiner());
        voiceCounter.setVotes(new Random().nextInt(3));
        return voiceCounter;
    }

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        counterService = new CounterService(counterRepo, dinerRepo, menuRepo, voterService);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getCounters() {
        counterService.getCounters();
        verify(counterRepo).findAll();
    }

    @Test
    void getCounter() {
        long id = getRandomCounter().getId();
        given(counterRepo.existsById(id)).willReturn(true);
        counterService.getCounter(id);
        verify(counterRepo).getById(id);
    }

    @Test
    void addCounter() {
        VoiceCounter voiceCounter1 = getRandomCounter();
        VoiceCounter voiceCounter2 = counterService.addCounter(voiceCounter1);
        assertThat(voiceCounter2).isEqualTo(voiceCounter1);
    }

    @Test
    void updateCounter() {
        VoiceCounter voiceCounter = getRandomCounter();
        long id = voiceCounter.getId();
        given(counterRepo.existsById(id)).willReturn(true);
        VoiceCounter voiceCounterToUpdate = getRandomCounter();
        voiceCounterToUpdate.setId(id);
        doReturn(voiceCounterToUpdate).when(counterRepo).getById(id);
        counterService.updateCounter(id, voiceCounter);
        assertThat(voiceCounterToUpdate.getDate()).isEqualTo(voiceCounter.getDate());
    }

    @Test
    void deleteCounter() {
        long id = getRandomCounter().getId();
        given(counterRepo.existsById(id)).willReturn(true);
        counterService.deleteCounter(id);
        verify(counterRepo).deleteById(id);
    }

    @Test
    void vote() {
    }

    @Test
    void getAllResults() {
    }

    @Test
    void getBestResult() {
    }

    @Test
    void voteForDiner() {
    }

    @Test
    void checkTodayCounter() {
    }
}