package com.EnglishCourse.servicos;

import com.EnglishCourse.model.Alunos;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public interface IAlunosService {

    ResponseEntity<?> salvarAluno(Alunos aluno);

    Object recuperarAlunos();

    Alunos buscarAluno(int idAlunos);

    ResponseEntity<?> atualizarAluno(int idAlunoMatricula, Alunos aluno);

    List<Alunos> buscarAlunosPorTurma(int idTurma);

    ResponseEntity<?> deletarAluno(int idAlunos);

    ResponseEntity<?> atualizarStatusAluno(int idAlunoMatricula, Alunos aluno);

}
