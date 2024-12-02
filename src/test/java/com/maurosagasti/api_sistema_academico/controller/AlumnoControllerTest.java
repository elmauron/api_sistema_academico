package com.maurosagasti.api_sistema_academico.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maurosagasti.api_sistema_academico.business.AlumnoService;
import com.maurosagasti.api_sistema_academico.model.Alumno;
import com.maurosagasti.api_sistema_academico.model.Asignatura;
import com.maurosagasti.api_sistema_academico.model.dto.AlumnoDto;
import com.maurosagasti.api_sistema_academico.model.exception.NotaIncorrectaException;
import com.maurosagasti.api_sistema_academico.persistence.exception.AlumnoNotFoundException;
import com.maurosagasti.api_sistema_academico.persistence.exception.AsignaturaNotFoundException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
class AlumnoControllerTest {

    @InjectMocks
    AlumnoController alumnoController;

    @Mock
    AlumnoService alumnoService;

    MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.mockMvc = standaloneSetup(this.alumnoController).build();
    }

    @AfterEach
    void tearDown() {
    }

    //Test: crear Alumno
    @Test
    void crearAlumnoTest() throws Exception {
        Alumno expectedAlumno = new Alumno();
        expectedAlumno.setNombre("Satoshi");
        expectedAlumno.setApellido("Nakamoto");
        expectedAlumno.setDni(202020);

        Mockito.when(alumnoService.crearAlumno(Mockito.any(AlumnoDto.class))).thenReturn(expectedAlumno);

        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("Satoshi");
        alumnoDto.setApellido("Nakamoto");
        alumnoDto.setDni(202020);

        MvcResult result = mockMvc.perform(post("/alumno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Alumno actualAlumno = mapper.readValue(result.getResponse().getContentAsString(), Alumno.class);
        assertEquals(expectedAlumno.getNombre(), actualAlumno.getNombre());
        assertEquals(expectedAlumno.getApellido(), actualAlumno.getApellido());
        assertEquals(expectedAlumno.getDni(), actualAlumno.getDni());
    }

    //Test: Crear un Alumno con DNI negativo
    @Test
    void crearAlumnoDniNegativoTest() {
        // Arrange
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("Satoshi");
        alumnoDto.setApellido("Nakamoto");
        alumnoDto.setDni(-202020); // Invalid DNI

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            alumnoController.crearAlumno(alumnoDto);
        });

        // Assert
        assertEquals("El DNI no puede ser negativo", exception.getMessage());
    }

    //Test: actualizar Alumno
    @Test
    void actualizarAlumnoTest() throws Exception, AlumnoNotFoundException {
        Alumno expectedAlumno = new Alumno();
        expectedAlumno.setNombre("ZZZatoshi");
        expectedAlumno.setApellido("Nakamotho");
        expectedAlumno.setDni(212121);

        Mockito.when(alumnoService.modificarAlumno(Mockito.anyInt(), Mockito.any(AlumnoDto.class))).thenReturn(expectedAlumno);

        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("ZZZatoshi");
        alumnoDto.setApellido("Nakamotho");
        alumnoDto.setDni(212121);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/alumno/212121")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(alumnoDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertEquals(expectedAlumno, mapper.readValue(result.getResponse().getContentAsString(), Alumno.class));
    }

    //Test: actualizar Alumno no existente, tira AlumnoNotFoundException
    @Test
    void actualizarAlumnoNoExistente() throws Exception {
        Mockito.when(alumnoService.modificarAlumno(Mockito.any(Integer.class), Mockito.any(AlumnoDto.class))).thenThrow(new AlumnoNotFoundException("No se encontró el alumno con el ID: 01"));
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setNombre("ZZZatoshi");
        alumnoDto.setApellido("Nakamotho");
        alumnoDto.setDni(212121);

        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/alumno/212121")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(alumnoDto))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn();
        } catch (ServletException e) {
            Throwable cause = e.getCause();
            assertEquals(AlumnoNotFoundException.class, cause.getClass());
            assertEquals("No se encontró el alumno con el ID: 01", cause.getMessage());
        }
    }

    //Test: eliminar Alumno
    @Test
    void eliminarAlumnoTest() throws Exception, AlumnoNotFoundException {
        Mockito.doNothing().when(alumnoService).eliminarAlumno(Mockito.anyInt());

        mockMvc.perform(MockMvcRequestBuilders.delete("/alumno/212121")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    //Test: eliminar Alumno no existente, tira AlumnoNotFoundException
    @Test
    void eliminarAlumnoNoExistente() throws Exception, AlumnoNotFoundException {
        Mockito.doThrow(new AlumnoNotFoundException("No se encontró el alumno con el ID: 01")).when(alumnoService).eliminarAlumno(Mockito.anyInt());

        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/alumno/212121")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ServletException e) {
            Throwable cause = e.getCause();
            assertEquals(AlumnoNotFoundException.class, cause.getClass());
            assertEquals("No se encontró el alumno con el ID: 01", cause.getMessage());
        }
    }

    //Test: cursar Asignatura
    @Test
    void cursarAsignaturaTest() throws Exception, AlumnoNotFoundException {
        Mockito.doNothing().when(alumnoService).cursarAsignatura(Mockito.anyInt(), Mockito.anyInt());

        mockMvc.perform(MockMvcRequestBuilders.put("/alumno/212121/asignatura/1/cursar")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //Test: cursar Asignatura a alumno no existente, tira AlumnoNotFoundException
    @Test
    void cursarAsignaturaNoExistente() throws Exception, AlumnoNotFoundException {
        Mockito.doThrow(new AlumnoNotFoundException("No se encontró el alumno con el ID: 01")).when(alumnoService).cursarAsignatura(Mockito.anyInt(), Mockito.anyInt());

        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/alumno/212121/asignatura/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ServletException e) {
            Throwable cause = e.getCause();
            assertEquals(AlumnoNotFoundException.class, cause.getClass());
            assertEquals("No se encontró el alumno con el ID: 01", cause.getMessage());
        }
    }

    //Test: cursar Asignatura con ID de asignatura no existente, tira AsignaturaNotFoundException
    @Test
    void cursarAsignaturaAsignaturaNoExistente() throws Exception, AsignaturaNotFoundException {
        Mockito.doThrow(new IllegalArgumentException("No se encontró la asignatura con el ID: 10")).when(alumnoService).cursarAsignatura(Mockito.anyInt(), Mockito.anyInt());

        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/alumno/212121/asignatura/10")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ServletException e) {
            Throwable cause = e.getCause();
            assertEquals(IllegalArgumentException.class, cause.getClass());
            assertEquals("No se encontró la asignatura con el ID: 10", cause.getMessage());
        }
    }

    //Test: aprobar Asignatura
    @Test
    void aprobarAsignaturaTest() throws Exception, AlumnoNotFoundException {
        Mockito.doNothing().when(alumnoService).aprobarAsignatura(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.when(alumnoService.getNombreAsignatura(Mockito.anyInt(), Mockito.anyInt())).thenReturn("Matemáticas");
        Mockito.when(alumnoService.getNotaAsignatura(Mockito.anyInt(), Mockito.anyInt())).thenReturn(10);

        mockMvc.perform(MockMvcRequestBuilders.put("/alumno/212121/asignatura/1/aprobar/10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("La asignatura Matemáticas fue aprobada con 10."));
    }

    //Test: aprobar Asignatura a alumno no existente, tira AlumnoNotFoundException
    @Test
    void aprobarAsignaturaNoExistente() throws Exception, AlumnoNotFoundException {
        Mockito.doThrow(new AlumnoNotFoundException("No se encontró el alumno con el ID: 01")).when(alumnoService).aprobarAsignatura(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());

        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/alumno/212121/asignatura/1/nota/10")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ServletException e) {
            Throwable cause = e.getCause();
            assertEquals(AlumnoNotFoundException.class, cause.getClass());
            assertEquals("No se encontró el alumno con el ID: 01", cause.getMessage());
        }
    }

    //Test: aprobar Asignatura con ID de asignatura no existente, tira AsignaturaNotFoundException
    @Test
    void aprobarAsignaturaAsignaturaNoExistente() throws Exception, AsignaturaNotFoundException {
        Mockito.doThrow(new AsignaturaNotFoundException("No se encontró la asignatura con el ID: 10")).when(alumnoService).aprobarAsignatura(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());

        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/alumno/212121/asignatura/10/nota/10")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ServletException e) {
            Throwable cause = e.getCause();
            assertEquals(IllegalArgumentException.class, cause.getClass());
            assertEquals("No se encontró la asignatura con el ID: 10", cause.getMessage());
        }
    }

    //Test: aprobar Asignatura con nota menor a 4, tira NotaIncorrectaException
    @Test
    void aprobarAsignaturaNotaMenorA4() throws Exception, NotaIncorrectaException {
        Mockito.doThrow(new NotaIncorrectaException("La nota debe ser mayor o igual a 4 y menor o igual a 10")).when(alumnoService).aprobarAsignatura(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());

        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/alumno/212121/asignatura/1/nota/3")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError());
        } catch (ServletException e) {
            Throwable cause = e.getCause();
            assertEquals(IllegalArgumentException.class, cause.getClass());
            assertEquals("La nota debe ser mayor o igual a 4 y menor o igual a 10", cause.getMessage());
        }
    }

    //Test: aprobar Asignatura con nota mayor a 10, tira NotaIncorrectaException
    @Test
    void aprobarAsignaturaNotaMayorA10() throws Exception, NotaIncorrectaException {
        Mockito.doThrow(new NotaIncorrectaException("La nota debe ser mayor o igual a 4 y menor o igual a 10")).when(alumnoService).aprobarAsignatura(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());

        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/alumno/212121/asignatura/1/nota/11")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is4xxClientError());
        } catch (ServletException e) {
            Throwable cause = e.getCause();
            assertEquals(IllegalArgumentException.class, cause.getClass());
            assertEquals("La nota debe ser mayor o igual a 4 y menor o igual a 10", cause.getMessage());
        }
    }

    //Test: perder Asignatura
    @Test
    void perderAsignaturaTest() throws Exception, AlumnoNotFoundException {
        Mockito.doNothing().when(alumnoService).perderAsignatura(Mockito.anyInt(), Mockito.anyInt());

        mockMvc.perform(MockMvcRequestBuilders.put("/alumno/212121/asignatura/1/perder")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //Test: perder Asignatura a alumno no existente, tira AlumnoNotFoundException
    @Test
    void perderAsignaturaNoExistente() throws Exception, AlumnoNotFoundException {
        Mockito.doThrow(new AlumnoNotFoundException("No se encontró el alumno con el ID: 01")).when(alumnoService).perderAsignatura(Mockito.anyInt(), Mockito.anyInt());

        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/alumno/212121/asignatura/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ServletException e) {
            Throwable cause = e.getCause();
            assertEquals(AlumnoNotFoundException.class, cause.getClass());
            assertEquals("No se encontró el alumno con el ID: 01", cause.getMessage());
        }
    }

    //Test: perder Asignatura con ID de asignatura no existente, tira AsignaturaNotFoundException
    @Test
    void perderAsignaturaAsignaturaNoExistente() throws Exception, AsignaturaNotFoundException {
        Mockito.doThrow(new AsignaturaNotFoundException("No se encontró la asignatura con el ID: 10")).when(alumnoService).perderAsignatura(Mockito.anyInt(), Mockito.anyInt());

        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/alumno/212121/asignatura/10")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        } catch (ServletException e) {
            Throwable cause = e.getCause();
            assertEquals(IllegalArgumentException.class, cause.getClass());
            assertEquals("No se encontró la asignatura con el ID: 10", cause.getMessage());
        }
    }
}
