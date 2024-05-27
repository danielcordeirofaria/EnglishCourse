package com.EnglishCourse.servicos;

import com.EnglishCourse.DAO.AlunosDAO;
import com.EnglishCourse.DAO.EnderecoDAO;
import com.EnglishCourse.model.Alunos;
import com.EnglishCourse.model.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AlunosServiceImpl implements IAlunosService {

    @Autowired
    private AlunosDAO alunosDAO;

    @Autowired
    private EnderecoDAO enderecoDAO;

    private static final Logger logger = LoggerFactory.getLogger(AlunosServiceImpl.class);

    public ResponseEntity<?> cadastrarAluno(Alunos newAluno) {
        try {
            logger.info("Tentando cadastrar um novo aluno: {}", newAluno);

            if (newAluno.getNome() == null || newAluno.getNome().isEmpty()) {
                logger.error("Nome do aluno é obrigatório.");
                return ResponseEntity.badRequest().body("Nome do aluno é obrigatório.");
            }

            Endereco newEndereco = newAluno.getEndereco();
            if (newEndereco == null) {
                logger.error("Endereço do aluno é obrigatório.");
                return ResponseEntity.badRequest().body("Endereço do aluno é obrigatório.");
            }
            if (newAluno.getEndereco().getLogradouro() == null) {
                logger.error("Logradouro inválido.");
                return ResponseEntity.badRequest().body("Logradouro inválido.");
            }
            if (newAluno.getEndereco().getNumero() == null) {
                logger.error("Insira um valor para o número. Caso não tenha, envie S/A.");
                return ResponseEntity.badRequest().body("Insira um valor para o número. Caso não tenha, envie S/A.");
            }
            if (newAluno.getEndereco().getBairro() == null) {
                logger.error("Valor para bairro inválido.");
                return ResponseEntity.badRequest().body("Valor para bairro inválido.");
            }
            if (newAluno.getEndereco().getCidade() == null) {
                logger.error("Insira a Cidade do aluno.");
                return ResponseEntity.badRequest().body("Insira a Cidade do aluno.");
            }
            if (newAluno.getEndereco().getEstado() == null) {
                logger.error("Insira o Estado do aluno.");
                return ResponseEntity.badRequest().body("Insira o Estado do aluno.");
            }
            if (newAluno.getEndereco().getCep() == null) {
                logger.error("Valor para CEP inválido.");
                return ResponseEntity.badRequest().body("Valor para CEP inválido.");
            }

            enderecoDAO.save(newEndereco);

            if (newAluno.getDataDeNascimento() == null) {
                logger.error("Data de nascimento do aluno é obrigatória.");
                return ResponseEntity.badRequest().body("Data de nascimento do aluno é obrigatória.");
            }
            if (newAluno.getCpf() == null || newAluno.getCpf().isEmpty())  {
                logger.error("CPF do aluno é obrigatório.");
                return ResponseEntity.badRequest().body("CPF do aluno é obrigatório.");
            }
            if (newAluno.getEmail() == null || newAluno.getEmail().isEmpty()) {
                logger.error("Email do aluno é obrigatório.");
                return ResponseEntity.badRequest().body("Email do aluno é obrigatório.");
            }
            if (newAluno.getFormacao() == null || newAluno.getFormacao().isEmpty()) {
                logger.error("Formação do aluno é obrigatória.");
                return ResponseEntity.badRequest().body("Formação do aluno é obrigatória.");
            }
            if (newAluno.getModuloFeito() == null || newAluno.getModuloFeito().isEmpty()) {
                logger.error("Módulo feito pelo aluno é obrigatório.");
                return ResponseEntity.badRequest().body("Módulo feito pelo aluno é obrigatório.");
            }
            if (newAluno.getNivel() == null || newAluno.getNivel().isEmpty()) {
                logger.error("Nível do aluno é obrigatório.");
                return ResponseEntity.badRequest().body("Nível do aluno é obrigatório.");
            }

            alunosDAO.save(newAluno);
            logger.info("Aluno cadastrado com sucesso: {}", newAluno);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao cadastrar o aluno.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao cadastrar o aluno.");
        }
    }

    @Override
    public Object recuperarAlunos() {
        try{
            return alunosDAO.findAll();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Alunos buscarAluno(int idAlunos) {
        try {
            return alunosDAO.findById(idAlunos).orElse(null);
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public ResponseEntity<?> atualizarAluno(Alunos aluno) {
        try {
            Alunos alunoExistente = alunosDAO.findById(aluno.getIdAlunoMatricula()).orElse(null);
            if (alunoExistente != null) {
                // Atualiza os dados do aluno existente com os novos dados
                alunoExistente.setNome(aluno.getNome());
                // Atualize outros campos conforme necessário...

                // Salva o aluno atualizado no banco de dados
                alunosDAO.save(alunoExistente);

                logger.info("Aluno atualizado com sucesso: {}", alunoExistente);
                return ResponseEntity.ok("Aluno atualizado com sucesso.");
            } else {
                logger.error("Aluno não encontrado para atualização.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado para atualização.");
            }
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao atualizar o aluno.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao atualizar o aluno.");
        }
    }

    @Override
    public ResponseEntity<?> deletarAluno(int idAlunos) {
        try {
            Alunos alunoExistente = alunosDAO.findById(idAlunos).orElse(null);
            if (alunoExistente != null) {
                // Remove o aluno do banco de dados
                alunosDAO.delete(alunoExistente);

                logger.info("Aluno excluído com sucesso: {}", alunoExistente);
                return ResponseEntity.ok("Aluno excluído com sucesso.");
            } else {
                logger.error("Aluno não encontrado para exclusão.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aluno não encontrado para exclusão.");
            }
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao excluir o aluno.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao excluir o aluno.");
        }
    }
}

