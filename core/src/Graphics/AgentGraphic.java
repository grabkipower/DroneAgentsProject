package Graphics;

import JadePackage.RobotAgent;
import Map.MapMain;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameController;
import com.mygdx.game.MainAgent;

import java.awt.*;

/**
 * Created by Mike on 09.05.2017.
 */
public class AgentGraphic {
    MainAgent main;
    Sprite sprite;
    Texture texture;

    public void setMain(MainAgent main) {
        this.main = main;
    }

    public void Initialize(Texture texture) {
        sprite = new Sprite(texture);
        this.texture = texture;
        Point Pos = main.PhysicalAgent.GetPosition();
        sprite.setPosition(Pos.x, Pos.y);
    }

    public void Update() {
        Point Pos = main.PhysicalAgent.GetPosition();
        int offset = 0;
        sprite.setPosition((offset +Pos.x)* 32, (offset +Pos.y)*32);
    }
}
