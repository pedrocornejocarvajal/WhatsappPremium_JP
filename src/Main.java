import javax.sound.midi.Soundbank;
import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String urlConnection = "jdbc:mysql://dns11036.phdns11.es";
    private static final String user = "ad2223_jgarcia";
    private static final String password = "1234";
    public static Statement statement = null;
    public static Connection connection = crearConexion();
    public static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        try {
            statement = connection.createStatement();
            iniciarSesion();
//            switch (menu()) {
//                case 1 -> iniciarSesion();
//                case 2 -> crearUsuario();
//            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sc.close();
    }

    private static void crearUsuario() {
    }

//    private static int menu() {
//    }

    private static void iniciarSesion() {
        String nombre, contrasenia;
        System.out.println("Inicio de Sesion");
        System.out.println("Introduce tu usuario");
        nombre = sc.nextLine();
        System.out.println("Introduce tu constraseña");
        contrasenia = sc.nextLine();
        if (conIniciarSesion(nombre,contrasenia)){
            System.out.println("Iniciado");
            //lee el mensaje de arriba
        } else {
            System.out.println("No iniciado");
        }
    }

    private static boolean conIniciarSesion(String nombre, String contrasenia) {
        boolean isIniciado = false;
        try{
            ResultSet rs = statement.executeQuery("SELECT idUsuario FROM ad2223_jgarcia.Usuarios WHERE Nombre LIKE ('"+nombre+"') and Contrasenia like ('"+contrasenia+"');");
            while(rs.next()){
                if(rs.getInt("idUsuario") != 0){
                    isIniciado = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isIniciado;
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