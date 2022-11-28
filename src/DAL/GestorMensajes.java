package DAL;

import Entidades.Contacto;
import Entidades.Mensaje;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestorMensajes {


    /**
     * Descripcion: Metodo para insertar un mensaje en la base de datos
     * Precondiciones:
     * Postcondiciones:
     *
     * @param mensaje
     * @return
     */
    public static boolean insertMensaje(Mensaje mensaje) {
        var exito = false;
        Connection cnn = null;
        var sql = "Insert into ad2223_jgarcia.Mensajes (usuarioOrigen, usuarioDestino, Texto, Leido)values (?,?,?,0)";
        try {
            cnn = MiConexion.abrirConexion();
            PreparedStatement sttmnt = cnn.prepareStatement(sql);
            sttmnt.setString(1, mensaje.getUsuarioOrigen());
            sttmnt.setString(2, mensaje.getUsuarioDestino());
            sttmnt.setString(3, mensaje.getMensaje());
            sttmnt.executeUpdate();
            exito = true;
        } catch (SQLException e) {
            System.err.println("Alguno de los datos introducidos no correspondia con los existentes en la Base de Datos");
        }finally{
            if(cnn != null){
               MiConexion.cerrarConexion(cnn);
            }
        }
        return exito;
    }

    /**
     * Descripcion: Metodo que muestra la lista de mensajes detallados con un contacto concreto
     * Precondiciones: El contacto debe existir en la BBDD
     * Postcondiciones: Devuelven los mensajes o un mensaje de error
     *
     * @param contacto
     * @return
     */
    public static ArrayList<Mensaje> getMensajesDeConversacion(Contacto contacto){
            ArrayList<Mensaje> mensajes = new ArrayList<>();

                Connection cnn = null;
                var sql = "Select * From ad2223_jgarcia.Mensajes Where (usuarioOrigen = ? and usuarioDestion = ?) or (usuarioOrigen = ? and usuarioDestion = ?)";
                try {
                    cnn = MiConexion.abrirConexion();
                    PreparedStatement pSttmnt = cnn.prepareStatement(sql);
                    pSttmnt.setString(1, contacto.getMiUsuario());
                    pSttmnt.setString(2, contacto.getMiContacto());
                    pSttmnt.setString(3, contacto.getMiContacto());
                    pSttmnt.setString(4, contacto.getMiUsuario());
                }catch(SQLException e){
                    System.err.println("No tiene ningún mensaje");
                    }


            return mensajes;
    }


    /**
     * Descripcion: Metodo que actualiza un mensaje al estado "leido"
     * Precondiciones: La base ded atos debe existir
     * Postcondiciones: El estado del mensaje debe quedar en estado "Leido"
     *
     * @param mensaje
     * @return
     */
    public static boolean updateMensajeLeido(Mensaje mensaje){
        Connection cnn = null;
        var exito = false;
        String sql = "Update ad2223_jgarcia.Mensajes set Leido = 1 Where usuarioOrigen = ? and usuarioDestion = ?";
        try{
            cnn = MiConexion.abrirConexion();
            PreparedStatement ps = cnn.prepareStatement(sql);
            ps.setString(1, mensaje.getUsuarioOrigen());
            ps.setString(2, mensaje.getUsuarioDestino());
            ps.executeUpdate();
            exito = true;
        }catch(SQLException e){
            System.err.println("Alguno de los datos introducidos no coinciden con la base de datos");

        }
        if(cnn != null){

                MiConexion.cerrarConexion(cnn);

        }
        return exito;
    }


}
