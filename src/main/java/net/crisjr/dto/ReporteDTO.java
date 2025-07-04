package net.crisjr.dto;

import java.math.BigDecimal;

public class ReporteDTO {
    private String nombreGrupo;
    private BigDecimal montoTotal;

    public ReporteDTO(String nombreGrupo, BigDecimal montoTotal) {
        this.nombreGrupo = nombreGrupo;
        this.montoTotal = montoTotal;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }  
}
