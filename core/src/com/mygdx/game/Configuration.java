package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Mike on 18.05.2017.
 */
public class Configuration {
    public Vector2 SpawnPosition;
    public int Index;

    public Configuration(Vector2 spawnPosition, int index) {
        SpawnPosition = spawnPosition;
        Index = index;
    }
}
