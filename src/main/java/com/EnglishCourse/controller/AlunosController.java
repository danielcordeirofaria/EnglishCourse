package com.EnglishCourse.controller;

import com.EnglishCourse.model.Alunos;
import com.EnglishCourse.servicos.IAlunosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AlunosController {

    @Autowired
    private IAlunosService iAlunosService;

    @PostMapping("/alunos")
    public ResponseEntity<String> cadastrarAlunos(@RequestBody Alunos aluno) {
        try {
            ResponseEntity<?> resposta = iAlunosService.cadastrarAluno(aluno);
            // Verifica se o corpo da resposta não é nulo antes de chamá-lo
            String responseBody = resposta.getBody() != null ? resposta.getBody().toString() : "";
            return ResponseEntity.status(resposta.getStatusCode()).body(responseBody);
        } catch (Exception e) {
            // Log da exceção para fins de depuração
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao cadastrar o aluno.");
        }
    }

    @GetMapping("/alunos")
    public ResponseEntity<ArrayList<Alunos>> listarAlunos() {
        ArrayList<Alunos> alunos= (ArrayList<Alunos>) iAlunosService.recuperarAlunos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("alunos/{idAlunos}")
    public ResponseEntity<Alunos> buscarAluno(@PathVariable("idAlunos") int idAlunos) {
        Alunos alunosRes = iAlunosService.buscarAluno(idAlunos);
        if (alunosRes != null) {
            return ResponseEntity.ok(alunosRes);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{idAlunos}")
    public ResponseEntity<String> atualizarAluno(@PathVariable("idAlunos") int idAlunos, @RequestBody Alunos aluno) {
        try {
            ResponseEntity<?> resposta = iAlunosService.atualizarAluno(aluno);
            String responseBody = resposta.getBody() != null ? resposta.getBody().toString() : "";
            return ResponseEntity.status(resposta.getStatusCode()).body(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao atualizar o aluno.");
        }
    }

    @DeleteMapping("/{idAlunos}")
    public ResponseEntity<String> deletarAluno(@PathVariable("idAlunos") int idAlunos) {
        try {
            ResponseEntity<?> resposta = iAlunosService.deletarAluno(idAlunos);
            String responseBody = resposta.getBody() != null ? resposta.getBody().toString() : "";
            return ResponseEntity.status(resposta.getStatusCode()).body(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao excluir o aluno.");
        }
    }

}

