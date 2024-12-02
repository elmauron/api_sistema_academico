package com.maurosagasti.api_sistema_academico.model;

import java.util.ArrayList;
import java.util.List;

public class Carrera {

    private String nombre;
    private int id;
    private static int UltimoId = 0;
    private int id_departamento;
    private int cuatrimestres;
    private List<Materia> materias;

    public Carrera() {
        this.id = getProximoId();
        materias = new ArrayList<>();
    }

    //Getters & Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getUltimoId() {
        return UltimoId;
    }

    public static void setUltimoId(int lastId) {
        Carrera.UltimoId = lastId;
    }

    public static int getProximoId() {
        return UltimoId++;
    }

    public int getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(int id_departamento) {
        this.id_departamento = id_departamento;
    }

    public int getCuatrimestres() {
        return cuatrimestres;
    }

    public void setCuatrimestres(int cuatrimestres) {
        this.cuatrimestres = cuatrimestres;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
}
