package gm.Taller.controlador;


import gm.Taller.modelo.Piezas;
import gm.Taller.servicio.IPiezaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//http://localhost:8081/taller-app/
@RequestMapping("taller-app")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081"})

public class PiezaControlador {
    private static final Logger logger =
            LoggerFactory.getLogger((PiezaControlador.class));
    @Autowired
    private IPiezaServicio PiezaServicio;


    @GetMapping("/piezas")
    public List<Piezas> getPiezas(){

        var piezas = PiezaServicio.listPiezas();
        piezas.forEach((pieza -> logger.info(pieza.toString())));

        return piezas;
    }
}
