//package DAL;
//
//import Entidades.Usuario;
//
//import java.sql.*;
//import java.util.Scanner;
//
//public class Main {
//
//
//    public static Statement statement = null;
//    public static MiConexion con = new MiConexion();
//    public static Connection connection = con;
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
////    private static int menu() {
////    }
//
//
//    /**
//     * Descripcion:
//     * Precondiciones: La base de datos debe de existir
//     * Postcondiciones: La conexion con la base de datos ha sido realizada
//     *
//     * @return
//     */
//    private static void iniciarSesion() {
//        String nombre, contrasenia;
//        System.out.println("Inicio de Sesion");
//        System.out.println("Introduce tu usuario");
//        nombre = sc.nextLine();
//        System.out.println("Introduce tu constraseña");
//        contrasenia = sc.nextLine();
//        if (conIniciarSesion(nombre, contrasenia)) {
//            System.out.println("Iniciado");
//            //lee el mensaje de arriba
//        } else {
//            System.out.println("No iniciado");
//        }
//    }
//
//
//}