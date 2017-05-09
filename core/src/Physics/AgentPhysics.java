package Physics;

import Interfaces.AgentInterface;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Mike on 07.05.2017.
 */
public class AgentPhysics extends PhysicsObject implements AgentInterface {
    Vector2 SpawnPosition;
    Vector2 DestinationPosition;
    public boolean LestMove = false;

    public void FindRoute() {

    }

    public AgentPhysics(Vector2 position, Vector2 velocity, int width, int height) {
        super(position, velocity, width, height);
    }

    @Override
    public Vector2 GetPosition() {
        return position;
    }

    @Override
    public void Update() {
        if (LestMove)
            position.add(new Vector2(1, 1));
        // Collision check
        // Move prediction
        // Check with route
        // Actual position change
    }
}
