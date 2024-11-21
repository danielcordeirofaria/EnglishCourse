package com.EnglishCourse.DAO;

import com.EnglishCourse.model.Responsavel;
import org.springframework.data.repository.CrudRepository;

public interface ResponsavelDAO extends CrudRepository<Responsavel, Integer> {

    Responsavel findByCpf(String cpf);
}
