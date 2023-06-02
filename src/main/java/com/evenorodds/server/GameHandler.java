package com.evenorodds.server;

import java.util.ArrayList;

public class GameHandler implements Runnable {
    private Player player1;
    private Player player2;

    private ArrayList<Player> players = new ArrayList<>();

    public GameHandler(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        players.add(player1);
        players.add(player2);
    }

    @Override
    public void run() {
        prepGame();

        do {
            play();
        } while (playAgain());

        shutdown();
    }

    public void prepGame() {
        Thread tPlayer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                player1.clearScreen();

                player1.sendMessage("Starting match!");

                player1.setUsername();
                player1.setEven();
            }
        });


        Thread tPlayer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                player2.clearScreen();

                player2.sendMessage("Starting match!");

                player2.setUsername();
                player2.setEven();
            }
        });

        tPlayer1.start();
        tPlayer2.start();

        try {
            tPlayer1.join();
            tPlayer2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean player1Even = player1.getEven();
        boolean player2Even = player2.getEven();

        if (player1Even == player2Even) {
            player2.setEven(!player2Even);
            String message = player2Even ? "\nYour opponent already chose even! You are now odd" : "\nYour opponent already chose odd! You are now even";
            player2.sendMessage(message);
        }

        for (Player p : players) {
            p.sendMessage("\n── " + player1.getUsername() + " ─ VS ─ " + player2.getUsername() + " ──");
        }
    }

    public void play() {
        Thread tPlayer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                player1.setNumber();
            }
        });
        
        Thread tPlayer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                player2.setNumber();
            }
        });

        tPlayer1.start();
        tPlayer2.start();

        try {
            tPlayer1.join();
            tPlayer2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int sum = player1.getNumber() + player2.getNumber();
        boolean isEven = sum % 2 == 0;
        
        String winner = player1.getEven() == isEven ? player1.getUsername() : player2.getUsername();
        if (player1.getEven() == isEven) {
            player1.addScore();
        } else {
            player2.addScore();
        }

        for (Player p : players) {
            p.sendMessage("\n");
            p.sendMessage(player1.getUsername() + ": " + player1.getNumber() + "\n"
                        + player2.getUsername() + ": " + player2.getNumber() + "\n");

            p.sendMessage("Sum: " + sum);
            p.sendMessage("Even: " + isEven);
            p.sendMessage("\nWinner: " + winner);

            p.sendMessage("\nMatch score:\n" +
                          player1.getUsername() + ": " + player1.getScore() + "\n"
                        + player2.getUsername() + ": " + player2.getScore() + "\n");
        }
    }

    public boolean playAgain() {
        Thread tPlayer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                player1.setPlayAgain();
            }
        });
        
        Thread tPlayer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                player2.setPlayAgain();
            }
        });

        tPlayer1.start();
        tPlayer2.start();

        try {
            tPlayer1.join();
            tPlayer2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean playAgain;
        playAgain = player1.getPlayAgain() && player2.getPlayAgain();

        if (!playAgain) {
            if (!player1.getPlayAgain() && !player2.getPlayAgain()) {
                for (Player p : players) {
                    p.sendMessage("Both of you doesn't want to play anymore =(");
                }
            } else {
                Player opponent = !player1.getPlayAgain() ? player2 : player1;
                opponent.sendMessage("Your opponent doesn't want to play anymore =(");
            }
        }
        
        return playAgain;
    }

    public void sendMessage(String message) {
        for(Player p : players) {
            p.sendMessage(message);
        }
    }

    public void shutdown() {
        for(Player p : players) {
            p.sendMessage("Press enter to continue...");
            p.shutdown();
        }
    }
}
