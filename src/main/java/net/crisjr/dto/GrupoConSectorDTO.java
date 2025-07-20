package net.crisjr.dto;

import net.crisjr.model.Grupo;

public class GrupoConSectorDTO {
    private Integer id;
    private String nombre;
    private Integer numero;
    private Integer sectorId;
    private String sectorNombre;

    // Constructor
    public GrupoConSectorDTO(Grupo grupo) {
        this.id = grupo.getId();
        this.nombre = grupo.getNombre();
        this.numero = grupo.getNumero();
        if (grupo.getSector() != null) {
            this.sectorId = grupo.getSector().getId();
            this.sectorNombre = grupo.getSector().getNombre();
        }
    }

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

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getSectorId() {
        return sectorId;
    }

    public void setSectorId(Integer sectorId) {
        this.sectorId = sectorId;
    }

    public String getSectorNombre() {
        return sectorNombre;
    }

    public void setSectorNombre(String sectorNombre) {
        this.sectorNombre = sectorNombre;
    }

    
}
