package gm.Taller.controlador;


import gm.Taller.modelo.Users;
import gm.Taller.servicio.IUserServicio;
import gm.Taller.servicio.UserServicio;
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
}
