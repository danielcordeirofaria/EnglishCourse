package com.EnglishCourse.DAO;

import com.EnglishCourse.model.Endereco;
import com.EnglishCourse.model.Responsavel;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
class ResponsavelDAOTest {

    @Autowired
    ResponsavelDAO responsavelDAO;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should verify if Reponsavel exist from DB")
    void findByCpfCase1() {

        String cpf = "12345678912";

// Cria um endereço para o responsável
        Endereco enderecoResponsavel = new Endereco("Rua dos Responsáveis", "789", null, "Bairro da Família", "Cidade da União", "Estado do Apoio", "98765432");
        entityManager.persist(enderecoResponsavel);

// Cria um responsável
        Responsavel responsavel = new Responsavel("Responsável Nome", cpf, enderecoResponsavel, "responsavel@email.com", "41987654321", LocalDate.of(1980, 1, 1), "Ensino Médio Completo", "Professor");
        entityManager.persist(responsavel);

        Responsavel responsavel1 = responsavelDAO.findByCpf(cpf);

        assertThat(responsavel1).isNotNull();

    }

    @Test
    @DisplayName("Should verify if Reponsavel exist from DB")
    void findByCpfCase2() {

        String cpf = "12345678912";

        Responsavel responsavel1 = responsavelDAO.findByCpf(cpf);

        assertThat(responsavel1).isNull();

    }
}