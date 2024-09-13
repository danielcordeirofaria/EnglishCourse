package com.EnglishCourse.DTO;

public class ProfessorDTO {
    private int idProfessor;
    private String nomeProfessor;
    private String whatsapp;

    // Construtor
    public ProfessorDTO(int idProfessor, String nomeProfessor, String whatsapp) {
        this.idProfessor = idProfessor;
        this.nomeProfessor = nomeProfessor;
        this.whatsapp = whatsapp;
    }

    // Getters e Setters
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

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }
}
