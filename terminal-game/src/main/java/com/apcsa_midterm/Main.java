package com.apcsa_midterm;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final int MIN_WIDTH = 75;
    private static final int MIN_HEIGHT = 24;

    public static int[] getTerminalSize() {
        try {
            Process process = new ProcessBuilder("stty", "size")
                .redirectInput(ProcessBuilder.Redirect.INHERIT)
                .start();
            String output = new String(process.getInputStream().readAllBytes()).trim();
            String[] parts = output.split(" ");
            return new int[] {Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
        } catch (Exception e) {
            return new int[] {24, 80};
        }
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // Ignore
        }
    }

    public static void waitForValidWindowSize() {
        while (true) {
            int[] size = getTerminalSize();
            int height = size[0];
            int width = size[1];

            if (width >= MIN_WIDTH && height >= MIN_HEIGHT) {
                clearScreen();
                System.out.println(Colors.GREEN + Colors.DIM + "Terminal size is good! Starting game..." + Colors.RESET);
                break;
            }

            clearScreen();
            System.out.println(Colors.RED + "Terminal window too small!" + Colors.RESET);
            System.out.println("Current size: " + width + "x" + height);
            System.out.println("Required size: " + MIN_WIDTH + "x" + MIN_HEIGHT);
            System.out.println("\nPlease resize your terminal window and press ENTER to continue...");

            try {
                System.in.read();
            } catch (IOException e) {
                // Continue checking
            }
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        waitForValidWindowSize();

        // Color test display
        for (String bg : Colors.BACKGROUNDS) {
            for (String fg : Colors.FOREGROUNDS) {
                System.out.print(fg + bg + " [X] " + Colors.RESET);
            }
            System.out.println();
            wait(5);
        }

        wait(500);
        clearScreen();

        // Initialize game components
        Map map = new Map(20, 20, "map");
        map.drawMap(map.map);
        
        Player player = new Player(3, 1, Colors.BRIGHT_GREEN + "X " + Colors.RESET, map);
        Enemy enemy = new Enemy(15, 15, Colors.BG_RED + Colors.BLACK + "@"+ Colors.RESET+" " , map, player);
        // player.addItem("Small Potion", "♥", "potion", 20);  
        // player.addItem("Tiny Sword", ">", "weapon", 5);   
        // player.addItem("Rusty Shield", "♦", "shield", 10);
        // player.addItem("Leather Armor", "♠", "armor", 15);
        // player.addItem("Healing Potion", "♥", "potion", 20);
        
        map.draw(map.getObjects());

        // Game loop
        Scanner scanner = new Scanner(System.in);
        while (true) {
            player.handleMovement(scanner.next().charAt(0));
            enemy.moveTowardsPlayer();
            map.draw(map.getObjects());
        }
    }
}
