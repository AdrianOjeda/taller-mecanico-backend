package gm.Taller.controlador;

import gm.Taller.modelo.Clientes;
import gm.Taller.modelo.Piezas;
import gm.Taller.modelo.Reparaciones;
import gm.Taller.modelo.Vehiculos;
import gm.Taller.servicio.IReparacionServicio;
import gm.Taller.servicio.IPiezaServicio;
import gm.Taller.servicio.IVehiculoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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


    @GetMapping("/reparaciones")
    public ResponseEntity<List<Reparaciones>> getAllReparaciones() {
        List<Reparaciones> reparacionesList = reparacionServicio.listReparaciones(); // Fetch all reparaciones

        for (Reparaciones reparacion : reparacionesList) {
            Vehiculos vehiculo = reparacion.getVehiculo();
            if (vehiculo != null) {
                // Optionally, you could also initialize or fetch the Cliente entity here if needed.
                // This assumes the Cliente entity is directly accessible from Vehiculos.
                Clientes cliente = vehiculo.getCliente(); // Fetch associated client
                reparacion.setVehiculo(vehiculo); // Make sure vehiculo is properly set (already should be)
            }
        }

        return new ResponseEntity<>(reparacionesList, HttpStatus.OK); // Return the list of reparaciones
    }
    @PostMapping("/reparaciones")
    public ResponseEntity<Reparaciones> createReparacion(@RequestBody Reparaciones reparacion) {
        // Log incoming reparacion
        System.out.println("Reparacion received: " + reparacion);

        // Fetch the associated vehicle
        Vehiculos vehiculo = vehiculoServicio.searchVehiculoById(reparacion.getVehiculo().getIdVehiculo());
        if (vehiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Vehicle not found
        }

        // Fetch full piezas details from the database
        List<Piezas> piezasUtilizadas = new ArrayList<>();
        for (Piezas pieza : reparacion.getPiezas()) {
            Piezas piezaInDb = piezaServicio.searchPiezaById(pieza.getIdPieza());
            if (piezaInDb == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Pieza not found
            }
            piezaInDb.setStock(pieza.getStock()); // Set the stock used in this repair
            piezasUtilizadas.add(piezaInDb);
        }

        reparacion.setPiezas(piezasUtilizadas); // Set the full list of piezas

        // Save the new reparacion
        Reparaciones createdReparacion = reparacionServicio.saveReparacion(reparacion);

        return new ResponseEntity<>(createdReparacion, HttpStatus.CREATED);
    }


}
