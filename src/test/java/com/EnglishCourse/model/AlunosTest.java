package com.EnglishCourse.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class AlunosTest {

    @Test
    public void testGetterSetter() {
        // Cria um objeto Endereco
        Endereco endereco = new Endereco();
        endereco.setIdEndereco(1);
        endereco.setLogradouro("Rua Teste");
        endereco.setNumero("123");
        endereco.setBairro("Bairro Teste");
        endereco.setCidade("Cidade Teste");
        endereco.setEstado("Estado Teste");
        endereco.setCep("12345-678");

        // Cria um objeto Alunos
        Alunos aluno = new Alunos();
        aluno.setIdAlunoMatricula(1);
        aluno.setNome("João");
        aluno.setEndereco(endereco);
        LocalDate dataDeNascimento = LocalDate.now();
        aluno.setDataDeNascimento(dataDeNascimento);
        aluno.setCpf("123.456.789-00");
        aluno.setEmail("joao@example.com");
        aluno.setFormacao("Engenharia");
        aluno.setModuloFeito("Básico");
        aluno.setNivel("Intermediário");
        aluno.setIdPacote(1);

        // Verifica se os valores definidos estão corretos
        assertEquals(1, aluno.getIdAlunoMatricula());
        assertEquals("João", aluno.getNome());
        assertEquals(endereco, aluno.getEndereco());
        assertEquals(dataDeNascimento, aluno.getDataDeNascimento());
        assertEquals("123.456.789-00", aluno.getCpf());
        assertEquals("joao@example.com", aluno.getEmail());
        assertEquals("Engenharia", aluno.getFormacao());
        assertEquals("Básico", aluno.getModuloFeito());
        assertEquals("Intermediário", aluno.getNivel());
        assertEquals(1, aluno.getIdPacote());

        // Verifica se os valores do Endereco estão corretos
        assertEquals(1, aluno.getEndereco().getIdEndereco());
        assertEquals("Rua Teste", aluno.getEndereco().getLogradouro());
        assertEquals("123", aluno.getEndereco().getNumero());
        assertEquals("Bairro Teste", aluno.getEndereco().getBairro());
        assertEquals("Cidade Teste", aluno.getEndereco().getCidade());
        assertEquals("Estado Teste", aluno.getEndereco().getEstado());
        assertEquals("12345-678", aluno.getEndereco().getCep());
    }
}
