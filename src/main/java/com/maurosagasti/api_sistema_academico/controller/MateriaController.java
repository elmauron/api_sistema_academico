package com.maurosagasti.api_sistema_academico.controller;

import com.maurosagasti.api_sistema_academico.business.MateriaService;
import com.maurosagasti.api_sistema_academico.model.Materia;
import com.maurosagasti.api_sistema_academico.model.dto.MateriaDto;
import com.maurosagasti.api_sistema_academico.persistence.exception.MateriaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("materia")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    //Crear una materia
    @PostMapping
    public ResponseEntity<Materia> crearMateria (@RequestBody MateriaDto materiaDto) {
        if (materiaDto.getNombre() == null) {
            throw new IllegalArgumentException("El nombre de la materia no puede ser nulo");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(materiaService.crearMateria(materiaDto));
    }

    //Actualizar una materia por ID
    @PutMapping("/{idMateria}")
    public ResponseEntity<Materia> modificarMateria(@PathVariable int idMateria, @RequestBody MateriaDto materiaDto) throws IllegalArgumentException , MateriaNotFoundException {
        return ResponseEntity.ok(materiaService.modificarMateria(idMateria, materiaDto));
    }

    //Eliminar una materia por ID
    @DeleteMapping("/{idMateria}")
    public ResponseEntity<Void> eliminarMateria(@PathVariable int idMateria) throws MateriaNotFoundException {
        materiaService.eliminarMateria(idMateria);
        return ResponseEntity.noContent().build();
    }

    //Obtener una materia por nombre
    @GetMapping("/buscar/nombre")
    public ResponseEntity<Materia> obtenerMateriaPorNombre(@RequestParam String nombre) throws MateriaNotFoundException {
        return ResponseEntity.ok(materiaService.obtenerMateriaPorNombre(nombre));
    }

    //listar todas las materias ordenadas por nombre ascendente o descendente y código ascendente o descendente
    // tiene que ser nombre_asc, nombre_desc, codigo_asc, o codigo_desc .

    @GetMapping("/listar/orden")
    public ResponseEntity<?> listarMaterias(@RequestParam String order) throws IllegalArgumentException {
        return ResponseEntity.ok(materiaService.listarMaterias(order));
    }

}
