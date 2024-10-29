package com.EnglishCourse.DAO;

import com.EnglishCourse.model.Alunos;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface AlunosDAO extends CrudRepository<Alunos, Integer> {

    boolean existsByCpf(String cpf);

    List<Alunos> findByTurmaIdTurma(int idTurma);
}
