package com.maurosagasti.api_sistema_academico.business;

import com.maurosagasti.api_sistema_academico.model.Materia;
import com.maurosagasti.api_sistema_academico.model.dto.MateriaDto;
import com.maurosagasti.api_sistema_academico.persistence.exception.CarreraNotFoundException;
import com.maurosagasti.api_sistema_academico.persistence.exception.MateriaNotFoundException;

import java.util.List;

public interface MateriaService {

    // Crear materia

    Materia crearMateria(MateriaDto inputData) throws IllegalArgumentException, CarreraNotFoundException, MateriaNotFoundException;

    // Modificar materia

    Materia modificarMateria(Integer id, MateriaDto inputData) throws IllegalArgumentException, MateriaNotFoundException;

    // Eliminar materia

    void eliminarMateria(Integer id) throws MateriaNotFoundException;

    // Obtener materia por nombre

    Materia obtenerMateriaPorNombre(String nombre) throws MateriaNotFoundException;

    // Listar todas las materias ordenadas por nombre ascendente o descendente y código ascendente o descendente

    List<Materia> listarMaterias(String order) throws MateriaNotFoundException;

}
