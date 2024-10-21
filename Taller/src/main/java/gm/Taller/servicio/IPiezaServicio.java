package gm.Taller.servicio;

import gm.Taller.modelo.Piezas;

import java.util.List;

public interface IPiezaServicio {

    public List<Piezas> listPiezas();
    public Piezas searchPiezaById(Integer idPieza);
    public Piezas savePieza(Piezas pieza);
    public Piezas updatePieza(Integer idPieza, Piezas updatedPieza);
    public void deletePieza(Piezas pieza);
}
