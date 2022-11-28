
package DAL;

import Entidades.*;

import java.sql.*;
import java.util.ArrayList;

public class Listas {







    /**
     *  Descripcion: metodo que recoge el listado completo de contactos de un usuario concreto de la base de datos
     *  Prercondiciones: debe existir la tabla contactos dentro de la base de datos
     *  Postcondiciones: ninguna
     *
     * @param nickName
     * @return
     */
    public static ArrayList<Contacto> getListadoContactos(String nickName) {
        Connection cnn = null;
        ArrayList<Contacto> contactos = new ArrayList<>();
        PreparedStatement Psttmnt = null;
        try {
            cnn = MiConexion.abrirConexion();
            cnn.setAutoCommit(false);
            Psttmnt = cnn.prepareStatement("Select miContacto From ad2223_jgarcia.Contactos Where miUsuario = ?");

            Psttmnt.setString(1, nickName);
            ResultSet result = Psttmnt.executeQuery();

            while (result.next()) {
                var nombre = result.getString(1);
                contactos.add(new Contacto(nickName, nombre, false));
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

