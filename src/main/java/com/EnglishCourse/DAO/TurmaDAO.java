package com.EnglishCourse.DAO;

import com.EnglishCourse.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaDAO extends JpaRepository<Turma, Integer> {

    boolean existsByIdTurma(int idTurma);

    boolean existsByNomeTurma(String nomeTurma);

    Turma findByIdTurma(int idTurma);

}
