package com.maurosagasti.api_sistema_academico.model.dto;

public class MateriaDto {
    private String nombre;
    private int anio;
    private int cuatrimestre;
    private long idProfesor;
    private int idCarrera;

    // Getters & Setters

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public int getAnio() {
        return this.anio;
    }

    public void setAnio(int anio){
        this.anio = anio;
    }

    public int getCuatrimestre() {
        return this.cuatrimestre;
    }

    public void setCuatrimestre(int cuatrimestre){
        this.cuatrimestre = cuatrimestre;
    }

    public long getProfesorId() {
        return this.idProfesor;
    }

    public void setProfesorId(long idProfesor){
        this.idProfesor = idProfesor;
    }

    public int getCarreraId() {
        return this.idCarrera;
    }

    public void setCarreraId(int idCarrera){
        this.idCarrera = idCarrera;
    }


}