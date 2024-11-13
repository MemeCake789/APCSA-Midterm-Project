package com.apcsa_midterm;

public abstract class Grid {
    protected int width;
    protected int height;
    protected String title;

    public Grid(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;

    }
    public static void wait(int ms) {
        try {
          Thread.sleep(ms);
        } catch (InterruptedException e) {
          // Ignore
        }
      }

    public boolean isOnBorder(int x, int y) {
        return x == 0 || y == 0 || x == width - 1 || y == height - 1;
    }
    
public void printBorder(int x, int y) {
    if (y == 0 && x >= 3 && x < 6 + title.length()) {
        if (x == 3 || x == 5 + title.length()) {
            System.out.print(Colors.BOLD + Colors.WHITE + "    " + Colors.RESET);
        } else if (x == 4) {
            System.out.print(Colors.BOLD + Colors.WHITE + ":" + Colors.RESET);
        } else {
            System.out.print(Colors.BOLD + Colors.WHITE + title.charAt(x - 5) + Colors.RESET);
        }
        return;
    }

    String borderChar;
    if (x == 0 && y == 0) borderChar = "┌";
    else if (x == 0 && y == height - 1) borderChar = "└";
    else if (x == width - 1 && y == 0) borderChar = "┐";
    else if (x == width - 1 && y == height - 1) borderChar = "┘";
    else if (x == 0 || x == width - 1) borderChar = "│";
    else if (y == 0 || y == height - 1) borderChar = "──";
    else borderChar = " ";
    
    System.out.print(Colors.BOLD + Colors.WHITE + borderChar + Colors.RESET);
}



    public void createGrid() {
         // 100 by 100 grid
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          if (isOnBorder(x, y)) {
            printBorder(x, y);
          } else {
              System.out.print(Colors.DIM + " ·" + Colors.RESET);
          }
        }
        wait(2);

        System.out.println();
      }
      
       
    }
}
