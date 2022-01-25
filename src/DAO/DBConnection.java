package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String dbName ="client_schedule";
    private static final String DB_URL="jdbc:mysql://localhost:3306/"+ dbName;
    private static final String username="sqlUser";
    private static final String password="Passw0rd!";
    static Connection cxn;

    public static void makeConnection() throws SQLException{
        cxn = DriverManager.getConnection(DB_URL,username,password);
    }
    public static void closeConnection() throws SQLException{
        cxn.close();
    }

}
