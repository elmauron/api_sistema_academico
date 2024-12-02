package com.maurosagasti.api_sistema_academico.business.implementation;


import com.maurosagasti.api_sistema_academico.business.imp.MateriaServiceImpl;
import com.maurosagasti.api_sistema_academico.model.Carrera;
import com.maurosagasti.api_sistema_academico.model.Materia;
import com.maurosagasti.api_sistema_academico.model.dto.MateriaDto;
import com.maurosagasti.api_sistema_academico.persistence.CarreraDao;
import com.maurosagasti.api_sistema_academico.persistence.MateriaDao;
import com.maurosagasti.api_sistema_academico.persistence.exception.CarreraNotFoundException;
import com.maurosagasti.api_sistema_academico.persistence.exception.MateriaNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MateriaServiceImplTest {

    @InjectMocks
    private MateriaServiceImpl materiaService;

    @Mock
    private MateriaDao materiaDao;

    private MateriaDto materiaDto;
    private Materia materia;

    @BeforeEach
    void setUp() {
        materiaDto = new MateriaDto();
        materiaDto.setNombre("Práctica Docente 1");
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(1);
        materiaDto.setCarreraId(1);

        materia = new Materia();
        materia.setNombre("Práctica Docente 1");
        materia.setAnio(1);
        materia.setCuatrimestre(1);
        materia.setCarreraId(1);
    }

    @Test
    void crearMateriaTest() throws CarreraNotFoundException, MateriaNotFoundException {
        Mockito.when(materiaDao.save(any(Materia.class))).thenReturn(materia);

        Materia createdMateria = materiaService.crearMateria(materiaDto);

        assertEquals("Práctica Docente 1", createdMateria.getNombre());
        assertEquals(1, createdMateria.getAnio());
        assertEquals(1, createdMateria.getCuatrimestre());
        assertEquals(1, createdMateria.getCarreraId());
    }

    //Test: crear una carrera con un nombre que contiene #, tira illegalArgumentException
    @Test
    void crearMateriaConNombreInvalidoTest() {
        //Arrange
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Matematica #1");
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(1);
        materiaDto.setCarreraId(1);

        when(materiaDao.save(any(Materia.class))).thenReturn(new Materia());

        //Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            materiaService.crearMateria(materiaDto);
        });

        //Assert
        assertEquals("No se pudo crear la materia", exception.getMessage());
    }

    //Test: crear una materia con una carrera no existente, tira CarreraNotFoundException
    @Test
    void crearMateriaConCarreraInexistenteTest() {
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Práctica Docente 1");
        materiaDto.setAnio(1);
        materiaDto.setCuatrimestre(1);
        materiaDto.setCarreraId(999); // Assuming 999 is a non-existent Carrera ID

        when(materiaDao.save(any(Materia.class))).thenThrow(new CarreraNotFoundException("No se encontró la carrera con el ID: 999"));

        assertThrows(CarreraNotFoundException.class, () -> {
            materiaService.crearMateria(materiaDto);
        });
    }

    //Test: modificarMateriaTest
    @Test
    void modificarMateriaTest() throws MateriaNotFoundException {
        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setNombre("Práctica Docente 4");
        materiaDto.setAnio(2);
        materiaDto.setCuatrimestre(2);
        materiaDto.setCarreraId(1);

        Materia materia = new Materia();
        materia.setMateriaId(1);
        materia.setNombre("Práctica Docente 1");
        materia.setAnio(1);
        materia.setCuatrimestre(1);
        materia.setCarreraId(1);

        Mockito.when(materiaDao.buscarPorId(1)).thenReturn(materia);
        Mockito.when(materiaDao.update(eq(1), any(Materia.class))).thenReturn(materia);

        Materia updatedMateria = materiaService.modificarMateria(1, materiaDto);

        assertNotNull(updatedMateria);
        assertEquals("Práctica Docente 4", updatedMateria.getNombre());
        assertEquals(2, updatedMateria.getAnio());
        assertEquals(2, updatedMateria.getCuatrimestre());
        assertEquals(1, updatedMateria.getCarreraId());
    }

    //Test: eliminar una materia
    @Test
    void eliminarMateriaTest() throws MateriaNotFoundException {
        Materia materia = new Materia();
        materia.setMateriaId(1);
        materia.setNombre("Matemáticas");

        lenient().when(materiaDao.buscarPorId(1)).thenReturn(materia);

        materiaService.eliminarMateria(1);
    }

    //Test: eliminar una materia que no existe, tira MateriaNotFoundException
    @Test
    void eliminarMateriaInexistenteTest() throws NullPointerException {
        assertThrows(NullPointerException.class, () -> {
            materiaService.eliminarMateria(1);
        });
    }

    //Test: obtener una materia por nombre
    @Test
    void obtenerMateriaPorNombreTest() {
        //Arrange
        Materia materia = new Materia();
        materia.setNombre("Matematica 1");
        materia.setAnio(1);
        materia.setCuatrimestre(1);
        materia.setCarreraId(1);
        materia.setMateriaId(1);

        when(materiaDao.buscarPorNombre("Matematica 1")).thenReturn(materia);

        //Act
        Materia materiaObtenida = materiaService.obtenerMateriaPorNombre("Matematica 1");

        //Assert
        assertNotNull(materiaObtenida);
        assertEquals("Matematica 1", materiaObtenida.getNombre());
        assertEquals(1, materiaObtenida.getAnio());
        assertEquals(1, materiaObtenida.getCuatrimestre());
        assertEquals(1, materiaObtenida.getCarreraId());
    }

    //Test: obtener una materia por nombre que no existe, tira MateriaNotFoundException
    @Test
    void obtenerMateriaPorNombreInexistenteTest() throws MateriaNotFoundException {
        //Arrange
        when(materiaDao.buscarPorNombre("Matematica 1")).thenReturn(null);

        //Act
        try {
            materiaService.obtenerMateriaPorNombre("Matematica 1");
        } catch (MateriaNotFoundException e) {
            //Assert
            assertEquals("No se pudo encontrar la materia", e.getMessage());
        }
    }

    //Test: listar todas las materias por nombre ascendente
    @Test
    void listarMateriasPorNombreAscendenteTest() {
        Materia materiaMock1 = new Materia();
        materiaMock1.setNombre("Análisis Matemático 1");
        materiaMock1.setAnio(1);
        materiaMock1.setCuatrimestre(1);
        materiaMock1.setCarreraId(1);

        Materia materiaMock2 = new Materia();
        materiaMock2.setNombre("Análisis Matemático 2");
        materiaMock2.setAnio(2);
        materiaMock2.setCuatrimestre(1);
        materiaMock2.setCarreraId(1);

        when(materiaDao.listarMaterias("nombre_asc")).thenReturn(java.util.Arrays.asList(materiaMock1, materiaMock2));

        List<Materia> materias = materiaService.listarMaterias("nombre_asc");

        assertNotNull(materias);
        assertEquals(2, materias.size());
        assertEquals("Análisis Matemático 1", materias.get(0).getNombre());
        assertEquals("Análisis Matemático 2", materias.get(1).getNombre());
        assertEquals(1, materias.get(0).getAnio());
        assertEquals(2, materias.get(1).getAnio());
        assertEquals(1, materias.get(0).getCuatrimestre());
        assertEquals(1, materias.get(1).getCuatrimestre());
        assertEquals(1, materias.get(0).getCarreraId());
        assertEquals(1, materias.get(1).getCarreraId());
    }

    //Test: listar todas las materias por nombre descendente
    @Test
    void listarMateriasPorNombreDescendenteTest() {
        Materia materiaMock1 = new Materia();
        materiaMock1.setNombre("Análisis Matemático 1");
        materiaMock1.setAnio(1);
        materiaMock1.setCuatrimestre(1);
        materiaMock1.setCarreraId(1);

        Materia materiaMock2 = new Materia();
        materiaMock2.setNombre("Análisis Matemático 2");
        materiaMock2.setAnio(2);
        materiaMock2.setCuatrimestre(1);
        materiaMock2.setCarreraId(1);

        when(materiaDao.listarMaterias("nombre_desc")).thenReturn(java.util.Arrays.asList(materiaMock2, materiaMock1));

        List<Materia> materias = materiaService.listarMaterias("nombre_desc");

        assertNotNull(materias);
        assertEquals(2, materias.size());
        assertEquals("Análisis Matemático 2", materias.get(0).getNombre());
        assertEquals("Análisis Matemático 1", materias.get(1).getNombre());
        assertEquals(2, materias.get(0).getAnio());
        assertEquals(1, materias.get(1).getAnio());
        assertEquals(1, materias.get(0).getCuatrimestre());
        assertEquals(1, materias.get(1).getCuatrimestre());
        assertEquals(1, materias.get(0).getCarreraId());
        assertEquals(1, materias.get(1).getCarreraId());
    }

    //Test: listar todas las materias por codigo ascendente
    @Test
    void listarMateriasPorCodigoAscendenteTest() {
        Materia materiaMock1 = new Materia();
        materiaMock1.setNombre("Análisis Matemático 1");
        materiaMock1.setAnio(1);
        materiaMock1.setCuatrimestre(1);
        materiaMock1.setCarreraId(1);
        materiaMock1.setMateriaId(1);

        Materia materiaMock2 = new Materia();
        materiaMock2.setNombre("Análisis Matemático 2");
        materiaMock2.setAnio(2);
        materiaMock2.setCuatrimestre(1);
        materiaMock2.setCarreraId(1);
        materiaMock2.setMateriaId(2);

        when(materiaDao.listarMaterias("codigo_asc")).thenReturn(java.util.Arrays.asList(materiaMock1, materiaMock2));

        List<Materia> materias = materiaService.listarMaterias("codigo_asc");

        assertNotNull(materias);
        assertEquals(2, materias.size());
        assertEquals("Análisis Matemático 1", materias.get(0).getNombre());
        assertEquals("Análisis Matemático 2", materias.get(1).getNombre());
        assertEquals(1, materias.get(0).getAnio());
        assertEquals(2, materias.get(1).getAnio());
        assertEquals(1, materias.get(0).getCuatrimestre());
        assertEquals(1, materias.get(1).getCuatrimestre());
        assertEquals(1, materias.get(0).getCarreraId());
        assertEquals(1, materias.get(1).getCarreraId());
        assertEquals(1, materias.get(0).getMateriaId());
        assertEquals(2, materias.get(1).getMateriaId());
    }

    //Test: listar todas las materias por codigo descendente
    @Test
    void listarMateriasPorCodigoDescendenteTest() {
        Materia materiaMock1 = new Materia();
        materiaMock1.setNombre("Análisis Matemático 1");
        materiaMock1.setAnio(1);
        materiaMock1.setCuatrimestre(1);
        materiaMock1.setCarreraId(1);
        materiaMock1.setMateriaId(1);

        Materia materiaMock2 = new Materia();
        materiaMock2.setNombre("Análisis Matemático 2");
        materiaMock2.setAnio(2);
        materiaMock2.setCuatrimestre(1);
        materiaMock2.setCarreraId(1);
        materiaMock2.setMateriaId(2);

        when(materiaDao.listarMaterias("codigo_desc")).thenReturn(java.util.Arrays.asList(materiaMock2, materiaMock1));

        List<Materia> materias = materiaService.listarMaterias("codigo_desc");

        assertNotNull(materias);
        assertEquals(2, materias.size());
        assertEquals("Análisis Matemático 2", materias.get(0).getNombre());
        assertEquals("Análisis Matemático 1", materias.get(1).getNombre());
        assertEquals(2, materias.get(0).getAnio());
        assertEquals(1, materias.get(1).getAnio());
        assertEquals(1, materias.get(0).getCuatrimestre());
        assertEquals(1, materias.get(1).getCuatrimestre());
        assertEquals(1, materias.get(0).getCarreraId());
        assertEquals(1, materias.get(1).getCarreraId());
        assertEquals(2, materias.get(0).getMateriaId());
        assertEquals(1, materias.get(1).getMateriaId());
    }

    //Test: listar todas las materias sin especificar orden, tira IllegalArgumentException
    @Test
    void listarMateriasSinOrdenTest() {
        //Arrange
        when(materiaDao.listarMaterias("")).thenThrow(new MateriaNotFoundException("En que orden desea listar las materias?"));

        //Act
        try {
            materiaService.listarMaterias("");
        } catch (MateriaNotFoundException e) {
            //Assert
            assertEquals("En que orden desea listar las materias?", e.getMessage());
        }
    }


}