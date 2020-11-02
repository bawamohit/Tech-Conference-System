public class TechConferenceSystem {

    protected UserManager um;
    protected EventManager em;
    protected MessageManager mm;

    public TechConferenceSystem (UserManager um, EventManager em, MessageManager mm) {
        this.um = um;
        this.em = em;
        this.mm = mm;
    }

    public void loginUser (String username, String password) {

    }
}
