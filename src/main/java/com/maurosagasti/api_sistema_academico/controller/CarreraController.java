package com.maurosagasti.api_sistema_academico.controller;

import com.maurosagasti.api_sistema_academico.business.CarreraService;
import com.maurosagasti.api_sistema_academico.model.Carrera;
import com.maurosagasti.api_sistema_academico.model.dto.CarreraDto;
import com.maurosagasti.api_sistema_academico.persistence.exception.CarreraNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("carrera")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    //Crear una carrera usando Dto
    @PostMapping
    public ResponseEntity<Carrera> crearCarrera(@RequestBody CarreraDto carreraDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carreraService.crearCarrera(carreraDto));
    }

    //Actualizar una carrera usando CarreraId
    @PutMapping("/{idCarrera}")
    public ResponseEntity<Carrera> actualizarCarrera(@PathVariable int idCarrera, @RequestBody CarreraDto carreraDto) throws CarreraNotFoundException, IllegalArgumentException {
        return ResponseEntity.ok(carreraService.actualizarCarrera(idCarrera, carreraDto));
    }

    //Eliminar una carrera usando CarreraId
    @DeleteMapping("/{idCarrera}")
    public ResponseEntity<String> eliminarCarrera(@PathVariable int idCarrera) throws CarreraNotFoundException{
        carreraService.eliminarCarrera(idCarrera);
        return ResponseEntity.ok("Carrera eliminada");
    }

    //Listar todas las carreras
    @GetMapping("/listar")
    public ResponseEntity<?> listarCarreras() {
        return ResponseEntity.ok(carreraService.listarCarreras());
    }

}
