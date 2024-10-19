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

    @PutMapping("/piezas/{piezaId}")
    public ResponseEntity<Piezas> updatePieza(@PathVariable Integer piezaId, @RequestBody Piezas updatedPieza) {
        try {
            Piezas existingPieza = PiezaServicio.searchPiezaById(piezaId); // Assuming a searchPiezaById method exists
            if (existingPieza != null) {
                updatedPieza.setIdPieza(piezaId); // Ensure the ID remains the same
                Piezas savedPieza = PiezaServicio.savePieza(updatedPieza); // Save the updated pieza
                logger.info("Pieza updated: " + savedPieza);
                return new ResponseEntity<>(savedPieza, HttpStatus.OK); // Return the updated pieza with status 200
            } else {
                logger.warn("Pieza not found with ID: " + piezaId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if pieza not found
            }
        } catch (Exception e) {
            logger.error("Error updating pieza with ID: " + piezaId, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 error if something goes wrong
        }
    }

    @DeleteMapping("/piezas/{piezaId}")
    public ResponseEntity<Void> deletePieza(@PathVariable Integer piezaId) {
        try {
            Piezas pieza = PiezaServicio.searchPiezaById(piezaId);
            if (pieza != null) {
                PiezaServicio.deletePieza(pieza);
                logger.info("Pieza deleted with ID: " + piezaId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 No Content if successfully deleted
            } else {
                logger.warn("Pieza not found with ID: " + piezaId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if pieza not found
            }
        } catch (Exception e) {
            logger.error("Error deleting pieza with ID: " + piezaId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 error if something goes wrong
        }
    }
}
