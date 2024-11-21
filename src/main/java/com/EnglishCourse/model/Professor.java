package com.EnglishCourse.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_profs")
public class Professor extends Pessoas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor")
    private int idProfessor;

    @Column(name = "login", length = 40, nullable = false, unique = true)
    private String login;

    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @Column(name = "roles")
    private RolesEnum roles;

    public Professor(String nome, String cpf, Endereco endereco, String email, String login, String password, String whatsapp, RolesEnum roles) {
        super(nome, cpf, endereco, email, whatsapp);
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public Professor() {
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
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

    public RolesEnum getRoles() {
        return roles;
    }

    public void setRoles(RolesEnum roles) {
        this.roles = roles;
    }
}