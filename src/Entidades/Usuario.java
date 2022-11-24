package Entidades;

public class Usuario {
    private String nombre;
    private String clave;


    public Usuario(String _nombre) {
        this.nombre = _nombre;
    }

    public Usuario() {
    }


    public Usuario( String _nombre, String _clave) {
        this.nombre = _nombre;
        this.clave = _clave;
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
