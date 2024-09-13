package com.EnglishCourse.DTO;

public class TurmaDTO {
    private int idTurma;
    private String nomeTurma;
    private ProfessorDTO professor;

    // Construtor
    public TurmaDTO(int idTurma, String nomeTurma, ProfessorDTO professor) {
        this.idTurma = idTurma;
        this.nomeTurma = nomeTurma;
        this.professor = professor;
    }

    // Getters e Setters
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

    public ProfessorDTO getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorDTO professor) {
        this.professor = professor;
    }
}
