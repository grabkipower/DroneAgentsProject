package Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.GameController;
import com.mygdx.game.MainAgent;
import com.mygdx.game.MyOrthogonalMap;


import java.util.List;

/**
 * Created by Mike on 09.05.2017.
 */
public class GraphicsEngine {
    GameController MainGameController;
    Texture img;
    TiledMap tiledMap;
    public OrthographicCamera camera;
    //TiledMapRenderer tiledMapRenderer;
    MyOrthogonalMap tiledMapRenderer;
    SpriteBatch sb;
    Texture texture;
    List<Sprite> AgentRobotSprites;
    Sprite sprite;

    public TiledMap GetTiledMap() {
        return tiledMap;
    }

    public GraphicsEngine() {
        MainGameController = GameController.getInstance();
    }

    public void create() {
        Gdx.graphics.setWindowedMode(1100, 950);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();

        texture = new Texture(Gdx.files.internal("Robotics.png"));

        sprite = new Sprite(texture);
        tiledMap = new TmxMapLoader().load("map.tmx");
        tiledMapRenderer = new MyOrthogonalMap(tiledMap);
        InitializeObjects();

    }

    private void InitializeObjects() {
        for (MainAgent agent : MainGameController.Agents) {
            agent.InitializeGraphic(texture);
            tiledMapRenderer.addSprite(agent.GraphicalAgent.sprite);
        }
    }
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }




}
