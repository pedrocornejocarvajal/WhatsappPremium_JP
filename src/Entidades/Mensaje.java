package Entidades;

import java.sql.Timestamp;
import java.util.Date;

public class Mensaje {
    private int idMensaje;
    private String usuarioOrigen;
    private String usuarioDestino;
    private String mensaje;
    private Timestamp fecha;
    private boolean leido;


    public Mensaje() {
    }

    public Mensaje( String usuarioOrigen, String usuarioDestino, String mensaje, Timestamp fecha, boolean leido) {
        this.usuarioOrigen = usuarioOrigen;
        this.usuarioDestino = usuarioDestino;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.leido = leido;
    }

    public String getUsuarioOrigen() {
        return usuarioOrigen;
    }

    public void setUsuarioOrigen(String usuarioOrigen) {
        this.usuarioOrigen = usuarioOrigen;
    }

    public String getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(String usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }



    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha() {
        return fecha;
    }


    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }
}
