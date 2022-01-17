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
}
