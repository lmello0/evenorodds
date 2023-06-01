package com.evenorodds.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;

    private boolean isEven = false;
    private String username;
    private int number;
    private boolean playAgain;
    private int score = 0;

    public Player(Socket client) {
        this.client = client;

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setUsername() {
        try {
            out.println("Username: ");
            this.username = in.readLine();
        } catch (IOException e) { }
    }

    public String getUsername() {
        return this.username;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void setEven() {
        String choice;
        try {
            do {
                out.println("Do you want even or odd?\nType 'even' or 'odd'");

                choice = in.readLine();
            } while(!choice.toLowerCase().equals("even") && !choice.toLowerCase().equals("odd"));

            if (choice.equals("even")) {
                isEven = true;
            }
        } catch (IOException e) { }
    }

    public void setEven(boolean even) {
        this.isEven = even;
    }

    public boolean getEven() {
        return this.isEven;
    }

    public void clearScreen() {
        sendMessage("\033[H\033[2J");
    }

    public void setNumber() {
        try {
            do {
                out.println("Choose a number between 0 and 5:");
            
                this.number = Integer.parseInt(in.readLine());
            } while(this.number < 0 || this.number > 5 );
        } catch (IOException e) { }
    }

    public int getNumber() {
        return this.number;
    }

    public void setPlayAgain() {
        String choice = "";
        try {
            do {
                out.println("Play again [Y/n] ");

                choice = in.readLine();
            } while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N"));
            
            if (choice.equalsIgnoreCase("Y")) {
                playAgain = true;                
            } else {
                playAgain = false;
            }
        } catch (IOException e) { }
    }

    public boolean getPlayAgain() {
        return this.playAgain;
    }

    public void addScore() {
        this.score++;
    }

    public int getScore() {
        return this.score;
    }

    public boolean isClosed() {
        return client.isClosed();
    }

    public void shutdown() {
        try {
            client.close();
            return;
        } catch (IOException e) { }
    }
}
