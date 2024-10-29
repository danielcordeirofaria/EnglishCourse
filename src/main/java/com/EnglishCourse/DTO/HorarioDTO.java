package com.EnglishCourse.DTO;

public class HorarioDTO {

    private int idHorario;
    private int idTurma;
    private String nomeTurma;
    private int idProfessor;
    private String nomeProfessor;
    private String diaSemana;
    private String horarioInicio;
    private String horarioFim;

    // Construtor
    public HorarioDTO(int idHorario, int idTurma, String nomeTurma, int idProfessor, String nomeProfessor, String diaSemana, String horarioInicio, String horarioFim) {
        this.idHorario = idHorario;
        this.idTurma = idTurma;
        this.nomeTurma = nomeTurma;
        this.idProfessor = idProfessor;
        this.nomeProfessor = nomeProfessor;
        this.diaSemana = diaSemana;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
    }

    // Getters e Setters
    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

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

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(String horarioFim) {
        this.horarioFim = horarioFim;
    }
}
