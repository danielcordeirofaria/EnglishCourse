package com.EnglishCourse.servicos;

import com.EnglishCourse.DAO.AlunosDAO;
import com.EnglishCourse.DAO.EnderecoDAO;
import com.EnglishCourse.DAO.ResponsavelDAO;
import com.EnglishCourse.DAO.TurmaDAO;
import com.EnglishCourse.model.Alunos;
import com.EnglishCourse.model.Endereco;
import com.EnglishCourse.model.Responsavel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

@Service
public class AlunosServiceImpl implements IAlunosService {

    @Autowired
    private AlunosDAO alunosDAO;

    @Autowired
    private EnderecoDAO enderecoDAO;

    @Autowired
    private TurmaDAO turmaDAO;

    @Autowired
    private ResponsavelDAO responsavelDAO;

    private static final Logger logger = LoggerFactory.getLogger(AlunosServiceImpl.class);

    @Override
    public ResponseEntity<Object> salvarAluno(Alunos newAluno) {
        logger.info("Tentando cadastrar um novo aluno: {}", newAluno);

        // Validação de CPF
        if (newAluno.getCpf() == null || newAluno.getCpf().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "CPF do aluno é obrigatório."));
        }
        try {
            if (alunosDAO.existsByCpf(newAluno.getCpf())) {
                logger.error("CPF já cadastrado.");
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "CPF já está sendo usado."));
            }
        } catch (DataAccessException e) {
            logger.error("Erro ao acessar o banco de dados para verificar CPF", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Erro ao acessar o banco de dados"));
        }

        // Validação da turma (se existir)
        if (newAluno.getTurma() != null) {
            int idTurma = newAluno.getTurma().getIdTurma();
            try {
                if (idTurma <= 0 || !turmaDAO.existsByIdTurma(idTurma)) {
                    return ResponseEntity.badRequest().body(Collections.singletonMap("message", "ID da turma inválido ou turma não encontrada."));
                }
            } catch (DataAccessException e) {
                logger.error("Erro ao acessar o banco de dados para verificar turma", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("message", "Erro ao acessar o banco de dados"));
            }
        }

        // Validações do aluno
        if (newAluno.getNome() == null || newAluno.getNome().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Nome do aluno é obrigatório."));
        }

        Endereco endereco = newAluno.getEndereco();
        if (endereco == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Endereço do aluno é obrigatório."));
        }
        if (endereco.getLogradouro() == null || endereco.getLogradouro().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Logradouro inválido."));
        }
        if (endereco.getNumero() == null || endereco.getNumero().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Insira um valor para o número. Caso não tenha, envie S/A."));
        }
        if (endereco.getBairro() == null || endereco.getBairro().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Valor para bairro inválido."));
        }
        if (endereco.getCidade() == null || endereco.getCidade().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Insira a Cidade do aluno."));
        }
        if (endereco.getEstado() == null || endereco.getEstado().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Insira o Estado do aluno."));
        }
        if (endereco.getCep() == null || endereco.getCep().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Valor para CEP inválido."));
        }

        if (newAluno.getDataDeNascimento() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Data de nascimento do aluno é obrigatória."));
        }
        if (newAluno.getEmail() == null || newAluno.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Email do aluno é obrigatório."));
        }
        if (newAluno.getFormacao() == null || newAluno.getFormacao().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Formação do aluno é obrigatória."));
        }
        if (newAluno.getModuloFeito() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Módulo feito pelo aluno é obrigatório."));
        }
        if (newAluno.getNivel() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Nível do aluno é obrigatório."));
        }
        if (newAluno.getProfissao() == null || newAluno.getProfissao().isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Profissão do aluno é obrigatória."));
        }

        try {
            // Salvando o endereço
            enderecoDAO.save(endereco);

            // Salvando o responsável (se existir)
            if (newAluno.getResponsavel() != null) {
                Responsavel responsavel = newAluno.getResponsavel();
                Responsavel responsavelExistente = responsavelDAO.findByCpf(responsavel.getCpf());
                if (responsavelExistente != null) {
                    newAluno.setResponsavel(responsavelExistente);
                } else {
                    responsavelDAO.save(responsavel);
                }
            }

            // Salvando o aluno
            Alunos savedAluno = alunosDAO.save(newAluno);
            System.out.println("aluno savo" + savedAluno);
            logger.info("Aluno cadastrado com sucesso: {}", savedAluno);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Collections.singletonMap("message", "Aluno cadastrado com sucesso."));

        } catch (DataAccessException e) {
            logger.error("Erro ao salvar aluno", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Erro ao salvar aluno"));
        }
    }

    private void validateAluno(Alunos aluno) throws IllegalArgumentException {
        if (aluno.getNome() == null || aluno.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome do aluno é obrigatório.");
        }

        Endereco endereco = aluno.getEndereco();
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço do aluno é obrigatório.");
        }
        if (endereco.getLogradouro() == null) {
            throw new IllegalArgumentException("Logradouro inválido.");
        }
        if (endereco.getNumero() == null) {
            throw new IllegalArgumentException("Insira um valor para o número. Caso não tenha, envie S/A.");
        }
        if (endereco.getBairro() == null) {
            throw new IllegalArgumentException("Valor para bairro inválido.");
        }
        if (endereco.getCidade() == null) {
            throw new IllegalArgumentException("Insira a Cidade do aluno.");
        }
        if (endereco.getEstado() == null) {
            throw new IllegalArgumentException("Insira o Estado do aluno.");
        }
        if (endereco.getCep() == null) {
            throw new IllegalArgumentException("Valor para CEP inválido.");
        }

        if (aluno.getDataDeNascimento() == null) {
            throw new IllegalArgumentException("Data de nascimento do aluno é obrigatória.");
        }
        if (aluno.getCpf() == null || aluno.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF do aluno é obrigatório.");
        }
        if (aluno.getEmail() == null || aluno.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email do aluno é obrigatório.");
        }
        if (aluno.getFormacao() == null || aluno.getFormacao().isEmpty()) {
            throw new IllegalArgumentException("Formação do aluno é obrigatória.");
        }
        if (aluno.getModuloFeito() == null) {
            throw new IllegalArgumentException("Módulo feito pelo aluno é obrigatório.");
        }
        if (aluno.getNivel() == null) {
            throw new IllegalArgumentException("Nível do aluno é obrigatório.");
        }
        if (aluno.getProfissao() == null || aluno.getProfissao().isEmpty()) {
            throw new IllegalArgumentException("Profissão do aluno é obrigatória.");
        }
    }

    @Override
    public Object recuperarAlunos() {
        try {
            return alunosDAO.findAll();
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao recuperar os alunos.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Ocorreu um erro ao recuperar os alunos."));
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



    public void updateAluno(Alunos alunoExistente, Alunos alunoNovo) {
        alunoExistente.setNome(alunoNovo.getNome());
        alunoExistente.setEndereco(alunoNovo.getEndereco());
        alunoExistente.setDataDeNascimento(alunoNovo.getDataDeNascimento());
        alunoExistente.setCpf(alunoNovo.getCpf());
        alunoExistente.setEmail(alunoNovo.getEmail());
        alunoExistente.setFormacao(alunoNovo.getFormacao());
        alunoExistente.setModuloFeito(alunoNovo.getModuloFeito());
        alunoExistente.setNivel(alunoNovo.getNivel());
        alunoExistente.setStatus(alunoNovo.getStatus());
        alunoExistente.setTurma(alunoNovo.getTurma());
        alunoExistente.setResponsavel(alunoNovo.getResponsavel());
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
    public List<Alunos> buscarAlunosPorTurma(int idTurma) {
        return alunosDAO.findByTurmaIdTurma(idTurma);
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