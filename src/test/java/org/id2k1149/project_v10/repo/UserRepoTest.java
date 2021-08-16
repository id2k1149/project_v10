package org.id2k1149.project_v10.repo;

import com.github.javafaker.Faker;
import org.id2k1149.project_v10.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepoTest {
    private final Faker faker = new Faker();

    @Autowired
    private UserRepo testUserRepo;

    @AfterEach
    void tearDown() {
        testUserRepo.deleteAll();
    }

    @Test
    void findByUsername() {
        User user1 = new User();
        String testName = faker.name().username();
        user1.setUsername(testName);
        testUserRepo.save(user1);

        User user2 = testUserRepo.findByUsername(testName);

        assertThat(user2).isEqualTo(user1);
    }
}