package gm.Taller.controlador;

import gm.Taller.modelo.Reparaciones;
import gm.Taller.modelo.Vehiculos;
import gm.Taller.servicio.IReparacionServicio;
import gm.Taller.servicio.IPiezaServicio;
import gm.Taller.servicio.IVehiculoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//http://localhost:8081/taller-app/
@RequestMapping("taller-app")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081"})
public class ReparacionControlador {

    @Autowired
    private IReparacionServicio reparacionServicio;

    @Autowired
    private IVehiculoServicio vehiculoServicio;

    @Autowired
    private IPiezaServicio piezaServicio;


    @PostMapping("/reparaciones")
    public ResponseEntity<Reparaciones> createReparacion(@RequestBody Reparaciones reparacion) {
        // Check if the associated vehicle exists
        Vehiculos vehiculo = vehiculoServicio.searchVehiculoById(reparacion.getVehiculo().getIdVehiculo());
        if (vehiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Vehicle not found
        }

        // Save the new reparacion
        Reparaciones createdReparacion = reparacionServicio.saveReparacion(reparacion);
        if (createdReparacion != null) {
            return new ResponseEntity<>(createdReparacion, HttpStatus.CREATED); // Successfully created
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Handle case where saving failed
        }
    }




}
