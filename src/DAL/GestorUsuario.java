package DAL;

import Entidades.Contacto;
import java.sql.*;

public class GestorUsuario {





    /**
     * Descripcion: Metodo para poder insertar un usuario en la base de datos. Los datos se pasan por parametros
     * Precondiciones: ninguna
     * Postcondiciones: el usuario se ha creado con exito
     *
     * @param name
     * @param pass
     * @return exito
     */
    public static boolean insertUsuario(String name, String pass) {
        var cnn = MiConexion.abrirConexion();
        boolean exito = false;

        try {

            String sql = "Insert into ad2223_jgarcia.Usuarios (Nickname, Contrasenia) values ( '" + name + "' ,'" + pass + "');";
            Statement stmt = cnn.createStatement();
            stmt.executeUpdate(sql);

            exito = true;
        } catch(SQLIntegrityConstraintViolationException p) {
            System.out.println("Ya existe un usuario con ese nick");
        } catch (SQLException e) {
            System.out.println("Los datos introducidos no coinciden con la base de datos.");
        } finally
         {

                MiConexion.cerrarConexion(cnn);

        }
        return exito;
    }



    /**
     * Descripcion: Metodo para bloquear a un usuario elegido de la lista de contactos
     * Precondiciones: el usuario esta en la lista de contactos
     * Postcondiciones: el usuario se ha bloqueado con exito
     *
     * @param nicknameBloqueador
     * @param nicknameBloqueado
     * @param nuevoEstado
     * @return exito
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

                    MiConexion.cerrarConexion(cnn);

            }
        }
        return exito;
    }

    /**
     * Descripcion: Método que inserta un registro en la tabla contactos de la base de datos.
     * Precondiciones: el contacto introducido existe en la lista de usuarios
     * Postcondiciones: ninguna
     *
     * @param contacto
     * @return exit:
     * -false: El registro no se pudo insertar en la base de datos.
     * -true: El registro se insertó en la base de datos.
     */
    public static boolean insertContacto(Contacto contacto) {
        boolean exito;
        Connection cnn = null;
        try {
            cnn = MiConexion.abrirConexion();
            cnn.setAutoCommit(false);
            PreparedStatement sttmnt = cnn.prepareStatement("Insert into ad2223_jgarcia.Contactos values(?, ?," + "0)");
            sttmnt.setString(1, contacto.getMiUsuario());
            sttmnt.setString(2, contacto.getMiContacto());
            sttmnt.executeUpdate();
            exito = true;
            cnn.commit();
        } catch (SQLException e) {
            exito = false;
        } finally {
            if (cnn != null) {
                    MiConexion.cerrarConexion(cnn);

            }
        }
        return exito;
    }

    /**
     * Descripcion: Metodo que comprueba si los datos de inicio de sesion introducidos son correctos
     * Precondiciones: La base de datos debe de existir
     * Postcondiciones: La conexion con la base de datos ha sido realizada
     *
     * @return
     */
    public static boolean comprobarIniciarSesion(String nombre, String contrasenia) {
        Connection cnn = null;
        var exito = false;
        String sql = "Select * From ad2223_jgarcia.Usuarios Where NickName = ? and Contrasenia = ?";
        try {
            cnn = MiConexion.abrirConexion();
            PreparedStatement pSttmnt = cnn.prepareStatement(sql);
            pSttmnt.setString(1, nombre);
            pSttmnt.setString(2, contrasenia);
            var result = pSttmnt.executeQuery();
            while (result.next()) {
                exito = true;
            }
        } catch (SQLException e) {
            System.err.println("La conexión que intenta cerrar ya se encontraba cerrada o no existe aún.");
        }finally{
            if(cnn != null){
                    MiConexion.cerrarConexion(cnn);

            }
        }
        if (!exito) {
            System.err.println("El usuario o contraseña no son correctos");
        }

        return exito;
    }



    /**
     * Descripcion:
     * Precondiciones: el usuario esta en la lista de contactos
     * Postcondiciones: el usuario se ha bloqueado con exito
     *
     * @param contacto
     * @return exito
     */
    public static boolean comprobarContactoDeUsuario(Contacto contacto){
        var exito = false;
        Connection cnn = null;
        var sql = "Select miContacto From ad2223_jgarcia.Contactos Where miUsuario = ? and miContacto = ?";

        try{
            cnn = MiConexion.abrirConexion();
            PreparedStatement psttmnt = cnn.prepareStatement(sql);
            psttmnt.setString(1, contacto.getMiUsuario());
            psttmnt.setString(2, contacto.getMiContacto());
            ResultSet result = psttmnt.executeQuery();
            result.next();
            result.getString(1);
            exito = true;
        }catch(SQLException e){
            System.err.println("Este usuario no se encuentra entre su lista de contactos");
        }
        return exito;
    }
}