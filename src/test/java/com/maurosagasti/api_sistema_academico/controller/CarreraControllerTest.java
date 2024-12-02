package com.maurosagasti.api_sistema_academico.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maurosagasti.api_sistema_academico.business.CarreraService;
import com.maurosagasti.api_sistema_academico.model.Carrera;
import com.maurosagasti.api_sistema_academico.model.dto.CarreraDto;
import com.maurosagasti.api_sistema_academico.persistence.exception.CarreraNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
class CarreraControllerTest {

    @InjectMocks
    CarreraController carreraController;

    @Mock
    CarreraService carreraService;

    MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.mockMvc = standaloneSetup(this.carreraController).build();
    }

    @AfterEach
    void tearDown() {
    }

    //Test: crear una carrera
    @Test
    void crearCarreraCorrectamenteTest() throws Exception {
        CarreraDto carreraDto = new CarreraDto();
        carreraDto.setNombre("Ingeniería en Sistemas");

        Carrera expectedCarrera = new Carrera();
        expectedCarrera.setId(1);
        expectedCarrera.setNombre("Ingeniería en Sistemas");

        Mockito.when(carreraService.crearCarrera(Mockito.any(CarreraDto.class))).thenReturn(expectedCarrera);

        MvcResult result = mockMvc.perform(post("/carrera")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()) // Expecting 201 status code
                .andReturn();

        Carrera actualCarrera = mapper.readValue(result.getResponse().getContentAsString(), Carrera.class);
        assertEquals(expectedCarrera.getNombre(), actualCarrera.getNombre());
        assertEquals(expectedCarrera.getId(), actualCarrera.getId());
    }

    //Test: crear una carrera con argumentos invalidos
    @Test
    void crearCarreraConArgumentosInvalidosTest() throws Exception {
        Mockito.when(carreraService.crearCarrera(Mockito.any(CarreraDto.class))).thenReturn(new Carrera());
        MvcResult result = mockMvc.perform(post("/carrera")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"nombre\": \"Biología\",\n" +
                                "    \"id_departamento\": \"Don\",\n" +
                                "    \"cuatrimestres\": \"Ramón\"\n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    //Test: actualizar una carrera
    @Test
    void actualizarCarreraTest() throws Exception {
        Carrera carreraMock = new Carrera();
        carreraMock.setNombre("Biología");
        carreraMock.setId_departamento(1);
        carreraMock.setCuatrimestres(10);

        Mockito.when(carreraService.actualizarCarrera(Mockito.anyInt(), Mockito.any(CarreraDto.class))).thenReturn(carreraMock);
        CarreraDto carreraDto = new CarreraDto();
        carreraDto.setNombre("Biología");
        carreraDto.setDepartamentoId(1);
        carreraDto.setCuatrimestres(10);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/carrera/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(carreraDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Carrera carrera = mapper.readValue(result.getResponse().getContentAsString(), Carrera.class);

        assertEquals("Biología", carrera.getNombre());
        assertEquals(1, carrera.getId_departamento());
        assertEquals(10, carrera.getCuatrimestres());
    }

    //Test: actualizar una carrera con argumentos invalidos
    @Test
    void actualizarCarreraConArgumentosInvalidosTest() throws Exception {
        Mockito.when(carreraService.actualizarCarrera(Mockito.anyInt(), Mockito.any(CarreraDto.class))).thenReturn(new Carrera());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/carrera/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"nombre\": \"Biología\",\n" +
                                "    \"id_departamento\": \"Don\",\n" +
                                "    \"cuatrimestres\": \"Ramón\"\n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    //Test: eliminar una carrera
    @Test
    void eliminarCarreraTest() throws Exception {
        Mockito.doNothing().when(carreraService).eliminarCarrera(Mockito.anyInt());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/carrera/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    //Test: eliminar una carrera no existente, tira carreranotfoundexception
    @Test
    void eliminarCarreraNoExistenteTest() throws Exception, CarreraNotFoundException {
        Mockito.doThrow(new CarreraNotFoundException("No se encontró la carrera con el ID: 1")).when(carreraService).eliminarCarrera(Mockito.anyInt());
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/carrera/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn();
        } catch (ServletException e) {
            // Aquí se captura la ServletException
            Throwable cause = e.getCause();
            Assertions.assertEquals(CarreraNotFoundException.class, cause.getClass());
            Assertions.assertEquals("No se encontró la carrera con el ID: 1", cause.getMessage());
        }
    }

    //Test: eliminar una carrera con id invalido, tira illegalargumentexception
    @Test
    void eliminarCarreraConIdInvalidoTest() throws Exception {
        Mockito.doThrow(new IllegalArgumentException("El ID de la carrera no puede ser negativo")).when(carreraService).eliminarCarrera(Mockito.anyInt());
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/carrera/-1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andReturn();
        } catch (ServletException e) {
            // Aquí se captura la ServletException
            Throwable cause = e.getCause();
            Assertions.assertEquals(IllegalArgumentException.class, cause.getClass());
            Assertions.assertEquals("El ID de la carrera no puede ser negativo", cause.getMessage());
        }
    }

    //Test: listar todas las carreras
    @Test
    void listarCarrerasTest() throws Exception {
        Carrera carreraMock = new Carrera();
        carreraMock.setNombre("Biología");
        carreraMock.setId_departamento(1);
        carreraMock.setCuatrimestres(10);

        Carrera carreraMock2 = new Carrera();
        carreraMock2.setNombre("Ciencias de la Computación");
        carreraMock2.setId_departamento(2);
        carreraMock2.setCuatrimestres(10);

        Carrera carreraMock3 = new Carrera();
        carreraMock3.setNombre("Geología");
        carreraMock3.setId_departamento(3);
        carreraMock3.setCuatrimestres(10);

        Mockito.when(carreraService.listarCarreras()).thenReturn(java.util.List.of(carreraMock, carreraMock2, carreraMock3));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/carrera/listar")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Carrera[] carreras = mapper.readValue(result.getResponse().getContentAsString(), Carrera[].class);

        assertEquals(3, carreras.length);
        assertEquals("Biología", carreras[0].getNombre());
        assertEquals(1, carreras[0].getId_departamento());
        assertEquals(10, carreras[0].getCuatrimestres());

        assertEquals("Ciencias de la Computación", carreras[1].getNombre());
        assertEquals(2, carreras[1].getId_departamento());
        assertEquals(10, carreras[1].getCuatrimestres());

        assertEquals("Geología", carreras[2].getNombre());
        assertEquals(3, carreras[2].getId_departamento());
        assertEquals(10, carreras[2].getCuatrimestres());
    }
}
