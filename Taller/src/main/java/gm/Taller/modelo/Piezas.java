package gm.Taller.modelo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Piezas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idPieza;
    String piezaName;
    String piezaDescripcion;
    Integer stock;

    public Integer getIdPieza(){
        return idPieza;
    }
    public String getPiezaName(){
        return piezaName;
    }
    public String getPiezaDescripcion(){
        return piezaDescripcion;
    }
    public Integer getStock(){
        return stock;
    }

    public void setIdPieza(Integer id) {
        this.idPieza = id;
    }

    public void setPiezaName(String piezaName) {
        this.piezaName = piezaName;
    }

    public void setPiezaDescripcion(String piezaDescripcion) {
        this.piezaDescripcion = piezaDescripcion;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
