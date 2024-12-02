package com.maurosagasti.api_sistema_academico.business.implementation;

import com.maurosagasti.api_sistema_academico.business.imp.CarreraServiceImpl;
import com.maurosagasti.api_sistema_academico.model.Carrera;
import com.maurosagasti.api_sistema_academico.model.dto.CarreraDto;
import com.maurosagasti.api_sistema_academico.persistence.CarreraDao;
import com.maurosagasti.api_sistema_academico.persistence.MateriaDao;
import com.maurosagasti.api_sistema_academico.persistence.exception.CarreraNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarreraServiceImplTest {

    @InjectMocks
    private CarreraServiceImpl carreraService;

    @Mock
    private CarreraDao carreraDao;

    @Mock
    private MateriaDao materiaDao;

    //Test: crearCarrera
    @Test
    void crearCarreraTest() throws IllegalArgumentException {
        CarreraDto carreraDto = new CarreraDto();
        carreraDto.setNombre("Biología");
        carreraDto.setDepartamentoId(1);
        carreraDto.setCuatrimestres(10);

        when(carreraDao.save(any(Carrera.class))).thenReturn(new Carrera());

        Carrera carrera = carreraService.crearCarrera(carreraDto);

        assertNotNull(carrera);
        assertEquals("Biología", carrera.getNombre());
        assertEquals(1, carrera.getId_departamento());
        assertEquals(10, carrera.getCuatrimestres());
    }

    //Test: actualizarCarrera
    @Test
    void actualizarCarreraTest() throws IllegalArgumentException {
        CarreraDto carreraDto = new CarreraDto();
        carreraDto.setNombre("Biología");
        carreraDto.setDepartamentoId(1);
        carreraDto.setCuatrimestres(10);

        Carrera carrera = new Carrera();
        carrera.setNombre("Biología");
        carrera.setId_departamento(1);
        carrera.setCuatrimestres(10);

        when(carreraDao.buscarPorId(1)).thenReturn(carrera);


        when(carreraDao.update(eq(1), any(Carrera.class))).thenReturn(new Carrera());

        Carrera carreraActualizada = carreraService.actualizarCarrera(1, carreraDto);

        assertNotNull(carreraActualizada);
        assertEquals("Biología", carrera.getNombre());
        assertEquals(1, carrera.getId_departamento());
        assertEquals(10, carrera.getCuatrimestres());
    }

    //Test: eliminarCarrera
    @Test
    void eliminarCarreraTest() {
        Carrera carrera = new Carrera();
        carrera.setId(1);
        carrera.setNombre("Biología");
        carrera.setId_departamento(1);
        carrera.setCuatrimestres(10);

        when(carreraDao.buscarPorId(1)).thenReturn(carrera);

        carreraService.eliminarCarrera(1);
    }

    //Test: eliminar una carrera no existente, tira carreraNotFoundException
    @Test
    void eliminarCarreraNoExistenteTest() throws NullPointerException {
        assertThrows(NullPointerException.class, () -> {
            carreraService.eliminarCarrera(1);
        });
    }

    //Test: eliminar una carrera con id nulo, tira NullPointerException
    @Test
    void eliminarCarreraIdNuloTest() {
        assertThrows(NullPointerException.class, () -> {
            carreraService.eliminarCarrera(null);
        });
    }

    //Test: listarCarreras
    @Test
    void listarCarrerasTest() {
        when(carreraDao.listarCarreras()).thenReturn(null);

        assertNull(carreraService.listarCarreras());
    }

}