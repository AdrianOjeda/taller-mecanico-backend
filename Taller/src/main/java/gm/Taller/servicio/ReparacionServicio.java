package gm.Taller.servicio;

import gm.Taller.modelo.Reparaciones;
import gm.Taller.modelo.Vehiculos;
import gm.Taller.repositorio.ReparacionRepositorio; // Import your repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReparacionServicio implements IReparacionServicio {

    private final ReparacionRepositorio reparacionRepositorio; // Assuming you have a repository
    private final VehiculoServicio vehiculoServicio; // Injecting VehiculoServicio to fetch vehicles

    @Autowired
    public ReparacionServicio(ReparacionRepositorio reparacionRepositorio, VehiculoServicio vehiculoServicio) {
        this.reparacionRepositorio = reparacionRepositorio;
        this.vehiculoServicio = vehiculoServicio;
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Reparaciones> listReparaciones() {
        return reparacionRepositorio.findAll();
    }

    @Override
    public List<Map<String, Object>> fechas(){
        String sql = "SELECT " +
                "    CASE " +
                "        WHEN (TO_DATE(r.fecha_entrega, 'YYYY-MM-DD') - TO_DATE(r.fecha_inicio, 'YYYY-MM-DD')) BETWEEN 0 AND 7 THEN '0-7 dias' " +
                "        WHEN (TO_DATE(r.fecha_entrega, 'YYYY-MM-DD') - TO_DATE(r.fecha_inicio, 'YYYY-MM-DD')) BETWEEN 8 AND 14 THEN '8-14 dias' " +
                "        WHEN (TO_DATE(r.fecha_entrega, 'YYYY-MM-DD') - TO_DATE(r.fecha_inicio, 'YYYY-MM-DD')) BETWEEN 15 AND 21 THEN '15-21 dias' " +
                "        ELSE 'Mas de 21 dias' " +
                "    END AS repair_duration, " +
                "    COUNT(*) AS total_reparaciones " +
                "FROM reparaciones r " +
                "WHERE TO_DATE(r.fecha_inicio, 'YYYY-MM-DD') IS NOT NULL  " +
                "  AND TO_DATE(r.fecha_entrega, 'YYYY-MM-DD') IS NOT NULL " +
                "GROUP BY repair_duration "+
                "LIMIT 10 ";
        return jdbcTemplate.queryForList(sql);
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
