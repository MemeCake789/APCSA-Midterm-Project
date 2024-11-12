package com.apcsa_midterm;

public abstract class Grid {
    protected int width;
    protected int height;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;

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
    
    public void createGrid() {
         // 100 by 100 grid
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          if (isOnBorder(x, y)) {
            System.out.print( Colors.WHITE + Colors.BOLD + "██" + Colors.RESET);  
          } else {
              System.out.print(Colors.DIM + " ·" + Colors.RESET);
          }
        }
        wait(5);

        System.out.println();
      }
      
       
    }
}
