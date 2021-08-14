package org.id2k1149.project_v10.service;

import org.id2k1149.project_v10.model.Role;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Rollback(false)
public class UserServiceImplTest {
    private final Faker faker = new Faker();

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void saveRoleTest() {
//        userService.saveRole(new Role(null,"ROLE_USER"));
//        userService.saveRole(new Role(null,"ROLE_ADMIN"));
    }


}
