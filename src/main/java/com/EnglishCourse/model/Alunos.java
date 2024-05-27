package com.EnglishCourse.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_alunos")
public class Alunos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno_matricula")
    private int idAlunoMatricula;

    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id_endereco")
    private Endereco endereco;

    @Column(name = "data_de_nascimento", nullable = false)
    private LocalDate dataDeNascimento;

    @Column(name = "cpf", length = 14, unique = true, nullable = false)
    private String cpf;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "formacao", length = 255, nullable = false)
    private String formacao;

    @Column(name = "modulo_feito", length = 255, nullable = false)
    private String moduloFeito;

    @Column(name = "nivel", length = 255, nullable = false)
    private String nivel;

    @Column(name = "id_pacote")
    private int idPacote;

    public Alunos() {}

    public int getIdAlunoMatricula() {
        return idAlunoMatricula;
    }

    public void setIdAlunoMatricula(int idAlunoMatricula) {
        this.idAlunoMatricula = idAlunoMatricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public String getModuloFeito() {
        return moduloFeito;
    }

    public void setModuloFeito(String moduloFeito) {
        this.moduloFeito = moduloFeito;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getIdPacote() {
        return idPacote;
    }

    public void setIdPacote(int idPacote) {
        this.idPacote = idPacote;
    }
}
