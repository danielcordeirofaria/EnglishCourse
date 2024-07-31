package com.EnglishCourse.servicos;

import com.EnglishCourse.model.Alunos;
import org.springframework.http.ResponseEntity;

public interface IAlunosService {

    ResponseEntity<?> salvarAluno(Alunos aluno);

    Object recuperarAlunos();

    Alunos buscarAluno(int idAlunos);

    ResponseEntity<?> atualizarAluno(int idAlunoMatricula, Alunos aluno);

    ResponseEntity<?> deletarAluno(int idAlunos);

    ResponseEntity<?> atualizarStatusAluno(int idAlunoMatricula, Alunos aluno);
}
