package com.EnglishCourse.servicos;

import com.EnglishCourse.model.Horario;
import org.springframework.http.ResponseEntity;

public interface IHorarioService {

    ResponseEntity<?> atualizarHorario(int idHorario, Horario horario);
    
    Object recuperarHorarios();

    boolean deletarHorario(int idHorario);
}
