package org.id2k1149.dinerVoting.repo;

import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CounterRepo extends JpaRepository<Counter, Long> {
    Optional<Counter> findByDateAndDiner(LocalDate localDate, Diner diner);
    Optional<Counter> getFirstByDate(LocalDate localDate);
    List<Counter> findAllByDate(LocalDate localDate);
}