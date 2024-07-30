package com.EnglishCourse.DAO;

import com.EnglishCourse.model.Professor;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorDAO extends CrudRepository<Professor, Integer> {
    boolean existsByIdProfessor(int IdProfessor);

    boolean existsByCpfCnpj(String cpfCnpj);
}
