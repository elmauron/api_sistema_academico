package com.maurosagasti.api_sistema_academico.model.dto;

public class CarreraDto {
    private String nombre;
    private int id_departamento;
    private int cuatrimestres;

    //Getters & Setters

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public int getDepartamentoId() {
        return this.id_departamento;
    }

    public void setDepartamentoId(int id_departamento){
        this.id_departamento = id_departamento;
    }

    public int getCuatrimestres() {
        return this.cuatrimestres;
    }

    public void setCuatrimestres(int cuatrimestres){
        this.cuatrimestres = cuatrimestres;
    }
}