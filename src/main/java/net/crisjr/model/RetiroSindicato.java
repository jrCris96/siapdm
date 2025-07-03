package net.crisjr.model;

import java.math.BigDecimal;
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
@Table(name = "retiros_sindicato")
public class RetiroSindicato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relación con la tabla mesa_directiva_detalle
    @ManyToOne
    @JoinColumn(name = "id_mesa_detalle", nullable = false)
    private DetalleMesa detalleMesa;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "monto", nullable = false)
    private BigDecimal monto;

    @Column(name = "motivo", nullable = false, length = 255)
    private String motivo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DetalleMesa getDetalleMesa() {
        return detalleMesa;
    }

    public void setDetalleMesa(DetalleMesa detalleMesa) {
        this.detalleMesa = detalleMesa;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "RetiroSindicato [id=" + id + ", detalleMesa=" + detalleMesa + ", fecha=" + fecha + ", monto=" + monto
                + ", motivo=" + motivo + "]";
    }
}
