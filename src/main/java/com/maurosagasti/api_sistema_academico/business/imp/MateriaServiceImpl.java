package com.maurosagasti.api_sistema_academico.business.imp;

import com.maurosagasti.api_sistema_academico.business.MateriaService;
import com.maurosagasti.api_sistema_academico.model.Materia;
import com.maurosagasti.api_sistema_academico.model.dto.MateriaDto;
import com.maurosagasti.api_sistema_academico.persistence.MateriaDao;
import com.maurosagasti.api_sistema_academico.persistence.exception.CarreraNotFoundException;
import com.maurosagasti.api_sistema_academico.persistence.exception.MateriaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaServiceImpl implements MateriaService {

    private MateriaDao materiaDao;

    @Autowired
    public MateriaServiceImpl(MateriaDao materiaDao) {
        this.materiaDao = materiaDao;
    }

    @Override
    public Materia crearMateria(MateriaDto inputData) throws IllegalArgumentException, CarreraNotFoundException, MateriaNotFoundException {
        Materia m = new Materia();
        m.setNombre(inputData.getNombre());
        m.setAnio(inputData.getAnio());
        m.setCuatrimestre(inputData.getCuatrimestre());
        m.setCarreraId(inputData.getCarreraId());
        materiaDao.save(m);
        if (m.getNombre().contains("#")) {
            throw new IllegalArgumentException("No se pudo crear la materia");
        }
        return m;
    }

    @Override
    public Materia modificarMateria(Integer id, MateriaDto inputData) throws IllegalArgumentException, MateriaNotFoundException {
        Materia m = materiaDao.buscarPorId(id);
        m.setNombre(inputData.getNombre());
        m.setAnio(inputData.getAnio());
        m.setCuatrimestre(inputData.getCuatrimestre());
        m.setCarreraId(inputData.getCarreraId());
        materiaDao.update(id, m);
        return m;
    }

    @Override
    public void eliminarMateria(Integer id) throws NullPointerException {
        if (materiaDao.buscarPorId(id) == null) {
            throw new NullPointerException("No se encontró la materia");
        }
        materiaDao.delete(id);
    }

    @Override
    public Materia obtenerMateriaPorNombre(String nombre) throws MateriaNotFoundException {
        return materiaDao.buscarPorNombre(nombre);
    }

    @Override
    public List<Materia> listarMaterias(String order) {
        return materiaDao.listarMaterias(order);
    }

}
