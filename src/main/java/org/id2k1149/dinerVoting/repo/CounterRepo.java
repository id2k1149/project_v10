package org.id2k1149.dinerVoting.repo;

import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.VoiceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CounterRepo extends JpaRepository<VoiceCounter, Long> {
    Optional<VoiceCounter> findByDateAndDiner(LocalDate localDate, Diner diner);
    Optional<VoiceCounter> getFirstByDate(LocalDate localDate);
    List<VoiceCounter> findAllByDate(LocalDate localDate);
}