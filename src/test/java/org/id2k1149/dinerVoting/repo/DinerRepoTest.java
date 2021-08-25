package org.id2k1149.dinerVoting.repo;

import com.github.javafaker.Faker;
import org.id2k1149.dinerVoting.model.Diner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class DinerRepoTest {

    @Autowired
    private DinerRepo testDinerRepo;

    public Diner getRandomDiner() {
        Diner diner = new Diner();
        diner.setTitle(new Faker().company().name() + "Co");
        testDinerRepo.save(diner);
        return diner;
    }

    @BeforeEach
    void setUp() {
        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> getRandomDiner())
                .forEach(answer -> testDinerRepo.save(answer));
    }

    @AfterEach
    void tearDown() {
        testDinerRepo.deleteAll();
    }

    @Test
    void findDinerByName() {
        Diner diner = getRandomDiner();
        String testTitle = diner.getTitle();

        testDinerRepo.save(diner);

        Diner diner2 = testDinerRepo.findDinerByTitle(testTitle);
        assertThat(diner2).isEqualTo(diner);
    }
}