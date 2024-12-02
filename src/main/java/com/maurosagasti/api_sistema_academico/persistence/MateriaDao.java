package com.maurosagasti.api_sistema_academico.persistence;

import com.maurosagasti.api_sistema_academico.model.Materia;
import com.maurosagasti.api_sistema_academico.persistence.exception.MateriaNotFoundException;

import java.util.List;

public interface MateriaDao {

    Materia save(Materia m) throws IllegalArgumentException;

    Materia buscarPorId(Integer id) throws MateriaNotFoundException;

    Materia update(Integer id, Materia m) throws IllegalArgumentException, MateriaNotFoundException;

    void delete(Integer id) throws MateriaNotFoundException;

    Materia buscarPorNombre(String nombre) throws MateriaNotFoundException;

    List<Materia> listarMaterias(String order) throws MateriaNotFoundException;

}
