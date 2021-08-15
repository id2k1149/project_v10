package org.id2k1149.project_v10.repo;

import org.id2k1149.project_v10.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}