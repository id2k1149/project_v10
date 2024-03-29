package org.id2k1149.dinerVoting.repo;

import com.github.javafaker.Faker;
import org.id2k1149.dinerVoting.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepoTest {

    @Autowired
    private UserRepo testUserRepo;

    public User getRandomUser() {
        User user = new User();
        user.setUsername(new Faker().name().username());
        user.setPassword(new Faker().internet().password());
        return user;
    }

    @BeforeEach
    void setUp() {
        IntStream.range(0, new Random().nextInt(4) + 2)
                .mapToObj(i -> getRandomUser())
                .forEach(user -> testUserRepo.save(user));
    }

    @AfterEach
    void tearDown() {
        testUserRepo.deleteAll();
    }

    @Test
    void findByUsername() {
        User user = getRandomUser();
        String testName = user.getUsername();
        testUserRepo.save(user);
        User user2 = testUserRepo.findByUsername(testName);
        assertThat(user2).isEqualTo(user);
    }
}