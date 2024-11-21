package com.EnglishCourse.DAO;

import com.EnglishCourse.model.Endereco;
import com.EnglishCourse.model.Professor;
import com.EnglishCourse.model.RolesEnum;
import com.EnglishCourse.model.Turma;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class TurmaDAOTest {

    @Autowired
    TurmaDAO turmaDAO;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should verify if Turma exist by idTurma from DB")
    void existsByIdTurmaCase1() {
        Endereco enderecoProfessor = new Endereco("Rua dos Professores", "456", null, "Bairro dos Educadores", "Cidade do Saber", "Estado do Conhecimento", "87654321");
        entityManager.persist(enderecoProfessor);

        // Cria um professor
        Professor professor = new Professor("Professor Nome", "99988877766", enderecoProfessor, "professor@email.com", "41999998888", "loginProfessor", "41995233589", RolesEnum.PROF);
        entityManager.persist(professor);

        // Cria uma turma
        Turma turma = new Turma("Turma A", professor);
        entityManager.persist(turma);

        Boolean turmaExistente = turmaDAO.existsByIdTurma(1);

        assertThat(turmaExistente).isTrue();

    }

    @Test
    @DisplayName("Should verify if Turma doesn't exist by idTurma from DB")
    void existsByIdTurmaCase2() {

        Boolean turmaExistente = turmaDAO.existsByIdTurma(1);

        assertThat(turmaExistente).isFalse();

    }

    @Test
    @DisplayName("Should verify if Turma exist by name from DB")
    void existsByNomeTurmaCase1() {
        Endereco enderecoProfessor = new Endereco("Rua dos Professores", "456", null, "Bairro dos Educadores", "Cidade do Saber", "Estado do Conhecimento", "87654321");
        entityManager.persist(enderecoProfessor);

        // Cria um professor
        Professor professor = new Professor("Professor Nome", "99988877766", enderecoProfessor, "professor@email.com", "41999998888", "loginProfessor", "41995233589", RolesEnum.PROF);
        entityManager.persist(professor);

        // Cria uma turma
        Turma turma = new Turma("Turma A", professor);
        entityManager.persist(turma);

        Boolean turmaExistente = turmaDAO.existsByNomeTurma("Turma A");

        assertThat(turmaExistente).isTrue();

    }

    @Test
    @DisplayName("Should verify if Turma exist by name from DB")
    void existsByNomeTurmaCase2() {

        Boolean turmaExistente = turmaDAO.existsByNomeTurma("Turma A");

        assertThat(turmaExistente).isFalse();

    }

    @Test
    @DisplayName("Should get Aluno successfully from DB")
    void findByIdTurmaCase1() {

        Endereco enderecoProfessor = new Endereco("Rua dos Professores", "456", null, "Bairro dos Educadores", "Cidade do Saber", "Estado do Conhecimento", "87654321");
        entityManager.persist(enderecoProfessor);

        // Cria um professor
        Professor professor = new Professor("Professor Nome", "99988877766", enderecoProfessor, "professor@email.com", "41999998888", "loginProfessor", "41995233589", RolesEnum.PROF);
        entityManager.persist(professor);

        // Cria uma turma
        Turma turma = new Turma("Turma A", professor);
        entityManager.persist(turma);

        Turma turma1 = turmaDAO.findByIdTurma(turma.getIdTurma());

        assertThat(turma1).isNotNull();

    }

    @Test
    @DisplayName("Should not get Aluno from DB")
    void findByIdTurmaCase2() {

        Turma turma1 = turmaDAO.findByIdTurma(1);

        assertThat(turma1).isNull();

    }
}