package com.EnglishCourse.servicos;

import com.EnglishCourse.DAO.AlunosDAO;
import com.EnglishCourse.DAO.ProfessorDAO;
import com.EnglishCourse.DAO.TurmaDAO;
import com.EnglishCourse.DTO.ProfessorDTO;
import com.EnglishCourse.DTO.TurmaDTO;
import com.EnglishCourse.model.Professor;
import com.EnglishCourse.model.Turma;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurmaServiceImpl implements ITurmaService {

    @Autowired
    private TurmaDAO turmaDAO;

    @Autowired
    private ProfessorDAO professorDAO;

    private static final Logger logger = LoggerFactory.getLogger(TurmaServiceImpl.class);
    @Autowired
    private AlunosDAO alunosDAO;

    @Override
    public List<TurmaDTO> recuperarTurmas() {
        try {
            List<Turma> turmas = turmaDAO.findAll();
            return turmas.stream().map(turma -> {
                Professor professor = turma.getProfessor();
                ProfessorDTO professorDTO = null;

                if (professor != null) {
                    professorDTO = new ProfessorDTO(professor.getIdProfessor(), professor.getNomeProfessor(), professor.getWhatsapp());
                }

                return new TurmaDTO(turma.getIdTurma(), turma.getNomeTurma(), professorDTO);
            }).collect(Collectors.toList()).reversed();
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao recuperar as turmas.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public TurmaDTO retornarTurmaPorId(int idTurma) {
        try {
            Turma turma = turmaDAO.findByIdTurma(idTurma);
            if (turma != null) {
                Professor professor = turma.getProfessor();
                ProfessorDTO professorDTO = null;

                if (professor != null) {
                    professorDTO = new ProfessorDTO(professor.getIdProfessor(), professor.getNomeProfessor(), professor.getWhatsapp());
                }

                return new TurmaDTO(turma.getIdTurma(), turma.getNomeTurma(), professorDTO);
            } else {
                return null; // ou você pode lançar uma exceção personalizada aqui, se preferir.
            }
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao buscar a turma.", e);
            return null;
        }
    }

    @Override
    public ResponseEntity<?> salvarTurma(Turma newTurma) {
        try {
            logger.info("Tentando cadastrar uma nova turma: {}", newTurma);

            // Validação do nome da turma
            if (newTurma.getNomeTurma() == null || newTurma.getNomeTurma().trim().equals("") || turmaDAO.existsByNomeTurma(newTurma.getNomeTurma())) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Nome da turma é obrigatório ou já existe."));
            }

            // Validação do professor associado
            if (newTurma.getProfessor() == null || newTurma.getProfessor().getIdProfessor() == 0 || !professorDAO.existsByIdProfessor(newTurma.getProfessor().getIdProfessor())) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Professor não existente."));
            }

            turmaDAO.save(newTurma);
            return ResponseEntity.ok(Collections.singletonMap("message", "Turma cadastrada com sucesso."));
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao cadastrar a turma.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Ocorreu um erro ao cadastrar a turma."));
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
            logger.info("Tentando atualizar turma com ID: {}", idTurma);
            Turma turmaExistente = turmaDAO.findById(idTurma).orElse(null);

            if (turmaExistente != null) {
                logger.info("Turma existente encontrada: {}", turmaExistente);

                // Verifique se o professor associado é válido
                if (turma.getProfessor() != null && turma.getProfessor().getIdProfessor() != 0) {
                    Professor professorExistente = professorDAO.findById(turma.getProfessor().getIdProfessor()).orElse(null);
                    if (professorExistente != null) {
                        turmaExistente.setProfessor(professorExistente);
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Collections.singletonMap("message", "Professor não encontrado."));
                    }
                } else {
                    turmaExistente.setProfessor(null);
                }

                // Atualize outros campos, se necessário
                turmaExistente.setNomeTurma(turma.getNomeTurma());

                Turma turmaAtualizada = turmaDAO.save(turmaExistente);
                logger.info("Turma atualizada com sucesso: {}", turmaAtualizada);
                return ResponseEntity.ok("Turma atualizada com sucesso: ");
            } else {
                logger.warn("Turma com ID {} não encontrada.", idTurma);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Turma não encontrada."));
            }
        } catch (Exception e) {
            logger.error("Erro ao atualizar turma com ID {}: {}", idTurma, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Ocorreu um erro ao atualizar a turma."));
        }
    }

}