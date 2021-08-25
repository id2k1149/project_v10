package org.id2k1149.dinerVoting.repo;

import com.github.javafaker.Faker;
import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.VoiceCounter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class VoiceCounterRepoTest {

    @Autowired
    private CounterRepo testCounterRepo;

    @Autowired
    private DinerRepo testDinerRepo;

    public Diner getRandomDiner() {
        Diner diner = new Diner();
        diner.setTitle(new Faker().beer().name());
        testDinerRepo.save(diner);
        return diner;
    }

    public LocalDate getRandomDate() {
        long minDay = LocalDate.of(2021, 7, 1).toEpochDay();
        long maxDay = LocalDate.of(2021, 7, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    @BeforeEach
    void setUp() {
        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> new VoiceCounter()).forEach(counter -> {
            counter.setDiner(getRandomDiner());
            counter.setDate(getRandomDate());
            testCounterRepo.save(counter);
        });
    }

    @AfterEach
    void tearDown() {
        testDinerRepo.deleteAll();
        testCounterRepo.deleteAll();
    }

    @Test
    void findByDateAndDiner() {
        Diner diner = getRandomDiner();
        LocalDate date = LocalDate.now();
        VoiceCounter voiceCounter = new VoiceCounter();
        voiceCounter.setDiner(diner);
        voiceCounter.setDate(date);
        testCounterRepo.save(voiceCounter);

        Optional<VoiceCounter> counter2 = testCounterRepo.findByDateAndDiner(date, diner);

        assertThat(counter2.get()).isEqualTo(voiceCounter);
    }

    @Test
    void findAllByDate() {
        LocalDate date = LocalDate.now();
        List<VoiceCounter> list = new ArrayList<>();
        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> new VoiceCounter()).forEach(counter -> {
            counter.setDiner(getRandomDiner());
            counter.setDate(date);
            list.add(counter);
            testCounterRepo.save(counter);
        });

        List<VoiceCounter> list2 = testCounterRepo.findAllByDate(date);

        assertThat(list2).isEqualTo(list);
    }
}