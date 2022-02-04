package Model;

/**This class defines Contact objects for use in the appointments tab. */
public class Contact {

    /**The contactID number of the contact. */
    private int contactID;

    /**The contactName and email address. */
    private String contactName, email;

    /**This method is the overridden toString() method to display contact names in the contactComboBox.
     *
     * @return the contactName string. */
    @Override
    public String toString() {
        return contactName;
    }

    /**The constructor for Contact objects.
     *
     * @param contactID the contactID
     * @param contactName the contactName
     * @param email the contact's email address*/
    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**This method gets the contactID.
     *
     * @return the contactID*/
    public int getContactID() {
        return contactID;
    }

    /**This method sets the contactID.
     *
     * @param contactID the contactID.*/
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**This method gets the contact name.
     *
     * @return the contact name.*/
    public String getContactName() {
        return contactName;
    }

    /**This method gets the email address.
     *
     * @return the email address. */
    public String getEmail() {
        return email;
    }

    /**This method sets the email address.
     *
     * @param email the email address. */
    public void setEmail(String email) {
        this.email = email;
    }
}
