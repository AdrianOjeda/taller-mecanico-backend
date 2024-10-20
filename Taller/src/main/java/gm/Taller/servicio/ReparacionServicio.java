package gm.Taller.servicio;

import gm.Taller.modelo.Reparaciones;
import gm.Taller.modelo.Vehiculos;
import gm.Taller.repositorio.ReparacionRepositorio; // Import your repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReparacionServicio implements IReparacionServicio {

    private final ReparacionRepositorio reparacionRepositorio; // Assuming you have a repository
    private final VehiculoServicio vehiculoServicio; // Injecting VehiculoServicio to fetch vehicles

    @Autowired
    public ReparacionServicio(ReparacionRepositorio reparacionRepositorio, VehiculoServicio vehiculoServicio) {
        this.reparacionRepositorio = reparacionRepositorio;
        this.vehiculoServicio = vehiculoServicio;
    }

    @Override
    public List<Reparaciones> listReparaciones() {
        return reparacionRepositorio.findAll();
    }

    @Override
    public Reparaciones searchReparacionById(Integer idReparacion) {
        return reparacionRepositorio.findById(idReparacion)
                .orElseThrow(() -> new RuntimeException("Reparacion not found with ID: " + idReparacion));
    }

    @Override
    public Reparaciones saveReparacion(Reparaciones reparacion) {
        // Validate if the associated vehiculo exists
        if (vehiculoServicio.searchVehiculoById(reparacion.getVehiculo().getIdVehiculo()) == null) {
            throw new RuntimeException("Vehiculo not found with ID: " + reparacion.getVehiculo().getIdVehiculo());
        }
        return reparacionRepositorio.save(reparacion);
    }

    @Override
    public Reparaciones updateReparacion(Integer idReparacion, Reparaciones updatedReparacion) {
        // Fetch the existing reparacion
        Reparaciones existingReparacion = reparacionRepositorio.findById(idReparacion)
                .orElseThrow(() -> new RuntimeException("Reparacion not found with ID: " + idReparacion));

        // Update fields
        existingReparacion.setFechaEntrega(updatedReparacion.getFechaEntrega());
        existingReparacion.setFalla(updatedReparacion.getFalla());
        existingReparacion.setFechaInicio(updatedReparacion.getFechaInicio());

        // Validate if the associated vehiculo exists
        Vehiculos vehiculo = vehiculoServicio.searchVehiculoById(updatedReparacion.getVehiculo().getIdVehiculo());
        if (vehiculo == null) {
            throw new RuntimeException("Vehiculo not found with ID: " + updatedReparacion.getVehiculo().getIdVehiculo());
        }
        existingReparacion.setVehiculo(vehiculo); // Set the fetched vehiculo
        existingReparacion.setPiezas(updatedReparacion.getPiezas()); // Update the piezas list

        // Save the updated reparacion
        return reparacionRepositorio.save(existingReparacion);
    }

    @Override
    public void deleteReparacion(Reparaciones reparacion) {
        reparacionRepositorio.delete(reparacion); // Delete the reparacion
    }
}
