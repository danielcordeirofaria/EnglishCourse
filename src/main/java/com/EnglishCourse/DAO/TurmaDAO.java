package com.EnglishCourse.DAO;

import com.EnglishCourse.model.Turma;
import org.springframework.data.repository.CrudRepository;

public interface TurmaDAO extends CrudRepository<Turma, Integer> {

    boolean existsByIdTurma(int idTurma);

    boolean existsByNomeTurma(String nomeTurma);

    Turma findByIdTurma(int idTurma);
}
