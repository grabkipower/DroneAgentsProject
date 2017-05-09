package Graphics;

import JadePackage.RobotAgent;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameController;

/**
 * Created by Mike on 09.05.2017.
 */
public class AgentGraphic {
    Sprite sprite;
    Texture texture;

    public AgentGraphic()
    {

    }
    public void  Initialize(RobotAgent robot, Texture texture) {
        sprite = new Sprite(texture);
        this.texture = texture;
        Vector2 Pos = robot.GetAgentPosition();
        sprite.setPosition(Pos.x, Pos.y);
    }

    public void Update(RobotAgent robot)
    {
        Vector2 Pos = robot.GetAgentPosition();
        sprite.setPosition(Pos.x,Pos.y);
    }
}
