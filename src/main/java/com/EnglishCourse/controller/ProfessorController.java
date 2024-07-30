package com.EnglishCourse.controller;

import com.EnglishCourse.model.Alunos;
import com.EnglishCourse.model.Professor;
import com.EnglishCourse.servicos.IAlunosService;
import com.EnglishCourse.servicos.IProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class ProfessorController {

    @Autowired
    private IProfessorService iProfessorService;

    @PostMapping("/professor")
    public ResponseEntity<?> cadastrarProfessor(@RequestBody Professor professor) {
        try {
            ResponseEntity<?> resposta = iProfessorService.salvarProfessor(professor);

            return resposta; // Retorna a resposta do serviço diretamente
        } catch (Exception e) {
            // Log da exceção para fins de depuração
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Ocorreu um erro ao cadastrar o professor."));
        }
    }

    @GetMapping("/professor")
    public ResponseEntity<List<Professor>> listarProfessor() {
        try {
            List<Professor> professores = (List<Professor>) iProfessorService.recuperarProfessor();
            return ResponseEntity.ok(professores);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(new Professor()));
        }
    }



}
