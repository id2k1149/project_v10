package org.id2k1149.dinerVoting.repo;

import com.github.javafaker.Faker;
import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.User;
import org.id2k1149.dinerVoting.model.Voter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class VoterRepoTest {

    @Autowired
    private VoterRepo testVoterRepo;
    @Autowired
    private DinerRepo testDinerRepo;
    @Autowired
    private UserRepo testUserRepo;

    @BeforeEach
    void setUp() {
        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> new Voter()).forEach(voter -> {
            voter.setDiner(getRandomDiner());
            voter.setDate(getRandomDate());
            voter.setUser(getRandomUser());
            testVoterRepo.save(voter);
        });
    }

    @AfterEach
    void tearDown() {
        testDinerRepo.deleteAll();
        testVoterRepo.deleteAll();
        testUserRepo.deleteAll();
    }

    public LocalDate getRandomDate() {
        long minDay = LocalDate.of(2021, 8, 1).toEpochDay();
        long maxDay = LocalDate.of(2021, 8, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    public Diner getRandomDiner() {
        Diner diner = new Diner();
        diner.setTitle(new Faker().beer().name());
        testDinerRepo.save(diner);
        return diner;
    }

    public User getRandomUser() {
        User user = new User();
        user.setUsername(new Faker().name().username());
        user.setPassword(new Faker().internet().password());
        testUserRepo.save(user);
        return user;
    }

    @Test
    void findByUserAndDate() {
        User user = getRandomUser();
        LocalDate date = getRandomDate();
        Voter voter = new Voter();
        voter.setUser(user);
        voter.setDate(date);
        testVoterRepo.save(voter);

        Optional<Voter> voter2 = testVoterRepo.findByUserAndDate(user, date);
        assertThat(voter2.get()).isEqualTo(voter);
    }

    @Test
    void findAllByUser() {
        User user = getRandomUser();
        List<Voter> list = new ArrayList<>();

        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> new Voter()).forEach(voter -> {
            voter.setDate(getRandomDate());
            voter.setDiner(getRandomDiner());
            voter.setUser(user);
            list.add(voter);
            testVoterRepo.save(voter);
        });

        List<Voter> list2 = testVoterRepo.findAllByUser(user);
        assertThat(list2).isEqualTo(list);
    }
}