
package DAL;

import Entidades.*;

import java.sql.*;
import java.util.ArrayList;

public class Listas {






    /**
     *
     *
     *
     * @return
     */
    public ArrayList<Usuario> getListadoUsuarios() {
        Connection cnn = MiConexion.abrirConexion();
        ArrayList<Usuario> usuarios = new ArrayList<>();

        try {
            Statement Psttmnt = cnn.createStatement();
            ResultSet result = Psttmnt.executeQuery("Select * From Usuarios");
            while (result.next()) {
                usuarios.add(new Usuario(result.getInt(0), result.getString(1), result.getString(1)));
            }
        } catch (SQLException e) {
            System.out.println("error de acceso a la base de datos o conexion no inicializada.");
        } finally {
            if (cnn != null) {
                try {
                    MiConexion.cerrarConexion(cnn);
                } catch (SQLException exc) {
                    System.out.println("La conexión que intentó cerrar ya se encontraba cerrada");
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
    public ArrayList<Usuario> getListadoContactos(String nickName) {
        Connection cnn = MiConexion.abrirConexion();
        ArrayList<Usuario> contactos = new ArrayList<>();
        var usuarios = getListadoUsuarios();
        try {
            PreparedStatement Psttmnt = cnn.prepareStatement("Select miContacto From Contactos Where miUsuario = ?");
            Psttmnt.setString(1, nickName);
            ResultSet result = Psttmnt.executeQuery();
            while (result.next()) {
                var salir = false;
                var nombre = result.getString(0);
                for (int i = 0; i < usuarios.size() && !salir; i++) {
                    if (usuarios.get(i).getNombre().equals(nombre)) {
                        contactos.add(usuarios.get(i));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("error de acceso a la base de datos o conexion no inicializada.");
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

    /**
     *
     *
     *
     * @param bloqueador
     * @param bloqueado
     * @param nuevoEstado
     * @return
     */
    public boolean bloquearDesbloquearUsuario(String bloqueador, String bloqueado, boolean nuevoEstado) {
        boolean exito;
        String sql = "Update Contactos set bloqueado = ? Where (miUsuario = ? and miContacto = ?) or (miUsuario = ? and miContacto = ?)";

        Connection cnn = null;
        try {
            cnn = MiConexion.abrirConexion();
            var pSttmnt = cnn.prepareStatement(sql);
            pSttmnt.setBoolean(1, nuevoEstado);
            pSttmnt.setString(2, bloqueador);
            pSttmnt.setString(3, bloqueado);
            pSttmnt.setString(4, bloqueado);
            pSttmnt.setString(5, bloqueador);

            pSttmnt.executeUpdate();

            exito = true;

        } catch (SQLException e) {
            System.out.println("Alguno de los parámetros que introdujo en la sentencia no concuerdan con los registros de la base de datos");
            exito = false;
        } finally {
            if (cnn != null) {
                try {
                    MiConexion.cerrarConexion(cnn);
                } catch (SQLException exc) {
                    System.out.println("La conexión que intentó cerrar ya se encontraba cerrada");
                }
            }
        }

        return exito;
    }







}

