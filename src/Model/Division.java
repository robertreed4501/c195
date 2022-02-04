package Model;

/**This class defines Division objects. */
public class Division {

    /**The divisionID and countryID of the country that the division is in. */
    private int divisionID, countryID;

    /**The name of the division. */
    private String division;

    /**The constructor for Division objects. */
    public Division(int divisionID, int countryID, String division) {
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.division = division;

    }

    /**This method gets the divisionID.
     *
     * @return the divisionID*/
    public int getDivisionID() {
        return divisionID;
    }

    /**This method sets the DivisionID.
     *
     * @param divisionID the divisionID*/
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**This method gets the division name.
     *
     * @return the division*/
    public String getDivision() {
        return division;
    }

    /**This method sets the division name.
     *
     * @param division the division*/
    public void setDivision(String division) {
        this.division = division;
    }
}
