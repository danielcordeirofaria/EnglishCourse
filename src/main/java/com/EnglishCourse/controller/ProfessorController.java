package com.EnglishCourse.controller;

import com.EnglishCourse.model.Professor;
import com.EnglishCourse.servicos.IProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private IProfessorService iProfessorService;

    @PostMapping
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

    @PostMapping("/batch")
    public ResponseEntity<?> cadastrarProfessores(@RequestBody List<Professor> professores) {
        try {
            for (Professor professor : professores) {
                iProfessorService.salvarProfessor(professor);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Professores cadastrados com sucesso."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Ocorreu um erro ao cadastrar os professores."));
        }
    }


    @GetMapping
    public ResponseEntity<List<Professor>> listarProfessor() {
        try {
            List<Professor> professores = iProfessorService.recuperarProfessor();
            return ResponseEntity.ok(professores);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());

        }
    }

    @GetMapping("/{idProfessor}")
    public ResponseEntity<Professor> buscarProfessor(@PathVariable("idProfessor") int idProfessor) {
        Professor professorRes = iProfessorService.buscarProfessor(idProfessor);
        if (professorRes != null) {
            return ResponseEntity.ok(professorRes);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{idProfessor}")
    public ResponseEntity<?> atualizarProfessor(@PathVariable("idProfessor") int idProfessor, @RequestBody Professor professor) {
        try {
            ResponseEntity<?> resposta = iProfessorService.atualizarProfessor(idProfessor, professor);
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

    @DeleteMapping("/{idProfessor}")
    public ResponseEntity<?> deleteProfessor(@PathVariable("idProfessor") int idProfessor) {
        try {
            boolean isDeleted = iProfessorService.deletarProfessor(idProfessor);

            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.singletonMap("message", "Professor deletado com sucesso."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Professor não encontrado."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Ocorreu um erro ao deletar o professor."));
        }
    }


}
