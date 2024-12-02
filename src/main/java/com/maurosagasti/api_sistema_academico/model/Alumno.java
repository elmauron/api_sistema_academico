package com.maurosagasti.api_sistema_academico.model;

import com.maurosagasti.api_sistema_academico.persistence.exception.AsignaturaNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Alumno {
    private String nombre;
    private String apellido;
    private int dni;
    private List<Asignatura> asignaturas;

    //Constructores con asignaturas hardcodeadas

    public Alumno() {
        asignaturas = Arrays.asList(
                new Asignatura(new Materia("Laboratorio 1", 1, 1, Arrays.asList(), 1, 1)),
                new Asignatura(new Materia("Laboratorio 2", 1, 2, Arrays.asList(1), 1, 2)),
                new Asignatura(new Materia("Laboratorio 3", 2, 1, Arrays.asList(1, 2), 1, 3)),
                new Asignatura(new Materia("Laboratorio 4", 2, 2, Arrays.asList(1, 2, 3), 1, 4))

        );

    }

    //Getters & Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    //metodos extras con uso en AlumnoService

    public String getNombreAsignatura(Integer asignaturaId) throws AsignaturaNotFoundException {
        if (asignaturaId == null || asignaturaId < 0 || asignaturaId >= asignaturas.size()) {
            throw new AsignaturaNotFoundException("No se encontró la asignatura con el ID: " + asignaturaId);
        }
        return asignaturas.get(asignaturaId).getNombreMateria();
    }

    public void cursarAsignatura(Integer asignaturaId) throws AsignaturaNotFoundException {
        if (asignaturaId == null || asignaturaId < 0) {
            throw new IllegalArgumentException("El ID de la asignatura no puede ser nulo o negativo");
        }
        asignaturas.get(asignaturaId).cursar();    }

    public void aprobarAsignatura(Integer asignaturaId, int nota) throws AsignaturaNotFoundException{
        if (nota < 6 || nota > 10){
            throw new IllegalArgumentException("La nota ingresada no es válida para aprobar una asignatura");
        }
        asignaturas.get(asignaturaId).aprobar(nota);
    }

    public void perderAsignatura(Integer asignaturaId) throws AsignaturaNotFoundException {
        asignaturas.get(asignaturaId).perder();
    }

    public Integer getNotaAsignatura(Integer asignaturaId) throws AsignaturaNotFoundException {
        return asignaturas.get(asignaturaId).getNota().orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumno alumno = (Alumno) o;
        return Objects.equals(dni, alumno.dni) &&
                Objects.equals(nombre, alumno.nombre) &&
                Objects.equals(apellido, alumno.apellido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni, nombre, apellido);
    }


}
