package DAO;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class AppointmentDAO {

    public static ObservableList<Appointment> allAppointments;
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        allAppointments = FXCollections.observableArrayList();
        Query.makeQuery("SELECT * FROM APPOINTMENTS");
        ResultSet resultSet = Query.getResult();
        while(resultSet.next()){
            int appointmentID = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            LocalDateTime start = LocalDateTime.parse(resultSet.getString("Start"), dtf);
            LocalDateTime end = LocalDateTime.parse(resultSet.getString("End"), dtf);
            LocalDateTime createDate = LocalDateTime.parse(resultSet.getString("Create_Date"), dtf);
            String createdBy = resultSet.getString("Created_By");
            LocalDateTime lastUpdated = LocalDateTime.parse(resultSet.getString("Last_Update"), dtf);
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");
            allAppointments.add(new Appointment(appointmentID, customerID, userID, contactID, title, description, location, type, createdBy, lastUpdatedBy, start, end, createDate, lastUpdated));
        }

        return allAppointments;
    }

    public static ObservableList<Appointment> getWeeklyAppointments() throws SQLException {
        allAppointments = FXCollections.observableArrayList();
        Query.makeQuery("select * from appointments where Start between now() and adddate(now(), 7)");
        ResultSet resultSet = Query.getResult();
        while(resultSet.next()){
            int appointmentID = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            LocalDateTime start = LocalDateTime.parse(resultSet.getString("Start"), dtf);
            LocalDateTime end = LocalDateTime.parse(resultSet.getString("End"), dtf);
            LocalDateTime createDate = LocalDateTime.parse(resultSet.getString("Create_Date"), dtf);
            String createdBy = resultSet.getString("Created_By");
            LocalDateTime lastUpdated = LocalDateTime.parse(resultSet.getString("Last_Update"), dtf);
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");
            allAppointments.add(new Appointment(appointmentID, customerID, userID, contactID, title, description, location, type, createdBy, lastUpdatedBy, start, end, createDate, lastUpdated));
        }

        return allAppointments;
    }

    public static ObservableList<Appointment> getMonthlyAppointments() throws SQLException {
        allAppointments = FXCollections.observableArrayList();
        Query.makeQuery("select * from appointments where Start between now() and adddate(now(),interval 1 month)");
        ResultSet resultSet = Query.getResult();
        while(resultSet.next()){
            int appointmentID = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String type = resultSet.getString("Type");
            LocalDateTime start = LocalDateTime.parse(resultSet.getString("Start"), dtf);
            LocalDateTime end = LocalDateTime.parse(resultSet.getString("End"), dtf);
            LocalDateTime createDate = LocalDateTime.parse(resultSet.getString("Create_Date"), dtf);
            String createdBy = resultSet.getString("Created_By");
            LocalDateTime lastUpdated = LocalDateTime.parse(resultSet.getString("Last_Update"), dtf);
            String lastUpdatedBy = resultSet.getString("Last_Updated_By");
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");
            allAppointments.add(new Appointment(appointmentID, customerID, userID, contactID, title, description, location, type, createdBy, lastUpdatedBy, start, end, createDate, lastUpdated));
        }

        return allAppointments;
    }
}
