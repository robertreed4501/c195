package Model;

import DAO.AppointmentDAO;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Appointment {
    private int appointmentID, customerID, userID;
    private String title, description, location, type, contact, start, end;
    private ZonedDateTime startZDT, endZDT;


    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public ZonedDateTime getStartZDT() {
        return startZDT;
    }

    public void setStartZDT(ZonedDateTime startZDT) {
        this.startZDT = startZDT;
    }

    public ZonedDateTime getEndZDT() {
        return endZDT;
    }

    public void setEndZDT(ZonedDateTime endZDT) {
        this.endZDT = endZDT;
    }

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




}
