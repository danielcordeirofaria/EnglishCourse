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

    @Column(name = "profissao", length = 255, nullable = false)
    private String profissao;

    @Column(name = "modulo_feito", length = 24, nullable = false)
    @Enumerated(EnumType.STRING)
    private NivelEnum moduloFeito;

    @Column(name = "nivel", length = 24, nullable = false)
    @Enumerated(EnumType.STRING)
    private NivelEnum nivel;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "id_professor", referencedColumnName = "id_professor", nullable = false)
//    private Professor idProfessor;

    @Column(name = "status", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_turma", referencedColumnName = "id_turma", nullable = true)
    private Turma turma;


    public Alunos() {}

    public Alunos(String nome, Endereco endereco, LocalDate dataDeNascimento, String cpf, String email, String formacao, String profissao, NivelEnum moduloFeito, NivelEnum nivel, Turma turma) {
        this.nome = nome;
        this.endereco = endereco;
        this.dataDeNascimento = dataDeNascimento;
        this.cpf = cpf;
        this.email = email;
        this.formacao = formacao;
        this.profissao = profissao;
        this.moduloFeito = moduloFeito;
        this.nivel = nivel;
        this.turma = turma;
    }

    // Getters e setters
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

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public NivelEnum getModuloFeito() {
        return moduloFeito;
    }

    public void setModuloFeito(NivelEnum moduloFeito) {
        this.moduloFeito = moduloFeito;
    }

    public NivelEnum getNivel() {
        return nivel;
    }

    public void setNivel(NivelEnum nivel) {
        this.nivel = nivel;
    }

    public Turma getTurma() { return turma; }

    public void setTurma(Turma idTurma) { this.turma = idTurma; }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
