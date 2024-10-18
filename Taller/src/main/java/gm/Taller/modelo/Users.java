package gm.Taller.modelo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idUser;
    String username;
    String password;
    String name;
    String lastName;
    String cellPhone;
    String address;
    @Enumerated(EnumType.STRING)
    Role role;



}
