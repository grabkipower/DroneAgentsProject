package JadePackage;

import Physics.AgentPhysics;
import com.badlogic.gdx.math.Vector2;
import jade.core.*;
import jade.core.Runtime;
import jade.core.messaging.TopicManagementHelper;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

/**
 * Created by Mike on 07.05.2017.
 */
public class JadeController {
    ContainerController myContainer;
    TopicManagementHelper topicHelper;


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
        myProfile.setParameter("services" ,"jade.core.messaging.TopicManagementService;jade.core.event.NotificationService;jade.core.mobility.AgentMobilityService");
// create the main container
        myContainer = myRuntime.createMainContainer(myProfile);

    }

    public RobotAgent CreateWorkingAgent(int index) {
        try {
            RobotAgent robot = new RobotAgent();
            AgentController cont = myContainer.acceptNewAgent("Agent_" + index, robot);
            cont.start();
//            rma.start();
            return robot;
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Master CreateMasterAgent() {
        try {
            Master master = new Master();
            AgentController cont = myContainer.acceptNewAgent("Master", master);
            cont.start();
//            rma.start();
            return master;
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
