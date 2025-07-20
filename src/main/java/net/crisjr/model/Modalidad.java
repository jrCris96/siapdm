package net.crisjr.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="modalidad")
public class Modalidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    // Getters y Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Sector getSector() {
        return sector;
    }
    public void setSector(Sector sector) {
        this.sector = sector;
    }

    @Override
    public String toString() {
        return "Modalidad [id=" + id + ", nombre=" + nombre + "]";
    }

}
