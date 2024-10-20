package gm.Taller.servicio;

import gm.Taller.modelo.Vehiculos;
import gm.Taller.repositorio.VehiculoRepositorio; // Assuming you have this repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoServicio implements IVehiculoServicio {

    @Autowired
    private VehiculoRepositorio vehiculoRepositorio;

    @Override
    public List<Vehiculos> listVehiculos() {
        return vehiculoRepositorio.findAll();
    }

    @Override
    public Vehiculos searchVehiculoById(Integer idVehiculo) {
        Optional<Vehiculos> vehiculo = vehiculoRepositorio.findById(idVehiculo);
        return vehiculo.orElse(null); // Return the vehicle if found, otherwise null
    }

    @Override
    public Vehiculos saveVehiculo(Vehiculos vehiculo) {
        Optional<Vehiculos> existingVehiculo = vehiculoRepositorio.findByMatriculaVehiculo(vehiculo.getMatriculaVehiculo());

        // Check if the matriculaVehiculo already exists
        if (existingVehiculo.isPresent()) {
            return null; // Optionally, throw an exception or handle it differently
        }

        return vehiculoRepositorio.save(vehiculo);
    }

    @Override
    public Vehiculos updateVehiculo(Integer idVehiculo, Vehiculos updatedVehiculo) {
        if (vehiculoRepositorio.existsById(idVehiculo)) {
            updatedVehiculo.setIdVehiculo(idVehiculo);
            return vehiculoRepositorio.save(updatedVehiculo); // Save the updated vehicle
        }
        return null;
    }

    @Override
    public void deleteVehiculo(Vehiculos vehiculo) {
        vehiculoRepositorio.delete(vehiculo); // Delete the vehicle from the database
    }
}
