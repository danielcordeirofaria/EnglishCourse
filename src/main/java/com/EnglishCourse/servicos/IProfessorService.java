package com.EnglishCourse.servicos;

import com.EnglishCourse.model.Professor;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProfessorService  {

    List<Professor> recuperarProfessor();

    ResponseEntity<?> salvarProfessor(Professor professor);

    Professor buscarProfessor(int idProfessor);

    ResponseEntity<?> atualizarProfessor(int idProfessor, Professor professor);

    boolean deletarProfessor(int idProfessor);
}
