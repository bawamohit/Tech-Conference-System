package GUI.DataHolders;

/**
 * This class that stores the instance of the collocutor
 */
public final class CollocutorHolder {
    private String username;
    private final static CollocutorHolder INSTANCE = new CollocutorHolder();

    private CollocutorHolder(){}

    /**
     * Gets the CollocutorHolder instance that is initialized when program is run
     *
     * @return CollocutorHolder
     */
    public static CollocutorHolder getInstance(){return INSTANCE;}

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
