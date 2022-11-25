package DAL;

import Entidades.Contacto;
import Entidades.Usuario;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class GestorUsuario {




    /**
     * @param name
     * @param pass
     * @return
     */
    public static boolean insertUsuario(String name, String pass) {
        var cnn = MiConexion.abrirConexion();
        boolean exito = false;

        try {

            String sql = "Insert into ad2223_jgarcia.Usuarios (Nickname, Contrasenia) values ( '" + name + "' ,'" + pass + "');";
            Statement stmt = cnn.createStatement();
            stmt.executeUpdate(sql);

            exito = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                MiConexion.cerrarConexion(cnn);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return exito;
    }




    /**
     *
     * Descripcion:
     * Precondiciones:
     * Postcondiciones:
     *
     * @param nicknameBloqueador
     * @param nicknameBloqueado
     * @param nuevoEstado
     * @return
     */
    public static boolean bloquearDesbloquearUsuario(String nicknameBloqueador, String nicknameBloqueado, boolean nuevoEstado) {
        boolean exito;
        String sql = "Update ad2223_jgarcia.Contactos set Bloqueado = ? Where (miUsuario = ? and miContacto = ?) or (miUsuario = ? and miContacto = ?)";

        Connection cnn = null;
        try {
            cnn = MiConexion.abrirConexion();
            var pSttmnt = cnn.prepareStatement(sql);
            pSttmnt.setBoolean(1, nuevoEstado);
            pSttmnt.setString(2, nicknameBloqueador);
            pSttmnt.setString(3, nicknameBloqueado);
            pSttmnt.setString(4, nicknameBloqueado);
            pSttmnt.setString(5, nicknameBloqueador);
            pSttmnt.executeUpdate();
            exito = true;
        } catch (SQLException e) {
            System.err.println("Alguno de los parámetros que introdujo en la sentencia no concuerdan con los registros de la base de datos");
            exito = false;
        } finally {
            if (cnn != null) {
                try {
                    MiConexion.cerrarConexion(cnn);
                } catch (SQLException exc) {
                    System.err.println("La conexión que intentó cerrar ya se encontraba cerrada");
                }
            }
        }
        return exito;
    }




    /**
     *
     * Descripcion: Método que inserta un registro en la tabla contactos de la base de datos.
     * @param contacto
     * @return
     *  exit:
     *      -false: El registro no se pudo insertar en la base de datos.
     *      -true: El registro se insertó en la base de datos.
     */
    public static boolean insertContacto(Contacto contacto) {
        boolean exito;
        Connection cnn = null;
        try {
            cnn = MiConexion.abrirConexion();
            PreparedStatement sttmnt = cnn.prepareStatement("Insert into ad2223_jgarcia.Contactos values(miUsuario = ?, miContacto = ?, Bloqueado = 0)");
            sttmnt.setString(1, contacto.getMiUsuario());
            sttmnt.setString(2, contacto.getMiContacto());
            sttmnt.executeUpdate();
            exito = true;

        } catch (SQLException e) {
            exito = false;
        }
        return exito;
    }






    /**
     * Descripcion:
     * Precondiciones: La base de datos debe de existir
     * Postcondiciones: La conexion con la base de datos ha sido realizada
     *
     * @return
     */
    public static boolean comprobarIniciarSesion(String nombre, String contrasenia) {

        var exito = false;
        String sql = "Select * From ad2223_jgarcia.Usuarios Where NickName = ? and Contrasenia = ?";
        try {
            Connection cnn = MiConexion.abrirConexion();
            PreparedStatement pSttmnt = cnn.prepareStatement(sql);
            pSttmnt.setString(1, nombre);
            pSttmnt.setString(2, contrasenia);
            var result = pSttmnt.executeQuery();
            while (result.next()) {
                exito = true;
            }
        } catch (SQLException e) {
            System.err.println("");
        }
        if (!exito) {
            System.err.println("El usuario o contraseña no son correctos");
        }

        return exito;
    }










    /**
     * Descripcion: Metodo para registrar un usuario en la base de datos
     * Precondiciones: La base de datos debe de existir
     * Postcondiciones: La conexion con la base de datos ha sido realizada
     *
     * @return
     */
    public static void registrarUsuario(Usuario usu) {
        Connection connection = null;
        try {
            connection = MiConexion.abrirConexion();
            //se deshabilita el modo de confirmación automática de la BD
            connection.setAutoCommit(false);
            String sql = "INSERT INTO ad2223_jgarcia.usuarios VALUES ( ? ,? )";
            PreparedStatement pSttmnt = connection.prepareStatement(sql);
            pSttmnt.setString(1, usu.getNombre());
            pSttmnt.setString(2, usu.getClave());
            pSttmnt.executeUpdate(sql);
            //se indica que se deben aplicar los cambios en la base de datos
            connection.commit();
        } catch (SQLException ex) {
            System.err.println("ERROR: " + ex.getMessage());
            if (connection != null) {
                System.out.println("Rollback");
                try {
                    //deshace todos los cambios realizados en los datos
                    connection.rollback();
                } catch (SQLException ex1) {
                    System.err.println("No se pudo deshacer" + ex1.getMessage());
                }
            }
        } finally {
            System.out.println("cierra conexion a la base de datos");
            try {
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}