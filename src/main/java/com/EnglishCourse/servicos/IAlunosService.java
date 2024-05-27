package com.EnglishCourse.servicos;

import com.EnglishCourse.model.Alunos;
import org.springframework.http.ResponseEntity;

public interface IAlunosService {
    ResponseEntity<?> cadastrarAluno(Alunos aluno);

    Object recuperarAlunos();

    Alunos buscarAluno(int idAlunos);

    ResponseEntity<?> atualizarAluno(Alunos aluno);

    ResponseEntity<?> deletarAluno(int idAlunos);
}
