package com.maurosagasti.api_sistema_academico.business;

import com.maurosagasti.api_sistema_academico.model.Alumno;
import com.maurosagasti.api_sistema_academico.model.dto.AlumnoDto;
import com.maurosagasti.api_sistema_academico.model.exception.EstadoIncorrectoException;
import com.maurosagasti.api_sistema_academico.model.exception.NotaIncorrectaException;
import com.maurosagasti.api_sistema_academico.persistence.exception.AlumnoNotFoundException;
import com.maurosagasti.api_sistema_academico.persistence.exception.AsignaturaNotFoundException;

public interface AlumnoService {

    // Crear alumno

    Alumno crearAlumno(AlumnoDto inputData) throws IllegalArgumentException;

    // Modificar alumno

    Alumno modificarAlumno(Integer dni, AlumnoDto inputData) throws IllegalArgumentException, AlumnoNotFoundException;

    // Eliminar alumno

    void eliminarAlumno(Integer dni) throws AlumnoNotFoundException;

    //Obtener nombre de asignatura

    String getNombreAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException;

    //Obtener nota de asignatura

    Integer getNotaAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException;

    // Cursar asignatura

    void cursarAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException;

    // Aprobar asignatura

    void aprobarAsignatura(Integer dni, Integer asignaturaId, int nota) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, EstadoIncorrectoException, NotaIncorrectaException;

    // Perder regularidad de una asignatura

    void perderAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException;
}
