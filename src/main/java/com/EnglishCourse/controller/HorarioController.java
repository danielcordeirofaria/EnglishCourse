package com.EnglishCourse.controller;

import com.EnglishCourse.DAO.HorarioDAO;
import com.EnglishCourse.model.Horario;
import com.EnglishCourse.servicos.IHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;

@RestController
@RequestMapping("/horario")
public class HorarioController {

    @Autowired
    private HorarioDAO horarioDAO;

    @Autowired
    private IHorarioService iHorarioService;

    @GetMapping
    public ResponseEntity<ArrayList<Horario>> listaHorarios() {
        ArrayList<Horario> horario = (ArrayList<Horario>) iHorarioService.recuperarHorarios();
        return ResponseEntity.ok(horario);
    }

    @GetMapping("/{idHorario}")
    public ResponseEntity<Horario> buscarHorario(@PathVariable("idHorario") int idHorario) {
        Horario horarioRes = horarioDAO.findByIdHorario(idHorario);
        if (horarioRes != null) {
            return ResponseEntity.ok(horarioRes);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{idHorario}")
    public ResponseEntity<?> atualizarHorario(@PathVariable("idHorario") int idHorario, @RequestBody Horario horario) {
        try {
            ResponseEntity<?> resposta = iHorarioService.atualizarHorario(idHorario, horario);
            if (resposta.getBody() != null) {
                return resposta;
            } else {
                return ResponseEntity.status(resposta.getStatusCode()).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{idHorario}")
    public ResponseEntity<?> deleteProfessor(@PathVariable("idHorario") int idHorario) {
        try {
            boolean isDeleted = iHorarioService.deletarHorario(idHorario);

            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.singletonMap("message", "Horario deletado com sucesso."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Horario não encontrado."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Ocorreu um erro ao deletar o Horario."));
        }
    }


}
