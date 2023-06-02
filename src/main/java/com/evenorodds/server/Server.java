package com.evenorodds.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server implements Runnable {
    private final int port;
    private ServerSocket server;
    private int matchCount = 1;

    private ArrayList<Player> connections;

    public Server(int port) {
        this.port = port;
        this.connections = new ArrayList<>();
    }

    public void shutdown() {
        try {
            for (Player p : connections) {
                p.sendMessage("[SERVER] Shutting down the server");
                p.shutdown();
            }

            if (!server.isClosed()) {
                server.close();
            }
        } catch (IOException e) { }
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);

            System.out.println("[SERVER] Server online on port " + port);

            while (!server.isClosed()) {
                System.out.println("[SERVER] Matching ID: " + matchCount);
                System.out.println("[SERVER] Waiting for connection...");
                
                System.out.println("[SERVER] Waiting for 1st player");
                Player player1 = new Player(server.accept());
                connections.add(player1);
                
                player1.sendMessage("Waiting for another player to join...");
                
                System.out.println("[SERVER] Waiting for 2nd player");
                Player player2 = new Player(server.accept());
                connections.add(player2);

                player1.sendMessage("Player 2 joined!");

                GameHandler handler = new GameHandler(player1, player2);

                new Thread(handler).start();

                matchCount++;
            }
        } catch (Exception e) {
            shutdown();
        }
    }

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        int port;
        try {
            System.out.print("Port to run the server: ");
            port = Integer.parseInt(keyboard.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("The value passed can't be parsed... Running on port " + 8080);
            port = 8080;
        }

        Server server = new Server(port);
        server.run();

        keyboard.close();
    }
}
