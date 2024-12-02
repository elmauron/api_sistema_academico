package com.maurosagasti.api_sistema_academico.persistence;

import com.maurosagasti.api_sistema_academico.model.Alumno;
import com.maurosagasti.api_sistema_academico.persistence.exception.AlumnoNotFoundException;

public interface AlumnoDao {

    Alumno buscarPorDni(Integer dni) throws AlumnoNotFoundException;

    Alumno update(Integer dni, Alumno a) throws AlumnoNotFoundException;

    Alumno save(Alumno a) throws IllegalArgumentException;

    void delete(Integer dni) throws AlumnoNotFoundException;
}
