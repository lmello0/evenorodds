package com.evenorodds.client;

import java.util.Scanner;

public class ClientBase {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        boolean end = false;

        while (!end) {
            System.out.println(
                    " ________                                                               ______         __        __            ");
            System.out.println(
                    "/        |                                                             /      \\       /  |      /  |          ");
            System.out.println(
                    "$$$$$$$$/__     __  ______   _______          ______    ______        /$$$$$$  |  ____$$ |  ____$$ |  _______ ");
            System.out.println(
                    "$$ |__  /  \\   /  |/      \\ /       \\        /      \\  /      \\       $$ |  $$ | /    $$ | /    $$ | /       |");
            System.out.println(
                    "$$    | $$  \\ /$$//$$$$$$  |$$$$$$$  |      /$$$$$$  |/$$$$$$  |      $$ |  $$ |/$$$$$$$ |/$$$$$$$ |/$$$$$$$/ ");
            System.out.println(
                    "$$$$$/   $$  /$$/ $$    $$ |$$ |  $$ |      $$ |  $$ |$$ |  $$/       $$ |  $$ |$$ |  $$ |$$ |  $$ |$$      \\ ");
            System.out.println(
                    "$$ |_____ $$ $$/  $$$$$$$$/ $$ |  $$ |      $$ \\__$$ |$$ |            $$ \\__$$ |$$ \\__$$ |$$ \\__$$ | $$$$$$  |");
            System.out.println(
                    "$$       | $$$/   $$       |$$ |  $$ |      $$    $$/ $$ |            $$    $$/ $$    $$ |$$    $$ |/     $$/ ");
            System.out.println(
                    "$$$$$$$$/   $/     $$$$$$$/ $$/   $$/        $$$$$$/  $$/              $$$$$$/   $$$$$$$/  $$$$$$$/ $$$$$$$/  \n\n");

            int option;
            do {
                System.out.println("┌───────── MENU ─────────┐");
                System.out.println("│ 1. Play online         │");
                System.out.println("│ 2. Play offline        │");
                System.out.println("│ 3. Exit                │");
                System.out.println("└────────────────────────┘");

                System.out.print("Type: ");
                option = Integer.parseInt(keyboard.nextLine());
            } while (option < 0 || option > 3);

            switch (option) {
                case 1:
                    String ip;
                    int port;
                    System.out.println("\033[H\033[2J");

                    do {
                        System.out.print("IP: ");
                        ip = keyboard.nextLine();
                    } while (!ip.matches(
                            "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"));

                    do {
                        System.out.print("Port: ");
                        port = Integer.parseInt(keyboard.nextLine());
                    } while (port < 1024 || port > 65535);

                    ClientOnline online = new ClientOnline(ip, port);
                    online.run();
                    break;
                case 2:
                    String username;
                    System.out.println("\033[H\033[2J");

                    do {
                        System.out.print("Username: ");
                        username = keyboard.nextLine();
                    } while (username.isEmpty());

                    ClientOffline offline = new ClientOffline(username);
                    offline.run();

                    break;
                case 3:
                    System.out.println("\nBye!");
                    end = true;
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }

            System.out.println("\033[H\033[2J");
        }

        keyboard.close();
    }

}
