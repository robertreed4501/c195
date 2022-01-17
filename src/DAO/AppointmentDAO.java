package DAO;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentDAO {

    public static ObservableList<Appointment> allAppointments;
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        allAppointments = FXCollections.observableArrayList();
        Query.makeQuery("select a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name as Contact, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID FROM appointments as a join contacts as c on a.Contact_ID=c.Contact_ID");
        ResultSet resultSet = Query.getResult();
        while(resultSet.next()){
            int appointmentID = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String contact = resultSet.getString("Contact");
            String type = resultSet.getString("Type");
            ZonedDateTime start = ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("Start"), dtf), ZoneId.systemDefault());
            ZonedDateTime end = ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("End"), dtf), ZoneId.systemDefault());
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");

            allAppointments.add(new Appointment(appointmentID, customerID, userID, contact, title, description, location, type, start, end));
        }

        return allAppointments;
    }

    public static ObservableList<Appointment> getWeeklyAppointments() throws SQLException {
        allAppointments = FXCollections.observableArrayList();
        Query.makeQuery("select a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name as Contact, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID FROM appointments as a join contacts as c on a.Contact_ID=c.Contact_ID where Start between now() and adddate(now(), 7)");
        ResultSet resultSet = Query.getResult();
        while(resultSet.next()){
            int appointmentID = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String contact = resultSet.getString("Contact");
            String type = resultSet.getString("Type");
            ZonedDateTime start = ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("Start"), dtf), ZoneId.systemDefault());
            ZonedDateTime end = ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("End"), dtf), ZoneId.systemDefault());
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");

            allAppointments.add(new Appointment(appointmentID, customerID, userID, contact, title, description, location, type, start, end));
        }

        return allAppointments;
    }

    public static ObservableList<Appointment> getMonthlyAppointments() throws SQLException {
        allAppointments = FXCollections.observableArrayList();
        Query.makeQuery("select a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name as Contact, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID FROM appointments as a join contacts as c on a.Contact_ID=c.Contact_ID where Start between now() and adddate(now(),interval 1 month)");
        ResultSet resultSet = Query.getResult();
        while(resultSet.next()){
            int appointmentID = resultSet.getInt("Appointment_ID");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            String location = resultSet.getString("Location");
            String contact = resultSet.getString("Contact");
            String type = resultSet.getString("Type");
            ZonedDateTime start = ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("Start"), dtf), ZoneId.systemDefault());
            ZonedDateTime end = ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("End"), dtf), ZoneId.systemDefault());
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");

            allAppointments.add(new Appointment(appointmentID, customerID, userID, contact, title, description, location, type, start, end));
        }

        return allAppointments;
    }
}
