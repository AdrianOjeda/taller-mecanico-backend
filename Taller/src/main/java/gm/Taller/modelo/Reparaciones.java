package gm.Taller.modelo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Reparaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idReparacion;
    String fechaEntrega;
    String falla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculos vehiculo;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "reparacion_piezas",
            joinColumns = @JoinColumn(name = "id_reparacion"),
            inverseJoinColumns = @JoinColumn(name = "id_pieza")
    )
    private List<Piezas> piezas;

}
