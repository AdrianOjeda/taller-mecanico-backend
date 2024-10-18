package gm.Taller.servicio;

import gm.Taller.modelo.Users;
import gm.Taller.repositorio.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServicio implements IUserServicio{

    @Autowired
    private UserRepositorio userRepositorio;

    @Override
    public List<Users> listUsers() {
        return userRepositorio.findAll();

    }

    @Override
    public Users searchUserById(Integer idUser) {
        Users user = userRepositorio.findById(idUser).orElse(null);
        return user;


    }

    @Override
    public Users saveUser(Users user) {
        return userRepositorio.save(user);

    }

    @Override
    public void deleteUser(Users user) {
        userRepositorio.delete(user);

    }
}
