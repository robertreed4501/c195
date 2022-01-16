package Controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import Model.Appointment;
import Model.Contact;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    public TableView <Appointment> appointmentsTable;
    public RadioButton monthRadio;
    public RadioButton weekRadio;
    public RadioButton allApptsRadio;
    public Label appointmentIDLabel;
    public TextField titleField;
    public TextArea descriptionField;
    public TextField locationField;
    public ComboBox <String> contactCombo;
    public TextField typeField;
    public ComboBox <LocalDate> startDateCombo;
    public ComboBox <LocalTime> startTimeCombo;
    public ComboBox <LocalTime> endTimeCombo;
    public ComboBox <String> customerIDCombo;
    public ComboBox <String> userIDCombo;
    public Button saveButton;
    public Button updateButton;
    public Button deleteButton;
    public Button exitButton;
    public Button newAppointmentButton;
    public ToggleGroup viewToggleGroup;
    private static User currentUser;
    public Label statusLabel;
    public TableColumn appointmentIDColumn;
    public TableColumn titleColumn;
    public TableColumn descriptionColumn;
    public TableColumn locationColumn;
    public TableColumn contactColumn;
    public TableColumn typeColumn;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public TableColumn customerColumn;
    public TableColumn userIDColumn;
    public DatePicker dateFieldPicker;
    public ObservableList<String> contactNameList;
    public ObservableList<Contact> contactsList;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        MainController.currentUser = currentUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        weekRadio.setToggleGroup(viewToggleGroup);
        monthRadio.setToggleGroup(viewToggleGroup);
        allApptsRadio.setToggleGroup(viewToggleGroup);
        statusLabel.setText("Currently logged in as: " + getCurrentUser().getUserName());
        try {
            setApptsTable(AppointmentDAO.getAllAppointments());
            contactsList = ContactDAO.getAllContacts();
            contactNameList = FXCollections.observableArrayList();
            for(int i = 0; i < contactsList.size(); i++){
                contactNameList.add(contactsList.get(i).getContactName());
            }
            contactCombo.setItems(contactNameList);
        } catch (SQLException throwables) {
        throwables.printStackTrace();
    }

    }

    public void allApptsClicked(ActionEvent actionEvent) throws SQLException {
        setApptsTable(AppointmentDAO.getAllAppointments());
    }

    public void weeklyClicked(ActionEvent actionEvent) throws SQLException {
        setApptsTable(AppointmentDAO.getWeeklyAppointments());
    }

    public void monthlyClicked(ActionEvent actionEvent) throws SQLException {
        setApptsTable(AppointmentDAO.getMonthlyAppointments());
    }

    public void setApptsTable(ObservableList<Appointment> currentList){
        appointmentsTable.setItems(currentList);

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    public void populateFields() {
        appointmentIDLabel.setText(Integer.toString(appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentID()));
        titleField.setText(appointmentsTable.getSelectionModel().getSelectedItem().getTitle());
        descriptionField.setText(appointmentsTable.getSelectionModel().getSelectedItem().getDescription());
        locationField.setText(appointmentsTable.getSelectionModel().getSelectedItem().getLocation());
        typeField.setText(appointmentsTable.getSelectionModel().getSelectedItem().getType());
        dateFieldPicker.setValue(appointmentsTable.getSelectionModel().getSelectedItem().getStart().toLocalDate());
        contactCombo.setValue(appointmentsTable.getSelectionModel().getSelectedItem().getContact());
    }
}
