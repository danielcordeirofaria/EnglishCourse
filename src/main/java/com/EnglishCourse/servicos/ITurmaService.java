package com.EnglishCourse.servicos;

import com.EnglishCourse.model.Turma;
import org.springframework.http.ResponseEntity;

public interface ITurmaService {
    Object recuperarTurma();

    ResponseEntity<?> salvarTurma(Turma turma);

    Turma findByIdTurma(int idTurma);

    boolean deletarTurma(int idTurma);

    ResponseEntity<?> atualizarTurma(int idTurma, Turma turma);
}
