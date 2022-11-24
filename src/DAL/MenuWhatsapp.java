package DAL;

import Entidades.Contacto;
import Entidades.Usuario;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuWhatsapp {

    private static final Usuario SPAM = new Usuario("SPAM");

    private Usuario usuario;
    private static Scanner sc = new Scanner(System.in);

    public MenuWhatsapp() {

        usuario = new Usuario();
    }

    /**
     * Descripcion:
     * Precondiciones:
     * Postcondiciones:
     *
     * @param
     */
    public void start() {
        var salir = false;
        while (!salir) {
            switch (menu()) {
                case 1 -> iniciarSesion();
                case 2 -> crearUsuario();
                case 0 -> salir = true;
            }
        }
        System.out.println("¡Vuelve pronto!");
    }




    /**
     * Descripcion:
     * Precondiciones:
     * Postcondiciones:
     *
     * @return
     */
    private int menu() {
        int opc;

        System.out.println("""
                Bienvenido a Whatsapp PCJ
                [1]. Iniciar sesion
                [2]. Crear usuario
                Pulse cualquier tecla para salir.
                Elige una opcion:
                """);

        opc = comprobarOpcion(sc.nextLine());


        return opc;
    }

    private void menuHablarOAgregar() {

    }


    /**
     * Descripcion:
     *
     * @param opcionString
     * @return
     */
    private static int comprobarOpcion(String opcionString) {
        int opcion = -1;

        try {
            opcion = Integer.parseInt(opcionString);
        } catch (NumberFormatException r) {
            System.err.println("Debe introducir un número");
        }

        return opcion;
    }

    /**
     * Descripcion:
     * Precondiciones:
     * Postcondiciones:
     *
     * @return
     */
    private void iniciarSesion() {
        String nombre, contrasenia;
        System.out.println("Inicio de Sesion");
        System.out.println("Introduce tu usuario");
        nombre = sc.nextLine();
        System.out.println("Introduce tu constraseña");
        contrasenia = sc.nextLine();
        if (GestorUsuario.comprobarIniciarSesion(nombre, contrasenia)) {
            usuario = new Usuario(nombre);
            muestraContactosUsuario();
        } else {
            switch (menuNoIniciado()) {
                case 1 -> crearUsuario();
                case 2 -> iniciarSesion();
                default -> System.out.println("¡Vuelve pronto!");
            }
        }
    }




    /**
     * Descripcion:
     * Precondiciones:
     * Postcondiciones:
     */
    private void crearUsuario() {
        String nombre, contrasenia;
        System.out.println("Introduce nombre de usuario");
        nombre = sc.nextLine();
        System.out.println("Introduce una contraseña contraseña");
        contrasenia = sc.nextLine();
        if (GestorUsuario.insertUsuario(nombre, contrasenia)) {
            GestorUsuario.insertContacto(new Contacto(nombre, SPAM.getNombre(), false));
            System.out.println("Creado con exito");

        } else {
            System.out.println("Se ha producido un error");
        }
    }




    /**
     * Descripcion:
     * Precondiciones:
     * Postcondiciones:
     *
     */
    private void muestraContactosUsuario() {
        ArrayList<Usuario> lista = Listas.getListadoContactos(usuario.getNombre());
        if (lista.size() == 1) {

            menuAgregarUsuario();
        } else {
            //Muestra usuarios y te dice con quien hablar segun el nickname
            for (Usuario user : lista) {
                System.out.println(user.getNombre());
            }
            //Menu para hablar o agregar nuevo usuario


        }
    }

    /**
     * Descripcion:
     * Precondiciones:
     * Postcondiciones:
     *
     */
    private void menuAgregarUsuario() {
        boolean salir = false;
        while (salir) {
            System.out.println("Introduce el nombre del usuario");
            var nick = sc.nextLine();
            if (GestorUsuario.insertContacto(new Contacto(usuario.getNombre(), nick, false))) {
                System.out.println("Has agregado a " + nick + " a tu lista de contactos");
                salir = true;
            } else {
                System.out.println("No se encontró " + nick + " en WhatsUp");
                System.out.println("1. volver a intentarlo, 2. Salir");
                switch (sc.nextLine()) {
                    case "1" -> salir = false;
                    case "2" -> salir = true;
                }
            }
        }
    }

    /**
     * Descripcion:
     * Precondiciones:
     * Postcondiciones:
     */
    private void menuHablarBloquear() {


    }


    /**
     * Descripcion: Menu que muestra
     * Precondiciones:
     * Postcondiciones:
     *
     * @return opcion
     */

    private static int menuNoIniciado() {
        String opc;
        int opcion = 0;
        System.out.println("""
                No has podido iniciar sesion :(
                ¿Deseas crear un usuario?
                [1]. Si
                [2]. Intentar volver a iniciar sesion
                Pulse cualquier tecla para salir.
                Elige una opcion:
                """);
        opcion = comprobarOpcion(sc.nextLine());
        return opcion;
    }
}
