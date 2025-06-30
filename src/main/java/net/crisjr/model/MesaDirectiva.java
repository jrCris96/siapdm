package net.crisjr.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "mesa_directiva")
public class MesaDirectiva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;
    private String motivo_cambio;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getFecha_inicio() {
        return fechaInicio;
    }
    public void setFecha_inicio(Date fecha_inicio) {
        this.fechaInicio = fecha_inicio;
    }
    public Date getFecha_fin() {
        return fechaFin;
    }
    public void setFecha_fin(Date fecha_fin) {
        this.fechaFin = fecha_fin;
    }
    public String getMotivo_cambio() {
        return motivo_cambio;
    }
    public void setMotivo_cambio(String motivo_cambio) {
        this.motivo_cambio = motivo_cambio;
    }

    @Override
    public String toString() {
        return "MesaDirectiva [id=" + id + ", fecha_inicio=" + fechaInicio + ", fecha_fin=" + fechaFin
                + ", motivo_cambio=" + motivo_cambio + "]";
    }  
}
