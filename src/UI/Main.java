package UI;

import java.util.Scanner;

public class Main {
    public static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        MenuWhatsapp menu = new MenuWhatsapp();
        menu.start();
    }
}