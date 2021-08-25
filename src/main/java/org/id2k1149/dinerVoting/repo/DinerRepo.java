package org.id2k1149.dinerVoting.repo;

import org.id2k1149.dinerVoting.model.Diner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface DinerRepo extends JpaRepository<Diner, Long> {
    Diner findDinerByTitle(String title);
}