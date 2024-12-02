package com.maurosagasti.api_sistema_academico.controller;


import com.maurosagasti.api_sistema_academico.business.AlumnoService;
import com.maurosagasti.api_sistema_academico.model.Alumno;
import com.maurosagasti.api_sistema_academico.model.dto.AlumnoDto;
import com.maurosagasti.api_sistema_academico.model.exception.CorrelativasNoAprobadasException;
import com.maurosagasti.api_sistema_academico.model.exception.EstadoIncorrectoException;
import com.maurosagasti.api_sistema_academico.model.exception.NotaIncorrectaException;
import com.maurosagasti.api_sistema_academico.persistence.exception.AlumnoNotFoundException;
import com.maurosagasti.api_sistema_academico.persistence.exception.AsignaturaNotFoundException;
import com.maurosagasti.api_sistema_academico.persistence.exception.MateriaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@RequestMapping("alumno")

public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    //Crear Alumno por DNI
    @PostMapping
    public ResponseEntity<Alumno> crearAlumno(@RequestBody AlumnoDto alumnoDto){
        if (alumnoDto.getDni() < 0) {
            throw new IllegalArgumentException("El DNI no puede ser negativo");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.crearAlumno(alumnoDto));
    }

    //Actualizar Alumno por DNI
    @PutMapping("/{dniAlumno}")
    public ResponseEntity<Alumno> modificarAlumno(@PathVariable int dniAlumno, @RequestBody AlumnoDto alumnoDto) throws IllegalArgumentException {
        return ResponseEntity.ok(alumnoService.modificarAlumno(dniAlumno, alumnoDto));
    }

    //Eliminar Alumno por DNI
    @DeleteMapping("/{dniAlumno}")
    public ResponseEntity<String> eliminarAlumno(@PathVariable int dniAlumno) {
        alumnoService.eliminarAlumno(dniAlumno);
        return ResponseEntity.ok("Alumno " + dniAlumno + " fue eliminado.");
    }

    //Cursar una asignatura por ID
    @PutMapping("/{dniAlumno}/asignatura/{idAsignatura}/cursar")
    public ResponseEntity<String> cursarAsignatura(@PathVariable int dniAlumno, @PathVariable int idAsignatura) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, CorrelativasNoAprobadasException {
        alumnoService.cursarAsignatura(dniAlumno, idAsignatura);

        return ResponseEntity.ok("La asignatura "+ alumnoService.getNombreAsignatura(dniAlumno, idAsignatura) + " fue cursada con éxito.");
    }

    //Aprobar una asignatura con nota por ID
    @PutMapping("/{dniAlumno}/asignatura/{idAsignatura}/aprobar/{nota}")
    public ResponseEntity<String> aprobarAsignatura(@PathVariable int dniAlumno, @PathVariable int idAsignatura, @PathVariable int nota) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException, CorrelativasNoAprobadasException, EstadoIncorrectoException, NotaIncorrectaException, MateriaNotFoundException {
        alumnoService.aprobarAsignatura(dniAlumno, idAsignatura, nota);
        return ResponseEntity.ok("La asignatura "+ alumnoService.getNombreAsignatura(dniAlumno, idAsignatura) + " fue aprobada con " + alumnoService.getNotaAsignatura(dniAlumno, idAsignatura) + ".");
    }

    //Perder regularidad de una asignatura por ID
    @PutMapping("/{dniAlumno}/asignatura/{idAsignatura}/perder")
    public ResponseEntity<String> perderAsignatura(@PathVariable int dniAlumno, @PathVariable int idAsignatura) throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        alumnoService.perderAsignatura(dniAlumno, idAsignatura);
        return ResponseEntity.ok("Se ha perdido la regularidad de la asignatura " + alumnoService.getNombreAsignatura(dniAlumno, idAsignatura) + ".");

    }



}
