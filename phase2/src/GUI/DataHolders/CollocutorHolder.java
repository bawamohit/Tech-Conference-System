package GUI.DataHolders;

public final class CollocutorHolder {
    private String username;
    private final static CollocutorHolder INSTANCE = new CollocutorHolder();

    private CollocutorHolder(){}

    public static CollocutorHolder getInstance(){return INSTANCE;}

    public void setUsername(String username){this.username = username;}

    public String getUsername() {
        return username;
    }
}
