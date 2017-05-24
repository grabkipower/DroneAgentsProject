package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

import java.awt.*;

/**
 * Created by Mike on 18.05.2017.
 */
public class Configuration {
    public Point SpawnPosition;
    public int Index;

    public Configuration(Point spawnPosition, int index) {
        SpawnPosition = spawnPosition;
        Index = index;
    }
}
