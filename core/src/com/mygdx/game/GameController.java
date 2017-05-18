package com.mygdx.game;

import Graphics.AgentGraphic;
import JadePackage.JadeController;
import JadePackage.Master;
import JadePackage.RobotAgent;
import Physics.AgentPhysics;
import Physics.PhysicsEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 07.05.2017.
 */
public class GameController {
    JadeController JadeEngine;
    PhysicsEngine PhysicsEngine;

    public Master MasterRobot;
    public List<MainAgent> Agents = new ArrayList<MainAgent>();

    private static GameController ourInstance = new GameController();
    public static GameController getInstance() {
        return ourInstance;
    }



    private GameController() {
        JadeEngine = JadeController.getInstance();
        CreateAgents();
    }

    private void CreateAgents()
    {
        // Spawn working Agents
        int index = 0;
        for( Vector2 vec : GameConfig.SpawnPositions) {
            AddAgents(vec, index);
            index++;
        }

        // Spawn Master
        MasterRobot = JadeEngine.CreateMasterAgent();
    }

    public void AddAgents(Vector2 vec, int index)
    {
        AgentPhysics phycicsAgent = new  AgentPhysics(vec,new Vector2(0,0),32,32);
        RobotAgent JadeAgent = JadeEngine.CreateWorkingAgent(index);
        AgentGraphic GraphicalAgent = new AgentGraphic();
        Configuration conf = new Configuration(vec,index);
        Agents.add(new MainAgent(GraphicalAgent,JadeAgent,phycicsAgent, conf));
    }

    private void UpdateAgents()
    {
        for( MainAgent agent : Agents)
            agent.Update();
    }


    public void UpdateGame()
    {
        UpdateAgents();
    }
}
