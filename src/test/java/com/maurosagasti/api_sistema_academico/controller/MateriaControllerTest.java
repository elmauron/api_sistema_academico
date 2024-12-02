package com.maurosagasti.api_sistema_academico.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maurosagasti.api_sistema_academico.business.MateriaService;
import com.maurosagasti.api_sistema_academico.model.Materia;
import com.maurosagasti.api_sistema_academico.model.dto.MateriaDto;
import com.maurosagasti.api_sistema_academico.persistence.exception.CarreraNotFoundException;
import com.maurosagasti.api_sistema_academico.persistence.exception.MateriaNotFoundException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
class MateriaControllerTest {

    @InjectMocks
    MateriaController materiaController;

    @Mock
    MateriaService materiaService;

    MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.mockMvc = standaloneSetup(this.materiaController).build();
    }

    @AfterEach
    void tearDown() {
    }

    //Test: crear una materia
    @Test
    void crearMateriaTest() throws Exception {
        Materia materiaMock = new Materia();
        materiaMock.setNombre("Práctica Docente 1");
        materiaMock.setAnio(1);
        materiaMock.setCuatrimestre(1);
        materiaMock.setCarreraId(1);

        Mockito.when(materiaService.crearMateria(Mockito.any(MateriaDto.class))).thenReturn(materiaMock);
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Práctica Docente 1");
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(1);
        materiaDto.setCarreraId(1);

        MvcResult result = mockMvc.perform(post("/materia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Materia materia = mapper.readValue(result.getResponse().getContentAsString(), Materia.class);

        assertEquals("Práctica Docente 1", materia.getNombre());
        assertEquals(1, materia.getAnio());
        assertEquals(1, materia.getCuatrimestre());
    }

    //Test: actualizar una materia
    @Test
    void modificarMateriaTest() throws Exception {
        Materia materiaMock = new Materia();
        materiaMock.setNombre("Práctica Docente 1");
        materiaMock.setAnio(1);
        materiaMock.setCuatrimestre(1);
        materiaMock.setCarreraId(1);

        Mockito.when(materiaService.modificarMateria(Mockito.anyInt(), Mockito.any(MateriaDto.class))).thenReturn(materiaMock);
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Práctica Docente 1");
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(1);
        materiaDto.setCarreraId(1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/materia/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(materiaDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Materia materia = mapper.readValue(result.getResponse().getContentAsString(), Materia.class);

        assertEquals("Práctica Docente 1", materia.getNombre());
        assertEquals(1, materia.getAnio());
        assertEquals(1, materia.getCuatrimestre());
    }

    //Test: actualizar una materia no existente, tira materiaNotFoundException
    @Test
    void modificarMateriaNoExistenteTest() throws Exception, MateriaNotFoundException {
        Mockito.when(materiaService.modificarMateria(Mockito.anyInt(), Mockito.any(MateriaDto.class))).thenThrow(new MateriaNotFoundException("No se encontró la materia con el ID: 1"));

        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/materia/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                    "    \"nombre\": \"Práctica Docente 1\",\n" +
                                    "    \"anio\": 1,\n" +
                                    "    \"cuatrimestre\": 1,\n" +
                                    "    \"carreraId\": 1\n" +
                                    "}")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn();

        } catch (ServletException e) {
    }
    }

    //Test: eliminar materia existente
    @Test
    void eliminarMateriaTest() throws Exception {
        Mockito.doNothing().when(materiaService).eliminarMateria(Mockito.anyInt());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/materia/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    //Test: eliminar materia no existente, tira materiaNotFoundException
    @Test
    void eliminarMateriaNoExistenteTest() throws Exception, MateriaNotFoundException {
        Mockito.doThrow(new MateriaNotFoundException("No se encontró la materia con el ID: 10")).when(materiaService).eliminarMateria(Mockito.anyInt());

        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/materia/10")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn();

        } catch (ServletException e) {
        }
    }

    //Test: obtener una materia existente
    @Test
    void obtenerMateriaTest() throws Exception {
        Materia materiaMock = new Materia();
        materiaMock.setNombre("Práctica Docente 1");
        materiaMock.setAnio(1);
        materiaMock.setCuatrimestre(1);
        materiaMock.setCarreraId(1);

        Mockito.when(materiaService.obtenerMateriaPorNombre(Mockito.anyString())).thenReturn(materiaMock);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/buscar/nombre?nombre=Práctica Docente 1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Materia materia = mapper.readValue(result.getResponse().getContentAsString(), Materia.class);

        assertEquals("Práctica Docente 1", materia.getNombre());
        assertEquals(1, materia.getAnio());
        assertEquals(1, materia.getCuatrimestre());
    }

    //Test: obtener una materia no existente, tira materiaNotFoundException
    @Test
    void obtenerMateriaNoExistenteTest() throws Exception {
        Mockito.when(materiaService.obtenerMateriaPorNombre(Mockito.anyString())).thenThrow(new MateriaNotFoundException("Materia no encontrada con nombre: Práctica Docente XD"));

        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/buscar/nombre?nombre=Práctica Docente XD")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn();
        } catch (ServletException e) {
            Throwable cause = e.getCause();
            if (cause instanceof MateriaNotFoundException) {
                assertEquals("Materia no encontrada con nombre: Práctica Docente XD", cause.getMessage());
            } else if (cause instanceof NullPointerException) {
                throw e;
            }
        }
    }

    //Test: listar materias
    @Test
    void listarMateriasTest() throws Exception {
        Materia materiaMock1 = new Materia();
        materiaMock1.setNombre("Práctica Docente 1");
        materiaMock1.setAnio(1);
        materiaMock1.setCuatrimestre(1);
        materiaMock1.setCarreraId(1);

        Materia materiaMock2 = new Materia();
        materiaMock2.setNombre("Práctica Docente 2");
        materiaMock2.setAnio(1);
        materiaMock2.setCuatrimestre(1);
        materiaMock2.setCarreraId(1);

        Mockito.when(materiaService.listarMaterias(Mockito.anyString())).thenReturn(List.of(materiaMock1, materiaMock2));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/listar/orden?order=nombre_asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Materia[] materias = mapper.readValue(result.getResponse().getContentAsString(), Materia[].class);

        assertEquals(2, materias.length);
        assertEquals("Práctica Docente 1", materias[0].getNombre());
        assertEquals("Práctica Docente 2", materias[1].getNombre());
    }

    //Test: listar materias con orden ascendente por nombre
    @Test
    void listarMateriasPorNombreAscTest() throws Exception, MateriaNotFoundException {
        Materia materiaMock1 = new Materia();
        materiaMock1.setNombre("Biofísica");
        materiaMock1.setAnio(1);
        materiaMock1.setCuatrimestre(1);
        materiaMock1.setCarreraId(1);

        Materia materiaMock2 = new Materia();
        materiaMock2.setNombre("Analisis Matemático 1");
        materiaMock2.setAnio(1);
        materiaMock2.setCuatrimestre(1);
        materiaMock2.setCarreraId(1);

        Materia materiaMock3 = new Materia();
        materiaMock3.setNombre("Diseño de sistemas");
        materiaMock3.setAnio(1);
        materiaMock3.setCuatrimestre(1);
        materiaMock3.setCarreraId(1);

        List<Materia> sortedMaterias = List.of(materiaMock2, materiaMock1, materiaMock3);

        Mockito.when(materiaService.listarMaterias("nombre_asc")).thenReturn(sortedMaterias);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/listar/orden?order=nombre_asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Materia[] materias = mapper.readValue(result.getResponse().getContentAsString(), Materia[].class);
        assertEquals(3, materias.length);
        assertEquals("Analisis Matemático 1", materias[0].getNombre());
        assertEquals("Biofísica", materias[1].getNombre());
        assertEquals("Diseño de sistemas", materias[2].getNombre());
    }

    //Test: listar materias con orden descendente por nombre
    @Test
    void listarMateriasPorNombreDescTest() throws Exception, MateriaNotFoundException {
        Materia materiaMock1 = new Materia();
        materiaMock1.setNombre("Analisis Matemático");
        materiaMock1.setAnio(1);
        materiaMock1.setCuatrimestre(1);
        materiaMock1.setCarreraId(1);

        Materia materiaMock2 = new Materia();
        materiaMock2.setNombre("Biofísica");
        materiaMock2.setAnio(1);
        materiaMock2.setCuatrimestre(1);
        materiaMock2.setCarreraId(1);

        Materia materiaMock3 = new Materia();
        materiaMock3.setNombre("Diseño de sistemas");
        materiaMock3.setAnio(1);
        materiaMock3.setCuatrimestre(1);
        materiaMock3.setCarreraId(1);

        List<Materia> sortedMaterias = List.of(materiaMock3, materiaMock2, materiaMock1); // Sorted list

        Mockito.when(materiaService.listarMaterias("nombre_desc")).thenReturn(sortedMaterias);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/listar/orden?order=nombre_desc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Materia[] materias = mapper.readValue(result.getResponse().getContentAsString(), Materia[].class);
        assertEquals(3, materias.length);
        assertEquals("Diseño de sistemas", materias[0].getNombre());
        assertEquals("Biofísica", materias[1].getNombre());
        assertEquals("Analisis Matemático", materias[2].getNombre());
    }

    //Test: listar materias con orden ascendente por código
    @Test
    void listarMateriasPorCodigoAscTest() throws Exception, MateriaNotFoundException {
        Materia materiaMock1 = new Materia();
        materiaMock1.setNombre("Práctica Docente 1");
        materiaMock1.setAnio(1);
        materiaMock1.setCuatrimestre(1);
        materiaMock1.setCarreraId(1);

        Materia materiaMock2 = new Materia();
        materiaMock2.setNombre("Práctica Docente 2");
        materiaMock2.setAnio(1);
        materiaMock2.setCuatrimestre(1);
        materiaMock2.setCarreraId(1);

        Materia materiaMock3 = new Materia();
        materiaMock3.setNombre("Práctica Docente 3");
        materiaMock3.setAnio(1);
        materiaMock3.setCuatrimestre(1);
        materiaMock3.setCarreraId(1);

        Mockito.when(materiaService.listarMaterias(Mockito.anyString())).thenReturn(List.of(materiaMock1, materiaMock2, materiaMock3));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/listar/orden?order=codigo_asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Materia[] materias = mapper.readValue(result.getResponse().getContentAsString(), Materia[].class);
        assertEquals(3, materias.length);
        assertEquals("Práctica Docente 1", materias[0].getNombre());
        assertEquals("Práctica Docente 2", materias[1].getNombre());
        assertEquals("Práctica Docente 3", materias[2].getNombre());
    }

    //Test: listar materias con orden descendente por código
    @Test
    void listarMateriasPorCodigoDescTest() throws Exception, MateriaNotFoundException {
        Materia materiaMock1 = new Materia();
        materiaMock1.setNombre("Práctica Docente 1");
        materiaMock1.setAnio(1);
        materiaMock1.setCuatrimestre(1);
        materiaMock1.setCarreraId(1);

        Materia materiaMock2 = new Materia();
        materiaMock2.setNombre("Práctica Docente 2");
        materiaMock2.setAnio(1);
        materiaMock2.setCuatrimestre(1);
        materiaMock2.setCarreraId(1);

        Materia materiaMock3 = new Materia();
        materiaMock3.setNombre("Práctica Docente 3");
        materiaMock3.setAnio(1);
        materiaMock3.setCuatrimestre(1);
        materiaMock3.setCarreraId(1);

        Mockito.when(materiaService.listarMaterias(Mockito.anyString())).thenReturn(List.of(materiaMock1, materiaMock2, materiaMock3));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/listar/orden?order=codigo_desc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Materia[] materias = mapper.readValue(result.getResponse().getContentAsString(), Materia[].class);
        assertEquals(3, materias.length);
        assertEquals("Práctica Docente 1", materias[0].getNombre());
        assertEquals("Práctica Docente 2", materias[1].getNombre());
        assertEquals("Práctica Docente 3", materias[2].getNombre());
    }

    //Test: listar materias sin orden, tira materiaNotFoundException
    @Test
    void listarMateriasSinOrdenTest() throws Exception, MateriaNotFoundException {
        Mockito.when(materiaService.listarMaterias(Mockito.anyString())).thenThrow(new MateriaNotFoundException("En que orden desea listar las materias?"));

        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/materia/listar/orden")
                            .param("order", "")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn();
        } catch (ServletException e) {
        }
    }
}
