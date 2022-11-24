
package DAL;

import Entidades.*;

import java.sql.*;
import java.util.ArrayList;

public class Listas {






    /**
     * public ArrayList<Usuario> getListadoUsuarios()
     * Descripcion: metodo que
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
                try {
                    MiConexion.cerrarConexion(cnn);
                } catch (SQLException exc) {
                    System.err.println("La conexión que intentó cerrar ya se encontraba cerrada");
                }
            }
        }
        return usuarios;
    }

    /**
     *
     *
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
            Psttmnt = cnn.prepareStatement("Select miContacto From ad2223_jdgarcia.Contactos Where miUsuario = ?");

            Psttmnt.setString(1, nickName);
            ResultSet result = Psttmnt.executeQuery();

            while (result.next()) {
                var salir = false;
                var nombre = result.getString(0);
                contactos.add(new Usuario(nombre));
            }
        } catch (SQLException e) {
            System.out.println("Error de Acceso a la Base de Datos o Conexion no Inicializada.");
        } finally {
            if (cnn != null) {
                try {
                    MiConexion.cerrarConexion(cnn);
                } catch (SQLException exc) {
                    System.out.println("La conexión que intentó cerrar ya se encontraba cerrada");
                }
            }
        }
        return contactos;
    }





}

