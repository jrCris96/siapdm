package net.crisjr.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reuniones_jefes")
public class ReunionJefe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reunion", nullable = false)
    private Reunion reunion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jefe", nullable = false)
    private JefeGrupo jefeGrupo;

    @Column(name = "mensaje_enviado", nullable = false)
    private boolean mensajeEnviado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Reunion getReunion() {
        return reunion;
    }

    public void setReunion(Reunion reunion) {
        this.reunion = reunion;
    }

    public JefeGrupo getJefeGrupo() {
        return jefeGrupo;
    }

    public void setJefeGrupo(JefeGrupo jefeGrupo) {
        this.jefeGrupo = jefeGrupo;
    }

    public boolean isMensajeEnviado() {
        return mensajeEnviado;
    }

    public void setMensajeEnviado(boolean mensajeEnviado) {
        this.mensajeEnviado = mensajeEnviado;
    }

    @Override
    public String toString() {
        return "ReunionJefe [id=" + id + ", reunion=" + reunion + ", jefeGrupo=" + jefeGrupo + ", mensajeEnviado="
                + mensajeEnviado + "]";
    }
}
