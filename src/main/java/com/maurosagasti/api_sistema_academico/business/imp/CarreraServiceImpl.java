package com.maurosagasti.api_sistema_academico.business.imp;

import com.maurosagasti.api_sistema_academico.business.CarreraService;
import com.maurosagasti.api_sistema_academico.model.Carrera;
import com.maurosagasti.api_sistema_academico.model.dto.CarreraDto;
import com.maurosagasti.api_sistema_academico.persistence.CarreraDao;
import com.maurosagasti.api_sistema_academico.persistence.exception.CarreraNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarreraServiceImpl implements CarreraService {

    private CarreraDao carreraDao;

    @Autowired
    public void setCarreraDao(CarreraDao carreraDao) {
        this.carreraDao = carreraDao;
    }

    @Override
    public Carrera crearCarrera(CarreraDto inputData) throws IllegalArgumentException {
        Carrera c = new Carrera();
        c.setNombre(inputData.getNombre());
        c.setId_departamento(inputData.getDepartamentoId());
        c.setCuatrimestres(inputData.getCuatrimestres());
        carreraDao.save(c);
        return c;
    }

    @Override
    public Carrera actualizarCarrera(Integer id, CarreraDto inputData) throws IllegalArgumentException, CarreraNotFoundException {
        Carrera c = carreraDao.buscarPorId(id);
        c.setNombre(inputData.getNombre());
        c.setId_departamento(inputData.getDepartamentoId());
        c.setCuatrimestres(inputData.getCuatrimestres());
        carreraDao.update(id, c);
        return c;
    }

    @Override
    public void eliminarCarrera(Integer id) throws NullPointerException {
        if (carreraDao.buscarPorId(id) == null) {
            throw new NullPointerException("No se encontró la carrera");
        }

        carreraDao.delete(id);
    }

    @Override
    public List<Carrera> listarCarreras() {
        return carreraDao.listarCarreras();
    }
}
