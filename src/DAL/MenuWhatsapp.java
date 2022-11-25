package DAL;

import Entidades.Contacto;
import Entidades.Mensaje;
import Entidades.Usuario;

import java.time.Instant;
import java.util.*;

public class MenuWhatsapp {


    private static final Usuario SPAM = new Usuario("SPAM");
    private static final String MENSAJESPAM = "Bienvenido a Whastapp PCJ, el lugar donde tus datos están tan seguros como un niño ucraniano en Rusia. Compra en Eneba";

    private Usuario usuario;
    private ArrayList<Usuario> contactosUsuario;
    private static Scanner sc = new Scanner(System.in);

    public MenuWhatsapp() {

        usuario = new Usuario();
    }

    /**
     * Descripcion: Metodo que te manda a las funciones de iniciar sesion o crear usuario segun la eleccion introducida en el metodo menu()
     * Precondiciones: Ninguna
     * Postcondiciones: Ninguna
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
     * Descripcion: Metodo que te muestra las opciones del menu por pantalla y te pide que elijas una de ellas o pulses cualquier tecla para salir.
     * Precondiciones: ninguna
     * Postcondiciones: Devuelve la opcion elegida por el usuario
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
                Elige una opcion:""");
        opc = comprobarOpcion(sc.nextLine());
        return opc;
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
     * Descripcion: Metodo que te muestra el menu para iniciar sesion, recoge los datos introducidos, si el usuario es correcto inicia la sesion correctamente, sino, te las opciones de crear inciar de nuevo
     * Precondiciones: ninguna
     * Postcondiciones: ninguna
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
     * Descripcion: Metodo que te muestra el menu de creacion de usuario y recoge los datos, despues le añade un usuario de Soporte de aplicacion con el mensaje de bienvenida
     * Precondiciones: Ninguna
     * Postcondiciones: Ninguna
     */
    private void crearUsuario() {
        String nombre, contrasenia;
        System.out.println("Introduce nombre de usuario");
        nombre = sc.nextLine();
        System.out.println("Introduce una contraseña contraseña");
        contrasenia = sc.nextLine();
        if (GestorUsuario.insertUsuario(nombre, contrasenia)) {

            GestorUsuario.insertContacto(new Contacto(nombre, SPAM.getNombre(), false));
            GestorMensajes.insertMensaje(new Mensaje(SPAM.getNombre(), usuario.getNombre(), MENSAJESPAM, Date.from(Instant.now()), false));
            System.out.println("Creado con exito");

        } else {
            System.out.println("Se ha producido un error");
        }
    }


    /**
     *
     * Descripcion:
     * Precondidiones:
     * Postcondiciones:
     *
     * @param contacto
     */
    private void mostrarCoversacion(Contacto contacto){
        ArrayList<Mensaje> mensajes = GestorMensajes.getMensajesDeConversacion(contacto);
        String usuarioOrigen, mnsj, hora;
        for (Mensaje mensaje : mensajes ) {
            //Comprobamos quien es el emisor
            if(mensaje.getUsuarioOrigen().equals(usuario.getNombre())){
                //Si es el usuario lo situaremos en la parte derecha de la pantalla
                usuarioOrigen = String.format("|%10s|", mensaje.getUsuarioOrigen());
                mnsj = String.format("|%10s|", mensaje.getFecha().toString());
                hora = String.format("|%10s|", mensaje.getMensaje());
            }else{
                //Si el emisor es el contacto del usuario se posicionará en la parte izquierda de la pantalla
                usuarioOrigen = String.format("|%-10s|", mensaje.getUsuarioOrigen());
                mnsj = String.format("|%-10s|", mensaje.getFecha().toInstant());
                hora = String.format("|%-10s|", mensaje.getMensaje());
            }
            System.out.println("========================");
            System.out.println(hora);
            System.out.println(usuarioOrigen);
            System.out.println(mensaje);
            System.out.println("========================");

        }
    }
    /**
     * Descripcion: Método que te muestra la lista de contactos de un usuario y te la opcion de hablar con alguien eligiendo su nick o agregar un nuevo contacto, en cas de n tener contactos, te envia al menu de agregar contactoos
     * Precondiciones:
     * Postcondiciones:
     */
    private void muestraContactosUsuario() {
        contactosUsuario = Listas.getListadoContactos(usuario.getNombre());
        if (contactosUsuario.size() == 1) {
            menuHablarOAgregar();
        } else {
            //Muestra usuarios y te dice con quien hablar segun el nickname
            for (Usuario user : contactosUsuario) {
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(
                        new TimerTask() {
                            @Override
                            public void run() {
                                ArrayList<Mensaje> mensajes = GestorMensajes.getMensajesDeConversacion(new Contacto(usuario.getNombre(), user.getNombre(), false));
                                if(mensajes.size() > 0){
                                    System.out.println(user.getNombre()+": " + mensajes.size() + " mensajes nuevos");
                                }
                            }
                        }, 1000, 5000);

            }
            menuHablarOAgregar();
        }
    }

    /**
     * Descripcion: Metodo que te muestra las opciones posibles con un contacto existente(Hablar o bloquear) o en su ultimo caso te manda al menu de agregar un nuevo usuario
     * Precondiciones: Ninguna
     * Postcondiciones: Ninguna
     */
    private void menuHablarOAgregar() {
        int opc;
        String nombre;
        Contacto contacto;
        do {
            System.out.println("Introduce un nombre de contacto");
            nombre = sc.nextLine();
            contacto = new Contacto(usuario.getNombre(), nombre, true);
        } while (contactosUsuario.contains(contacto.getMiContacto()));
        System.out.println("¿Que deseas hacer con el contacto " + nombre + "?");
        switch (menuContacto()) {
            case 1 -> getMensajes(contacto);
            case 2 ->{
                    if (!contacto.isBloqueado()){
                        GestorUsuario.bloquearDesbloquearUsuario(usuario.getNombre(), nombre, true);
                    } else {
                        GestorUsuario.bloquearDesbloquearUsuario(usuario.getNombre(), nombre, false);
                    }
            }
            case 3 -> menuAgregarUsuario();
            default -> muestraContactosUsuario();
        }

    }


    private ArrayList<Mensaje> getMensajes(Contacto contacto) {
        ArrayList<Mensaje> mensajes = new ArrayList<>();

    }

    /**
     * Descripcion: Menu que te muestra las opciones posibles al entrar en contactos de un usuario concreto
     * Precondiciones: ninguna
     * Postcondiciones: devuelve una opcion correcta del menu
     *
     * @return opc
     */
    private int menuContacto() {
        int opc;
        System.out.println("""
                [1]. Hablar
                [2]. Bloquear
                [3]. Agregar nuevo contacto
                Elige una opcion""");
        opc = comprobarOpcion(sc.nextLine());
        return opc;
    }

    /**
     * Descripcion: Metodo que te muestra las ociones o pasos necesarios para agregar un nuevo contacto
     * Precondiciones:
     * Postcondiciones:
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
                System.out.println("""
                        [1] Volver a intentarlo
                        [2] Salir
                        Elige una opcion""");
                switch (sc.nextLine()) {
                    case "1" -> salir = false;
                    case "2" -> salir = true;
                }
            }
        }
    }


    /**
     * Descripcion: Menu que muestra por pantalla el mensaje de error al iniciar sesion y da la opcion de crear un usuario o volver a intentarlo
     * Precondiciones: ninguna
     * Postcondiciones: devuelve una opcion posible del menu
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
                Elige una opcion:""");
        opcion = comprobarOpcion(sc.nextLine());
        return opcion;
    }
}
