package DAO;

import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {

    public static ObservableList<Countries> getAllCountries() throws SQLException {
        ObservableList<Countries> allCountries = FXCollections.observableArrayList();
        Query.makeQuery("SELECT * FROM countries");
        ResultSet rs = Query.getResult();
        while (rs.next()){
            int countryID = rs.getInt("Country_ID");
            String country = rs.getString("Country");
            allCountries.add(new Countries(countryID, country));
        }
        return allCountries;
    }

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
