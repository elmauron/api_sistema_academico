package com.maurosagasti.api_sistema_academico.business;

import com.maurosagasti.api_sistema_academico.model.Carrera;
import com.maurosagasti.api_sistema_academico.model.dto.CarreraDto;
import com.maurosagasti.api_sistema_academico.persistence.exception.CarreraNotFoundException;

import java.util.List;

public interface CarreraService {

    // Crear una carrera

    Carrera crearCarrera(CarreraDto inputData) throws IllegalArgumentException;

    // Actualizar una carrera

    Carrera actualizarCarrera(Integer id, CarreraDto inputData) throws IllegalArgumentException, CarreraNotFoundException;

    // Eliminar una carrera

    void eliminarCarrera(Integer id) throws CarreraNotFoundException;

    List<Carrera> listarCarreras();


}
