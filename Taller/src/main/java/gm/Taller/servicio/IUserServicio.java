package gm.Taller.servicio;

import gm.Taller.modelo.Users;

import java.util.List;

public interface IUserServicio {

    public List<Users> listUsers();
    public Users searchUserById(Integer idUser);
    public Users saveUser(Users user);


    public void deleteUser(Users user);
}
