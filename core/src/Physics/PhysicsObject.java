package Physics;

import Interfaces.PhysicalObjectInterface;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

/**
 * Created by Mike on 07.05.2017.
 */
public class PhysicsObject  {
    protected Point position;
    protected Point velocity;
    protected int width;
    protected int height;

    public PhysicsObject(Point position, Point velocity, int width, int height) {
        this.position = position;
        this.velocity = velocity;
        this.width = width;
        this.height = height;
    }


}
