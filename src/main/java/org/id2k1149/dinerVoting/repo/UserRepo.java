package org.id2k1149.dinerVoting.repo;

import org.id2k1149.dinerVoting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}