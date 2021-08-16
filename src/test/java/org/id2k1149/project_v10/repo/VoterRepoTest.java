package org.id2k1149.project_v10.repo;

import com.github.javafaker.Faker;
import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.model.Info;
import org.id2k1149.project_v10.model.User;
import org.id2k1149.project_v10.model.Voter;
import org.junit.jupiter.api.AfterEach;
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
        String testName = faker.name().username();
        user.setUsername(testName);
        testUserRepo.save(user);
        return user;
    }

    @Test
    void findByUserAndDate() {
        Voter voter1 = new Voter();
        LocalDate ld = getRandomDate();
        voter1.setDate(ld);
        Answer answer1 = getRandomAnswer();
        voter1.setAnswer(answer1);
        User user1 = getRandomUser();
        voter1.setUser(user1);
        testVoterRepo.save(voter1);

        Random random = new Random();
        int bound = random.nextInt(4) + 2;
        IntStream.range(0, bound).mapToObj(i -> new Voter()).forEach(voter -> {
            voter.setDate(getRandomDate());
            voter.setAnswer(getRandomAnswer());
            voter.setUser(getRandomUser());
            testVoterRepo.save(voter);
        });

        Optional<Voter> ov = testVoterRepo.findByUserAndDate(user1, ld);
        assertThat(ov.get()).isEqualTo(voter1);
    }

    @Test
    void findAllByUser() {
        User user1 = getRandomUser();
        List<Voter> list1 = new ArrayList<>();

        Random random = new Random();
        int bound = random.nextInt(4) + 2;
        IntStream.range(0, bound).mapToObj(i -> new Voter()).forEach(voter -> {
            voter.setDate(getRandomDate());
            voter.setAnswer(getRandomAnswer());
            voter.setUser(user1);
            list1.add(voter);
            testVoterRepo.save(voter);
        });

        random = new Random();
        bound = random.nextInt(4) + 2;
        IntStream.range(0, bound).mapToObj(i -> new Voter()).forEach(voter -> {
            voter.setDate(getRandomDate());
            voter.setAnswer(getRandomAnswer());
            voter.setUser(getRandomUser());
            testVoterRepo.save(voter);
        });

        List<Voter> list2 = testVoterRepo.findAllByUser(user1);
        assertThat(list2).isEqualTo(list1);
    }
}