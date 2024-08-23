package com.EnglishCourse.controller;

import com.EnglishCourse.model.Alunos;
import com.EnglishCourse.model.Professor;
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
    public ResponseEntity<List<Turma>> listarTurmas() {
        try {
            List<Turma> turmas = (List<Turma>) iTurmaService.recuperarTurma();
            return ResponseEntity.ok(turmas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(new Turma()));
        }
    }

    @GetMapping("/idTurma")
    public ResponseEntity<Turma> buscarTurma(@RequestParam("idTurma") int idTurma) {
        Turma turmaRes = iTurmaService.findByIdTurma(idTurma);
        if(idTurma <= 0){
            return ResponseEntity.ok(turmaRes);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



    @PostMapping
    public ResponseEntity<?> cadastrarTurma(@RequestBody Turma turma) {
        try {
            ResponseEntity<?> resposta = iTurmaService.salvarTurma(turma);

            return resposta; // Retorna a resposta do serviço diretamente
        } catch (Exception e) {
            // Log da exceção para fins de depuração
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Ocorreu um erro ao cadastrar a turma."));
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

    @PutMapping("/idTurma")
    public ResponseEntity<?> atualizarTurma(@PathVariable("idTurma") int idTurma, @RequestBody Turma turma) {
        try {
            ResponseEntity<?> resposta = iTurmaService.atualizarTurma(idTurma, turma);
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
}
