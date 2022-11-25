package DAL;

import Entidades.Mensaje;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class GestorMensajes {


    /**
     *
     * @param mensaje
     * @return
     */
    public static boolean insertMensaje(Mensaje mensaje) {
        var exito = false;
        Connection cnn = null;
        var sql = "Insert into ad2223_jgarcia.Mensajes (usuarioOrigen, usuarioDestino, Texto, Fecha, Leido) values (?,?,?,?,0)";
        try {
            cnn = MiConexion.abrirConexion();
            PreparedStatement sttmnt = cnn.prepareStatement(sql);
            sttmnt.setString(1, mensaje.getUsuarioOrigen());
            sttmnt.setString(2, mensaje.getUsuarioDestino());
            sttmnt.setString(3, mensaje.getMensaje());
            sttmnt.setTimestamp(4, new Timestamp(mensaje.getFecha().getTime()));

        } catch (SQLException e) {
            System.err.println("Alguno de los datos introducidos no correspondia con los existentes en la Base de Datos");
        }
        return exito;
    }

}