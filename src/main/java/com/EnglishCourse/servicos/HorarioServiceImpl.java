package com.EnglishCourse.servicos;

import com.EnglishCourse.DAO.HorarioDAO;
import com.EnglishCourse.model.Horario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class HorarioServiceImpl implements IHorarioService{

    @Autowired
    private HorarioDAO horarioDAO;

    private static final Logger logger = LoggerFactory.getLogger(HorarioServiceImpl.class);

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
}
