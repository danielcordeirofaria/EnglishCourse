package com.EnglishCourse.servicos;

import com.EnglishCourse.DAO.ResponsavelDAO;
import com.EnglishCourse.model.Responsavel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResponsavelServiceImpl implements IResponsavelService {

    @Autowired
    private ResponsavelDAO responsavelDAO;

    public Responsavel save(Responsavel responsavel) {
        return responsavelDAO.save(responsavel);
    }

    public Responsavel update(Responsavel responsavel) {
        return responsavelDAO.save(responsavel);
    }

    public Optional<Responsavel> findById(Integer id) {
        return responsavelDAO.findById(id);
    }

    public void deleteById(Integer id) {
        responsavelDAO.deleteById(id);
    }

}
