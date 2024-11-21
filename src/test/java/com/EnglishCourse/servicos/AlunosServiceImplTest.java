package com.EnglishCourse.servicos;

import com.EnglishCourse.DAO.AlunosDAO;
import com.EnglishCourse.DAO.EnderecoDAO;
import com.EnglishCourse.DAO.ResponsavelDAO;
import com.EnglishCourse.DAO.TurmaDAO;
import com.EnglishCourse.model.*;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlunosServiceImplTest {

    @Mock
    private AlunosDAO alunosDAO;

    @Mock
    private EnderecoDAO enderecoDAO;

    @Mock
    private TurmaDAO turmaDAO;

    @Mock
    private ResponsavelDAO responsavelDAO;

    @InjectMocks
    private AlunosServiceImpl alunosServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should create a new Aluno successfully when everything is ok")
    void salvarAlunoCase1() {

// Cria um endereço
        Endereco endereco = new Endereco("Rua dos Alunos", "123", "apto 2", "Bairro da Escola", "Cidade do Aprendizado", "Estado do Estudo", "12345678");
//        entityManager.persist(endereco);

// Cria um responsável
        Responsavel responsavel = new Responsavel("Responsável Nome", "98765432109", endereco, "responsavel@email.com", "41987654321", LocalDate.of(1980, 1, 1), "Ensino Médio Completo", "Professor");
//        entityManager.persist(responsavel);

// Cria um aluno
        Alunos aluno = new Alunos("Aluno Nome", "12345678901", endereco, "aluno@email.com", "41123456789", LocalDate.of(2000, 10, 20), "Ensino Médio Completo", "Estudante", NivelEnum.pre_intermediate_1, NivelEnum.intermediate_1, StatusEnum.ATIVADO, null, responsavel);
//        entityManager.persist(aluno);

        when(alunosDAO.existsByCpf(aluno.getCpf())).thenReturn(false);

        ResponseEntity<?> response = alunosServiceImpl.salvarAluno(aluno);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(alunosDAO, times(1)).save(aluno);
    }

    @Test
    @DisplayName("Should throw Exception when something is worng")
    void salvarAlunoCase2() {

        // Cria um endereço com logradouro nulo para causar erro na validação
        Endereco endereco = new Endereco(null, "123", "apto 2", "Bairro da Escola", "Cidade do Aprendizado", "Estado do Estudo", "12345678");

        // Cria um responsável
        Responsavel responsavel = new Responsavel("Responsável Nome", "98765432109", endereco, "responsavel@email.com", "41987654321", LocalDate.of(1980, 1, 1), "Ensino Médio Completo", "Professor");

        // Cria um aluno
        Alunos aluno = new Alunos("Aluno Nome", "12345678901", endereco, "aluno@email.com", "41123456789", LocalDate.of(2000, 10, 20), "Ensino Médio Completo", "Estudante", NivelEnum.pre_intermediate_1, NivelEnum.intermediate_1, StatusEnum.ATIVADO, null, responsavel);

        // Define o comportamento do mock do alunosDAO
        when(alunosDAO.existsByCpf(aluno.getCpf())).thenReturn(false);

        // Chama o método que você quer testar
        ResponseEntity<?> response = alunosServiceImpl.salvarAluno(aluno);

        // Verifica se a resposta é a esperada (deve falhar)
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); // Corrigido

        verify(alunosDAO, times(0)).save(aluno);
    }

    @Test
    @DisplayName("Should throw Exception when cpf is an existent cpf.")
    void salvarAlunoCase3() {

        // Cria um endereço com logradouro nulo para causar erro na validação
        Endereco endereco = new Endereco(null, "123", "apto 2", "Bairro da Escola", "Cidade do Aprendizado", "Estado do Estudo", "12345678");

        // Cria um responsável
        Responsavel responsavel = new Responsavel("Responsável Nome", "98765432109", endereco, "responsavel@email.com", "41987654321", LocalDate.of(1980, 1, 1), "Ensino Médio Completo", "Professor");

        // Cria um aluno
        Alunos aluno = new Alunos("Aluno Nome", "12345678901", endereco, "aluno@email.com", "41123456789", LocalDate.of(2000, 10, 20), "Ensino Médio Completo", "Estudante", NivelEnum.pre_intermediate_1, NivelEnum.intermediate_1, StatusEnum.ATIVADO, null, responsavel);

        // Define o comportamento do mock do alunosDAO
        when(alunosDAO.existsByCpf(aluno.getCpf())).thenReturn(true);

        // Chama o método que você quer testar
        ResponseEntity<?> response = alunosServiceImpl.salvarAluno(aluno);

        // Verifica se a resposta é a esperada (deve falhar)
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); // Corrigido

        verify(alunosDAO, times(0)).save(aluno);
    }

    @Test
    @DisplayName("Should return a list of Alunos")
    void recuperarAlunosCase1() {
        // Cria a lista de alunos mockada
        List<Alunos> alunosList = new ArrayList<>();

        // Cria os alunos e adiciona na lista
        Endereco endereco1 = new Endereco("Rua 1", "123", "apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
        Responsavel responsavel1 = new Responsavel("Responsável 1", "98765432101", endereco1, "responsavel1@email.com", "41987654321", LocalDate.of(1980, 1, 1), "Ensino Médio Completo", "Professor");
        Alunos aluno1 = new Alunos("Aluno 1", "12345678901", endereco1, "aluno1@email.com", "41123456781", LocalDate.of(2000, 10, 20), "Ensino Médio Completo", "Estudante", NivelEnum.pre_intermediate_1, NivelEnum.intermediate_1, StatusEnum.ATIVADO, null, responsavel1);
        alunosList.add(aluno1);

        Endereco endereco2 = new Endereco("Rua 2", "456", "apto 2", "Bairro 2", "Cidade 2", "Estado 2", "98765432");
        Responsavel responsavel2 = new Responsavel("Responsável 2", "98765432102", endereco2, "responsavel2@email.com", "42987654321", LocalDate.of(1985, 5, 10), "Ensino Superior Completo", "Engenheiro");
        Alunos aluno2 = new Alunos("Aluno 2", "12345678902", endereco2, "aluno2@email.com", "41123456782", LocalDate.of(1998, 3, 15), "Ensino Superior Incompleto", "Programador", NivelEnum.beginners_1, NivelEnum.elementary_1, StatusEnum.DESATIVADO, null, responsavel2);
        alunosList.add(aluno2);

        Endereco endereco3 = new Endereco("Rua 3", "789", null, "Bairro 3", "Cidade 3", "Estado 3", "10111213");
        Responsavel responsavel3 = new Responsavel("Responsável 3", "98765432103", endereco3, "responsavel3@email.com", "43987654321", LocalDate.of(1990, 8, 22), "Pós-graduação", "Médico");
        Alunos aluno3 = new Alunos("Aluno 3", "12345678903", endereco3, "aluno3@email.com", "41123456783", LocalDate.of(2002, 12, 5), "Ensino Fundamental Completo", "Designer", NivelEnum.intermediate_2, NivelEnum.pre_advanced_1, StatusEnum.ATIVADO, null, responsavel3);
        alunosList.add(aluno3);

        // Define o comportamento do mock do alunosDAO
        when(alunosDAO.findAll()).thenReturn(alunosList);

        // Chama o método recuperarAlunos()
        List<Alunos> alunosRecuperados = (List<Alunos>) alunosServiceImpl.recuperarAlunos();

        // Asserções para verificar o comportamento do método
        assertThat(alunosRecuperados).containsExactlyInAnyOrder(aluno1, aluno2, aluno3);
        assertEquals(alunosList, alunosRecuperados);
        verify(alunosDAO, times(1)).findAll(); // Verifica se o findAll foi chamado
    }

    @Test
    @DisplayName("Should throw an exception when there is an error retrieving the students")
    void recuperarAlunos_shouldThrowException() {
        // Define o comportamento do mock do alunosDAO para lançar uma exceção
        when(alunosDAO.findAll()).thenThrow(new RuntimeException("Erro ao buscar alunos"));

        // Chama o método recuperarAlunos()
        Object response = alunosServiceImpl.recuperarAlunos();

        // Verifica se a resposta é um ResponseEntity
        assertTrue(response instanceof ResponseEntity, "Expected ResponseEntity, but got " + response.getClass().getName());

        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        // Verifica se a resposta é a esperada
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(Collections.singletonMap("message", "Ocorreu um erro ao recuperar os alunos."), responseEntity.getBody());
    }

    @Test
    @DisplayName("Should find an Aluno by ID successfully")
    void buscarAlunoCaso1() {

        // Cria um endereço com logradouro nulo para causar erro na validação
        Endereco endereco = new Endereco(null, "123", "apto 2", "Bairro da Escola", "Cidade do Aprendizado", "Estado do Estudo", "12345678");

        // Cria um responsável
        Responsavel responsavel = new Responsavel("Responsável Nome", "98765432109", endereco, "responsavel@email.com", "41987654321", LocalDate.of(1980, 1, 1), "Ensino Médio Completo", "Professor");

        // Cria um aluno
        Alunos aluno = new Alunos("Aluno Nome", "12345678901", endereco, "aluno@email.com", "41123456789", LocalDate.of(2000, 10, 20), "Ensino Médio Completo", "Estudante", NivelEnum.pre_intermediate_1, NivelEnum.intermediate_1, StatusEnum.ATIVADO, null, responsavel);

        when(alunosDAO.findById(1)).thenReturn(Optional.of(aluno));

        Alunos response = alunosServiceImpl.buscarAluno(1);

        assertThat(response).isNotNull();
        verify(alunosDAO, times(1)).findById(1);

    }

    @Test
    @DisplayName("Should find an Aluno by ID successfully")
    void buscarAlunoCase2() {

        when(alunosDAO.findById(1)).thenReturn(Optional.empty());

        Alunos response = alunosServiceImpl.buscarAluno(1);

        assertThat(response).isNull();
        verify(alunosDAO, times(1)).findById(1);

    }

    @Test
    @DisplayName("Should update an Aluno successfully")
    void atualizarAlunoCase1() {
        // Cria um aluno existente
        Endereco enderecoExistente = new Endereco("Rua A", "123", "apto 1", "Bairro B", "Cidade C", "Estado E", "12345678");
        Responsavel responsavelExistente = new Responsavel("Responsável Nome", "98765432109", enderecoExistente, "responsavel@email.com", "41987654321", LocalDate.of(1980, 1, 1), "Ensino Médio Completo", "Professor");
        Alunos alunoExistente = new Alunos("Aluno Nome", "12345678901", enderecoExistente, "aluno@email.com", "41123456789", LocalDate.of(2000, 10, 20), "Ensino Médio Completo", "Estudante", NivelEnum.pre_intermediate_1, NivelEnum.intermediate_1, StatusEnum.ATIVADO, null, responsavelExistente);

        // Cria um aluno com dados atualizados
        Endereco enderecoAtualizado = new Endereco("Rua Nova", "456", "apto 2", "Bairro Novo", "Cidade Nova", "Estado Novo", "98765432");
        Alunos alunoAtualizado = new Alunos("Aluno Nome Atualizado", "12345678901", enderecoAtualizado, "aluno.atualizado@email.com", "41999998888", LocalDate.of(2001, 11, 21), "Ensino Superior Completo", "Engenheiro", NivelEnum.intermediate_2, NivelEnum.pre_advanced_1, StatusEnum.DESATIVADO, null, responsavelExistente);

        // Define o comportamento do mock do alunosDAO
        when(alunosDAO.findById(1)).thenReturn(Optional.of(alunoExistente));
        when(alunosDAO.save(any(Alunos.class))).thenAnswer(invocation -> {
            Alunos alunoASerSalvo = invocation.getArgument(0);

            assertEquals(alunoExistente.getIdAlunoMatricula(), alunoASerSalvo.getIdAlunoMatricula());
            return alunoASerSalvo;
        });

        // Chama o método que você quer testar
        ResponseEntity<Alunos> response = alunosServiceImpl.atualizarAluno(1, alunoAtualizado);

        // Verifica se a resposta é a esperada
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(alunosDAO, times(1)).save(alunoExistente);
    }

    @Test
    @DisplayName("Should return 404 NOT FOUND when Aluno ID not found")
    void atualizarAlunoCase2() {
        // Cria um aluno com dados atualizados
        Endereco enderecoAtualizado = new Endereco("Rua Nova", "456", "apto 2", "Bairro Novo", "Cidade Nova", "Estado Novo", "98765432");
        Alunos alunoAtualizado = new Alunos("Aluno Nome Atualizado", "12345678901", enderecoAtualizado, "aluno.atualizado@email.com", "41999998888", LocalDate.of(2001, 11, 21), "Ensino Superior Completo", "Engenheiro", NivelEnum.intermediate_2, NivelEnum.pre_advanced_1, StatusEnum.DESATIVADO, null, null);

        // Define o comportamento do mock do alunosDAO para retornar vazio
        when(alunosDAO.findById(1)).thenReturn(Optional.empty());

        // Chama o método que você quer testar
        ResponseEntity<Alunos> response = alunosServiceImpl.atualizarAluno(1, alunoAtualizado);

        // Verifica se a resposta é a esperada
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        // Verifica se o método save não foi chamado
        verify(alunosDAO, never()).save(any(Alunos.class));
    }

    @Test
    void atualizarStatusAlunoCase1() {

        Endereco enderecoExistente = new Endereco("Rua A", "123", "apto 1", "Bairro B", "Cidade C", "Estado E", "12345678");
        Responsavel responsavelExistente = new Responsavel("Responsável Nome", "98765432109", enderecoExistente, "responsavel@email.com", "41987654321", LocalDate.of(1980, 1, 1), "Ensino Médio Completo", "Professor");
        Alunos alunoExistente = new Alunos("Aluno Nome", "12345678901", enderecoExistente, "aluno@email.com", "41123456789", LocalDate.of(2000, 10, 20), "Ensino Médio Completo", "Estudante", NivelEnum.pre_intermediate_1, NivelEnum.intermediate_1, StatusEnum.DESATIVADO, null, responsavelExistente);

        // Cria um aluno com dados atualizados
        Endereco enderecoAtualizado = new Endereco("Rua Nova", "456", "apto 2", "Bairro Novo", "Cidade Nova", "Estado Novo", "98765432");
        Alunos alunoAtualizado = new Alunos("Aluno Nome Atualizado", "12345678901", enderecoAtualizado, "aluno.atualizado@email.com", "41999998888", LocalDate.of(2001, 11, 21), "Ensino Superior Completo", "Engenheiro", NivelEnum.intermediate_2, NivelEnum.pre_advanced_1, StatusEnum.ATIVADO, null, responsavelExistente);



        when(alunosDAO.findById(1)).thenReturn(Optional.of(alunoExistente));
        when(alunosDAO.save(any(Alunos.class))).thenAnswer(invocation -> {
            Alunos alunoASerSalvo = invocation.getArgument(0);

            assertEquals(alunoExistente.getIdAlunoMatricula(), alunoASerSalvo.getIdAlunoMatricula());
            return alunoASerSalvo;
        });

        // Chama o método que você quer testar
        ResponseEntity<Alunos> response = alunosServiceImpl.atualizarAluno(1, alunoAtualizado);

        // Verifica se a resposta é a esperada
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(alunoAtualizado.getStatus(), response.getBody().getStatus());

        verify(alunosDAO, times(1)).save(alunoExistente);

    }

    @Test
    @DisplayName("Should return 404 NOT FOUND when Aluno ID not found")
    void atualizarStatusAlunoCase2() {
        // Cria um aluno com status atualizado
        Alunos alunoAtualizado = new Alunos();
        alunoAtualizado.setStatus(StatusEnum.ATIVADO);

        // Define o comportamento do mock do alunosDAO para retornar vazio
        when(alunosDAO.findById(1)).thenReturn(Optional.empty());

        // Chama o método que você quer testar
        ResponseEntity<?> response = alunosServiceImpl.atualizarStatusAluno(1, alunoAtualizado);

        // Verifica se a resposta é a esperada
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        // Verifica se o método save não foi chamado
        verify(alunosDAO, never()).save(any(Alunos.class));
    }

    @Test
    @DisplayName("Should find Alunos by Turma ID successfully")
    void buscarAlunosPorTurmaCaso1() {

        // Cria um endereço
        Endereco endereco = new Endereco("Rua dos Alunos", "123", "apto 2", "Bairro da Escola", "Cidade do Aprendizado", "Estado do Estudo", "12345678");

        // Cria um responsável
        Responsavel responsavel = new Responsavel("Responsável Nome", "98765432109", endereco, "responsavel@email.com", "41987654321", LocalDate.of(1980, 1, 1), "Ensino Médio Completo", "Professor");

        // Cria um professor
        Endereco enderecoProfessor = new Endereco("Rua dos Professores", "456", null, "Bairro dos Educadores", "Cidade do Saber", "Estado do Conhecimento", "87654321");
        Professor professor = new Professor("Professor Nome", "99988877766", enderecoProfessor, "professor@email.com", "41999998888", "loginProfessor", "senhaProfessor", RolesEnum.PROF);

        // Cria uma turma
        Turma turma = new Turma("Turma A", professor);

        // Cria 3 alunos e associa à turma
        Alunos aluno1 = new Alunos("Aluno 1", "12345678901", endereco, "aluno1@email.com", "41123456789", LocalDate.of(2000, 10, 20), "Ensino Médio Completo", "Estudante", NivelEnum.pre_intermediate_1, NivelEnum.intermediate_1, StatusEnum.ATIVADO, turma, responsavel);
        Alunos aluno2 = new Alunos("Aluno 2", "12345678902", endereco, "aluno2@email.com", "41123456789", LocalDate.of(2001, 11, 21), "Ensino Médio Completo", "Estudante", NivelEnum.pre_intermediate_1, NivelEnum.intermediate_1, StatusEnum.ATIVADO, turma, responsavel);
        Alunos aluno3 = new Alunos("Aluno 3", "12345678903", endereco, "aluno3@email.com", "41123456789", LocalDate.of(2002, 12, 22), "Ensino Médio Completo", "Estudante", NivelEnum.pre_intermediate_1, NivelEnum.intermediate_1, StatusEnum.ATIVADO, turma, responsavel);

        // Cria uma lista com os alunos
        List<Alunos> alunosList = new ArrayList<>();
        alunosList.add(aluno1);
        alunosList.add(aluno2);
        alunosList.add(aluno3);

        when(alunosDAO.findByTurmaIdTurma(1)).thenReturn(alunosList);

        List<Alunos> alunosRecuperados = alunosServiceImpl.buscarAlunosPorTurma(1);

        assertThat(alunosRecuperados).isNotNull();
        assertThat(alunosRecuperados.size()).isEqualTo(3);

        verify(alunosDAO, times(1)).findByTurmaIdTurma(1);

    }

    @Test
    @DisplayName("Should find Alunos by Turma ID successfully")
    void buscarAlunosPorTurmaCaso2() {


        // Cria uma lista com os alunos
        List<Alunos> alunosList = new ArrayList<>();

        when(alunosDAO.findByTurmaIdTurma(1)).thenReturn(alunosList);

        List<Alunos> alunosRecuperados = alunosServiceImpl.buscarAlunosPorTurma(1);

        assertThat(alunosRecuperados).isEmpty();
        assertThat(alunosRecuperados.size()).isEqualTo(0);

        verify(alunosDAO, times(1)).findByTurmaIdTurma(1);

    }

    @Test
    @DisplayName("Should delete an Aluno successfully")
    void deletarAlunoCase1() {

        // Endereco endereco = new Endereco("Rua dos Alunos", "123", "apto 2", "Bairro da Escola", "Cidade do Aprendizado", "Estado do Estudo", "12345678");
        Endereco endereco = new Endereco("Rua dos Alunos", "123", "apto 2", "Bairro da Escola", "Cidade do Aprendizado", "Estado do Estudo", "12345678");

        // Cria um responsável
        Responsavel responsavel = new Responsavel("Responsável Nome", "98765432109", endereco, "responsavel@email.com", "41987654321", LocalDate.of(1980, 1, 1), "Ensino Médio Completo", "Professor");

        // Cria um aluno
        Alunos aluno1 = new Alunos("Aluno 1", "12345678901", endereco, "aluno1@email.com", "41123456789", LocalDate.of(2000, 10, 20), "Ensino Médio Completo", "Estudante", NivelEnum.pre_intermediate_1, NivelEnum.intermediate_1, StatusEnum.ATIVADO, null, responsavel);

        when(alunosDAO.findById(1)).thenReturn(Optional.of(aluno1));

        ResponseEntity<?> response = alunosServiceImpl.deletarAluno(1);

        // Asserções para verificar o comportamento do método
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonMap("message", "Aluno excluído com sucesso."), response.getBody());
        verify(alunosDAO, times(1)).delete(aluno1);
    }
}