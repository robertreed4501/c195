package DAO;

import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

/**This class talks to the database about it's first_level_divisions table. */
public class DivisionDAO {

    /**This method gets a list of all divisions from the database.
     * Runs a SELECT statement to get all divisions, iterates through the results to populate
     * a list of Division objects.
     *
     * @return the observableList of Division objects*/
    public static ObservableList<Division> getAllDivisions() throws SQLException {
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();
        Query.makeQuery("SELECT * FROM first_level_divisions");
        ResultSet rs = Query.getResult();
        while (rs.next()){
            int divisionID = rs.getInt("Division_ID");
            int countryID = rs.getInt("Country_ID");
            String division = rs.getString("Division");
            allDivisions.add(new Division(divisionID, countryID, division));
        }
        return allDivisions;
    }

    /**This method returns a list of strings holding all division names.
     * Runs a SELECT statement, iterates through the results and saves each division name to the list of strings.
     *
     * @return the observableList of all division names*/
    public static ObservableList<String> getAllDivisionNames() throws SQLException {
        ObservableList<String> allDivisionNames = FXCollections.observableArrayList();
        Query.makeQuery("SELECT * FROM first_level_divisions");
        ResultSet rs = Query.getResult();
        while (rs.next()){
            String division = rs.getString("Division");
            allDivisionNames.add(division);
        }
        return allDivisionNames;
    }

    /**This method returns the divisions of one particular country.
     * Runs a SELECT statement that queries the database by the country name passed in to return all division names
     * in an observableList of strings.
     *
     * @param country the country name to search by
     * @return the observableList of strings of all divisions in country.*/
    public static ObservableList<String> getDivisionsOfCountry(String country) throws SQLException {
        ObservableList<String> countriesDivisions = FXCollections.observableArrayList();
        Query.makeQuery("SELECT a.division FROM first_level_divisions as a JOIN countries as b ON " +
                "a.Country_ID=b.Country_ID WHERE Country='" + country + "'");
        ResultSet rs = Query.getResult();
        while (rs.next()){
            String division = rs.getString("Division");
            countriesDivisions.add(division);
        }
        return countriesDivisions;
    }
}
