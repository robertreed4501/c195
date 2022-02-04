package DAO;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**This class talks to the database about customers.*/
public class CustomerDAO {

    /**This method gets all the customers from the database.
     * Creates a query, iterates through result, populating an observableList of customer objects.
     *
     * @return the observableList of all customers.*/
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        Query.makeQuery("SELECT a.Customer_ID, a.Customer_Name, a.Address, a.Postal_Code, a.Phone, b.Division, " +
                "c.Country FROM customers AS a JOIN first_level_divisions as b ON a.Division_ID=b.Division_ID JOIN " +
                "countries as c ON b.Country_ID=c.Country_ID");
        ResultSet rs = Query.getResult();
        while(rs.next()){
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            String division = rs.getString("Division");
            String country = rs.getString("Country");
            allCustomers.add(new Customer(customerID, customerName, address, postalCode, phone, division, country));
        }
        return allCustomers;
    }

    /**This method updates customer information in the database.
     * Runs a query that sets customer information to the new (or same) data that is being passed in.
     *
     * @param userID userID from the gui form
     * @param customerID customerID from the customerIDLabel element
     * @param address address from the gui forms
     * @param customerName name of the customer from the gui forms
     * @param divisionID division ID of the division name selected
     * @param phone phone number from the gui forms
     * @param postalCode postal code from the gui forms*/
    public static void updateCustomer(String customerName, String address, String postalCode, String phone,
                                      String userID, int divisionID, String customerID){
        Query.makeQuery("UPDATE customers SET Customer_Name='" + customerName + "',Address='" + address +
                "',Postal_Code='" + postalCode + "',Phone='" + phone + "', Last_Update=NOW(), Last_Updated_By='"
                + userID + "', Division_ID=" + Integer.toString(divisionID) + " WHERE Customer_ID=" + customerID);
    }

    /**This method creates a new customer record in the database.
     * Runs an INSERT statement using data from the gui.
     *
     * @param userID userID from the gui form
     * @param customerID customerID from the auto-gen function getNextCustomerID()
     * @param address address from the gui forms
     * @param customerName name of the customer from the gui forms
     * @param divisionID division ID of the division name selected
     * @param phone phone number from the gui forms
     * @param postalCode postal code from the gui forms*/
    public static void createNewCustomer(int customerID, String customerName, String address, String postalCode,
                                         String phone, String userID, int divisionID){
        Query.makeQuery("INSERT INTO customers VALUES(" + Integer.toString(customerID) + ",'" + customerName +
                "','" + address + "','" + postalCode + "','" + phone + "',NOW(),'" + userID + "',NOW(),'" + userID +
                "'," + Integer.toString(divisionID) + ")");
    }

    /**Deletes a customer record from the database by customerID.
     * runs DELETE statement WHERE customerID= the customer ID passed in.
     *
     * @param customerID the customerID to delete*/
    public static void deleteCustomer(int customerID){
        Query.makeQuery("DELETE FROM customers WHERE Customer_ID=" + Integer.toString(customerID));
    }

    /**This method gets a customer record by customerID.
     * Runs a query, iterates through result set and creates a Customer object.
     *
     * @param customerID the customerID being searched for
     * @return the Customer object with that customerID*/
    public static Customer getCustomerByID(int customerID) throws SQLException {
        Query.makeQuery("SELECT a.Customer_ID, a.Customer_Name, a.Address, a.Postal_Code, a.Phone, b.Division, " +
                "c.Country FROM customers AS a JOIN first_level_divisions as b ON a.Division_ID=b.Division_ID " +
                "JOIN countries as c ON b.Country_ID=c.Country_ID WHERE a.Customer_ID=" + Integer.toString(customerID));
        ResultSet rs = Query.getResult();
        rs.next();
        int custID = rs.getInt("Customer_ID");
        String customerName = rs.getString("Customer_Name");
        String address = rs.getString("Address");
        String postalCode = rs.getString("Postal_Code");
        String phone = rs.getString("Phone");
        String division = rs.getString("Division");
        String country = rs.getString("Country");

        return new Customer(custID, customerName, address, postalCode, phone, division, country);
    }
}
