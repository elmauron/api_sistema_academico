package com.maurosagasti.api_sistema_academico.model;

import java.util.Optional;

public class Asignatura {

    private Materia materia;
    private EstadoAsignatura estado;
    private Integer nota;


    private Profesor profesor;


    public Asignatura() {

    }

    public Asignatura(Materia materia) {
        this.materia = materia;
        this.estado = EstadoAsignatura.NO_CURSADA;
        this.nota = null;

    }

    //Getters & Setters

    public Optional<Integer> getNota() {
        return Optional.ofNullable(this.nota);
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Materia getMateria() {
        return this.materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public String getNombreMateria() {
        return this.materia.getNombre();
    }

    public void setNombreMateria(String nombre) {
        this.materia.setNombre(nombre);
    }

    public Profesor getProfesor() {
        return this.profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public EstadoAsignatura getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoAsignatura estado) {
        this.estado = estado;
    }

    // Otros metodos

    public void perder() {
        this.estado = EstadoAsignatura.DESAPROBADA;
    }

    public void cursar() {
        this.estado = EstadoAsignatura.CURSADA;
    }

    public void aprobar(Integer nota) {

        this.estado = EstadoAsignatura.APROBADA;
        this.nota = nota;
    }

}
