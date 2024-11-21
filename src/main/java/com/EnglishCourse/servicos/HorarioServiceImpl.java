package com.EnglishCourse.servicos;

import com.EnglishCourse.DAO.HorarioDAO;
import com.EnglishCourse.DAO.TurmaDAO;
import com.EnglishCourse.DTO.HorarioDTO;
import com.EnglishCourse.model.Horario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HorarioServiceImpl implements IHorarioService{

    @Autowired
    private HorarioDAO horarioDAO;

    private static final Logger logger = LoggerFactory.getLogger(HorarioServiceImpl.class);
    @Autowired
    private TurmaDAO turmaDAO;

    @Override
    public ResponseEntity<?> atualizarHorario(int idHorario, Horario horario) {
        try {
            Optional<Horario> horarioExistente = horarioDAO.findById(idHorario);
            if (horarioExistente.isPresent()) {
                Horario horarioAtualizado = horarioExistente.get();
                // Atualize os campos conforme necessário
                horarioAtualizado.setDiaSemana(horario.getDiaSemana());
                horarioAtualizado.setHorarioInicio(horario.getHorarioInicio());
                horarioAtualizado.setHorarioFim(horario.getHorarioFim());
                horarioAtualizado.setTurma(horario.getTurma());

                // Salva o horário atualizado no banco de dados
                horarioDAO.save(horarioAtualizado);
                return ResponseEntity.ok(horarioAtualizado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Horário não encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o horário");
        }
    }

    @Override
    public Object recuperarHorarios() {
        try {
            return horarioDAO.findAll();
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao recuperar os Horarios.", e);
            return Collections.singletonMap("message", "Ocorreu um erro ao recuperar os Horarios.");
        }
    }

    @Override
    public boolean deletarHorario(int idHorario) {
        if (horarioDAO.existsById(idHorario)) {
            horarioDAO.deleteById(idHorario);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ResponseEntity<?> cadastrarNovoHorario(Horario newHorario) {
        try {
            logger.info("Tentando cadastrar um novo horario: {}", newHorario);
            if( !turmaDAO.existsById(newHorario.getTurma().getIdTurma())){
                logger.error("Turma não encontrada.");
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Turma não encontrada."));
            }
            switch (newHorario.getDiaSemana()) {
                case SEGUNDA:
                case TERCA:
                case QUARTA:
                case QUINTA:
                case SEXTA:
                case SABADO:
                case DOMINGO:
                    // Dia da semana válido, continue o processamento
                    break;
                default:
                    logger.error("Dia da semana não encontrado");
                    return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Dia da semana não encontrado."));
            }

            horarioDAO.save(newHorario);
            return ResponseEntity.ok(newHorario);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar o horário");
        }
    }


    @Override
    public ResponseEntity<List<HorarioDTO>> retornarHorariosPeloIdTurma(int idTurma) {
        List<Horario> horarios = horarioDAO.findByTurma_IdTurma(idTurma);

        // Mapeamento de Horario para HorarioDTO
        List<HorarioDTO> horarioDTOs = horarios.stream().map(horario ->
                new HorarioDTO(
                        horario.getIdHorario(),
                        horario.getTurma().getIdTurma(),
                        horario.getTurma().getNomeTurma(),
                        horario.getTurma().getProfessor().getIdProfessor(),
                        horario.getTurma().getProfessor().getNome(),
                        horario.getDiaSemana().name(),  // Se o enum for convertido para string
                        horario.getHorarioInicio().toString(),
                        horario.getHorarioFim().toString()
                )
        ).collect(Collectors.toList());

        return ResponseEntity.ok(horarioDTOs);
    }


}
