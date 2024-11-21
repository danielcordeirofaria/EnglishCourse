package com.EnglishCourse.DAO;

import com.EnglishCourse.model.Endereco;
import com.EnglishCourse.model.Professor;
import com.EnglishCourse.model.RolesEnum;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;




@DataJpaTest
@ActiveProfiles("test")
class ProfessorDAOTest {

    @Autowired
    ProfessorDAO professorDAO;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should verify if Professor exist from DB")
    void existsByIdProfessorCase1() {
        Endereco enderecoProfessor = new Endereco("Rua dos Professores", "456", null, "Bairro dos Educadores", "Cidade do Saber", "Estado do Conhecimento", "87654321");
        entityManager.persist(enderecoProfessor);

        Professor professor = new Professor("Professor Nome", "99988877766", enderecoProfessor, "professor@email.com", "41999998888", "loginProfessor", "41995233589", RolesEnum.PROF);
        entityManager.persist(professor);

        Boolean professorLocalizado = professorDAO.existsByIdProfessor(1);

        assertThat(professorLocalizado).isTrue();
    }

    @Test
    @DisplayName("Should verify if Professor doesn't exist from DB")
    void existsByIdProfessorCase2() {

        Boolean professorLocalizado = professorDAO.existsByIdProfessor(1);

        assertThat(professorLocalizado).isFalse();
    }

    @Test
    @DisplayName("Should get Professor successfully from DB")
    void existsByCpfCase1() {

        String cpf = "12345678912";

        Endereco enderecoProfessor = new Endereco("Rua dos Professores", "456", null, "Bairro dos Educadores", "Cidade do Saber", "Estado do Conhecimento", "87654321");
        entityManager.persist(enderecoProfessor);

        Professor professor = new Professor("Professor Nome", cpf, enderecoProfessor, "professor@email.com", "41999998888", "loginProfessor", "41995233589", RolesEnum.PROF);
        entityManager.persist(professor);

        boolean existentProfessor = professorDAO.existsByCpf(cpf);

        assertThat(existentProfessor).isTrue();

    }

    @Test
    @DisplayName("Should get Professor successfully from DB")
    void existsByCpfCase2() {

        String cpf = "12345678912";

        Endereco enderecoProfessor = new Endereco("Rua dos Professores", "456", null, "Bairro dos Educadores", "Cidade do Saber", "Estado do Conhecimento", "87654321");
        entityManager.persist(enderecoProfessor);

        Professor professor = new Professor("Professor Nome", "cpf", enderecoProfessor, "professor@email.com", "41999998888", "loginProfessor", "41995233589", RolesEnum.PROF);
        entityManager.persist(professor);

        boolean existentProfessor = professorDAO.existsByCpf("12345678978");

        assertThat(existentProfessor).isFalse();

    }
}