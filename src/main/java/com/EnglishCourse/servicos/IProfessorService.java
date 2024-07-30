package com.EnglishCourse.servicos;

import com.EnglishCourse.model.Professor;
import org.springframework.http.ResponseEntity;

public interface IProfessorService  {

    Object recuperarProfessor();

    ResponseEntity<?> salvarProfessor(Professor professor);
}
