package GUI.DataHolders;

/**
 * This class that stores the instance of the user
 */
public final class UserHolder {
    private String username;
    private final static UserHolder INSTANCE = new UserHolder();

    private UserHolder(){}

    /**
     * Gets the UserHolder instance that is initialized when program is run
     *
     * @return UserHolder
     */
    public static UserHolder getInstance(){return INSTANCE;}

    /** Setter for username of User
     *
     * @param username username of user
     */
    public void setUsername(String username){this.username = username;}

    /** Returns the login username of the User
     *
     * @return username of User
     */
    public String getUsername() {
        return username;
    }
}
