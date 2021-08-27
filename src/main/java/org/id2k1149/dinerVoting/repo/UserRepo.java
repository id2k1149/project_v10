package org.id2k1149.dinerVoting.repo;

import org.id2k1149.dinerVoting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}