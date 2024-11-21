package com.EnglishCourse.DAO;

import com.EnglishCourse.model.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
class HorarioDAOTest {

    @Autowired
    HorarioDAO horarioDAO;

    @Autowired
    EntityManager entityManager;


    @Test
    @DisplayName("Should get Horario By Id successfully from DB")
    void findByIdHorarioCase1() {
// Crie uma turma
        Endereco enderecoProfessor = new Endereco("Rua dos Professores", "456", null, "Bairro dos Educadores", "Cidade do Saber", "Estado do Conhecimento", "87654321");
        entityManager.persist(enderecoProfessor);

        Professor professor = new Professor("Professor Nome", "99988877766", enderecoProfessor, "professor@email.com", "41999998888", "loginProfessor", "41995233589", RolesEnum.PROF);
        entityManager.persist(professor);

        Turma turma = new Turma("Turma A", professor);
        entityManager.persist(turma);

// Crie um horário e associe à turma
        Horario horario = new Horario(turma, DiaDaSemanaEnum.SEGUNDA, LocalTime.of(10, 0), LocalTime.of(11, 30));
        entityManager.persist(horario);

        Horario horarioTeste = horarioDAO.findByIdHorario(1);

        assertThat(horarioTeste).isNotNull();
    }

    @Test
    @DisplayName("Should not get Horario By Id successfully from DB")
    void findByIdHorarioCase2() {

        Horario horarioTeste = horarioDAO.findByIdHorario(1);

        assertThat(horarioTeste).isNull();
    }

    @Test
    @DisplayName("Should get Turma By Id_turma successfully from DB")
    void findByTurma_IdTurmaCase1() {
        // Cria uma lista de endereços para os professores
        List<Endereco> enderecosProfessores = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Endereco enderecoProfessor = new Endereco("Rua dos Professores " + i, "456", null, "Bairro dos Educadores", "Cidade do Saber", "Estado do Conhecimento", "8765432" + i);
            enderecosProfessores.add(enderecoProfessor);
            entityManager.persist(enderecoProfessor);
        }

        // Cria uma lista de professores
        List<Professor> professores = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Professor professor = new Professor("Professor " + i, "9998887776" + i, enderecosProfessores.get(i-1), "professor" + i + "@email.com", "4199999888" + i, "loginProfessor" + i, "41995233589", RolesEnum.PROF);
            professores.add(professor);
            entityManager.persist(professor);
        }

        // Cria uma lista de turmas
        List<Turma> turmas = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Turma turma = new Turma("Turma " + i, professores.get(i-1));
            turmas.add(turma);
            entityManager.persist(turma);
        }

        List<Horario> horarios = horarioDAO.findByTurma_IdTurma(1);

        assertThat(horarios).isNotNull();

    }

    @Test
    @DisplayName("Should not get Turma By Id_turma successfully from DB")
    void findByTurma_IdTurmaCase2() {


        List<Horario> horarios = horarioDAO.findByTurma_IdTurma(1);

        assertThat(horarios).isEmpty();

    }

}