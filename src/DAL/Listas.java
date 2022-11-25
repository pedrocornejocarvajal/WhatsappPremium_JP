
package DAL;

import Entidades.*;

import java.sql.*;
import java.util.ArrayList;

public class Listas {



    /**
     *
     * Descripcion: metodo que recoge el listado completo de usuarios de la tabla Usuarios de la base de datos
     * Precondiciones: debe xistir la tabla usuarios dentro de la base de datos
     * Postcondiciones: ninguna
     *
     * @return
     */
    public ArrayList<Usuario> getListadoUsuarios() {
        Connection cnn = MiConexion.abrirConexion();
        ArrayList<Usuario> usuarios = new ArrayList<>();

        try {
            Statement Psttmnt = cnn.createStatement();
            ResultSet result = Psttmnt.executeQuery("Select * From ad2223_jdgarcia.Usuarios");
            while (result.next()) {
                usuarios.add(new Usuario(result.getString(0), result.getString(1)));
            }
        } catch (SQLException e) {
            System.err.println("Error de Acceso a la Base de Datos o Conexion no Inicializada.");
        } finally {
            if (cnn != null) {

                    MiConexion.cerrarConexion(cnn);

            }
        }
        return usuarios;
    }



    /**
     *  Descripcion: metodo que recoge el listado completo de contactos de un usuario concreto de la base de datos
     *  Prercondiciones: debe existir la tabla contactos dentro de la base de datos
     *  Postcondiciones: ninguna
     *
     * @param nickName
     * @return
     */
    public static ArrayList<Usuario> getListadoContactos(String nickName) {
        Connection cnn = null;
        ArrayList<Usuario> contactos = new ArrayList<>();
        PreparedStatement Psttmnt = null;
        try {
            cnn = MiConexion.abrirConexion();
            cnn.setAutoCommit(false);
            Psttmnt = cnn.prepareStatement("Select miContacto From ad2223_jdgarcia.Contactos Where miUsuario = ?");

            Psttmnt.setString(1, nickName);
            ResultSet result = Psttmnt.executeQuery();

            while (result.next()) {
                var nombre = result.getString(0);
                contactos.add(new Usuario(nombre));
            }
            cnn.commit();
        } catch (SQLException e) {
            System.out.println("Error de Acceso a la Base de Datos o Conexion no Inicializada.");
        } finally {
            if (cnn != null) {
                    MiConexion.cerrarConexion(cnn);

            }
        }
        return contactos;
    }





}

