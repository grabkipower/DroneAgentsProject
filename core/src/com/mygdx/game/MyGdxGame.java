package com.mygdx.game;

import Graphics.GraphicsEngine;
import JadePackage.JadeController;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.List;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
    GameController MainGameController;
    GraphicsEngine Graphics;
    static final double DT = 1 / 1.0;
    static final int MAX_UPDATES_PER_FRAME = 1; //for preventing spiral of death
    private long currentTimeMillis;

    public MyGdxGame() {
        Graphics = new GraphicsEngine();
    }

    @Override
    public void create() {
        Graphics.create();
        MainGameController = GameController.getInstance();
        MainGameController.SetTiledMap(Graphics.GetTiledMap());
        MainGameController.CreateMap(Graphics.GetTiledMap());
        MainGameController.ReinitializeSpawns();

        currentTimeMillis = System.currentTimeMillis();
        PrepareSkin();
        stage = new Stage();
        StartButton = CreateButton(StartButton, "Start", 10, 10, 70, 100);
       StopButton = CreateButton(StopButton, "Stop", 110, 10, 70, 100);
    }

    @Override
    public void render() {
        long newTimeMillis = System.currentTimeMillis();
        float frameTimeSeconds = (newTimeMillis - currentTimeMillis);
        if (frameTimeSeconds > 1) {
            currentTimeMillis = newTimeMillis;
            MainGameController.UpdateGame();
        }
        Graphics.render();
        Gdx.input.setInputProcessor(this);

        stage.draw();
    }


    TextButton StartButton;
    TextButton StopButton;
    TextButton RandomFireButton;


    Stage stage;
    Skin skin;
    TextButton.TextButtonStyle textButtonStyle;

    private void PrepareSkin() {
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);
        //Create a texture

        //Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth() , (int) Gdx.graphics.getHeight() , Pixmap.Format.RGB888);
        Pixmap pixmap = new Pixmap(1000,1000,Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

    }

    private TextButton CreateButton(TextButton button, String text, int x, int y, int height, int width) {
        button = new TextButton(text, skin);
        button.setText(text);
        button.setX(x);
        button.setY(y);
        button.setSize(width, height);
      //  button.setOrigin(x,y);

       // button.setCullingArea(new Rectangle(x, y, width, height));
        stage.addActor(button);
        return button;
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
        Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
        Vector3 position = Graphics.camera.unproject(clickCoordinates);
     //   Vector3 position = new Vector3(screenX,screenY,0);




        if (GetCommonRectangle(StartButton).contains(new Vector2(position.x, position.y))) {
            GameController.getInstance().StartTheGame = true;
            System.out.println("Starting the game");
        }
        if (GetCommonRectangle(StopButton).contains(new Vector2(position.x, position.y))) {
            GameController.getInstance().StopTheGame = true;
            System.out.println("Stoping the game");
        }

        //    sprite.setPosition(position.x, position.y);
        return true;
    }

    private Rectangle GetCommonRectangle(TextButton button)
    {
        return new Rectangle(button.getX(),button.getY(),button.getWidth(),button.getHeight());
    }
    private Rectangle GetUnprojectedRectangle(TextButton button)
    {
        Vector3 coords = new Vector3(button.getX(),button.getY(),0);
        Vector3 position = Graphics.camera.unproject(coords);
        return new Rectangle(position.x,position.y,button.getWidth(),button.getHeight());
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