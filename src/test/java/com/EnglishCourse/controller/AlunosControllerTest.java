package com.EnglishCourse.controller;

import com.EnglishCourse.DAO.AlunosDAO;
import com.EnglishCourse.model.*;
import com.EnglishCourse.servicos.AlunosServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlunosController.class)
class AlunosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AlunosServiceImpl alunosServiceImpl;

    @MockBean
    private AlunosDAO alunosDAO;

//    @Test
//    void cadastrarAlunosCase1() throws Exception {
//        // Criação do objeto Aluno
//        Endereco endereco = new Endereco("Rua A", "123", "Complemento", "Bairro", "Cidade", "Estado", "12345-678");
//        Responsavel responsavel = new Responsavel(
//                "Responsável",
//                "98765432109",
//                endereco,
//                "responsavel@email.com",
//                "1234567890",
//                LocalDate.of(1980, 1, 1),
//                "Ensino Médio",
//                "Professor"
//        );
//        Alunos aluno = new Alunos("João da Silva", "12345678901", endereco, "joao@email.com", "9876543210",
//                LocalDate.of(2000, 1, 1), "Ensino Médio", "Programador", NivelEnum.beginners_1, NivelEnum.beginners_2,
//                StatusEnum.ATIVADO, new Turma(), responsavel);
//
//        // Simulação do comportamento do alunosDAO
//        when(alunosDAO.existsByCpf(aluno.getCpf())).thenReturn(false);
//        when(alunosDAO.save(any(Alunos.class))).thenReturn(aluno);
//
//        // Simulação do comportamento do serviço
//        when(alunosServiceImpl.salvarAluno(aluno)).thenReturn(ResponseEntity.status(HttpStatus.CREATED)
//                .body(Collections.singletonMap("message", "Aluno cadastrado com sucesso.")));
//
//        // Conversão do objeto para JSON
//        String alunoJson = objectMapper.writeValueAsString(aluno);
//
//        // Requisição POST e verificação da resposta
//        mockMvc.perform(post("/alunos")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(alunoJson))
//                .andExpect(status().isCreated());
//    }

    @Test
    void listarAlunosCase1() throws Exception {
        // Cria uma lista de alunos
        List<Alunos> alunos = new ArrayList<>();
        alunos.add(new Alunos());
        alunos.add(new Alunos());

        // Define o comportamento do serviço
        when(alunosServiceImpl.recuperarAlunos()).thenReturn(alunos);

        // Executa a requisição GET
        mockMvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))); // Verifica se a lista tem 2 alunos
    }

    @Test
    void listarAlunosCase2() throws Exception {
        // Define o comportamento do serviço para lançar uma exceção
        when(alunosServiceImpl.recuperarAlunos()).thenThrow(new RuntimeException("Erro ao recuperar alunos"));

        // Executa a requisição GET
        mockMvc.perform(get("/alunos"))
                .andExpect(status().isInternalServerError()); // Verifica se o status é 500
    }
}