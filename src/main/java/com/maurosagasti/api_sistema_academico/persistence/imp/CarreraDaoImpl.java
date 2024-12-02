package com.maurosagasti.api_sistema_academico.persistence.imp;

import com.maurosagasti.api_sistema_academico.model.Carrera;
import com.maurosagasti.api_sistema_academico.persistence.CarreraDao;
import com.maurosagasti.api_sistema_academico.persistence.MateriaDao;
import com.maurosagasti.api_sistema_academico.persistence.exception.CarreraNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CarreraDaoImpl implements CarreraDao {

    @Autowired
    private MateriaDao materiaDao;

    private Map<Integer, Carrera> carreras = new HashMap<>();



    @Override
    public Carrera save(Carrera c) throws IllegalArgumentException {
        carreras.put(c.getId(), c);
        return c;
    }

    @Override
    public Carrera buscarPorId(Integer id) throws CarreraNotFoundException {
        Carrera carrera = carreras.get(id);
        if (carrera == null) {
            throw new CarreraNotFoundException("No se encontró la carrera con el ID: " + id);
        }
        return carrera;
    }

    @Override
    public Carrera update(Integer idCarrera, Carrera c) throws CarreraNotFoundException, IllegalArgumentException {
        Carrera carrera = carreras.get(idCarrera);
        if (carrera == null) {
            throw new CarreraNotFoundException("No se encontró la carrera con el ID: " + idCarrera);
        }
        carrera.setNombre(c.getNombre());
        carrera.setId_departamento(c.getId_departamento());
        carrera.setCuatrimestres(c.getCuatrimestres());

        carreras.put(idCarrera, carrera);

        return carrera;
    }

    @Override
    public void delete(Integer id) throws CarreraNotFoundException {
        Carrera carrera = carreras.get(id);
        if (carrera == null) {
            throw new CarreraNotFoundException("No se encontró la carrera con el ID: " + id);
        }
        carreras.remove(id);

    }

    @Override
    public List<Carrera> listarCarreras() {
        return new ArrayList<>(carreras.values());
    }
}
