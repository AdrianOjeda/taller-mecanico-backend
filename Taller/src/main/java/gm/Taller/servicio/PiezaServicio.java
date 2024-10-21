package gm.Taller.servicio;

import gm.Taller.modelo.Piezas;
import gm.Taller.repositorio.PiezaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;

@Service
public class PiezaServicio implements IPiezaServicio{

    @Autowired
    private PiezaRepositorio piezaRepositorio;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> listPiezas() {
        String sql = "SELECT * FROM piezas";
        return jdbcTemplate.queryForList(sql);
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
