package DAL;

import Entidades.Mensaje;
import Entidades.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static DAL.GestorUsuario.comprobarIniciarSesion;

public class Main {
    public static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        MenuWhatsapp menu = new MenuWhatsapp();
        menu.start();
    }
}