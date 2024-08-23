package com.EnglishCourse.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turma")
    private int idTurma;

    @Column(name = "nome_turma", length = 255, nullable = false)
    private String nomeTurma;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_professor", referencedColumnName = "id_professor", nullable = false)
    private Professor professor;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Horario> horarios;

    public Turma() {}

    public Turma(String nomeTurma, Professor professor, List<Horario> horarios) {
        this.nomeTurma = nomeTurma;
        this.professor = professor;
        this.horarios = horarios;
    }

    // Getters e setters
    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }
}
