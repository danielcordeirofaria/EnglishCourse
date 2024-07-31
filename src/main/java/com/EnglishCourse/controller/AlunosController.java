package com.EnglishCourse.controller;

import com.EnglishCourse.model.Alunos;
import com.EnglishCourse.servicos.IAlunosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alunos")
public class AlunosController {

    @Autowired
    private IAlunosService iAlunosService;

    @PostMapping
    public ResponseEntity<?> cadastrarAlunos(@RequestBody Alunos aluno) {
        try {
            ResponseEntity<?> resposta = iAlunosService.salvarAluno(aluno);

            return resposta; // Retorna a resposta do serviço diretamente
        } catch (Exception e) {
            // Log da exceção para fins de depuração
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Ocorreu um erro ao cadastrar o aluno."));
        }
    }

    @PostMapping("/batch")
    public ResponseEntity<?> cadastrarVariosAlunos(@RequestBody List<Alunos> alunos) {
        try {
            List<ResponseEntity<?>> respostas = alunos.stream()
                    .map(iAlunosService::salvarAluno)
                    .collect(Collectors.toList());

            boolean hasErrors = respostas.stream().anyMatch(resposta -> !resposta.getStatusCode().is2xxSuccessful());

            if (hasErrors) {
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(respostas);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(respostas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Ocorreu um erro ao cadastrar os alunos."));
        }
    }



    @GetMapping
    public ResponseEntity<ArrayList<Alunos>> listarAlunos() {
        ArrayList<Alunos> alunos= (ArrayList<Alunos>) iAlunosService.recuperarAlunos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{idAlunoMatricula}")
    public ResponseEntity<Alunos> buscarAluno(@PathVariable("idAlunoMatricula") int idAlunos) {
        Alunos alunosRes = iAlunosService.buscarAluno(idAlunos);
        if (alunosRes != null) {
            return ResponseEntity.ok(alunosRes);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{idAlunoMatricula}")
    public ResponseEntity<?> atualizarAluno(@PathVariable("idAlunoMatricula") int idAlunoMatricula, @RequestBody Alunos aluno) {
        try {
            ResponseEntity<?> resposta = iAlunosService.atualizarAluno(idAlunoMatricula, aluno);
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

    @PutMapping("/inutilizandoAluno/{idAlunoMatricula}")
    public ResponseEntity<?> desativarAluno(@PathVariable("idAlunoMatricula") int idAlunoMatricula, @RequestBody Alunos aluno) {
        try {
            ResponseEntity<?> resposta = iAlunosService.atualizarStatusAluno(idAlunoMatricula, aluno);
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


    @DeleteMapping("/{idAlunoMatricula}")
    public ResponseEntity<String> deletarAluno(@PathVariable("idAlunoMatricula") int idAlunos) {
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

