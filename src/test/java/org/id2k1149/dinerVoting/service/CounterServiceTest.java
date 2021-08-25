package org.id2k1149.dinerVoting.service;

import org.id2k1149.dinerVoting.model.Counter;
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
class CounterServiceTest {
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

    public static Counter getRandomCounter() {
        Counter counter = new Counter();
        counter.setId((long) new Random().nextInt(10));
        counter.setDate(LocalDate.now().minusDays(counter.getId()));
        counter.setDiner(getRandomDiner());
        counter.setVotes(new Random().nextInt(3));
        return counter;
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
        Counter counter1 = getRandomCounter();
        Counter counter2 = counterService.addCounter(counter1);
        assertThat(counter2).isEqualTo(counter1);
    }

    @Test
    void updateCounter() {
        Counter counter = getRandomCounter();
        long id = counter.getId();
        given(counterRepo.existsById(id)).willReturn(true);
        Counter counterToUpdate = getRandomCounter();
        counterToUpdate.setId(id);
        doReturn(counterToUpdate).when(counterRepo).getById(id);
        counterService.updateCounter(id, counter);
        assertThat(counterToUpdate.getDate()).isEqualTo(counter.getDate());
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