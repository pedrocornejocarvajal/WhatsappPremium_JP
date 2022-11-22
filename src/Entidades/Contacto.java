package Entidades;

public class Contacto {
    private String miUsuario;
    private String miContacto;
    private boolean bloqueado;

    public Contacto(){

    }


    public Contacto(String miUsuario, String miContacto, boolean bloqueado) {
        this.miUsuario = miUsuario;
        this.miContacto = miContacto;
        this.bloqueado = bloqueado;
    }

    public String getMiUsuario() {
        return miUsuario;
    }

    public void setMiUsuario(String miUsuario) {
        this.miUsuario = miUsuario;
    }

    public String getMiContacto() {
        return miContacto;
    }

    public void setMiContacto(String miContacto) {
        this.miContacto = miContacto;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
}
