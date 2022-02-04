package Model;

/**This class defines Country objects for use in the customer list and table. */
public class Countries {

    /**The countryID*/
    private int countryID;

    /**The country name*/
    private String country;

    /**The constructor for Countries objects. */
    public Countries(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    /**This method gets the country name.
     *
     * @return country name*/
    public String getCountry() {
        return country;
    }

    /**This method sets the country name.
     *
     * @param country the country name. */
    public void setCountry(String country) {
        this.country = country;
    }
}
