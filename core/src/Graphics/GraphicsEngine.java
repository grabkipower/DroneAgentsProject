package Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
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
    OrthographicCamera camera;
    //TiledMapRenderer tiledMapRenderer;
    MyOrthogonalMap tiledMapRenderer;
    SpriteBatch sb;
    Texture texture;
    List<Sprite> AgentRobotSprites;
    Sprite sprite;

    public GraphicsEngine( )
    {
        MainGameController = GameController.getInstance();
    }
    public void create()
    {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();

        texture = new Texture(Gdx.files.internal("image.png"));
        sprite = new Sprite(texture);
        tiledMap = new TmxMapLoader().load("desert.tmx");
        tiledMapRenderer = new MyOrthogonalMap(tiledMap);
        InitializeObjects();
    }

    private void InitializeObjects()
    {
        for( MainAgent agent: MainGameController.Robots) {
            agent.InitializeGraphic(texture);
            tiledMapRenderer.addSprite(agent.graphical.sprite);
        }

    }


    public void render()
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

}