package gm.Taller.servicio;

import gm.Taller.modelo.Reparaciones;
import gm.Taller.modelo.Vehiculos;

import java.util.List;

public interface IReparacionServicio {

    public List<Reparaciones> listReparaciones();
    public Reparaciones searchReparacionById(Integer idReparacion);
    public Reparaciones saveReparacion(Reparaciones reparacion);
    public Reparaciones updateReparacion(Integer idReparacion, Reparaciones updatedreparacion);

    public void deleteReparacion(Reparaciones reparacion);
}
