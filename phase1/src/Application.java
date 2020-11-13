public class Application {
    public static void main(String[] args) {
        String userManagerPath = "phase1/src/userManager.ser";
        String eventManagerPath = "phase1/src/eventManager.ser";
        String messageManagerPath = "phase1/src/messageManager.ser";
        TechConferenceSystem T = new TechConferenceSystem(userManagerPath, eventManagerPath, messageManagerPath);
        T.run();
    }
}
