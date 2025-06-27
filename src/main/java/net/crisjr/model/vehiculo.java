package net.crisjr.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="vehiculo")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String foto;
    private String tipo_vehiculo;
    private String placa;
    private String marca;
    private String modelo;
    private String color;
    private String año_fabricacion;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuarioId; 

    @ManyToOne
    @JoinColumn(name = "socioAsalariadoId")
    private Usuario socioAsalariadoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTipo_vehiculo() {
        return tipo_vehiculo;
    }

    public void setTipo_vehiculo(String tipo_vehiculo) {
        this.tipo_vehiculo = tipo_vehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAño_fabricacion() {
        return año_fabricacion;
    }

    public void setAño_fabricacion(String año_fabricacion) {
        this.año_fabricacion = año_fabricacion;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario getSocioAsalariadoId() {
        return socioAsalariadoId;
    }

    public void setSocioAsalariadoId(Usuario socioAsalariadoId) {
        this.socioAsalariadoId = socioAsalariadoId;
    }

    @Override
    public String toString() {
        return "Vehiculo [id=" + id + ", foto=" + foto + ", tipo_vehiculo=" + tipo_vehiculo + ", placa=" + placa
                + ", marca=" + marca + ", modelo=" + modelo + ", color=" + color + ", año_fabricacion="
                + año_fabricacion + ", usuarioId=" + usuarioId + ", socioAsalariadoId=" + socioAsalariadoId + "]";
    }

}
