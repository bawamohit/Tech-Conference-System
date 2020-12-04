package GUI;

public final class UserHolder {
    private String username;
    private final static UserHolder INSTANCE = new UserHolder();

    private UserHolder(){}

    public static UserHolder getInstance(){return INSTANCE;}

    public void setUsername(String username){this.username = username;}

    public String getUsername() {
        return username;
    }
}
