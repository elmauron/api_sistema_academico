package com.maurosagasti.api_sistema_academico.business.implementation;

import com.maurosagasti.api_sistema_academico.business.imp.AlumnoServiceImpl;
import com.maurosagasti.api_sistema_academico.model.Alumno;
import com.maurosagasti.api_sistema_academico.model.Asignatura;
import com.maurosagasti.api_sistema_academico.model.dto.AlumnoDto;
import com.maurosagasti.api_sistema_academico.model.exception.NotaIncorrectaException;
import com.maurosagasti.api_sistema_academico.persistence.AlumnoDao;
import com.maurosagasti.api_sistema_academico.persistence.exception.AlumnoNotFoundException;
import com.maurosagasti.api_sistema_academico.persistence.exception.AsignaturaNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class AlumnoServiceImplTest {

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @Mock
    private AlumnoDao alumnoDaoMock;

    //Testear métodos de la clase AlumnoServiceImpl
    //Test: Crear un alumno
    @Test
    void crearAlumnoTest() throws IllegalArgumentException {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setDni(202020);
        alumnoDto.setNombre("Indiana");
        alumnoDto.setApellido("Jones");

        Mockito.when(alumnoDaoMock.save(any(Alumno.class))).thenReturn(new Alumno());

        Alumno alumno = alumnoService.crearAlumno(alumnoDto);

        assertNotNull(alumno);
        assertEquals(202020, alumno.getDni());
        assertEquals("Indiana", alumno.getNombre());
        assertEquals("Jones", alumno.getApellido());
    }

    //Test: Modificar un alumno
    @Test
    void modificarAlumnoTest() throws AlumnoNotFoundException {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setDni(12345678);
        alumnoDto.setNombre("Henry");
        alumnoDto.setApellido("Jones");

        Mockito.when(alumnoDaoMock.buscarPorDni(12345678)).thenReturn(new Alumno());
        Mockito.when(alumnoDaoMock.update(eq(12345678), any(Alumno.class))).thenReturn(new Alumno());

        Alumno alumno = alumnoService.modificarAlumno(12345678, alumnoDto);

        assertNotNull(alumno);
        assertEquals(12345678, alumno.getDni());
        assertEquals("Henry", alumno.getNombre());
        assertEquals("Jones", alumno.getApellido());
    }

    //Test: Eliminar un alumno
    @Test
    void eliminarAlumnoTest() throws AlumnoNotFoundException {
        Mockito.doNothing().when(alumnoDaoMock).delete(any(Integer.class));

        alumnoService.eliminarAlumno(202020);
    }

    //Test: Eliminar un alumno que no existe
    @Test
    void eliminarAlumnoNoExistenteTest() throws AlumnoNotFoundException {
        Mockito.doThrow(new AlumnoNotFoundException("No se encontró el alumno con el DNI: 202020")).when(alumnoDaoMock).delete(any(Integer.class));

        try {
            alumnoService.eliminarAlumno(202020);
        } catch (AlumnoNotFoundException e) {
            assertEquals("No se encontró el alumno con el DNI: 202020", e.getMessage());
        }
    }

    //Test: Eliminar un alumno con pasaje de parámetro DNI nulo
    @Test
    void eliminarAlumnoDniNuloTest() throws IllegalArgumentException {
        try {
            alumnoService.eliminarAlumno(null);
        } catch (IllegalArgumentException e) {
            assertEquals("El DNI del alumno no puede ser nulo", e.getMessage());
        }
    }

    //Test: Eliminar un alumno con pasaje de parámetro DNI negativo
    @Test
    void eliminarAlumnoDniNegativoTest() throws IllegalArgumentException {
        try {
            alumnoService.eliminarAlumno(-202020);
        } catch (IllegalArgumentException e) {
            assertEquals("El DNI del alumno no puede ser negativo", e.getMessage());
        }
    }

    //Test: Cursar una asignatura
    @Test
    void cursarAsignaturaTest() throws IllegalArgumentException, AlumnoNotFoundException {
        Mockito.when(alumnoDaoMock.buscarPorDni(any(Integer.class))).thenReturn(new Alumno());

        alumnoService.cursarAsignatura(202020, 1);
    }

    //Test: Cursar una asignatura con asignaturaId negativo
    @Test
    void cursarAsignaturaIdNegativoTest() throws IllegalArgumentException, AlumnoNotFoundException {
        assertThrows(AsignaturaNotFoundException.class, () -> {
            alumnoService.cursarAsignatura(202020, -1);
        });
    }

    //Test: Get nombre de asignatura
    @Test
    void getNombreAsignaturaTest() throws IllegalArgumentException, AlumnoNotFoundException {
        Mockito.when(alumnoDaoMock.buscarPorDni(any(Integer.class))).thenReturn(new Alumno());

        alumnoService.getNombreAsignatura(202020, 1);
    }

    //Test: Get nombre de asignatura con asignaturaId no existente, tira AsignaturaNotFoundException
    @Test
    void getNombreAsignaturaIdNoExistenteTest() throws IllegalArgumentException, AsignaturaNotFoundException {
        Mockito.when(alumnoDaoMock.buscarPorDni(909090)).thenReturn(new Alumno());

        assertThrows(AsignaturaNotFoundException.class, () -> alumnoService.getNombreAsignatura(909090, 4));
    }
    //Test: Get nota de asignatura
    @Test
    void getNotaAsignaturaTest() throws IllegalArgumentException, AlumnoNotFoundException {
        Mockito.when(alumnoDaoMock.buscarPorDni(any(Integer.class))).thenReturn(new Alumno());

        alumnoService.getNotaAsignatura(202020, 1);
    }

    //Test: Get nota de asignatura con asignaturaId negativo
    @Test
    void getNotaAsignaturaIdNegativoTest() throws AlumnoNotFoundException {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            alumnoService.getNotaAsignatura(202020, -1);
        });
        assertEquals("El ID de la asignatura no puede ser nulo o negativo", exception.getMessage());
    }

    //Test: Get nota de asignatura con asignaturaId nulo
    @Test
    void getNotaAsignaturaIdNuloTest() throws AlumnoNotFoundException {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            alumnoService.getNotaAsignatura(202020, -1);
        });
        assertEquals("El ID de la asignatura no puede ser nulo o negativo", exception.getMessage());
    }

    //Test: Get nota de asignatura con asignaturaId no existente, tira AsignaturaNotFoundException
    @Test
    void getNotaAsignaturaIdNoExistenteTest() throws IllegalArgumentException, AsignaturaNotFoundException {
        Alumno alumno = new Alumno();
        Mockito.when(alumnoDaoMock.buscarPorDni(202020)).thenReturn(alumno);

        AsignaturaNotFoundException exception = assertThrows(AsignaturaNotFoundException.class, () -> {
            alumnoService.getNotaAsignatura(202020, 4);
        });
        assertEquals("No se encontró la asignatura con el ID: 4", exception.getMessage());
    }

    //Test: Get nota de asignatura con dni no existente, tira AlumnoNotFoundException
    @Test
    void getNotaAsignaturaDniNoExistenteTest() throws IllegalArgumentException, AlumnoNotFoundException {
        Mockito.when(alumnoDaoMock.buscarPorDni(any(Integer.class))).thenThrow(new AlumnoNotFoundException("No se encontró el alumno con el DNI: 202020"));

        try {
            alumnoService.getNotaAsignatura(202020, 1);
        } catch (AlumnoNotFoundException e) {
            assertEquals("No se encontró el alumno con el DNI: 202020", e.getMessage());
        }
    }

    //Test: Aprobar una asignatura
    @Test
    void aprobarAsignaturaTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Mockito.when(alumnoDaoMock.buscarPorDni(any(Integer.class))).thenReturn(new Alumno());

        alumnoService.aprobarAsignatura(202020, 1, 7);
    }

    //Test: Aprobar una asignatura con asignaturaId negativo
    @Test
    void aprobarAsignaturaIdNegativoTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Integer asignaturaId = -1;

        AsignaturaNotFoundException exception = assertThrows(AsignaturaNotFoundException.class, () -> {
            alumnoService.aprobarAsignatura(202020, asignaturaId, 7);
        });
        assertEquals("No se encontró la asignatura con el ID: " + asignaturaId, exception.getMessage());
    }

    //Test: Aprobar una asignatura con asignaturaId nulo
    @Test
    void aprobarAsignaturaIdNuloTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            alumnoService.aprobarAsignatura(202020, null, 7);
        });
        assertEquals("Cannot invoke \"java.lang.Integer.intValue()\" because \"asignaturaId\" is null", exception.getMessage());
    }

    //Test: Aprobar una asignatura con asignaturaId no existente, tira AsignaturaNotFoundException
    @Test
    void aprobarAsignaturaIdNoExistenteTest() throws IllegalArgumentException, AsignaturaNotFoundException {
        try {
            alumnoService.aprobarAsignatura(202020, 4, 7);
        } catch (AsignaturaNotFoundException e) {
            assertEquals("No se encontró la asignatura con el ID: 4", e.getMessage());
        }
    }

    //Test: Aprobar una asignatura con nota menor a 4
    @Test
    void aprobarAsignaturaNotaMenorACuatroTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        NotaIncorrectaException exception = assertThrows(NotaIncorrectaException.class, () -> {
            alumnoService.aprobarAsignatura(202020, 1, 3);
        });
        assertEquals("La nota debe ser mayor o igual a 4 y menor o igual a 10", exception.getMessage());
    }

    //Test: Aprobar una asignatura con nota mayor a 10
    @Test
    void aprobarAsignaturaNotaMayorADiezTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        NotaIncorrectaException exception = assertThrows(NotaIncorrectaException.class, () -> {
            alumnoService.aprobarAsignatura(202020, 1, 11);
        });
        assertEquals("La nota debe ser mayor o igual a 4 y menor o igual a 10", exception.getMessage());
    }

    //Test: Perder una asignatura
    @Test
    void perderAsignaturaTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Mockito.when(alumnoDaoMock.buscarPorDni(any(Integer.class))).thenReturn(new Alumno());

        alumnoService.perderAsignatura(202020, 1);
    }

    //Test: Perder una asignatura con asignaturaId negativo
    @Test
    void perderAsignaturaIdNegativoTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        Integer asignaturaId = -1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            alumnoService.perderAsignatura(202020, asignaturaId);
        });
        assertEquals("No se encontró la asignatura con el ID: " + asignaturaId, exception.getMessage());
    }

    //Test: Perder una asignatura con asignaturaId nulo
    @Test
    void perderAsignaturaIdNuloTest() throws IllegalArgumentException, AlumnoNotFoundException, AsignaturaNotFoundException {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            alumnoService.perderAsignatura(202020, null);
        });
        assertEquals("Cannot invoke \"java.lang.Integer.intValue()\" because \"asignaturaId\" is null", exception.getMessage());
    }

    //Test: Perder una asignatura con asignaturaId no existente
    @Test
    void perderAsignaturaIdNoExistenteTest() throws IllegalArgumentException, AsignaturaNotFoundException {
        Integer asignaturaId = 70;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            alumnoService.perderAsignatura(202020, asignaturaId);
        });
        assertEquals("No se encontró la asignatura con el ID: " + asignaturaId, exception.getMessage());
    }

    //Test: Perder una asignatura con dni no existente, tira AlumnoNotFoundException
    @Test
    void perderAsignaturaDniNoExistenteTest() throws IllegalArgumentException, AlumnoNotFoundException {
        Mockito.when(alumnoDaoMock.buscarPorDni(any(Integer.class))).thenThrow(new AlumnoNotFoundException("No se encontró el alumno con el DNI: 202020"));

        try {
            alumnoService.perderAsignatura(202020, 1);
        } catch (AlumnoNotFoundException e) {
            assertEquals("No se encontró el alumno con el DNI: 202020", e.getMessage());
        }
    }





}
