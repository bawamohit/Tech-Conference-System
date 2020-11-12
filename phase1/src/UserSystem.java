public abstract class UserSystem {
    private Presenter presenter;
    private UserManager um;
    private EventManager em;
    private MessageManager mm;

    public UserSystem (Presenter p, UserManager uMan, EventManager eMan, MessageManager mMan) {
        presenter = p;
        um = uMan;
        em = eMan;
        mm = mMan;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public UserManager getUm() {
        return um;
    }

    public EventManager getEm() {
        return em;
    }

    public MessageManager getMm() {
        return mm;
    }

    abstract void run(String username);

    public void menu(UserType accountType) {
        if (accountType == UserType.ATTENDEE) {
            presenter.printAttendeeMenu();
        } else if (accountType == UserType.ORGANIZER) {
            presenter.printOrganizerMenu();
        } else {
            presenter.printSpeakerMenu();
        }
    }

    public void message(UserType accountType) {
        if (accountType == UserType.ATTENDEE) {
            presenter.printAttendeeMessageMenu();
        } else if (accountType == UserType.ORGANIZER) {
            presenter.printOrganizerMessageMenu();
        } else {
            presenter.printSpeakerMessageMenu();
        }
    }
}
