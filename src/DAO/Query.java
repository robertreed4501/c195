package DAO;

import static DAO.DBConnection.cxn;
import java.sql.Statement;
import java.sql.ResultSet;

/**This class handles the creation and execution of SQL queries. */
public class Query {
    /**The query to be executed. */
    private static String query;
    /**The Statement object created from Connection.createStatement() and later gets executed with above query. */
    private static Statement stmt;
    /**The ResultSet object that holds the results of the query. */
    private static ResultSet result;

    /**This method creates and executes an SQL query.
     * Creates a statement from the Connection, executes a query it is passed in, and sets ResultSet result.
     *
     * @param q the query to be executed.*/
    public static void makeQuery(String q){
        query =q;
        try{
            stmt=cxn.createStatement();
            // determine if query or update statement
            if(query.toLowerCase().startsWith("select"))
                result=stmt.executeQuery(q);
            if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update"))
                stmt.executeUpdate(q);

        }
        catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    /**This statement returns ResultSet result.
     *
     *@return the ResultSet object from the last query executed. */
    public static ResultSet getResult(){
        return result;
    }
}
