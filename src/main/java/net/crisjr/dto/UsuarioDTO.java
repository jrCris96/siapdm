package net.crisjr.dto;

public class UsuarioDTO {
    private Integer id;
    private String idUsuario;
    private String nombre;
    private String apellido;

    public UsuarioDTO() {}

    public UsuarioDTO(Integer id, String idUsuario, String nombre, String apellido) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public UsuarioDTO(net.crisjr.model.Usuario u) {
        this.id = u.getId();
        this.idUsuario = u.getIdUsuario();
        this.nombre = u.getNombre();
        this.apellido = u.getApellido();
    }

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
}
