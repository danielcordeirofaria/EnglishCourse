package com.EnglishCourse.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_profs")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor")
    private int idProfessor;

    @Column(name = "nome_professor", length = 255, nullable = false)
    private String nomeProfessor;

    @Column(name = "cpfCnpj", length = 40, nullable = false, unique = true)
    private String cpfCnpj;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id_endereco")
    private Endereco endereco;

    @Column(name = "email", length = 40, nullable = false)
    private String email;

    @Column(name = "login", length = 40, nullable = false, unique = true)
    private String login;

    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @Column(name = "whatsapp", length = 13, nullable = false)
    private String whatsapp;

    public Professor(int idProfessor, String nomeProfessor, String cpfCnpj, Endereco endereco, String email, String login, String password, String whatsapp) {
        this.idProfessor = idProfessor;
        this.nomeProfessor = nomeProfessor;
        this.cpfCnpj = cpfCnpj;
        this.endereco = endereco;
        this.email = email;
        this.login = login;
        this.password = password;
        this.whatsapp = whatsapp;
    }

    public Professor() {

    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }
}
