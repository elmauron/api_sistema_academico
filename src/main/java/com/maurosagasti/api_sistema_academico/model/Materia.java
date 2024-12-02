package com.maurosagasti.api_sistema_academico.model;

import java.util.ArrayList;
import java.util.List;

public class Materia {

    private int materiaId;
    private static int ultimoId = 0;
    private String nombre;
    private int anio;
    private int cuatrimestre;
    private int carreraId;

    private List<Integer> correlativas;

    public Materia() {
        this.materiaId = getProximoId();
        correlativas = new ArrayList<Integer>();
    }

    public Materia(String nombre, int anio, int cuatrimestre, List<Integer> correlativas, int carreraId, int materiaId) {
        this.materiaId = materiaId;
        this.nombre = nombre;
        this.anio = anio;
        this.cuatrimestre = cuatrimestre;
        this.correlativas = correlativas;
        this.carreraId = carreraId;
    }

    //Getters & Setters

    public int getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(int materiaId) {
        this.materiaId = materiaId;
    }

    public static int getProximoId() {
        return ultimoId++;
    }

    public static void setUltimoId(int ultimoId) {
        Materia.ultimoId = ultimoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(int cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public List<Integer> getCorrelativas() {
        return correlativas;
    }

    public void setCorrelativas(List<Integer> correlativas) {
        this.correlativas = correlativas;
    }

    public int getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(int carreraId) {
        this.carreraId = carreraId;
    }

    //metodos extras


}
