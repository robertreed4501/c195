package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String dbName ="client_schedule";
    private static final String DB_URL="jdbc:mysql://localhost:3306/"+ dbName;
    private static final String username="rob";
    private static final String password="mobius42";
    static Connection cxn;
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception{
        cxn =(Connection) DriverManager.getConnection(DB_URL,username,password);
    }
    public static void closeConnection() throws ClassNotFoundException,SQLException, Exception{
        cxn.close();
    }

}
