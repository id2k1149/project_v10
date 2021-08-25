package org.id2k1149.dinerVoting.repo;

import com.github.javafaker.Faker;
import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.Menu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class MenuRepoTest {

    @Autowired
    private MenuRepo testMenuRepo;
    @Autowired
    private DinerRepo testDinerRepo;

    @BeforeEach
    void setUp() {
        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> new Menu()).forEach(info -> {
            info.setDiner(getRandomDiner());
            info.setDate(getRandomDate());
            testMenuRepo.save(info);
        });
    }

    @AfterEach
    void tearDown() {
        testDinerRepo.deleteAll();
        testMenuRepo.deleteAll();
    }

    public LocalDate getRandomDate() {
        long minDay = LocalDate.of(2021, 7, 1).toEpochDay();
        long maxDay = LocalDate.of(2021, 7, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    public Diner getRandomDiner() {
        Diner diner = new Diner();
        diner.setTitle(new Faker().beer().name());
        testDinerRepo.save(diner);
        return diner;
    }

    @Test
    void findAllMenuByDate() {
        LocalDate date = LocalDate.now();
        List<Menu> list = new ArrayList<>();

        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> new Menu()).forEach(info -> {
            info.setDate(date);
            info.setDiner(getRandomDiner());
            list.add(info);
            testMenuRepo.save(info);
        });
        List<Menu> list2 = testMenuRepo.findAllByDate(date);
        assertThat(list2).isEqualTo(list);
    }

    @Test
    void findAllByDiner() {
        Diner diner = getRandomDiner();
        List<Menu> list = new ArrayList<>();

        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> new Menu()).forEach(menu -> {
            menu.setDate(getRandomDate());
            menu.setDiner(diner);
            list.add(menu);
            testMenuRepo.save(menu);
        });

        List<Menu> list2 = testMenuRepo.findAllByDiner(diner);
        assertThat(list2).isEqualTo(list);
    }

    @Test
    void findByDateAndDiner() {
        LocalDate date = getRandomDate();
        Diner diner = getRandomDiner();
        Menu menu = new Menu();
        menu.setDate(date);
        menu.setDiner(diner);
        testMenuRepo.save(menu);

        Menu menu2 = testMenuRepo.findByDateAndDiner(date, diner);
        assertThat(menu2).isEqualTo(menu);
    }
}