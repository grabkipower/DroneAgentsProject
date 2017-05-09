package com.mygdx.game;

import Graphics.AgentGraphic;
import JadePackage.RobotAgent;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Mike on 09.05.2017.
 */
public class MainAgent {
    public AgentGraphic graphical;
    public RobotAgent jade;

    public MainAgent(AgentGraphic graphical, RobotAgent jade) {
        this.graphical = graphical;
        this.jade = jade;
    }

    public void InitializeGraphic(Texture texture)
    {
        graphical.Initialize(jade,texture);
    }

    public void Update()
    {
        jade.PhysicalAgent.Update();
        graphical.Update(jade);
    }
}
