package net.crisjr.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "reuniones")
public class Reunion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String asunto;
    private String detalle;
    
    @Column(name = "fecha_reunion")
    private LocalDate fechaReunion;

    @Column(name = "fecha_envio")
    private LocalDate  fechaEnvio;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getAsunto() {
        return asunto;
    }
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
    public String getDetalle() {
        return detalle;
    }
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    public LocalDate getFechaReunion() {
        return fechaReunion;
    }
    public void setFechaReunion(LocalDate fechaReunion) {
        this.fechaReunion = fechaReunion;
    }
    public LocalDate  getFechaEnvio() {
        return fechaEnvio;
    }
    public void setFechaEnvio(LocalDate  fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
    @Override
    public String toString() {
        return "Reunion [id=" + id + ", asunto=" + asunto + ", detalle=" + detalle + ", fechaReunion=" + fechaReunion
                + ", fechaEnvio=" + fechaEnvio + "]";
    }
}
