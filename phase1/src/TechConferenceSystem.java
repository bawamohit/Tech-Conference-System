import java.util.UUID;

public class TechConferenceSystem {

    protected UserManager um;
    protected EventManager em;
    protected MessageManager mm;

    public TechConferenceSystem () {
        um = new UserManager();
        em = new EventManager();
        //Need to instantiate messagemanager but idk how to put the parameter
    }

    public void loginUser (String username, String password) {

    }

    public void eventSignUp(String username, UUID eventId){
        em.addAttendee(username, eventId);
    }

    public void eventCancelAttendee(String username, UUID eventId){
        em.removeAttendee(username, eventId);
    }

}
