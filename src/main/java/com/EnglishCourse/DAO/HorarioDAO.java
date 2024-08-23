package com.EnglishCourse.DAO;

import com.EnglishCourse.model.Horario;
import org.springframework.data.repository.CrudRepository;

public interface HorarioDAO extends CrudRepository<Horario, Integer> {

    Horario findByIdHorario(int idHorario);
}
