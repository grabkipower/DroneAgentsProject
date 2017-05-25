package TaskPackage;

import Map.MapMain;
import com.badlogic.gdx.Game;
import com.mygdx.game.GameConfig;
import com.mygdx.game.GameController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mike on 25.05.2017.
 */
public class Order {
    List<Carry> carries;
    Point TransitPos;
    boolean TrainsitToShelf;
    boolean IsDone = false;
    int TasksDone = 0;
    int id;

    public Order(Point transitPos, boolean trainsitToShelf, int CarriesNum, int _id) {
        TransitPos = transitPos;
        TrainsitToShelf = trainsitToShelf;
        id =_id;
        carries = new ArrayList<Carry>();
        CreateCarries(CarriesNum);

    }

    private void CreateCarries(int num)
    {
        List<Point> RackPoints = GameController.getInstance().map.GetMapRepresentation().GetRackPositions();
        Random generator = new Random(System.currentTimeMillis());

        for( int i = 0; i < num ;i++)
        {
            int IndexCarry = generator.nextInt(RackPoints.size());
            int IndexName = generator.nextInt(GameConfig.CarryNames.size());

            Carry carry = new Carry(GameConfig.CarryNames.get(IndexName), RackPoints.get(IndexCarry));
            carries.add(carry);
        }
    }

    public List<Task> CreateTasks()
    {
        List<Task> tasks = new ArrayList<Task>();
        for( int i = 0; i < carries.size(); i++) {
            Task task = new Task(carries.get(i),TransitPos,i,id, TrainsitToShelf);
            tasks.add(task);
        }
        return tasks;
    }


}


