package com.mygdx.game;

import Graphics.AgentGraphic;
import JadePackage.JadeController;
import JadePackage.Master;
import JadePackage.RobotAgent;
import Map.MapMain;
import Physics.AgentPhysics;
import Physics.PhysicsEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 07.05.2017.
 */
public class GameController {
    JadeController JadeEngine;
    PhysicsEngine PhysicsEngine;

    public MapMain map;

    public Master MasterRobot;
    public List<MainAgent> Agents = new ArrayList<MainAgent>();

    private static GameController ourInstance = new GameController();

    public static GameController getInstance() {
        return ourInstance;
    }


    private GameController() {
        JadeEngine = JadeController.getInstance();
        CreateAgents();
        map = new MapMain();
    }

    public void CreateMap(TiledMap Tiledmap) {
        map.CreateGrid(Tiledmap);
    }

    private void CreateAgents() {
        // Spawn working Agents
        int index = 0;
        for (Point vec : GameConfig.SpawnPositions) {
            AddAgents(vec, index);
            index++;
        }

        // Spawn Master
        MasterRobot = JadeEngine.CreateMasterAgent();
    }
    public void ReinitializeSpawns()
    {
        SetSpawnPositions(map.GetSpawnPositions());
    }

    private void SetSpawnPositions(List<Point> lst)
    {
        int i =0;
        for(MainAgent agent : Agents)
        {
            agent.Conf.SpawnPosition = lst.get(i);
            agent.PhysicalAgent.SetPoisition(lst.get(i));
            i++;
        }
    }

    public void AddAgents(Point vec, int index) {
        AgentPhysics phycicsAgent = new AgentPhysics(vec, new Point(0, 0), 32, 32);
        RobotAgent JadeAgent = JadeEngine.CreateWorkingAgent(index);
        AgentGraphic GraphicalAgent = new AgentGraphic();
        Configuration conf = new Configuration(vec, index);
        Agents.add(new MainAgent(GraphicalAgent, JadeAgent, phycicsAgent, conf));
    }

    private void UpdateAgents() {
        for (MainAgent agent : Agents)
            agent.Update();
    }


    public void UpdateGame() {
        UpdateAgents();
    }
}
