package com.EnglishCourse.servicos;

import com.EnglishCourse.DAO.AlunosDAO;
import com.EnglishCourse.DAO.EnderecoDAO;
import com.EnglishCourse.DAO.ProfessorDAO;
import com.EnglishCourse.model.Alunos;
import com.EnglishCourse.model.Endereco;
import com.EnglishCourse.model.Professor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class AlunosServiceImpl implements IAlunosService {

    @Autowired
    private AlunosDAO alunosDAO;

    @Autowired
    private EnderecoDAO enderecoDAO;

    @Autowired
    private ProfessorDAO professorDAO;

    private static final Logger logger = LoggerFactory.getLogger(AlunosServiceImpl.class);

    @Override
    public ResponseEntity<?> salvarAluno(Alunos newAluno) {
        try {
            logger.info("Tentando cadastrar um novo aluno: {}", newAluno);

            if (alunosDAO.existsByCpf(newAluno.getCpf())) {
                logger.error("CPF já cadastrado.");
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "CPF já está sendo usado."));
            }

            ResponseEntity<?> validationResponse = validateAluno(newAluno);
            if (validationResponse != null) {
                return validationResponse;
            }

            enderecoDAO.save(newAluno.getEndereco());
            Alunos savedAluno = alunosDAO.save(newAluno);
            logger.info("Aluno cadastrado com sucesso");

            return ResponseEntity.ok(Collections.singletonMap("message", "Aluno cadastrado com sucesso.")); // Retorna a mensagem de sucesso
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao cadastrar o aluno.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Ocorreu um erro ao cadastrar o aluno."));
        }
    }

    private ResponseEntity<?> validateAluno(Alunos aluno) {
        if (aluno.getNome() == null || aluno.getNome().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Nome do aluno é obrigatório."));
        }

        Endereco endereco = aluno.getEndereco();
        if (endereco == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Endereço do aluno é obrigatório."));
        }
        if (endereco.getLogradouro() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Logradouro inválido."));
        }
        if (endereco.getNumero() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Insira um valor para o número. Caso não tenha, envie S/A."));
        }
        if (endereco.getBairro() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Valor para bairro inválido."));
        }
        if (endereco.getCidade() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Insira a Cidade do aluno."));
        }
        if (endereco.getEstado() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Insira o Estado do aluno."));
        }
        if (endereco.getCep() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Valor para CEP inválido."));
        }

        if (aluno.getDataDeNascimento() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Data de nascimento do aluno é obrigatória."));
        }
        if (aluno.getCpf() == null || aluno.getCpf().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "CPF do aluno é obrigatório."));
        }
        if (aluno.getEmail() == null || aluno.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email do aluno é obrigatório."));
        }
        if (aluno.getFormacao() == null || aluno.getFormacao().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Formação do aluno é obrigatória."));
        }
        if (aluno.getModuloFeito() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Módulo feito pelo aluno é obrigatório."));
        }
        if (aluno.getNivel() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Nível do aluno é obrigatório."));
        }
        if (aluno.getProfissao() == null || aluno.getProfissao().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Profissão do aluno é obrigatória."));
        }

        Professor professor = aluno.getProfessor();
        if (professor == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Professor não fornecido."));
        }

        int idProfessor = professor.getIdProfessor();
        if (idProfessor <= 0) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "ID do professor não fornecido ou inválido."));
        }

        // Verificar se o professor com o ID fornecido existe
        if (!professorDAO.existsByIdProfessor(idProfessor)) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Professor com ID fornecido não encontrado."));
        }

        return null;
    }

    @Override
    public Object recuperarAlunos() {
        try {
            return alunosDAO.findAll();
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao recuperar os alunos.", e);
            return Collections.singletonMap("message", "Ocorreu um erro ao recuperar os alunos.");
        }
    }

    @Override
    public Alunos buscarAluno(int idAlunos) {
        try {
            return alunosDAO.findById(idAlunos).orElse(null);
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao buscar o aluno.", e);
            return null;
        }
    }

    @Override
    public ResponseEntity<Alunos> atualizarAluno(int idAlunoMatricula, Alunos aluno) {
        try {
            logger.info("Tentando atualizar aluno com idMatricula: {}", idAlunoMatricula);

            Alunos alunoExistente = alunosDAO.findById(idAlunoMatricula).orElse(null);
            if (alunoExistente != null) {
                logger.info("Aluno encontrado: {}", alunoExistente);

                updateAluno(alunoExistente, aluno);
                Alunos alunoAtualizado = alunosDAO.save(alunoExistente);

                logger.info("Aluno atualizado com sucesso: {}", alunoAtualizado);
                return ResponseEntity.ok(alunoAtualizado);
            } else {
                logger.error("Aluno não encontrado para atualização.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao atualizar o aluno.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    private void updateAluno(Alunos alunoExistente, Alunos alunoNovo) {
        alunoExistente.setNome(alunoNovo.getNome());
        alunoExistente.setEndereco(alunoNovo.getEndereco());
        alunoExistente.setDataDeNascimento(alunoNovo.getDataDeNascimento());
        alunoExistente.setCpf(alunoNovo.getCpf());
        alunoExistente.setEmail(alunoNovo.getEmail());
        alunoExistente.setFormacao(alunoNovo.getFormacao());
        alunoExistente.setModuloFeito(alunoNovo.getModuloFeito());
        alunoExistente.setNivel(alunoNovo.getNivel());
        alunoExistente.setStatus(alunoNovo.getStatus());
    }

    @Override
    public ResponseEntity<?> atualizarStatusAluno(int idAlunoMatricula, Alunos aluno) {
        try {
            logger.info("Tentando atualizar aluno com idMatricula: {}", idAlunoMatricula);

            Alunos alunoExistente = alunosDAO.findById(idAlunoMatricula).orElse(null);
            if (alunoExistente != null) {
                logger.info("Aluno encontrado: {}", alunoExistente);

                alunoExistente.setStatus(aluno.getStatus());
                Alunos alunoAtualizado = alunosDAO.save(alunoExistente);

                logger.info("Aluno atualizado com sucesso: {}", alunoAtualizado);
                return ResponseEntity.ok(alunoAtualizado);
            } else {
                logger.error("Aluno não encontrado para atualização.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao atualizar o aluno.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<?> deletarAluno(int idAlunos) {
        try {
            Alunos alunoExistente = alunosDAO.findById(idAlunos).orElse(null);
            if (alunoExistente != null) {
                alunosDAO.delete(alunoExistente);

                logger.info("Aluno excluído com sucesso: {}", alunoExistente);
                return ResponseEntity.ok(Collections.singletonMap("message", "Aluno excluído com sucesso."));
            } else {
                logger.error("Aluno não encontrado para exclusão.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Aluno não encontrado para exclusão."));
            }
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao excluir o aluno.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Ocorreu um erro ao excluir o aluno."));
        }
    }


}
