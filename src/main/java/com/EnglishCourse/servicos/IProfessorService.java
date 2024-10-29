package com.EnglishCourse.servicos;

import com.EnglishCourse.model.Alunos;
import com.EnglishCourse.model.Professor;
import org.springframework.http.ResponseEntity;

public interface IProfessorService  {

    Object recuperarProfessor();

    ResponseEntity<?> salvarProfessor(Professor professor);

    Professor buscarProfessor(int idProfessor);

    ResponseEntity<?> atualizarProfessor(int idProfessor, Professor professor);

    boolean deletarProfessor(int idProfessor);
}
