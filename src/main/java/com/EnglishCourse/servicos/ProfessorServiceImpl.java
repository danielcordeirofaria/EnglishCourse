package com.EnglishCourse.servicos;

import com.EnglishCourse.DAO.EnderecoDAO;
import com.EnglishCourse.DAO.ProfessorDAO;
import com.EnglishCourse.model.Endereco;
import com.EnglishCourse.model.Professor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProfessorServiceImpl implements IProfessorService{

    @Autowired
    private ProfessorDAO professorDAO;

    @Autowired
    private EnderecoDAO enderecoDAO;

    private static final Logger logger = LoggerFactory.getLogger(ProfessorServiceImpl.class);

    @Override
    public List<Professor> recuperarProfessor() {
        try {
            return (List<Professor>) professorDAO.findAll();
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao recuperar os professores.", e);
            return Collections.emptyList(); // Retorna uma lista vazia em caso de erro
        }
    }

    @Override
    public ResponseEntity<?> salvarProfessor(Professor newProfessor) {
        try {
            logger.info("Tentando cadastrar um novo professor: {}", newProfessor);

            if (professorDAO.existsByCpf(newProfessor.getCpf())) {
                logger.error("CPF ou CNPJ já cadastrado.");
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "CPF ou CNPJ já está sendo usado."));
            }

            String validationMessage = validateProfessor(newProfessor);
            if (validationMessage != null) {
                logger.error(validationMessage);
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", validationMessage));
            }

            enderecoDAO.save(newProfessor.getEndereco());
            Professor savedProfessor = professorDAO.save(newProfessor);
            logger.info("Professor cadastrado com sucesso");

            return ResponseEntity.ok(Collections.singletonMap("message", "Professor cadastrado com sucesso.")); // Retorna a mensagem de sucesso
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao cadastrar o professor.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Ocorreu um erro ao cadastrar o professor."));
        }
    }

    private String validateProfessor(Professor professor) {
        if (professor.getNome() == null || professor.getNome().isEmpty()) {
            return "Nome do professor é obrigatório.";
        }

        Endereco endereco = professor.getEndereco();
        if (endereco == null) {
            return "Endereço do professor é obrigatório.";
        }
        if (endereco.getLogradouro() == null || endereco.getLogradouro().isEmpty()) {
            return "Logradouro inválido.";
        }
        if (endereco.getNumero() == null || endereco.getNumero().isEmpty()) {
            return "Insira um valor para o número. Caso não tenha, envie S/A.";
        }
        if (endereco.getBairro() == null || endereco.getBairro().isEmpty()) {
            return "Valor para bairro inválido.";
        }
        if (endereco.getCidade() == null || endereco.getCidade().isEmpty()) {
            return "Insira a Cidade do professor.";
        }
        if (endereco.getEstado() == null || endereco.getEstado().isEmpty()) {
            return "Insira o Estado do professor.";
        }
        if (endereco.getCep() == null || endereco.getCep().isEmpty()) {
            return "Valor para CEP inválido.";
        }

        if (professor.getCpf() == null || professor.getCpf().isEmpty()) {
            return "CPF do professor é obrigatório.";
        }
        if (professor.getEmail() == null || professor.getEmail().isEmpty()) {
            return "Email do professor é obrigatório.";
        }
        if (professor.getLogin() == null || professor.getLogin().isEmpty()) {
            return "Login do professor é obrigatório.";
        }
        if (professor.getPassword() == null || professor.getPassword().isEmpty()) {
            return "Senha do professor é obrigatória.";
        }
        if (professor.getWhatsapp() == null || professor.getWhatsapp().isEmpty()) {
            return "Whatsapp do professor é obrigatório.";
        }

        return null;
    }

    @Override
    public Professor buscarProfessor(int idProfessor) {
        try {
            return professorDAO.findById(idProfessor).orElse(null);
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao buscar o professor.", e);
            return null;
        }
    }

    @Override
    public ResponseEntity<?> atualizarProfessor(int idProfessor, Professor professor) {
            try {
                logger.info("Tentando atualizar aluno com idProfessor: {}", idProfessor);

                Professor professorExistente = professorDAO.findById(idProfessor).orElse(null);
                if (professorExistente != null) {
                    logger.info("Aluno encontrado: {}", professorExistente);

                    updateProfessor(professorExistente, professor);
                    Professor professorAtualizado = professorDAO.save(professorExistente);

                    logger.info("Aluno atualizado com sucesso: {}", professorAtualizado);
                    return ResponseEntity.ok(professorAtualizado);
                } else {
                    logger.error("Aluno não encontrado para atualização.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
            } catch (Exception e) {
                logger.error("Ocorreu um erro ao atualizar o aluno.", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

    private void updateProfessor(Professor professorExistente, Professor professorNovo) {
        professorExistente.setNome(professorNovo.getNome());
        professorExistente.setCpf(professorNovo.getCpf());
        professorExistente.setEndereco(professorNovo.getEndereco());
        professorExistente.setEmail(professorNovo.getEmail());
        professorExistente.setLogin(professorNovo.getLogin());
        professorExistente.setPassword(professorNovo.getPassword());
        professorExistente.setWhatsapp(professorNovo.getWhatsapp());
        professorExistente.setRoles(professorNovo.getRoles());

    }

    @Override
    public boolean deletarProfessor(int idProfessor) {
        if (professorDAO.existsById(idProfessor)) {
            professorDAO.deleteById(idProfessor);
            return true;
        } else {
            return false;
        }
    }
}

