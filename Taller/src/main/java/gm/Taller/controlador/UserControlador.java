package gm.Taller.controlador;


import gm.Taller.modelo.Users;
import gm.Taller.servicio.IUserServicio;
import gm.Taller.servicio.UserServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//http://localhost:8081/taller-app/
@RequestMapping("taller-app")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081"})
public class UserControlador {
    private static final Logger logger =
            LoggerFactory.getLogger((UserControlador.class));
    @Autowired
    private IUserServicio UserServicio;

    @GetMapping("/users")
    public List<Users> getUsers(){
        var users = UserServicio.listUsers();
        users.forEach((user -> logger.info(user.toString())));

        return users;

    }
    @PostMapping("/users")  // New endpoint for creating users
    public ResponseEntity<Users> createUser(@RequestBody Users newUser) {
        try {
            Users savedUser = UserServicio.saveUser(newUser); // Save the user using the service layer
            logger.info("Usuario creado: " + savedUser);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED); // Return the saved user with a 201 status
        } catch (Exception e) {
            logger.error("Error al crear el usuario: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 if there's an error
        }
    }
}
