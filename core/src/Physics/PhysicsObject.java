package Physics;

import Interfaces.PhysicalObjectInterface;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Mike on 07.05.2017.
 */
public class PhysicsObject  {
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;
    protected int height;

    public PhysicsObject(Vector2 position, Vector2 velocity, int width, int height) {
        this.position = position;
        this.velocity = velocity;
        this.width = width;
        this.height = height;
    }


}
