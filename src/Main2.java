//import java.sql.*;
//import java.util.Scanner;
//
//public class Main2 {
//
//    private static final String urlConnection = "jdbc:mysql://dns11036.phdns11.es";
//    private static final String user = "ad2223_jgarcia";
//    private static final String password = "1234";
//    public static Statement statement = null;
//    public static Connection connection = crearConexion();
//    public static Scanner sc;
//
//    public static void main(String[] args) {
//        sc = new Scanner(System.in);
//        try {
//            statement = connection.createStatement();
//            iniciarSesion();
////            switch (menu()) {
////                case 1 -> iniciarSesion();
////                case 2 -> crearUsuario();
////            }
//            connection.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        sc.close();
//    }
//
//    private static void crearUsuario() {
//    }
//
////    private static int menu() {
////    }
//
//    private static void iniciarSesion() {
//        String nombre, contrasenia;
//        System.out.println("Inicio de Sesion");
//        System.out.println("Introduce tu usuario");
//        nombre = sc.nextLine();
//        System.out.println("Introduce tu constraseña");
//        contrasenia = sc.nextLine();
//        if (conIniciarSesion(nombre,contrasenia)){
//            System.out.println("Iniciado");
//            //lee el mensaje de arriba
//        } else {
//            System.out.println("No iniciado");
//        }
//    }
//
//    private static boolean conIniciarSesion(String nombre, String contrasenia) {
//        boolean isIniciado = false;
//        try{
//            ResultSet rs = statement.executeQuery("SELECT idUsuario FROM ad2223_jgarcia.Usuarios WHERE Nombre LIKE ('"+nombre+"') and Contrasenia like ('"+contrasenia+"');");
//            if(rs.getInt("idUsuario") != 0){
//                isIniciado = true;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return isIniciado;
//    }
//
//    /**
//     * Descripcion: Metodo para crear la conexion con la base de datos
//     * Precondiciones: La base de datos debe de existir
//     * Postcondiciones: La conexion con la base de datos ha sido realizada
//     *
//     * @return
//     */
//    private static Connection crearConexion() {
//        Connection con;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            con = DriverManager.getConnection(urlConnection, user, password);
//        } catch (ClassNotFoundException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return con;
//    }
//
//    public void registrarUsuario(Usuario usu) {
//
//        try {
//            //se deshabilita el modo de confirmación automática de la BD
//            connection.setAutoCommit(false);
//
//            String sql = "INSERT INTO USUARIO('usuario', 'clave', 'apellidos', 'dni') VALUES ( ? ,? , ? , ?)";
//
//            statement = connection.prepareStatement(sql);
//
//            statement.setCursorName(1, usu.getId());
//            statement.(2, usu.getNombre());
//            statement.(3, usu.getClave());
//
//            statement.executeUpdate(sql);
//
//            //se indica que se deben aplicar los cambios en la base de datos
//            connection.commit();
//
//        } catch (SQLException ex) {
//            System.err.println("ERROR: " + ex.getMessage());
//            if(connection!=null)
//            {
//                System.out.println("Rollback");
//                try {
//                    //deshace todos los cambios realizados en los datos
//                    connection.rollback();
//                } catch (SQLException ex1) {
//                    System.err.println( "No se pudo deshacer" + ex1.getMessage() );
//                }
//            }
//        }finally{
//            System.out.println( "cierra conexion a la base de datos" );
//            try {
//                if(statement!=null) statement.close();
//                if(connection!=null) connection.close();
//            } catch (SQLException ex) {
//                System.err.println( ex.getMessage() );
//            }
//        }
//    }
//
//
//
//}