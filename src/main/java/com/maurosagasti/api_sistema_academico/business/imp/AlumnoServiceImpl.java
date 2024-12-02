package com.maurosagasti.api_sistema_academico.business.imp;

import com.maurosagasti.api_sistema_academico.business.AlumnoService;
import com.maurosagasti.api_sistema_academico.model.Alumno;
import com.maurosagasti.api_sistema_academico.model.dto.AlumnoDto;
import com.maurosagasti.api_sistema_academico.model.exception.EstadoIncorrectoException;
import com.maurosagasti.api_sistema_academico.model.exception.NotaIncorrectaException;
import com.maurosagasti.api_sistema_academico.persistence.AlumnoDao;
import com.maurosagasti.api_sistema_academico.persistence.exception.AlumnoNotFoundException;
import com.maurosagasti.api_sistema_academico.persistence.exception.AsignaturaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoDao alumnoDao;

    @Autowired
    public AlumnoServiceImpl(AlumnoDao alumnoDao) {
        this.alumnoDao = alumnoDao;
    }

    @Override
    public Alumno crearAlumno(AlumnoDto inputData) throws IllegalArgumentException {
        Alumno a = new Alumno();
        a.setNombre(inputData.getNombre());
        a.setApellido(inputData.getApellido());
        a.setDni(inputData.getDni());
        alumnoDao.save(a);
        return a;
    }

    @Override
    public Alumno modificarAlumno(Integer dni, AlumnoDto inputData) throws IllegalArgumentException, AlumnoNotFoundException {
        Alumno a = alumnoDao.buscarPorDni(dni);
        a.setNombre(inputData.getNombre());
        a.setApellido(inputData.getApellido());
        a.setDni(inputData.getDni());
        alumnoDao.update(dni, a);
        return a;
    }

    @Override
    public void eliminarAlumno(Integer dni) throws AlumnoNotFoundException {
        alumnoDao.delete(dni);
    }

    @Override
    public String getNombreAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Alumno a = alumnoDao.buscarPorDni(dni);
        return a.getNombreAsignatura(asignaturaId);
    }

    @Override
    public Integer getNotaAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        if (asignaturaId == null || asignaturaId < 0) {
            throw new IllegalArgumentException("El ID de la asignatura no puede ser nulo o negativo");
        }
        Alumno a = alumnoDao.buscarPorDni(dni);
        if (a == null) {
            throw new AlumnoNotFoundException("No se encontró el alumno con el DNI: " + dni);
        }
        if (asignaturaId >= a.getAsignaturas().size()) {
            throw new AsignaturaNotFoundException("No se encontró la asignatura con el ID: " + asignaturaId);
        }
        return a.getNotaAsignatura(asignaturaId);
    }

    @Override
    public void cursarAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        if (asignaturaId < 0 || asignaturaId > 3){
            throw new AsignaturaNotFoundException("No se encontró la asignatura con el ID: " + asignaturaId);
        }
        Alumno a = alumnoDao.buscarPorDni(dni);
        a.cursarAsignatura(asignaturaId);
    }

    @Override
    public void aprobarAsignatura(Integer dni, Integer asignaturaId, int nota) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, EstadoIncorrectoException, NotaIncorrectaException {
        if (asignaturaId < 0 || asignaturaId > 3){
            throw new AsignaturaNotFoundException("No se encontró la asignatura con el ID: " + asignaturaId);
        }
        Alumno a = alumnoDao.buscarPorDni(dni);

        if (nota < 4 || nota > 10){
            throw new NotaIncorrectaException("La nota debe ser mayor o igual a 4 y menor o igual a 10");
        } else {
            a.aprobarAsignatura(asignaturaId, nota);
        }
    }

    @Override
    public void perderAsignatura(Integer dni, Integer asignaturaId) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        if (asignaturaId < 0 || asignaturaId > 3){
            throw new IllegalArgumentException("No se encontró la asignatura con el ID: " + asignaturaId);
        }

        Alumno a = alumnoDao.buscarPorDni(dni);
        a.perderAsignatura(asignaturaId);
    }
}
