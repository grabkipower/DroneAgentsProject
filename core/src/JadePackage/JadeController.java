package JadePackage;

import Physics.AgentPhysics;
import com.badlogic.gdx.math.Vector2;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

/**
 * Created by Mike on 07.05.2017.
 */
public class JadeController {
    ContainerController myContainer;
    private static JadeController ourInstance = new JadeController();

    public static JadeController getInstance() {
        return ourInstance;
    }


    public JadeController() {
        Runtime myRuntime = Runtime.instance();

// prepare the settings for the platform that we're going to start
        Profile myProfile = new ProfileImpl("localhost", 1099, Profile.PLATFORM_ID);
        myProfile.setParameter(Profile.PLATFORM_ID, "MyMainPlatform");
        myProfile.setParameter("gui", "true");
// create the main container
        myContainer = myRuntime.createMainContainer(myProfile);
    }

    public RobotAgent CreateWorkingAgent(AgentPhysics physics, int index) {
        try {
            RobotAgent robot = new RobotAgent();
            robot.SetPhysics(physics);
            AgentController cont = myContainer.acceptNewAgent("Agent_" + index, robot);
            cont.start();
//            rma = myContainer.createNewAgent("agencik", "JadePackage.RobotAgent", null);
//            rma.start();
            return robot;
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Master CreateMasterAgent()
    {
        try {
            Master master = new Master();
            AgentController cont = myContainer.acceptNewAgent("Master" , master);
            cont.start();
//            rma = myContainer.createNewAgent("agencik", "JadePackage.RobotAgent", null);
//            rma.start();
            return master;
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
