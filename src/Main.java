import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private static final String urlConnection = "jdbc:mysql://dns11036.phdns11.es";
    private static final String user = "ad2223_jgarcia";
    private static final String password = "1234";
    public static Statement statement = null;
    public static Connection connection = crearConexion();

    public static void main(String[] args) {
        try {
            statement = connection.createStatement();
            iniciarSesion();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void iniciarSesion() {
        
    }

    /**
     * Descripcion: Metodo para crear la conexion con la base de datos
     * Precondiciones: La base de datos debe de existir
     * Postcondiciones: La conexion con la base de datos ha sido realizada
     *
     * @return
     */
    private static Connection crearConexion() {
        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(urlConnection, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }
}