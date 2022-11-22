//package DAL;
//
//import Entidades.Usuario;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class GestorUsuario {
//
//
//
//
//    /**
//     * pene
//     * @param name
//     * @param pass
//     * @return
//     */
//    public boolean insertUsuario(String name, String pass){
//        Usuario usuario = null;
//        var cnn = MiConexion.abrirConexion();
//        boolean filasAfectadas = false;
//
//        try{
//            Statement stmt = cnn.createStatement();
//            String sql = "Insert into ad2223_jdgarcia.Usuarios values( " + name + ", " + pass + " )";
//            filasAfectadas = stmt.execute(sql);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        return filasAfectadas;
//    }
//
//
//
//
//
//
//
//    /**
//     * Descripcion:
//     * Precondiciones: La base de datos debe de existir
//     * Postcondiciones: La conexion con la base de datos ha sido realizada
//     *
//     * @return
//     */
//    private static Usuario conIniciarSesion(String nombre, String contrasenia) {
//
//        Usuario user = null;
//        try{
//
//        }
//
//
//
//
//
//        boolean isIniciado = false;
//        try{
//            ResultSet rs = statement.executeQuery("SELECT idUsuario FROM ad2223_jgarcia.Usuarios WHERE Nombre LIKE ('"+nombre+"') and Contrasenia like ('"+contrasenia+"');");
//            while(rs.next()){
//                if(rs.getInt("idUsuario") != 0){
//                    isIniciado = true;
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return user
//    }
//
//
//
//
//    /**
//     * Descripcion: Metodo para registrar un usuario en la base de datos
//     * Precondiciones: La base de datos debe de existir
//     * Postcondiciones: La conexion con la base de datos ha sido realizada
//     *
//     * @return
//     */
//
//    public void registrarUsuario(Usuario usu) {
//
//        try {
//            //se deshabilita el modo de confirmación automática de la BD
//            connection.setAutoCommit(false);
//            String sql = "INSERT INTO USUARIO('usuario', 'clave', 'apellidos', 'dni') VALUES ( ? ,? , ? , ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, usu.getId());
//            preparedStatement.setString(2, usu.getNombre());
//            preparedStatement.setString(3, usu.getClave());
//            statement.executeUpdate(sql);
//            //se indica que se deben aplicar los cambios en la base de datos
//            connection.commit();
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
//                if(connection!=null) connection.close();
//            } catch (SQLException ex) {
//                System.err.println( ex.getMessage() );
//            }
//        }
//    }
//}
