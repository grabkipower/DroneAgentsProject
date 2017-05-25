package TaskPackage;

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
public class TaskController {
    public List<Order> orders = new ArrayList<Order>();
    public List<Task> Tasks = new ArrayList<Task>();

    int TaskIndex = 0 ;

    List<Point> RackPoints;
    List<Point> TransitPoints;
    public void AddTasks(int number)
    {
        RackPoints = GameController.getInstance().map.GetMapRepresentation().GetRackPositions();
        TransitPoints = GameController.getInstance().map.GetMapRepresentation().GetTransitPositions();

        for( int i = 0; i < number; i++)
            Tasks.add(CreateTask());
    }
    public int GetUnassignedTaskId()
    {
        for( int i = 0; i < Tasks.size();i++)
        {
            if(Tasks.get(i).IsAssigned == false)
                return i;
        }
        return -1;
    }
    public void SetAsDone(int id)
    {
        for( int i = 0; i < Tasks.size(); i++)
        {
            if( Tasks.get(i).id == id) {
                Tasks.remove(i);
                return;
            }
        }
    }

    public Task GetTaskById(int id)
    {
        for( int i = 0; i < Tasks.size(); i++)
        {
            if( Tasks.get(i).id == id)
                return Tasks.get(i);
        }
        return null;
    }

    private Task CreateTask()
    {
        Random generator = new Random(System.currentTimeMillis());
        int IndexCarry = generator.nextInt(RackPoints.size());
        int IndexName = generator.nextInt(GameConfig.CarryNames.size());
        int IndexTransit = generator.nextInt(TransitPoints.size());
        Carry carry = new Carry(GameConfig.CarryNames.get(IndexName), RackPoints.get(IndexCarry));

        int TrainsitToShelfInt = generator.nextInt(1);
        boolean TransitToShelf = TrainsitToShelfInt == 1;

        Task task = new Task(carry,TransitPoints.get(IndexTransit),TaskIndex,-1, TransitToShelf );
        return task;
    }
}
