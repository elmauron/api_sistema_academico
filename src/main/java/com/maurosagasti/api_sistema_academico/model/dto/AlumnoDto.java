package com.maurosagasti.api_sistema_academico.model.dto;

public class AlumnoDto {
    private String nombre;
    private String apellido;
    private int dni;

    // Getters & Setters

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public int getDni() {
        return this.dni;
    }

    public void setDni(int dni){
        this.dni = dni;
    }


}