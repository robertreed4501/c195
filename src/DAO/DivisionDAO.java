package DAO;

import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDAO {

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

    public static ObservableList<String> getDivisionsOfCountry(String country) throws SQLException {
        ObservableList<String> countriesDivisions = FXCollections.observableArrayList();
        Query.makeQuery("SELECT a.division FROM first_level_divisions as a JOIN countries as b ON a.Country_ID=b.Country_ID WHERE Country='" + country + "'");
        ResultSet rs = Query.getResult();
        while (rs.next()){
            String division = rs.getString("Division");
            countriesDivisions.add(division);
        }
        return countriesDivisions;

    }
}
