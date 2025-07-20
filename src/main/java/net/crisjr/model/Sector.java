package net.crisjr.model;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="sector") 
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    // Relación con modalidad
    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Modalidad> modalidades = new ArrayList<>();
    
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
        public List<Modalidad> getModalidades() {
        return modalidades;
    }
    public void setModalidades(List<Modalidad> modalidades) {
        this.modalidades = modalidades;
    }
    
    @Override
    public String toString() {
        return "Sector [id=" + id + ", nombre=" + nombre + "]";
    }
    
}
