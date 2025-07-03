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
@Table(name = "aportes_socio")
public class AporteSocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuarioId", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "aporte_grupal_id", nullable = false)
    private AporteGrupal aporteGrupal;

    @Column(name = "pagado", nullable = false)
    private Boolean pagado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public AporteGrupal getAporteGrupal() {
        return aporteGrupal;
    }

    public void setAporteGrupal(AporteGrupal aporteGrupal) {
        this.aporteGrupal = aporteGrupal;
    }

    public Boolean getPagado() {
        return pagado;
    }

    public void setPagado(Boolean pagado) {
        this.pagado = pagado;
    }

    @Override
    public String toString() {
        return "AporteSocio [id=" + id + ", usuario=" + usuario + ", aporteGrupal=" + aporteGrupal + ", pagado="
                + pagado + "]";
    }

}