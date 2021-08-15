package org.id2k1149.project_v10.repo;

import org.id2k1149.project_v10.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AnswerRepo extends JpaRepository<Answer, Long> {
    Answer findAnswerByTitle(String title);
}