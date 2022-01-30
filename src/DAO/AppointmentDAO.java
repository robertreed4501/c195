package DAO;

import Controller.MainController;
import Model.Appointment;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
/**This class handles talking to the database about appointments.*/
public class AppointmentDAO {
    /**This list holds all appointments from the database.*/
    public static ObservableList<Appointment> allAppointments;
    /**This is the date time formatter that talks properly to the database.*/
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**This method returns a list of appointments from the database.
     * Gets the data needed for the appointments table by joining appointments and contacts tables in the sql query.
     *
     * @return observableList of all appointments from the database.*/
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
            ZonedDateTime start = ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("Start"), dtf), ZoneId.of("Europe/London"));
            ZonedDateTime end = ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("End"), dtf), ZoneId.of("Europe/London"));
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");

            allAppointments.add(new Appointment(appointmentID, customerID, userID, contact, title, description, location, type, start, end));
        }
        return allAppointments;
    }

    /**This method gets appointments for the next week.
     * It uses a different query that filters by date.
     *
     * @return the observableList of appointments for the next week.*/
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
            ZonedDateTime start = ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("Start"), dtf), ZoneId.of("Europe/London"));
            ZonedDateTime end = ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("End"), dtf), ZoneId.of("Europe/London"));
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");

            allAppointments.add(new Appointment(appointmentID, customerID, userID, contact, title, description, location, type, start, end));
        }
        return allAppointments;
    }

    /**This method gets the next months' appointments.
     * It also uses date filtering in the query.
     *
     * @return the observableList of appointments scheduled within the next month.*/
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
            ZonedDateTime start = ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("Start"), dtf), ZoneId.of("Europe/London"));
            ZonedDateTime end = ZonedDateTime.of(LocalDateTime.parse(resultSet.getString("End"), dtf), ZoneId.of("Europe/London"));
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");

            allAppointments.add(new Appointment(appointmentID, customerID, userID, contact, title, description, location, type, start, end));
        }
        return allAppointments;
    }

    /**This method creates a new appointment in the database.
     * Uses an insert query to create a new appointment using parameters passed in.
     *
     * @param customerID the customers ID number
     * @param apptID the new appointmentID
     * @param contactID the contacts ID number
     * @param description description of the appointment
     * @param end ZonedDateTime of appointment end
     * @param location the location of the appointment
     * @param start ZonedDateTime of appointment start
     * @param title a title for the appointment
     * @param type the type of appointment
     * @param userID the users ID number */
    public static void createNewAppointment(int apptID, String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, int customerID, int userID, int contactID) throws SQLException {
        Query.makeQuery("INSERT INTO appointments VALUES("+Integer.toString(apptID) +",'"+title+"','"+description+"','"+location+"','"+type+"','"+dtf.format(start.withZoneSameInstant(ZoneId.of("Europe/London")))+"','"+dtf.format(end.withZoneSameInstant(ZoneId.of("Europe/London")))+"', NOW(),'"+MainController.getCurrentUser().getUserName()+"', NOW(),'"+MainController.getCurrentUser().getUserName()+"',"+Integer.toString(customerID)+","+Integer.toString(userID)+","+Integer.toString(contactID)+")");
    }

    /**This method deletes an appointment from the database.
     * SQL delete statement that uses appointment ID to find the record to delete.
     *
     * @param apptID the appointment ID to be deleted*/
    public static void delete(int apptID) {
        Query.makeQuery("DELETE FROM appointments WHERE Appointment_ID="+Integer.toString(apptID));
    }

    /**This method updates an existing appointment.
     * SQL update statement that uses apptID to lookup which record to update.
     *  @param customerID the customers ID number
     *  @param apptID the new appointmentID
     *  @param contactID the contacts ID number
     *  @param description description of the appointment
     *  @param end ZonedDateTime of appointment end
     *  @param location the location of the appointment
     *  @param start ZonedDateTime of appointment start
     *  @param title a title for the appointment
     *  @param type the type of appointment
     *  @param userID the users ID number */
    public static void updateAppointment(int apptID, String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, int customerID, int userID, int contactID) {
        Query.makeQuery("UPDATE appointments SET Title='"+title+"',Description='"+description+"',Location='"+location+"',Type='"+type+"',Start='"+dtf.format(start.withZoneSameInstant(ZoneId.of("Europe/London")))+"',End='"+dtf.format(end.withZoneSameInstant(ZoneId.of("Europe/London")))+"',Last_Update='"+dtf.format(ZonedDateTime.now(ZoneId.of("UTC")))+"',Last_Updated_By='"+MainController.getCurrentUser().getUserName()+"',Customer_ID="+Integer.toString(customerID)+",User_ID="+Integer.toString(userID)+",Contact_ID="+Integer.toString(contactID)+" WHERE Appointment_ID="+apptID);
    }

    /**This method gets a customer's list of appointments.
     * A customer object is passed and a SQL statement executed that filters all appointments by a particular customerID
     *
     * @param customer the customer object that we are getting the list for.
     * @return the observableList of customer's appointments*/
    public static ObservableList<Appointment> getAppointmentsByCustomer(Customer customer) throws SQLException {
        ObservableList<Appointment> customersAppointments = FXCollections.observableArrayList();
        Query.makeQuery("SELECT * FROM appointments as a JOIN customers as b ON a.Customer_ID = b.Customer_ID WHERE a.Customer_ID=" + customer.getCustomerID());
        ResultSet rs = Query.getResult();
        while(rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contactID = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            ZonedDateTime start = ZonedDateTime.of(LocalDateTime.parse(rs.getString("Start"), dtf), ZoneId.of("Europe/London"));
            ZonedDateTime end = ZonedDateTime.of(LocalDateTime.parse(rs.getString("End"), dtf), ZoneId.of("Europe/London"));
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");

            customersAppointments.add(new Appointment(appointmentID, customerID, userID, ContactDAO.getContactNameByID(contactID), title, description, location, type, start, end));
        }
        return customersAppointments;
    }

    /**This method gets a list of appointments scheduled for a certain user.
     * Is passed in a User object, creates a SQL statement that gets all appointments from that user.
     *
     * @param user the user whose appointments we are searching for.
     * @return the observableList of user's appointments*/
    public static ObservableList<Appointment> getAppointmentsByUser(User user) throws SQLException {
        ObservableList<Appointment> customersAppointments = FXCollections.observableArrayList();
        Query.makeQuery("SELECT * FROM appointments as a JOIN customers as b ON a.Customer_ID = b.Customer_ID WHERE a.User_ID=" + user.getUserID());
        ResultSet rs = Query.getResult();
        while(rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            int contactID = rs.getInt("Contact_ID");
            String type = rs.getString("Type");
            ZonedDateTime start = ZonedDateTime.of(LocalDateTime.parse(rs.getString("Start"), dtf), ZoneId.of("Europe/London"));
            ZonedDateTime end = ZonedDateTime.of(LocalDateTime.parse(rs.getString("End"), dtf), ZoneId.of("Europe/London"));
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");

            customersAppointments.add(new Appointment(appointmentID, customerID, userID, ContactDAO.getContactNameByID(contactID), title, description, location, type, start, end));
        }
        return customersAppointments;
    }
}
