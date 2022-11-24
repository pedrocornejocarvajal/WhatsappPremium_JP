package Entidades;

public class Usuario {
    private int id;
    private String nombre;
    private String clave;


    public Usuario(int _id, String _nombre) {
        this.id = _id;
        this.nombre = _nombre;
    }
    public Usuario(int _id, String _nombre, String _clave) {
        this.id = _id;
        this.nombre = _nombre;
        this.clave = _clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }
}
