package gm.Taller.controlador;


import gm.Taller.modelo.Vehiculos;
import gm.Taller.servicio.IPiezaServicio;
import gm.Taller.servicio.IVehiculoServicio;
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
public class VehiculoControlador {

    private static final Logger logger =
            LoggerFactory.getLogger((VehiculoControlador.class));

    @Autowired
    private IVehiculoServicio VehiculoServicio;

    @GetMapping("/vehiculos")
    public List<Vehiculos> getVehiculos(){
        var vehiculos = VehiculoServicio.listVehiculos();
        logger.info("Vehiculos "+vehiculos);
        return vehiculos;
    }

    @PostMapping("/vehiculos")
    public ResponseEntity<?> createVehiculo(@RequestBody Vehiculos newVehiculo) {
        Vehiculos savedVehiculo = VehiculoServicio.saveVehiculo(newVehiculo);
        logger.info("Vehiculo a agregar "+newVehiculo);
        if (savedVehiculo == null) {
            // Respond with an appropriate error if the matriculaVehiculo exists
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Vehiculo with matriculaVehiculo already exists.");
        }

        // If the save operation was successful, return the saved entity
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVehiculo);
    }

}
