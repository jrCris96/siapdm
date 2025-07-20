package net.crisjr.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="mesa_directiva_detalle")
public class DetalleMesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;

    @ManyToOne
    @JoinColumn(name = "id_socio")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_mesa")
    private MesaDirectiva mesaDirectiva;

    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private CargoMesa cargo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    public Usuario getUsuario() { 
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public MesaDirectiva getMesaDirectiva() {
        return mesaDirectiva;
    }

    public void setMesaDirectiva(MesaDirectiva mesaDirectiva) {
        this.mesaDirectiva = mesaDirectiva;
    }

    public CargoMesa getCargo() {
        return cargo;
    }

    public void setCargo(CargoMesa cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "DetalleMesa [id=" + id + ", estado=" + estado + ", usuario=" + usuario + ", mesaDirectiva="
                + mesaDirectiva + ", cargo=" + cargo + "]";
    }
}
