package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**This class manages the connection to the database. */
public class DBConnection {

    /**The name of the database. */
    private static final String dbName ="client_schedule";

    /**The database url. */
    private static final String DB_URL="jdbc:mysql://localhost:3306/"+ dbName;

    /**The username to log into the database with. */
    private static final String username="sqlUser";

    /**The password for the above username. */
    private static final String password="Passw0rd!";

    /**The connection object from java.sql.  */
    static Connection cxn;

    /**This method creates a connection to the database.
     * Using the static final strings above, is uses java.sql.DriverManager to get a connection and set it
     * to a Connection object.*/
    public static void makeConnection() throws SQLException{
        cxn = DriverManager.getConnection(DB_URL,username,password);
    }

    /**This method closes the connection made above.
     * Calls the close method on our Connection object.*/
    public static void closeConnection() throws SQLException{
        cxn.close();
    }

}
