package com.EnglishCourse.controller;

import com.EnglishCourse.DTO.TurmaDTO;
import com.EnglishCourse.model.Turma;
import com.EnglishCourse.servicos.ITurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/turma")
public class TurmaController {

    @Autowired
    private ITurmaService iTurmaService;

    @GetMapping
    public ResponseEntity<List<TurmaDTO>> listarTurmas() {
        try {
            List<TurmaDTO> turmas = iTurmaService.recuperarTurmas();
            return ResponseEntity.ok(turmas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }


    @GetMapping("/{idTurma}")
    public ResponseEntity<TurmaDTO> buscarTurma(@PathVariable("idTurma") int idTurma) {
        TurmaDTO turma = iTurmaService.retornarTurmaPorId(idTurma);
        if (turma != null) {
            return ResponseEntity.ok(turma);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping
    public ResponseEntity<?> cadastrarTurma(@RequestBody Turma turma) {
        try {
            // Use a resposta do serviço diretamente
            return iTurmaService.salvarTurma(turma);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Ocorreu um erro ao cadastrar a turma."));
        }
    }

    @PutMapping("/{idTurma}")
    public ResponseEntity<?> alterandoTurma(@PathVariable("idTurma") int idTurma, @RequestBody Turma turma) {
        try {

            return iTurmaService.atualizarTurma(idTurma, turma);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Ocorreu um erro ao atualizar a turma."));
        }
    }


    @DeleteMapping("/{idTurma}")
    public ResponseEntity<?> deleteTurma(@PathVariable("idTurma") int idTurma) {
        try {
            boolean isDeleted = iTurmaService.deletarTurma(idTurma);

            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.singletonMap("message", "Turma deletada com sucesso."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Turma não encontrada."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Ocorreu um erro ao deletar a Turma."));
        }
    }
}