package Model;

import DAO.AppointmentDAO;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**This class defines appointment objects for use in the appointment table. */
public class Appointment {
    private int appointmentID, customerID, userID;
    private String title, description, location, type, contact, start, end;
    private ZonedDateTime startZDT, endZDT;

    /**This is the constructor for Appointment objects. */
    public Appointment(int appointmentID, int customerID, int userID, String contactID, String title, String description, String location, String type, ZonedDateTime startZDT, ZonedDateTime endZDT) {
        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.userID = userID;
        this.contact = contactID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = AppointmentDAO.dtf.format(startZDT.withZoneSameInstant(ZoneId.systemDefault()));
        this.end = AppointmentDAO.dtf.format(endZDT.withZoneSameInstant(ZoneId.systemDefault()));
        this.startZDT = startZDT;
        this.endZDT = endZDT;
    }

    /**This method gets the appointmentID.
     *
     * @return the appointmentID. */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**This method gets the customerID.
     *
     * @return the customerID */
    public int getCustomerID() {
        return customerID;
    }

    /**This method sets the customerID.
     *
     * @param customerID the customerID*/
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**This method gets the userID.
     *
     * @return the userID*/
    public int getUserID() {
        return userID;
    }

    /**This method sets the userID.
     *
     * @param userID the userID. */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**This method gets the title.
     *
     * @return  the title of the appointment. */
    public String getTitle() {
        return title;
    }

    /**This method sets the title.
     *
     * @param title the new title. */
    public void setTitle(String title) {
        this.title = title;
    }

    /**This method gets the description of the appointment.
     *
     * @return the description of the appintment. */
    public String getDescription() {
        return description;
    }

    /**This method sets the description.
     *
     * @param description the new description. */
    public void setDescription(String description) {
        this.description = description;
    }

    /**This method gets the location of the appointment.
     *
     * @return the location. */
    public String getLocation() {
        return location;
    }

    /**This method sets the location.
     *
     * @param location the new location. */
    public void setLocation(String location) {
        this.location = location;
    }

    /**This string gest the type of appointment.
     *
     * @return  the appointment type. */
    public String getType() {
        return type;
    }

    /**This method sets the type of the appointment.
     *
     * @param type the appointment type. */
    public void setType(String type) {
        this.type = type;
    }

    /**This method gets the contact name.
     *
     * @return  the contact name. */
    public String getContact() {
        return contact;
    }

    /**This method sets the contact name.
     *
     * @param contact the contact name. */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**This method gets the start date and time formatted as a string and in local time zone.
     *
     * @return the string of start date and time. */
    public String getStart() {
        return start;
    }

    /**This method sets the start date and time.
     *
     * @param start the start date and time formatted as a string. */
    public void setStart(String start) {
        this.start = start;
    }

    /**This method gets the end date and time formatted as a string and in local time zone.
     *
     * @return the string of end date and time. */
    public String getEnd() {
        return end;
    }

    /**This method sets the end date and time.
     *
     * @param end the end date and time formatted as a string. */
    public void setEnd(String end) {
        this.end = end;
    }

    /**This method gets the start date and time in ZonedDateTime format.
     *
     * @return ZonedDateTime the start date and time. */
    public ZonedDateTime getStartZDT() {
        return startZDT;
    }

    /**This method gets the end date and time in ZonedDateTime format.
     *
     * @return ZonedDateTime the end date and time. */
    public ZonedDateTime getEndZDT() {
        return endZDT;
    }
}
