package UI;

import DAL.GestorMensajes;
import DAL.GestorUsuario;
import DAL.Listas;
import Entidades.Contacto;
import Entidades.Mensaje;
import Entidades.Usuario;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;


public class MenuWhatsapp {


    private static final Usuario SPAM = new Usuario("SPAM");
    private static final String MENSAJESPAM = "Bienvenido a Whastapp PCJ, el lugar donde tus datos están tan seguros como un niño ucraniano en Rusia. Compra en Eneba";
    private int mensajes = 0;
    private int contactos = 0;
    private static final Timer timer = new Timer();
    private Usuario usuario;

    private ArrayList<Mensaje> mensajesSinLeer = new ArrayList<>();
    private ArrayList<Contacto> contactosUsuario;
    private static Scanner sc = new Scanner(System.in);

    public MenuWhatsapp() {

        usuario = new Usuario();
    }


    /**
     * Descripcion:
     * Precondidiones:
     * Postcondiciones:
     *
     * @return
     */

    private int comprobarMensajes() {


        mensajesSinLeer = GestorMensajes.getMensajesNoLeidosDeUsuario(usuario);
        if (mensajes < mensajesSinLeer.size()) {


            for (int i = 0; i < mensajesSinLeer.size(); i++) {
                mensajes++;
                if (i > 0) {
                    if (!(mensajesSinLeer.get(i).getUsuarioOrigen().equals(mensajesSinLeer.get(i - 1).getUsuarioOrigen()))) {
                        contactos++;
                    }
                } else {
                    contactos++;
                }
            }
        }

        return mensajes;
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
     * @return opc
     */
    private int menu() {
        int opc;
        System.out.println("""
                Bienvenido a Whatsapp PCJ
                [1]. Iniciar sesion
                [2]. Crear usuario
                [0]. Salir
                Elige una opcion:""");
        opc = comprobarOpcion(sc.nextLine());
        return opc;
    }


    /**
     * Descripcion: Metodo que comprueba que la opcion introducido por teclado es realmente un numero.
     * Precondiciones: ninguna
     * Postcondiciones: ninguna
     *
     * @param opcionString
     * @return opcion
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
     */
    private void iniciarSesion() {
        String nombre, contrasenia;
        System.out.println("Inicio de Sesion");
        System.out.println("Introduce tu usuario");
        nombre = sc.nextLine();
        System.out.println("Introduce tu constraseña");
        contrasenia = sc.nextLine();
        var salir = false;
        while (!salir) {
            if (GestorUsuario.comprobarIniciarSesion(nombre, contrasenia)) {
                usuario = new Usuario(nombre);
                muestraContactosUsuario();
                menuSesionIniciada();

            } else {
                switch (menuNoIniciado()) {
                    case 1 -> crearUsuario();
                    case 2 -> iniciarSesion();
                    case 0 -> salir = true;
                    default -> System.out.println("¡Vuelve pronto!");
                }
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
            GestorUsuario.insertContacto(new Contacto(SPAM.getNombre(), nombre, false));
            GestorMensajes.insertMensaje(new Mensaje(SPAM.getNombre(), nombre, MENSAJESPAM, Timestamp.from(Instant.now()), false));
            System.out.println("Creado con exito");

        }
    }


    /**
     * Descripcion: Metodo que muestra y da formato a un mensaje con detalles
     * Precondidiones: ninguna
     * Postcondiciones: ninguna
     *
     * @param mensaje
     */
    private void mostrarMensajeConversacion(Mensaje mensaje) {
        //Compruebo si el mensaje esta leido o no
        if (!mensaje.isLeido() && mensaje.getUsuarioDestino().equals(usuario.getNombre())) {
            mensaje.setLeido(true);
            GestorMensajes.updateMensajeLeido(mensaje);
        }
        //Strings para recoger los datos del mensaje
        String usuarioOrigen, mnsj, hora;
        //Formato de la fecha
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String horaS = simpleDateFormat.format(mensaje.getFecha());
        //Comprobamos quien es el emisor
        if (mensaje.getUsuarioOrigen().equals(usuario.getNombre())) {
            //Si es el usuario lo situaremos en la parte derecha de la pantalla
            usuarioOrigen = String.format("|%30s|", mensaje.getUsuarioOrigen());
            mnsj = String.format("|%30s|", mensaje.getMensaje());
            hora = String.format("|%30s|", horaS);
        } else {
            //Si el emisor es el contacto del usuario se posicionará en la parte izquierda de la pantalla
            usuarioOrigen = String.format("|%-30s|", mensaje.getUsuarioOrigen());
            mnsj = String.format("|%-30s|", mensaje.getMensaje());
            hora = String.format("|%-30s|", horaS);
        }

        System.out.println(hora);
        System.out.println(usuarioOrigen);
        System.out.println("----------------------------------------------");


        System.out.println(mnsj);
        System.out.println("================================================");


    }


    /**
     * Descripcion: Metodo que muestra la conversacion con el contacto introducido por parametros
     * Precondidiones: el contacto debe existir en la lista
     * Postcondiciones: ninguna
     *
     * @param contacto
     */
    private void mostrarCoversacion(Contacto contacto) {
        ArrayList<Mensaje> mensajes = GestorMensajes.getMensajesDeConversacion(contacto);


        System.out.println("================================================");
        System.out.println("Conversación con " + contacto.getMiContacto());
        System.out.println("================================================");
        for (Mensaje mensaje : mensajes) {
            mostrarMensajeConversacion(mensaje);
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                ArrayList<Mensaje> mensajesBackUp = GestorMensajes.getMensajesDeConversacion(contacto);

                for (Mensaje mnsj : mensajesBackUp) {

                    if (!mnsj.isLeido() && mnsj.getUsuarioDestino().equals(usuario.getNombre())) {

                        mostrarMensajeConversacion(mnsj);

                    }
                }
            }
        }, 1000, 5000);
        System.out.println("================================================");
    }


    /**
     * Descripcion: Método que te muestra la lista de contactos de un usuario, dandote la opcion de hablar con alguien eligiendo su nick o agregar un nuevo contacto, en caso de no tener contactos, te envia al menu de agregar contactos
     * Precondiciones: ninguna
     * Postcondiciones: ninguna
     */
    private void muestraContactosUsuario() {
        contactosUsuario = Listas.getListadoContactos(usuario.getNombre());
        System.out.println("========================");
        System.out.println("Tu lista de Contactos");
        System.out.println("========================");
        //Muestra usuarios y te dice con quien hablar segun el nickname
        for (Contacto user : contactosUsuario) {

            String bloq;
            String bloqS = "";
            if (user.isBloqueado()) {
                bloqS = "Bloqueado";
            }
            bloq = String.format("%10s", bloqS);
            System.out.println("-" + user.getMiContacto() + bloq);
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                comprobarMensajes();
                if (mensajes == mensajesSinLeer.size()) {


                    System.out.println("========================");
                    System.out.printf("Tienes %d mensajes de %d conversaciones.%n", mensajes, contactos);

                    mensajes++;

                }


            }
        }, 100, 5000);
        System.out.println("========================");
    }

    /**
     * Descripcion: Metodo que muestra el menu con las opciones despue de haber iniciado sesion correctamente
     * Precondidiones: ninguna
     * Postcondiciones: ninguna
     */

    private void menuSesionIniciada() {
        var salir = false;
        while (!salir) {
            System.out.println("""
                    [1]. Hablar a un contacto
                    [2]. Agregar nuevo contacto
                    [0]. Salir
                    Elige una Opcion""");
            switch (comprobarOpcion(sc.nextLine())) {
                case 1 -> {
                    System.out.println("Escribe el nombre del usuario al que deseas seleccionar de tu lista");
                    var nombreContacto = sc.nextLine();
                    Contacto ct = new Contacto(usuario.getNombre(), nombreContacto, false);
                    if (GestorUsuario.comprobarContactoDeUsuario(ct)) {
                        menuHablarOAgregar(ct);
                    } else {
                        menuAgregarUsuario();
                    }
                }
                case 2 -> {
                    menuAgregarUsuario();
                }
                case 0 -> {
                    salir = true;
                }
            }
        }
    }

    /**
     * Descripcion: Metodo que te muestra las opciones posibles para un contacto existente(Hablar o bloquear) o en su ultimo caso te manda al menu de agregar un nuevo usuario
     * Precondiciones: Ninguna
     * Postcondiciones: Ninguna
     */
    private void menuHablarOAgregar(Contacto contacto) {
        int opc;
        System.out.println("¿Que deseas hacer con el contacto " + contacto.getMiContacto() + "?");
        switch (menuContacto()) {
            case 1 -> menuChat(contacto);
            case 2 -> {
                if (!contacto.isBloqueado()) {
                    GestorUsuario.bloquearDesbloquearUsuario(usuario.getNombre(), contacto.getMiContacto(), true);
                } else {
                    GestorUsuario.bloquearDesbloquearUsuario(usuario.getNombre(), contacto.getMiContacto(), false);
                }
            }
            default -> muestraContactosUsuario();
        }

    }

    /**
     * Descripcion: Metodo que te abre el chat con un contacto seleccionado
     * Precondiciones: ninguna
     * Postcondiciones: ninguna
     *
     * @param contacto
     */
    private void menuChat(Contacto contacto) {
        var salir = false;
        mostrarCoversacion(contacto);
        while (!salir) {
            System.out.println("Escribe tu mensaje para " + contacto.getMiContacto() + " a continuación");
            var mensaje = sc.nextLine();
            var opc = 0;
            do {
                System.out.println("""
                        ¿Desea enviar el mensaje?        
                        [1]. Enviar
                        [2]. Reescribir
                        [0]. Salir
                        Elige una opcion""");
                opc = comprobarOpcion(sc.nextLine());
                switch (opc) {
                    case 1 -> {
                        GestorMensajes.insertMensaje(new Mensaje(contacto.getMiUsuario(), contacto.getMiContacto(), mensaje, Timestamp.from(Instant.now()), false));

                    }
                    case 0 -> salir = true;
                }
            } while (opc < 0 || opc > 2);

            mostrarCoversacion(contacto);
        }


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
                [0]. Salir
                Elige una opcion""");
        opc = comprobarOpcion(sc.nextLine());
        return opc;
    }


    /**
     * Descripcion: Metodo que te muestra las ociones o pasos necesarios para agregar un nuevo contacto
     * Precondiciones: el contacto eleido existe en la lista de usuarios
     * Postcondiciones: nignuna
     */
    private void menuAgregarUsuario() {
        boolean salir = false;
        while (!salir) {
            System.out.println("Introduce el nombre del usuario");
            var nick = sc.nextLine();
            if (GestorUsuario.insertContacto(new Contacto(usuario.getNombre(), nick, false))) {
                GestorUsuario.insertContacto(new Contacto(nick, usuario.getNombre(), false));
                System.out.println("Has agregado a " + nick + " a tu lista de contactos");
                salir = true;
            } else {
                System.out.println("No se encontró " + nick + " en WhatsApp PCJ");
                System.out.println("""
                        [1]. Volver a intentarlo
                        [0]. Salir
                        Elige una opcion""");
                salir = comprobarOpcion(sc.nextLine()) != 1;

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
                No has podido iniciar sesion en WhatsApp PCJ
                ¿Deseas crear un usuario?
                [1]. Si
                [2]. Intentar volver a iniciar sesion
                [0]. Salir
                Elige una opcion:""");
        opcion = comprobarOpcion(sc.nextLine());
        return opcion;
    }
}
