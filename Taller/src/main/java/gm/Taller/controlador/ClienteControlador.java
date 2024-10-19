package gm.Taller.controlador;


import gm.Taller.modelo.Clientes;
import gm.Taller.servicio.IClienteServicio;
import gm.Taller.servicio.IPiezaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
//http://localhost:8081/taller-app/
@RequestMapping("taller-app")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081"})

public class ClienteControlador {

    private static final Logger logger =
            LoggerFactory.getLogger((ClienteControlador.class));
    @Autowired
    private IClienteServicio clienteServicio;

    @GetMapping("/clientes")
    public List<Clientes>getClientes(){
        var clientes = clienteServicio.listClientes();
        clientes.forEach((cliente->logger.info(cliente.toString())));
        return clientes;
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> createCliente(@RequestBody Clientes newCliente) {
        logger.info(newCliente.toString());

        // Check if the emailCliente already exists
        if (clienteServicio.emailExists(newCliente.getEmailCliente())) {
            return new ResponseEntity<>("El correo del cliente ya existe", HttpStatus.CONFLICT); // 409 Conflict
        }

        // Save the new client
        Clientes savedCliente = clienteServicio.saveCliente(newCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente); // 201 Created
    }

}
