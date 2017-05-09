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
    JadeController Jade;
    PhysicsEngine Physics;

    public Master MasterRobot;
    public List<MainAgent> Robots = new ArrayList<MainAgent>();

    private static GameController ourInstance = new GameController();

    public static GameController getInstance() {
        return ourInstance;
    }

    private GameController() {
        Jade = JadeController.getInstance();
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
        MasterRobot = Jade.CreateMasterAgent();
    }

    public void AddAgents(Vector2 vec, int index)
    {
        AgentPhysics agent = new  AgentPhysics(vec,new Vector2(0,0),32,32);
        RobotAgent rob = Jade.CreateWorkingAgent(agent, index);
        AgentGraphic graphic = new AgentGraphic();
        Robots.add(new MainAgent(graphic,rob));
    }

    private void UpdateAgents()
    {
        for( MainAgent agent : Robots)
            agent.Update();
    }


    public void UpdateGame()
    {
        UpdateAgents();
    }
}
