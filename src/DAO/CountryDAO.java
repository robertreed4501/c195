package DAO;

import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**This class talks to the database about Countries. */
public class CountryDAO {

    /**This class gets a list of all the country names from the database.
     * Creates query, iterates through result set populating list of countries.
     *
     * @return the ObservableList of country names*/
    public static ObservableList<String> getAllCountryNames() throws SQLException {
        ObservableList<String> allCountryNames = FXCollections.observableArrayList();
        Query.makeQuery("SELECT * FROM countries");
        ResultSet rs = Query.getResult();
        while (rs.next()){
            String country = rs.getString("Country");
            allCountryNames.add(country);
        }
        return allCountryNames;
    }
}
