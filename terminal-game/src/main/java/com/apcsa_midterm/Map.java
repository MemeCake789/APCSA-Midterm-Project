package com.apcsa_midterm;

import java.util.ArrayList;
import java.util.List;

public class Map extends Grid {
    private List<ObjectStorage> objects;

    public Map(int width, int height, String title) {
        super(width, height, title);
        objects = new ArrayList<>();
        
        // Initialize starting objects
        objects.add(new ObjectStorage(3, 1, "ッ", "player"));
        objects.add(new ObjectStorage(10, 1, "Ø ", "enemy"));
    }

    public List<ObjectStorage> getObjects() {
        return objects;
    }
}
