package com.apcsa_midterm;

import java.io.IOException;
import java.util.Scanner;  // Import the Scanner class


public class Main {
    
  private static final int MIN_WIDTH = 35;
  private static final int MIN_HEIGHT = 24;

  public static int[] getTerminalSize() {
    try {
      Process process = new ProcessBuilder("stty", "size")
        .redirectInput(ProcessBuilder.Redirect.INHERIT)
        .start();
      String output = new String(
        process.getInputStream().readAllBytes()
      ).trim();
      String[] parts = output.split(" ");
      return new int[] {
        Integer.parseInt(parts[0]),
        Integer.parseInt(parts[1]),
      };
    } catch (Exception e) {
      return new int[] { 24, 80 }; // Default fallback values
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
        System.out.println(
          Colors.GREEN +Colors.DIM+ "Terminal size is good! Starting game..." + Colors.RESET
        );
        break;
      }

      clearScreen();
      System.out.println(Colors.RED + "Terminal window too small!" + Colors.RESET);
      System.out.println("Current size: " + width + "x" + height);
      System.out.println("Required size: " + MIN_WIDTH + "x" + MIN_HEIGHT);
      System.out.println(
        "\nPlease resize your terminal window and press ENTER to continue..."
      );

      try {
        System.in.read();
      } catch (IOException e) {
        // Continue checking
      }
    }
  }

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();//
  }

  public static void main(String[] args) {
    waitForValidWindowSize();

    for (String bg : Colors.BACKGROUNDS) {
      for (String fg : Colors.FOREGROUNDS) {
        System.out.print(fg + bg + " [X] " + Colors.RESET);
        
      }
      System.out.println();
      wait(5);
    }

    wait(500);
    clearScreen();

    // Create map instance
    Map map = new Map(20, 20, "termRng");
    
    // Draw using the List of objects
    map.draw(map.getObjects());
    map.drawMap(map.map);
    map.draw(map.getObjects());
    Player player = new Player(3, 1, "☺ ", map);

    map.draw(map.getObjects());

    Scanner scanner = new Scanner(System.in);
    // String key = scanner.nextLine();  // Read user input
    // System.out.println(key);
    // System.out.println(map.objectPositions[1][0]);
    while (true) {
      player.handleMovement(scanner.next().charAt(0));
      map.draw(map.getObjects());
    }


    // System.err.println(map.getObjects());

  }}
