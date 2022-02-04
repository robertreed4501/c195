package DAO;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
/**This class handles talking to the database about Contacts. */
public class ContactDAO {

    /**The list of contacts used by methods in this class. */
    public static ObservableList<Contact> allContacts;

    /**This method gets all contacts from the database and returns a list of Contact objects.
     * Makes a query, iterates through the resultSet and creates contacts in the ObservableList.
     *
     * @return the ObservableList of all contacts from the database*/
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

    /**This method gets a contacts name field when passed in a contactID.
     * Makes a query WHERE contactID = (the contactID passed in), and returns the name associated with it.
     *
     * @param contactID int the contactID being searched
     * @return String the name associated with that contactID*/
    public static String getContactNameByID(int contactID) throws SQLException {
        String name;
        Query.makeQuery("SELECT Contact_Name FROM contacts WHERE Contact_ID=" + contactID);
        ResultSet rs = Query.getResult();
        rs.next();
        name = rs.getString("Contact_Name");
        return name;

    }
}
