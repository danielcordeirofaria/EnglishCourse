package com.EnglishCourse.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_alunos")
public class Alunos extends Pessoas{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno_matricula")
    private int idAlunoMatricula;

    @Column(name = "data_de_nascimento", nullable = false)
    private LocalDate dataDeNascimento;

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

    @Column(name = "status", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_turma", referencedColumnName = "id_turma", nullable = true)
    private Turma turma;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_responsavel", referencedColumnName = "id_responsavel", nullable = true)
    private Responsavel responsavel;

    public Alunos(String nome, String cpf, Endereco endereco, String email, String whatsapp, LocalDate dataDeNascimento, String formacao, String profissao, NivelEnum moduloFeito, NivelEnum nivel, StatusEnum status, Turma turma, Responsavel responsavel) {
        super(nome, cpf, endereco, email, whatsapp);
        this.dataDeNascimento = dataDeNascimento;
        this.formacao = formacao;
        this.profissao = profissao;
        this.moduloFeito = moduloFeito;
        this.nivel = nivel;
        this.status = status;
        this.turma = turma;
        this.responsavel = responsavel;
    }

    public Alunos(String nome, String cpf, Endereco endereco, String email, String whatsapp, LocalDate dataDeNascimento, String formacao, String profissao, NivelEnum moduloFeito, NivelEnum nivel, StatusEnum status) {
        super(nome, cpf, endereco, email, whatsapp);
        this.dataDeNascimento = dataDeNascimento;
        this.formacao = formacao;
        this.profissao = profissao;
        this.moduloFeito = moduloFeito;
        this.nivel = nivel;
        this.status = status;
    }

    public Alunos(String nome, String cpf, Endereco endereco, String email, String whatsapp, LocalDate dataDeNascimento, String formacao, String profissao, NivelEnum moduloFeito, NivelEnum nivel) {
        super(nome, cpf, endereco, email, whatsapp);
        this.dataDeNascimento = dataDeNascimento;
        this.formacao = formacao;
        this.profissao = profissao;
        this.moduloFeito = moduloFeito;
        this.nivel = nivel;
    }

    public Alunos() {}


    // Getters e setters
    public int getIdAlunoMatricula() {
        return idAlunoMatricula;
    }

    public void setIdAlunoMatricula(int idAlunoMatricula) {
        this.idAlunoMatricula = idAlunoMatricula;
    }


    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
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

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }
}
