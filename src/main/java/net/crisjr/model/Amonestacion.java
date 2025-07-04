package net.crisjr.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "amonestaciones")
public class Amonestacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_socio", nullable = false)
    private Usuario socio;

    @Column(nullable = false)
    private LocalDate fecha;

    private String nivel;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Column(name = "fecha_reingreso")
    private LocalDate fechaReingreso;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getSocio() {
        return socio;
    }

    public void setSocio(Usuario socio) {
        this.socio = socio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaReingreso() {
        return fechaReingreso;
    }

    public void setFechaReingreso(LocalDate fechaReingreso) {
        this.fechaReingreso = fechaReingreso;
    }

    @Override
    public String toString() {
        return "Amonestacion [id=" + id + ", socio=" + socio + ", fecha=" + fecha + ", nivel=" + nivel
                + ", descripcion=" + descripcion + ", fechaReingreso=" + fechaReingreso + "]";
    }

}
