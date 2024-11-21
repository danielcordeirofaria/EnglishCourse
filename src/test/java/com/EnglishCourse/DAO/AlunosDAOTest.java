package com.EnglishCourse.DAO;

import com.EnglishCourse.model.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.EnglishCourse.model.RolesEnum.PROF;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AlunosDAOTest {


    @Autowired
    AlunosDAO alunosDAO;

    @Autowired
    EntityManager entityManager;


    @Test
    @DisplayName("Should get Aluno successfully from DB")
    void existsByCpfCase1() {

        String cpf = "12345678912";
        // Cria um endereço
        Endereco endereco = new Endereco("Rua A", "123", "apto 1", "Bairro B", "Cidade C", "Estado E", "12345678");
        entityManager.persist(endereco);

        // Cria um responsável
        Responsavel responsavel = new Responsavel("Responsável Nome", "98765432109", endereco, "responsavel@email.com", "41987654321", LocalDate.of(1980, 1, 1), "Ensino Médio Completo", "Professor");
        entityManager.persist(responsavel);

        // Cria um aluno
        Alunos aluno = new Alunos("Daniel Faria", cpf, endereco, "aluno@email.com", "41123456789", LocalDate.of(2000, 10, 20), "Ensino Médio Completo", "Estudante", NivelEnum.pre_intermediate_1, NivelEnum.intermediate_1, StatusEnum.ATIVADO, null, responsavel);
        entityManager.persist(aluno);

        Boolean foundedAluno = this.alunosDAO.existsByCpf(cpf);

        assertThat(foundedAluno).isTrue();
    }


    @Test
    @DisplayName("Should not get Aluno from DB when user not exists")
    void existsByCpfCase2() {

        String cpf = "12345678912";

        Boolean foundedAluno = this.alunosDAO.existsByCpf(cpf);

        assertThat(foundedAluno).isFalse();
    }

    @Test
    @DisplayName("Should get Alunos List from DB")
    void findByTurmaIdTurmaCase1() {
        // Cria um endereço
        Endereco endereco = new Endereco("Rua A", "123", "apto 1", "Bairro B", "Cidade C", "Estado E", "12345678");
        entityManager.persist(endereco);

        // Cria um responsável
        Responsavel responsavel = new Responsavel("Responsável Nome", "98765432109", endereco, "responsavel@email.com", "41987654321", LocalDate.of(1980, 1, 1), "Ensino Médio Completo", "Professor");
        entityManager.persist(responsavel);

// Cria um endereço para o professor
        Endereco enderecoProfessor = new Endereco("Rua dos Professores", "456", null, "Bairro dos Educadores", "Cidade do Saber", "Estado do Conhecimento", "87654321");
        entityManager.persist(enderecoProfessor);

// Cria um professor
        Professor professor = new Professor("Professor Nome", "99988877766", enderecoProfessor, "professor@email.com", "professor@yahoo.com.br", "senhaProfessor", "41995233589", PROF);
        entityManager.persist(professor);

// Cria uma turma e associa o professor
        Turma turma = new Turma("Turma A", professor); // Passa o professor como parâmetro
        entityManager.persist(turma);

        // Cria uma lista de alunos
        List<Alunos> alunosList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Alunos aluno = new Alunos("Aluno " + i, "1234567890" + i, endereco, "aluno" + i + "@email.com", "4112345678" + i, LocalDate.of(2000, 10, 20), "Ensino Médio Completo", "Estudante", NivelEnum.pre_intermediate_1, NivelEnum.intermediate_1, StatusEnum.ATIVADO, turma, responsavel);
            alunosList.add(aluno);
            entityManager.persist(aluno);
        }


        List<Alunos> resultAlunosList = alunosDAO.findByTurmaIdTurma(1);

        assertThat(resultAlunosList).isNotEmpty();

    }

    @Test
    @DisplayName("Should not get Alunos List from DB")
    void findByTurmaIdTurmaCase2() {

        List<Alunos> resultAlunosList = alunosDAO.findByTurmaIdTurma(1);

        assertThat(resultAlunosList).isEmpty();

    }
}