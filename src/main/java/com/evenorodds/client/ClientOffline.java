package com.evenorodds.client;

import java.util.Random;
import java.util.Scanner;

public class ClientOffline implements Runnable {
    private Random machine;
    private String username;

    private String choice;
    private boolean isEven = false;
    private int difficulty;
    
    private int machineScore = 0;
    private int userScore = 0;

    private Scanner keyboard;

    public ClientOffline(String username) {
        this.username = username;
        this.keyboard = new Scanner(System.in);
        this.machine = new Random();
    }

    @Override
    public void run() {
        System.out.println("\033[H\033[2J");

        boolean playAgain;
        do {
            setChoice();
            setDifficulty();

            playGame();
            playAgain = playAgain();
        } while (playAgain);
    }

    private void setChoice() {
        System.out.println("Do you want even or odd?");

        do {
            System.out.println("Type 'even' or 'odd'");
            choice = keyboard.nextLine();
        } while (!choice.toLowerCase().equals("even") && !choice.toLowerCase().equals("odd"));

        if (choice.equals("even")) {
            isEven = true;
        }
    }

    private void setDifficulty() {
        System.out.println("┌───────── DIFFICULTY ─────────┐");
        System.out.println("│ 1. Easy                      │");
        System.out.println("│ 2. Medium                    │");
        System.out.println("│ 3. Hard                      │");
        System.out.println("│ 4. Impossible                │");
        System.out.println("└──────────────────────────────┘");

        int option;
        do {
            System.out.print("Type: ");
            option = Integer.parseInt(keyboard.nextLine());
        } while (option < 0 || option > 4);

        this.difficulty = option;
    }

    private void playGame() {
        System.out.printf("\n\n── %s ─ VS ─ MACHINE ──\n\n", username);

        int userInput;
        do {
            System.out.println("Choose a number between 0 and 5: ");

            userInput = Integer.parseInt(keyboard.nextLine());
        } while (userInput < 0 || userInput > 5);

        int machineInput = machineMove(userInput);

        int sum = userInput + machineInput;
        boolean isResultEven = sum % 2 == 0;

        String winner = isEven == isResultEven ? username : "MACHINE";
        
        if (isEven == isResultEven) {
            userScore++;
        } else {
            machineScore++;
        }

        System.out.println("\n");
        System.out.printf("%s: %d\n", username, userInput);
        System.out.printf("MACHINE: %d\n\n", machineInput);

        System.out.println("Sum: " + sum);
        System.out.println("Even: " + isResultEven);
        System.out.println("\nWinner: " + winner);

        System.out.println("\nMatch score:");
        System.out.printf("%s: %d\n", username, userScore);
        System.out.printf("MACHINE: %d\n", machineScore);
    }

    private int machineMove(int userInput) {
        int machineInput = 0;
        int remainder;
        boolean userWin;

        switch (difficulty) {
            case 1:
                remainder = (isEven ? 0 : 1);
                while (true) {
                    machineInput = machine.nextInt(6);
                    if ((machineInput + userInput) % 2 == remainder) {
                        break;
                    }
                }
                break;

            case 2:
                machineInput = machine.nextInt(6);
                break;

            case 3:
                userWin = machine.nextInt(11) > 6;
                remainder = (isEven ? (userWin ? 0 : 1) : (userWin ? 1 : 0));

                while (true) {
                    machineInput = machine.nextInt(6);
                    if ((machineInput + userInput) % 2 == remainder) {
                        break;
                    }
                }
                break;
            case 4:
                userWin = machine.nextInt(11) > 8;
                remainder = (isEven ? (userWin ? 0 : 1) : (userWin ? 1 : 0));

                while (true) {
                    machineInput = machine.nextInt(6);
                    if ((machineInput + userInput) % 2 == remainder) {
                        break;
                    }
                }
                break;
        }

        return machineInput;
    }

    public boolean playAgain() {
        boolean playAgain = false;
        String choice;

        do {
            System.out.println("\nPlay again [Y/n]");

            choice = keyboard.nextLine();
        } while(!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N"));

        if (choice.equalsIgnoreCase("Y")) {
            playAgain = true;
        }

        return playAgain;
    }
}
