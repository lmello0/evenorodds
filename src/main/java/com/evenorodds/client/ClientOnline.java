package com.evenorodds.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientOnline implements Runnable {
    private Socket client;

    private BufferedReader in;
    private PrintWriter out;
    private String serverMessage;

    public ClientOnline(String ip, int port) {
        try {
            client = new Socket(ip, port);
            System.out.println("Connected to server!");
        } catch (IOException e) { }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            OutputHandler outHandler = new OutputHandler();
            Thread t = new Thread(outHandler);
            t.start();

            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String message = userIn.readLine();
                out.println(message);

                try {
                    if (serverMessage.equals(null)) {
                        break;
                    }
                } catch (NullPointerException e) {
                    break;
                }
            }
        } catch (IOException e) { }
    }

    class OutputHandler implements Runnable {
        @Override
        public void run() {
            try {
                while((serverMessage = in.readLine()) != null) {
                    System.out.println(serverMessage);
                }
            } catch (IOException e) { }
        }
    }
}