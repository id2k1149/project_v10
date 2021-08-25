package org.id2k1149.dinerVoting.repo;

import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MenuRepo extends JpaRepository<Menu, Long> {
    List<Menu> findAllByDate(LocalDate date);
    List<Menu> findAllByDiner(Diner diner);
    Menu findByDateAndDiner(LocalDate date, Diner diner);
}