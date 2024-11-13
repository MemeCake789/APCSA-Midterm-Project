package com.apcsa_midterm;

public class Map extends Grid {

    public Object[][] objectPositions = {
        {10, 10, "#"}, // Player
        {20, 20, "@"}, // Enemy
    };
    public Map(int width, int height, String title) {
        super(width, height, title);

        // for (Object[] object : objectPositions) {
        //     int x = (int) object[0];
        //     int y = (int) object[1];
        //     String symbol = (String) object[2];
        // }
    }


}
