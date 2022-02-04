package Model;

/**This class defines Customer objects for the customersTable. */
public class Customer {
    private int customerID;
    private String customerName, address, postalCode, phone, division, Country;

    /**The constructor for Customer objects. */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone, String division, String country) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
        Country = country;
    }

    /**This method gets the customerID.
     *
     * @return the CustomerID*/
    public int getCustomerID() {
        return customerID;
    }

    /**This method sets the customerID.
     *
     * @param customerID the customerID*/
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**This method gets the customerName.
     *
     * @return the customerName*/
    public String getCustomerName() {
        return customerName;
    }

    /**This method sets the customerName.
     *
     * @param customerName the name of the customer*/
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**This method gets the address.
     *
     * @return the address*/
    public String getAddress() {
        return address;
    }

    /**This method sets the address.
     *
     * @param address the address*/
    public void setAddress(String address) {
        this.address = address;
    }

    /**This method gets the postalCode.
     *
     * @return the postal code*/
    public String getPostalCode() {
        return postalCode;
    }

    /**This method sets the postalCode.
     *
     * @param postalCode the postal code*/
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**This method gets the phoneNumber.
     *
     * @return the phone number*/
    public String getPhone() {
        return phone;
    }

    /**This method sets the phoneNumber.
     *
     * @param phone the phone number*/
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**This method gets the division.
     *
     * @return the division*/
    public String getDivision() {
        return division;
    }

    /**This method sets the division.
     *
     * @param division the division*/
    public void setDivision(String division) {
        this.division = division;
    }

    /**This method gets the country.
     *
     * @return the country*/
    public String getCountry() {
        return Country;
    }

    /**This method sets the country.
     *
     * @param country the country*/
    public void setCountry(String country) {
        Country = country;
    }
}