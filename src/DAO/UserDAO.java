package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**This class talks to the database about Users.*/
public class UserDAO {

    /**An observableList to hold all users returned from the database.*/
    private static ObservableList<User> allUsers;

    /**This method gets all users from the database and returns a list of them.
     * Runst SELECT statement, iterates through resultSet and adds a new User object to the list each time.
     *
     * @return the observableList of all Users*/
    public static ObservableList<User> getAllUsers() throws SQLException {
        allUsers = FXCollections.observableArrayList();
        Query.makeQuery("SELECT User_ID, User_Name, Password FROM USERS");
        ResultSet result=Query.getResult();

        while(result.next()) {
            int userID = result.getInt("User_ID");
            String userName = result.getString("User_Name");
            String password = result.getString("Password");

            User userResult = new User(userID, userName, password);
            allUsers.add(userResult);
        }
        return allUsers;
    }
}
