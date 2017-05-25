package Physics;

import Interfaces.PhysicalObjectInterface;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

/**
 * Created by Mike on 07.05.2017.
 */
public class PhysicsObject {
    protected Point position;
    protected Point velocity;
    protected Vector2 PhysicalPosition;
    protected int width;
    protected int height;
    protected com.badlogic.gdx.math.Rectangle rectangle;

    public void SetRectangle()
    {
        rectangle = new com.badlogic.gdx.math.Rectangle(PhysicalPosition.x,PhysicalPosition.y,32.0f,32.0f);
    }
    public com.badlogic.gdx.math.Rectangle GetRectangle()
    {
        return rectangle;
    }

    public PhysicsObject(Point position, Point velocity, int width, int height) {
        this.position = position;
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        PhysicalPosition = new Vector2(position.x * 32, position.y * 32);
        SetRectangle();
    }

    public void SetPhysicalFromPointPosition()
    {
        PhysicalPosition = new Vector2(position.x * 32, position.y * 32);
        SetRectangle();
    }


}
