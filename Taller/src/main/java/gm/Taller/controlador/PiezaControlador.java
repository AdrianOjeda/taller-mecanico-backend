package gm.Taller.controlador;


import gm.Taller.modelo.Piezas;
import gm.Taller.repositorio.PiezaRepositorio;
import gm.Taller.servicio.IPiezaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//http://localhost:8081/taller-app/
@RequestMapping("taller-app")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081"})

public class PiezaControlador {
    private static final Logger logger =
            LoggerFactory.getLogger((PiezaControlador.class));
    @Autowired
    private IPiezaServicio PiezaServicio;


    @GetMapping("/piezas")
    public List<Piezas> getPiezas(){

        var piezas = PiezaServicio.listPiezas();
        piezas.forEach((pieza -> logger.info(pieza.toString())));

        return piezas;
    }

    @PostMapping("/piezas")
    public ResponseEntity<Piezas> createPieza(@RequestBody Piezas newPieza){
        try{
            Piezas savedPieza = PiezaServicio.savePieza(newPieza);
            logger.info("Received request to create pieza: " + newPieza);
            return new ResponseEntity<>(savedPieza, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 error
        }
    }
}
