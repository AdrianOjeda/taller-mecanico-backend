package gm.Taller.servicio;

import gm.Taller.modelo.Piezas;
import gm.Taller.repositorio.PiezaRepositorio;
import gm.Taller.repositorio.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PiezaServicio implements IPiezaServicio{

    @Autowired
    private PiezaRepositorio piezaRepositorio;
    @Override
    public List<Piezas> listPiezas() {
        return piezaRepositorio.findAll();
    }

    @Override
    public Piezas searchPiezaById(Integer idPieza) {
        Piezas pieza = piezaRepositorio.findById(idPieza).orElse(null);
        return pieza;
    }

    @Override
    public Piezas savePieza(Piezas pieza) {
        return piezaRepositorio.save(pieza);
    }

    @Override
    public Piezas updatePieza(Integer idPieza, Piezas updatedPieza) {
        Piezas existingPieza = piezaRepositorio.findById(idPieza).orElse(null);
        if(existingPieza != null){
            //Updates the content of the existing object with the values of the brand-new object
            existingPieza.setPiezaName(updatedPieza.getPiezaName());
            existingPieza.setPiezaDescripcion(updatedPieza.getPiezaDescripcion());
            existingPieza.setStock(updatedPieza.getStock());

            return piezaRepositorio.save(existingPieza);
        }

        return null;
    }

    @Override
    public void deletePieza(Piezas pieza) {
        piezaRepositorio.delete(pieza);
    }
}
