package org.id2k1149.project_v10.repo;

import org.id2k1149.project_v10.model.User;
import org.id2k1149.project_v10.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoterRepo extends JpaRepository<Voter, Long> {
    Optional<Voter> findByUserAndDate(User user, LocalDate localDate);
    List<Voter> getByUser(User user);
}