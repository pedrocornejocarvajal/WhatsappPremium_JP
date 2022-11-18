public class Usuario {
    private int id;
    private String Nombre;
    private String Clave;

    public Usuario(int id, String nombre, String clave) {
        this.id = id;
        Nombre = nombre;
        Clave = clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }
}
