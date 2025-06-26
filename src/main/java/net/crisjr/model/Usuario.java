package net.crisjr.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 
    
    @Column(unique = true)
    private String id_usuario;
    private String foto;
    private String nombre;
    private String apellido;
    private String carnet;
    private Date fecha_nacimiento;
    private String ubicacion;
    private String celular;
    private String genero;
    private String estado_civil;
    private Date fecha_ingreso;
    private String estado;
    private Integer es_decano=0;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    //muchos a muchos
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name="UsuarioPerfil",
        joinColumns = @JoinColumn(name="idUsuario"),
        inverseJoinColumns = @JoinColumn(name="idPerfil")
    )
    private List<Perfil> perfiles;

    public void agregar(Perfil tempPerfil){
        if(perfiles==null){
            perfiles= new LinkedList<Perfil>();
        }
        perfiles.add(tempPerfil);
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getCarnet() {
        return carnet;
    }
    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }
    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }
    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    public String getCelular() {
        return celular;
    }
    public void setCelular(String celular) {
        this.celular = celular;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getEstado_civil() {
        return estado_civil;
    }
    public void setEstado_civil(String estado_civil) {
        this.estado_civil = estado_civil;
    }
    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }
    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Integer getEs_decano() {
        return es_decano;
    }
    public void setEs_decano(Integer es_decano) {
        this.es_decano = es_decano;
    }
    public Grupo getGrupo() {
        return grupo;
    }
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
    
    public List<Perfil> getPerfiles() {
        return perfiles;
    }
    public void setPerfiles(List<Perfil> perfiles) {
        this.perfiles = perfiles;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", id_usuario=" + id_usuario + ", foto=" + foto + ", nombre=" + nombre
                + ", apellido=" + apellido + ", carnet=" + carnet + ", fecha_nacimiento=" + fecha_nacimiento
                + ", ubicacion=" + ubicacion + ", celular=" + celular + ", genero=" + genero + ", estado_civil="
                + estado_civil + ", fecha_ingreso=" + fecha_ingreso + ", estado=" + estado + ", es_decano=" + es_decano
                + ", grupo=" + grupo + "]";
    }
}
