package com.EnglishCourse.DAO;

import com.EnglishCourse.model.Horario;
import com.EnglishCourse.model.Turma;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public interface HorarioDAO extends CrudRepository<Horario, Integer> {



    Horario findByIdHorario(int idHorario);

    List<Horario> findByTurma_IdTurma(int idTurma);

}
