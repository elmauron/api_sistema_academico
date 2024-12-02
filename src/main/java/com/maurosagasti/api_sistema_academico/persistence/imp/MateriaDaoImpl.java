package com.maurosagasti.api_sistema_academico.persistence.imp;

import com.maurosagasti.api_sistema_academico.model.Materia;
import com.maurosagasti.api_sistema_academico.persistence.MateriaDao;
import com.maurosagasti.api_sistema_academico.persistence.exception.CarreraNotFoundException;
import com.maurosagasti.api_sistema_academico.persistence.exception.MateriaNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MateriaDaoImpl implements MateriaDao {

    private Map<Integer, Materia> materias = new HashMap<>();

    @Override
    public Materia save(Materia materia) throws CarreraNotFoundException, MateriaNotFoundException {
        materias.put(materia.getMateriaId(), materia);
        return materia;
    }

    @Override
    public Materia buscarPorId(Integer id) throws MateriaNotFoundException {
        Materia materia = materias.get(id);
        if (materia == null) {
            throw new MateriaNotFoundException("No se encontró la materia con el ID: " + id);
        }
        return materia;
    }

    @Override
    public Materia update(Integer idMateria, Materia m) throws IllegalArgumentException, MateriaNotFoundException {
        Materia materia = materias.get(idMateria);
        if (materia == null) {
            throw new MateriaNotFoundException("No se encontró la materia con el ID: " + idMateria);
        }
        materia.setAnio(m.getAnio());
        materia.setNombre(m.getNombre());
        materia.setCuatrimestre(m.getCuatrimestre());
        materia.setMateriaId(m.getMateriaId());

        materias.put(idMateria, materia);
        return materia;
    }

    @Override
    public void delete(Integer idMateria) throws MateriaNotFoundException {
        Materia materia = materias.get(idMateria);
        if (materia == null) {
            throw new MateriaNotFoundException("No se encontró la materia con el ID: " + idMateria);
        }
        materias.remove(idMateria);
    }

    @Override
    public Materia buscarPorNombre(String nombre) throws MateriaNotFoundException {
        for (Materia materia : materias.values()) {
            if (materia.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                return materia;
            }
        }
        throw new MateriaNotFoundException("Materia no encontrada con nombre: " + nombre);
    }

    @Override
    public List<Materia> listarMaterias(String order) {
        List <Materia> listaMaterias = new ArrayList<>(materias.values());

        switch (order) {
            case "nombre_asc":
                listaMaterias.sort(Comparator.comparing(Materia::getNombre));
                break;
            case "nombre_desc":
                listaMaterias.sort(Comparator.comparing(Materia::getNombre).reversed());
                break;
            case "codigo_asc":
                listaMaterias.sort(Comparator.comparing(Materia::getMateriaId));
                break;
            case "codigo_desc":
                listaMaterias.sort(Comparator.comparing(Materia::getMateriaId).reversed());
                break;
            default:
                throw new MateriaNotFoundException("En que orden desea listar las materias?");
        }

        return listaMaterias;
    }
}
