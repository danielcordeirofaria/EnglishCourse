package com.EnglishCourse.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "tbl_horarios")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private int idHorario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_turma", referencedColumnName = "id_turma", nullable = false)
    private Turma turma;

    @Column(name = "dia_semana", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    @Column(name = "horario_inicio", nullable = false)
    private LocalTime horarioInicio;

    @Column(name = "horario_fim", nullable = false)
    private LocalTime horarioFim;

    public Horario(Turma turma, DiaSemana diaSemana, LocalTime horarioInicio, LocalTime horarioFim) {
        this.turma = turma;
        this.diaSemana = diaSemana;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }

    public Horario() {}

    // Getters e setters
    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalTime horarioFim) {
        this.horarioFim = horarioFim;
    }
}
