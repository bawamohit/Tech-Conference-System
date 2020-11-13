public class OrganizerSystem extends UserSystem{
    public OrganizerSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan) {
        super(p, uMan, eMan, mMan);
    }
    public void run(String username){
        System.out.println("testing");
    }
}
