package com.EnglishCourse.servicos;

import com.EnglishCourse.model.Horario;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public interface IHorarioService {


    ResponseEntity<?> atualizarHorario(int idHorario, Horario horario);
    
    Object recuperarHorarios();

    boolean deletarHorario(int idHorario);

    ResponseEntity<?> cadastrarNovoHorario(Horario horario);

    ResponseEntity<?> retornarHorariosPeloIdTurma(int idTurma);
}
