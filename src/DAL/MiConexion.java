package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class MiConexion {
    private static final String urlConnection = "jdbc:mysql://dns11036.phdns11.es";
    private static final String user = "ad2223_jgarcia";
    private static final String password = "1234";

    /**
     * Descripcion: Metodo para crear la conexion con la base de datos
     * Precondiciones: La base de datos debe de existir
     * Postcondiciones: La conexion con la base de datos ha sido realizada
     *
     * @return
     */
    public static Connection abrirConexion() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return connection = DriverManager.getConnection(urlConnection, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("No se pudo acceder a la base de datos");
        }
        return connection;
    }


    /**
     * Descripcion: Método que se encarga de cerrar la conexion a una base de datos
     * Precondiciones: Ninguna
     * Postcondiciones: La conexion ha sido cerrada
     *
     * @param cnn
     * @throws SQLException
     */
    public static void cerrarConexion(Connection cnn){
        try{
            if(cnn != null){
                cnn.close();
            }
        }catch(SQLException er){
            System.out.println("La conexión no existe o ya estaba cerrada");
        }

    }
}
