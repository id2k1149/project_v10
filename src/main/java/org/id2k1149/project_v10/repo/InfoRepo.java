package org.id2k1149.project_v10.repo;

import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.model.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface InfoRepo extends JpaRepository<Info, Long> {
    List<Info> findAllByDate(LocalDate date);
    List<Info> findAllByAnswer(Answer answer);
    Info findByDateAndAnswer(LocalDate date, Answer answer);
}