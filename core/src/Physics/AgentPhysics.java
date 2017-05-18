package Physics;

import Interfaces.AgentInterface;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainAgent;

/**
 * Created by Mike on 07.05.2017.
 */
public class AgentPhysics extends PhysicsObject {
    MainAgent main;
    Vector2 SpawnPosition;
    Vector2 DestinationPosition;
    public boolean LestMove = false;

    public void setMain(MainAgent main) {
        this.main = main;
    }


    public void FindRoute() {

    }

    public AgentPhysics(Vector2 position, Vector2 velocity, int width, int height) {
        super(position, velocity, width, height);
    }


    public Vector2 GetPosition() {
        return position;
    }


    public void Update() {
        if (LestMove)
            position.add(new Vector2(1, 1));
        // Collision check
        // Move prediction
        // Check with route
        // Actual position change
    }
}
