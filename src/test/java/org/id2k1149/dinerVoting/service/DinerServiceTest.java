package org.id2k1149.dinerVoting.service;

import com.github.javafaker.Faker;
import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.repo.DinerRepo;
import org.id2k1149.dinerVoting.repo.MenuRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class DinerServiceTest {
    private DinerService dinerService;
    private AutoCloseable autoCloseable;

    @Mock
    private DinerRepo dinerRepo;
    @Mock
    private MenuRepo menuRepo;

    public static Diner getRandomDiner() {
        Diner diner = new Diner();
        diner.setId((long) new Random().nextInt(10));
        diner.setTitle(new Faker().company().name());
        return diner;
    }

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        dinerService = new DinerService(dinerRepo, menuRepo);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllDiners() {
        dinerService.getAllDiners();
        verify(dinerRepo).findAll();
    }

    @Test
    void getDiner() {
        Diner diner = getRandomDiner();
        long id = diner.getId();
        doReturn(Optional.of(diner)).when(dinerRepo).findById(id);
        Diner testDiner = dinerService.getDiner(id);
        assertThat(testDiner).isEqualTo(diner);
    }

    @Test
    void addDiner() {
        Diner diner1 = getRandomDiner();
        Diner diner2 = dinerService.addDiner(diner1);
        assertThat(diner2).isEqualTo(diner1);
    }

    @Test
    void updateDiner() {
        Diner diner = getRandomDiner();
        long id = diner.getId();
        given(dinerRepo.findById(id)).willReturn(Optional.of(diner));
        Diner dinerToUpdate = getRandomDiner();
        dinerToUpdate.setId(id);
        doReturn(dinerToUpdate).when(dinerRepo).getById(id);
        dinerService.updateDiner(diner, id);
        assertThat(dinerToUpdate.getTitle()).isEqualTo(diner.getTitle());
    }

    @Test
    void deleteDiner() {
        Diner diner = getRandomDiner();
        long id = diner.getId();
        given(dinerRepo.findById(id)).willReturn(Optional.of(diner));
        dinerService.deleteDiner(id);
        verify(dinerRepo).deleteById(id);
    }

    @Test
    void getAllMenuForDiner() {
    }

    @Test
    void getTodayMenuForDiner() {
    }
}