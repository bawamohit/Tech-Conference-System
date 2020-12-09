package GUI;

import Gateways.EventGateway;
import Gateways.MessageGateway;
import Gateways.RoomGateway;
import Gateways.UserGateway;
import UseCases.EventManager;
import UseCases.MessageManager;
import UseCases.RoomManager;
import UseCases.UserManager;

import java.io.File;
import java.io.IOException;

public final class ManagersStorage {
    private UserGateway userGateway;
    private EventGateway eventGateway;
    private MessageGateway messageGateway;
    private RoomGateway roomGateway;
    private File userInfo = new File("./src/Data/userManager.json");
    private File eventInfo = new File("./src/Data/eventManager.json");
    private File messageInfo = new File("./src/Data/messageManager.json");
    private File roomInfo = new File("./src/Data/roomManager.json");
    private UserManager userManager;
    private EventManager eventManager;
    private MessageManager messageManager;
    private RoomManager roomManager;

    private final static ManagersStorage INSTANCE = new ManagersStorage();

    private ManagersStorage(){
        userGateway = new UserGateway();
        eventGateway = new EventGateway();
        messageGateway = new MessageGateway();
        roomGateway = new RoomGateway();
        try {
            userManager = userGateway.readFromFile(userInfo.getPath());
            eventManager = eventGateway.readFromFile(eventInfo.getPath());
            messageManager = messageGateway.readFromFile(messageInfo.getPath());
            roomManager = roomGateway.readFromFile(roomInfo.getPath());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static ManagersStorage getInstance(){
        return INSTANCE;
    }

    public UserManager getUserManager(){
        return userManager;
    }

    public EventManager getEventManager(){
        return eventManager;
    }

    public MessageManager getMessageManager(){
        return messageManager;
    }

    public RoomManager getRoomManager(){
        return roomManager;
    }

    public void save(){
        try {
            userGateway.saveToFile(userInfo.getPath(), userManager);
            eventGateway.saveToFile(eventInfo.getPath(), eventManager);
            messageGateway.saveToFile(messageInfo.getPath(), messageManager);
            roomGateway.saveToFile(roomInfo.getPath(), roomManager);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
