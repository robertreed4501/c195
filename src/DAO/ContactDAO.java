package DAO;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAO {
    public static ObservableList<Contact> allContacts;

    public static ObservableList<Contact> getAllContacts() throws SQLException {
        allContacts = FXCollections.observableArrayList();
        Query.makeQuery("SELECT * FROM CONTACTS");
        ResultSet rs = Query.getResult();
        while(rs.next()){
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            allContacts.add(new Contact(contactID, contactName, email));
        }
        return allContacts;
    }


}
