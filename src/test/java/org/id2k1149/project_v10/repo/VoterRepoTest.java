package org.id2k1149.project_v10.repo;

import com.github.javafaker.Faker;
import org.id2k1149.project_v10.model.*;
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
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VoterRepoTest {
    private final Faker faker = new Faker();
    @Autowired
    private VoterRepo testVoterRepo;
    @Autowired
    private AnswerRepo testAnswerRepo;
    @Autowired
    private UserRepo testUserRepo;

    @BeforeEach
    void setUp() {
        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> new Voter()).forEach(voter -> {
            voter.setAnswer(getRandomAnswer());
            voter.setDate(getRandomDate());
            voter.setUser(getRandomUser());
            testVoterRepo.save(voter);
        });
    }

    @AfterEach
    void tearDown() {
        testAnswerRepo.deleteAll();
        testVoterRepo.deleteAll();
        testUserRepo.deleteAll();
    }

    public LocalDate getRandomDate() {
        long minDay = LocalDate.of(2021, 8, 1).toEpochDay();
        long maxDay = LocalDate.of(2021, 8, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    public Answer getRandomAnswer() {
        Answer answer = new Answer();
        answer.setTitle(faker.beer().name());
        testAnswerRepo.save(answer);
        return answer;
    }

    public User getRandomUser() {
        User user = new User();
        user.setUsername(faker.name().username());
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
            voter.setAnswer(getRandomAnswer());
            voter.setUser(user);
            list.add(voter);
            testVoterRepo.save(voter);
        });

        List<Voter> list2 = testVoterRepo.findAllByUser(user);
        assertThat(list2).isEqualTo(list);
    }
}