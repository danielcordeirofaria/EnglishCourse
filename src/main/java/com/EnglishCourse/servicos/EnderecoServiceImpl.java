package com.EnglishCourse.servicos;

import com.EnglishCourse.DAO.AlunosDAO;
import com.EnglishCourse.DAO.EnderecoDAO;
import com.EnglishCourse.model.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnderecoServiceImpl implements IEnderecoService{

    @Autowired
    private EnderecoDAO enderecoDAO;

    public Endereco save(Endereco endereco) {
        return enderecoDAO.save(endereco);
    }

    public Endereco update(Endereco endereco) {
        return enderecoDAO.save(endereco);
    }

    public Optional<Endereco> findById(Integer id) {
        return enderecoDAO.findById(id);
    }

    public void deleteById(Integer id) {
        enderecoDAO.deleteById(id);
    }
}
