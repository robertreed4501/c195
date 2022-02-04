package Model;

/**This class defines User objects to store the users database table in observable lists. */
public class User {

    /**The userID of the user.*/
    private int userID;

    /**The username and password of the user.*/
    private String userName, password;

    /**The constructor creates User objects from userID, userName, and password. */
    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**This method gets the userID.
     *
     * @return the userID*/
    public int getUserID() {
        return userID;
    }

    /**This method sets the userID.
     *
     * @param userID the userID*/
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**This method gets the userName.
     *
     * @return the userName*/
    public String getUserName() {
        return userName;
    }

    /**This method sets the userName.
     *
     * @param userName the userName*/
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**This method gets the password.
     *
     * @return the password*/
    public String getPassword() {
        return password;
    }

    /**This method sets the password.
     *
     * @param password the new password*/
    public void setPassword(String password) {
        this.password = password;
    }
}
