package com.maurosagasti.api_sistema_academico.persistence.imp;

import com.maurosagasti.api_sistema_academico.model.Alumno;
import com.maurosagasti.api_sistema_academico.persistence.AlumnoDao;
import com.maurosagasti.api_sistema_academico.persistence.MateriaDao;
import com.maurosagasti.api_sistema_academico.persistence.exception.AlumnoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AlumnoDaoImpl implements AlumnoDao {
    private Map<Integer, Alumno> alumnos = new HashMap<>();


    private MateriaDao materiaDao;


    @Autowired
    public void setMateriaDao(MateriaDao materiaDao) {
        this.materiaDao = materiaDao;
    }

    @Override
    public Alumno buscarPorDni(Integer dni) throws AlumnoNotFoundException {
        Alumno alumno = alumnos.get(dni);
        if (alumno == null) {
            throw new AlumnoNotFoundException("No se encontró el alumno con el ID: " + dni);
        }

        return alumno;
    }

    @Override
    public Alumno update(Integer dni, Alumno a) throws AlumnoNotFoundException {
        Alumno alumno = alumnos.get(dni);
        if (alumno == null) {
            throw new AlumnoNotFoundException("No se encontró el alumno con el DNI: " + dni);
        }

        alumno.setNombre(a.getNombre());
        alumno.setApellido(a.getApellido());
        alumno.setDni(a.getDni());
        alumnos.remove(alumno.getDni());
        alumnos.put(alumno.getDni(), alumno);

        return alumno;
    }

    @Override
    public Alumno save(Alumno a) throws IllegalArgumentException {
        alumnos.put(a.getDni(), a);
        return a;
    }

    @Override
    public void delete(Integer dni) throws AlumnoNotFoundException {
        Alumno alumno = alumnos.get(dni);
        if (alumno == null) {
            throw new AlumnoNotFoundException("No se encontró el alumno con el DNI: " + dni);
        }
        alumnos.remove(dni);
    }
}
