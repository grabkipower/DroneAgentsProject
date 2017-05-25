package com.mygdx.game;

import Graphics.AgentGraphic;
import Graphics.GraphicsEngine;
import JadePackage.RobotAgent;
import Physics.AgentPhysics;
import TaskPackage.Task;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Mike on 09.05.2017.
 */
public class MainAgent {
    public AgentGraphic GraphicalAgent;
    public RobotAgent JadeAgent;
    public AgentPhysics PhysicalAgent;
    public Configuration Conf;
    public Task CurrentTask;

    public MainAgent(AgentGraphic graphicalAgent, RobotAgent jadeAgent, AgentPhysics physicalAgent, Configuration conf) {
        GraphicalAgent = graphicalAgent;
        JadeAgent = jadeAgent;
        PhysicalAgent = physicalAgent;
        Conf = conf;

        GraphicalAgent.setMain(this);
        JadeAgent.setMain(this);
        PhysicalAgent.setMain(this);
    }

    public void InitializeGraphic(Texture texture) {
        GraphicalAgent.Initialize(texture);
    }

    public void Update() {
        PhysicalAgent.Update();
        GraphicalAgent.Update();
    }
}
