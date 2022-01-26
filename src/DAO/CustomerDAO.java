package DAO;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        Query.makeQuery("SELECT a.Customer_ID, a.Customer_Name, a.Address, a.Postal_Code, a.Phone, b.Division, c.Country FROM customers AS a JOIN first_level_divisions as b ON a.Division_ID=b.Division_ID JOIN countries as c ON b.Country_ID=c.Country_ID");
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

    public static void updateCustomer(String customerName, String address, String postalCode, String phone, String userID, int divisionID, String customerID){
        Query.makeQuery("UPDATE customers SET Customer_Name='" + customerName + "',Address='" + address + "',Postal_Code='" + postalCode + "',Phone='" + phone + "', Last_Update=NOW(), Last_Updated_By='" + userID + "', Division_ID=" + Integer.toString(divisionID) + " WHERE Customer_ID=" + customerID);

    }

    public static void createNewCustomer(int customerID, String customerName, String address, String postalCode, String phone, String userID, int divisionID){
        Query.makeQuery("INSERT INTO customers VALUES(" + Integer.toString(customerID) + ",'" + customerName + "','" + address + "','" + postalCode + "','" + phone + "',NOW(),'" + userID + "',NOW(),'" + userID + "'," + Integer.toString(divisionID) + ")");

    }

    public static void deleteCustomer(int customerID){
        Query.makeQuery("DELETE FROM customers WHERE Customer_ID=" + Integer.toString(customerID));
    }

    public static Customer getCustomerByID(int customerID) throws SQLException {
        Query.makeQuery("SELECT a.Customer_ID, a.Customer_Name, a.Address, a.Postal_Code, a.Phone, b.Division, c.Country FROM customers AS a JOIN first_level_divisions as b ON a.Division_ID=b.Division_ID JOIN countries as c ON b.Country_ID=c.Country_ID WHERE a.Customer_ID=" + Integer.toString(customerID));
        ResultSet rs = Query.getResult();
        int custID;
        String customerName;
        String address;
        String postalCode;
        String phone;
        String division;
        String country;
        Customer customer;
        rs.next();
            custID = rs.getInt("Customer_ID");
            customerName = rs.getString("Customer_Name");
            address = rs.getString("Address");
            postalCode = rs.getString("Postal_Code");
            phone = rs.getString("Phone");
            division = rs.getString("Division");
            country = rs.getString("Country");

        return new Customer(custID, customerName, address, postalCode, phone, division, country);
    }
}
