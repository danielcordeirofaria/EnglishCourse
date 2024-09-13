package com.EnglishCourse.servicos;

import com.EnglishCourse.DTO.TurmaDTO;
import com.EnglishCourse.model.Turma;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITurmaService {
    List<TurmaDTO> recuperarTurmas();

    ResponseEntity<?> salvarTurma(Turma turma);

    boolean deletarTurma(int idTurma);

    ResponseEntity<?> atualizarTurma(int idTurma, Turma turma);

    TurmaDTO retornarTurmaPorId(int idTurma);
}
