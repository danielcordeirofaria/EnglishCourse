package com.EnglishCourse.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_responsaveis")
public class Responsavel extends Pessoas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_responsavel")
    private int idResponsavel;

    @Column(name = "data_de_nascimento", nullable = false)
    private LocalDate dataDeNascimento;

    @Column(name = "formacao", length = 255, nullable = false)
    private String formacao;

    @Column(name = "profissao", length = 255, nullable = false)
    private String profissao;

    public Responsavel(String nome, String cpf, Endereco endereco, String email, String whatsapp, LocalDate dataDeNascimento, String formacao, String profissao) {
        super(nome, cpf, endereco, email, whatsapp); // Chama o construtor da superclasse
        this.dataDeNascimento = dataDeNascimento;
        this.formacao = formacao;
        this.profissao = profissao;
    }

    public Responsavel() {}

    public int getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(int idResponsavel) {
        this.idResponsavel = idResponsavel;
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
}
