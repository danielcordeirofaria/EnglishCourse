package com.EnglishCourse.servicos;

import com.EnglishCourse.DAO.AlunosDAO;
import com.EnglishCourse.DAO.TurmaDAO;
import com.EnglishCourse.model.Professor;
import com.EnglishCourse.model.Turma;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class TurmaServiceImpl implements ITurmaService {

    @Autowired
    private TurmaDAO turmaDAO;

    private static final Logger logger = LoggerFactory.getLogger(TurmaServiceImpl.class);
    @Autowired
    private AlunosDAO alunosDAO;

    @Override
    public Object recuperarTurma() {
        try {
            return turmaDAO.findAll();
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao recuperar as turmas.", e);
            return Collections.singletonMap("message", "Ocorreu um erro ao recuperar as turmas.");
        }
    }

    @Override
    public ResponseEntity<?> salvarTurma(Turma newTurma) {
        try {
            logger.info("Tentando cadastrar um nova turma: {}", newTurma);
            if (newTurma.getNomeTurma() == null || newTurma.getNomeTurma().trim().equals("") || turmaDAO.existsByNomeTurma(newTurma.getNomeTurma())) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Nome da turma é obrigatório ou já existe."));
            }
            turmaDAO.save(newTurma);
            return ResponseEntity.ok(Collections.singletonMap("message", "Turma cadastrada com sucesso.")); // Retorna a mensagem de sucesso
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao cadastrar a turma.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Ocorreu um erro ao cadastrar a turma."));
        }
    }

    @Override
    public Turma findByIdTurma(int idTurma) {
        try {
            return turmaDAO.findById(idTurma).orElse(null);
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao buscar a turma.", e);
            return null;
        }
    }

    @Override
    public boolean deletarTurma(int idTurma) {
        if (turmaDAO.existsById(idTurma)) {
            turmaDAO.deleteById(idTurma);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ResponseEntity<?> atualizarTurma(int idTurma, Turma turma) {
        try {
            logger.info("Tentando atualizar turma: {}", idTurma);
            Turma turmaExistente = turmaDAO.findByIdTurma(idTurma);

            if(turmaExistente != null){
                logger.info("Turma encontrada: {}", turmaExistente);
                updateTurma(turmaExistente, turma);
                Turma turmaAtualizado = turmaDAO.save(turmaExistente);
                logger.info("Turma atualizada com sucesso: {}", turmaAtualizado);
                return ResponseEntity.ok(turmaAtualizado);
            } else {
                logger.error("Turma não encontrada para atualização.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao atualizar a turma.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private void updateTurma(Turma turmaExistente, Turma turmaNova) {
        turmaExistente.setNomeTurma(turmaNova.getNomeTurma());
        turmaExistente.setProfessor(turmaNova.getProfessor());

    }


}