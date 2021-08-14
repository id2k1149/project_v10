package org.id2k1149.project_v10.repo;

import com.github.javafaker.Faker;
import org.id2k1149.project_v10.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Rollback(false)
public class UserRepoTest {
    private final Faker faker = new Faker();

    @Autowired
    private RoleRepo roleRepo;

    @Test
    public void saveRole() {
        roleRepo.save(new Role(null,"ROLE_ADMIN"));
        roleRepo.save(new Role(null,"ROLE_USER"));
    }


}
