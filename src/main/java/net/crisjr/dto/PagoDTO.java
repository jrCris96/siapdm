package net.crisjr.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PagoDTO {
    private String fechaFormateada;
    private boolean pagado;

    public PagoDTO(LocalDate fecha, boolean pagado) {
        this.fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.pagado = pagado;
    }

    public String getFechaFormateada() {
        return fechaFormateada;
    }

    public boolean isPagado() {
        return pagado;
    }
}

