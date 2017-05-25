package com.mygdx.game;

import Graphics.GraphicsEngine;
import JadePackage.JadeController;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;

import java.util.List;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
    GameController MainGameController;
    GraphicsEngine Graphics;
    static final double DT = 1/1.0;
    static final int MAX_UPDATES_PER_FRAME = 1; //for preventing spiral of death
    private long currentTimeMillis;

    public MyGdxGame()
    {
        Graphics = new GraphicsEngine();
    }

    @Override
    public void create() {
        Graphics.create();
        MainGameController = GameController.getInstance();
        MainGameController.CreateMap(Graphics.GetTiledMap());
        MainGameController.ReinitializeSpawns();

        currentTimeMillis = System.currentTimeMillis();
    }

    @Override
    public void render() {


        long newTimeMillis = System.currentTimeMillis();
        float frameTimeSeconds = (newTimeMillis - currentTimeMillis);
        if( frameTimeSeconds > 1) {
            currentTimeMillis = newTimeMillis;
            MainGameController.UpdateGame();
        }

//        int updateCount = 0;
//        while (frameTimeSeconds > 0.0f && updateCount <= MAX_UPDATES_PER_FRAME) {
//            double deltaTimeSeconds =  DT;
//            frameTimeSeconds -= deltaTimeSeconds;
//            ++updateCount;
//        }



        Graphics.render();
        Gdx.input.setInputProcessor(this);
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT)
          Graphics.camera.translate(-32, 0);
        if (keycode == Input.Keys.RIGHT)
            Graphics.camera.translate(32, 0);
        if (keycode == Input.Keys.DOWN)
            Graphics.camera.translate(0, -32);
        if (keycode == Input.Keys.UP)
            Graphics.camera.translate(0, 32);
//        if (keycode == Input.Keys.NUM_1)
//            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
//        if (keycode == Input.Keys.NUM_2)
//            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
     //   Vector3 position = camera.unproject(clickCoordinates);
    //    sprite.setPosition(position.x, position.y);
        return true;
    }
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        return false;
//    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}