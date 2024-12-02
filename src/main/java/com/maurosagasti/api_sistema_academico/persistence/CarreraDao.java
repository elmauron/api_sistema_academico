package com.maurosagasti.api_sistema_academico.persistence;

import com.maurosagasti.api_sistema_academico.model.Carrera;
import com.maurosagasti.api_sistema_academico.persistence.exception.CarreraNotFoundException;

import java.util.List;

public interface CarreraDao {

    Carrera save(Carrera c) throws IllegalArgumentException;

    Carrera buscarPorId(Integer id) throws CarreraNotFoundException;

    Carrera update(Integer id, Carrera c) throws CarreraNotFoundException, IllegalArgumentException;

    void delete(Integer id) throws CarreraNotFoundException;

    List<Carrera> listarCarreras();
}
