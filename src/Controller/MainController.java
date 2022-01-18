package Controller;

import DAO.*;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
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
    public ComboBox <String> startTimeCombo;
    public ComboBox <String> endTimeCombo;
    public ComboBox <String> customerIDCombo;
    public ComboBox <String> userIDCombo;
    public Button saveButton;
    public Button updateButton;
    public Button deleteButton;
    public Button exitButton;
    public Button newAppointmentButton;
    public ToggleGroup viewToggleGroup;
    private static User currentUser;
    public static ZoneOffset offset;
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
    public ObservableList<String> userIDList;
    public ObservableList<User> userList;
    public ObservableList<String> customerIDList;
    public ObservableList<Customer> customerList;
    public ObservableList<String> apptTimes;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        MainController.currentUser = currentUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        //***Set up Appointments Tab***//
        offset = ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now());
        appointmentIDLabel.setText("");
        weekRadio.setToggleGroup(viewToggleGroup);
        monthRadio.setToggleGroup(viewToggleGroup);
        allApptsRadio.setToggleGroup(viewToggleGroup);
        saveButton.setDisable(true);
        statusLabel.setText("Currently logged in as: " + getCurrentUser().getUserName());
        try {
            setApptsTable(AppointmentDAO.getAllAppointments());

            contactsList = ContactDAO.getAllContacts();
            contactNameList = FXCollections.observableArrayList();
            for(int i = 0; i < contactsList.size(); i++){
                contactNameList.add(contactsList.get(i).getContactName());
            }
            contactCombo.setItems(contactNameList);

            userList = UserDAO.getAllUsers();
            userIDList = FXCollections.observableArrayList();
            for(int i = 0; i < userList.size(); i++){
                userIDList.add(Integer.toString(userList.get(i).getUserID()));
            }
            userIDCombo.setItems(userIDList);

            customerList = CustomerDAO.getAllCustomers();
            customerIDList = FXCollections.observableArrayList();
            for(int i = 0; i < customerList.size(); i++){
                customerIDList.add(Integer.toString(customerList.get(i).getCustomerID()));
            }
            customerIDCombo.setItems(customerIDList);

            apptTimes = FXCollections.observableArrayList();
            for (int i = 0; i < 14; i++){
                for (int j = 0; j < 4; j++){
                    if (j == 0) apptTimes.add(Integer.toString((13 + i)%24) + ":00");
                    else apptTimes.add(Integer.toString((13 + i)%24) + ":" + Integer.toString(15 * j));
                }

            }
            startTimeCombo.setItems(apptTimes);
            endTimeCombo.setItems(apptTimes);
            appointmentsTable.getSortOrder().add(appointmentIDColumn);


                ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
                for (int i = 0; i < allAppointments.size(); i++){
                    if (ZonedDateTime.now().plusMinutes(15).isAfter(allAppointments.get(i).getStartZDT()) && ZonedDateTime.now().isBefore(allAppointments.get(i).getStartZDT())){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have an appointment soon");
                        alert.showAndWait();
                    }
                }

        } catch (SQLException throwables) {
        throwables.printStackTrace();
        }


        //***set up Customers tab***//


        //***set up Reports tab***//



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
        startColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("start"));
        /*startColumn.setCellFactory(column -> {
            TableCell<Appointment, ZonedDateTime> cell = new TableCell<Appointment, ZonedDateTime>() {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                @Override
                protected void updateItem(ZonedDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        this.setText(dtf.format(item));
                    }
                }
            };
            return cell;
        });*/
        endColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("end"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentsTable.getSortOrder().add(appointmentIDColumn);
    }

    public void populateApptFields() {
        if(!appointmentsTable.getSelectionModel().isEmpty()) {
            appointmentIDLabel.setText(Integer.toString(appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentID()));
            titleField.setText(appointmentsTable.getSelectionModel().getSelectedItem().getTitle());
            descriptionField.setText(appointmentsTable.getSelectionModel().getSelectedItem().getDescription());
            locationField.setText(appointmentsTable.getSelectionModel().getSelectedItem().getLocation());
            typeField.setText(appointmentsTable.getSelectionModel().getSelectedItem().getType());
            dateFieldPicker.setValue(appointmentsTable.getSelectionModel().getSelectedItem().getStartZDT().toLocalDate());
            contactCombo.setValue(appointmentsTable.getSelectionModel().getSelectedItem().getContact());
            userIDCombo.setValue(Integer.toString(appointmentsTable.getSelectionModel().getSelectedItem().getUserID()));
            customerIDCombo.setValue(Integer.toString(appointmentsTable.getSelectionModel().getSelectedItem().getCustomerID()));
            startTimeCombo.setValue(appointmentsTable.getSelectionModel().getSelectedItem().getStartZDT().withZoneSameInstant(ZoneId.systemDefault()).toLocalTime().toString());
            endTimeCombo.setValue(appointmentsTable.getSelectionModel().getSelectedItem().getEndZDT().withZoneSameInstant(ZoneId.systemDefault()).toLocalTime().toString());
            saveButton.setDisable(true);
            updateButton.setDisable(false);
            newAppointmentButton.setDisable(false);
        }
    }

    public static int nextAppointmentID() throws SQLException {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        int max =0;
        for (int i = 0; i < allAppointments.size(); i++){
            if (allAppointments.get(i).getAppointmentID() > max){
                max = allAppointments.get(i).getAppointmentID();
            }
        }
        return max + 1;
    }

    public void newAppointmentClicked() throws SQLException {
        clearApptFields();
        appointmentIDLabel.setText(Integer.toString(nextAppointmentID()));
        newAppointmentButton.setDisable(true);
    }

    public void clearApptFields(){
        appointmentIDLabel.setText("");
        titleField.clear();
        descriptionField.clear();
        locationField.clear();
        typeField.clear();
        dateFieldPicker.setValue(null);
        contactCombo.setValue("");
        startTimeCombo.setValue("");
        endTimeCombo.setValue("");
        userIDCombo.setValue("");
        customerIDCombo.setValue("");
        saveButton.setDisable(false);
        updateButton.setDisable(true);
        appointmentsTable.getSelectionModel().clearSelection();
    }

    public void saveApptClicked() throws SQLException {
        int apptID = nextAppointmentID();
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        ZonedDateTime start = ZonedDateTime.of(dateFieldPicker.getValue(), LocalTime.parse(startTimeCombo.getValue()), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(dateFieldPicker.getValue(), LocalTime.parse(endTimeCombo.getValue()), ZoneId.systemDefault());
        int customerID = Integer.parseInt(customerIDCombo.getValue());
        int userID = Integer.parseInt(userIDCombo.getValue());
        int contactID = getContactID(contactCombo.getValue());

        AppointmentDAO.createNewAppointment(apptID, title, description, location, type, start, end, customerID, userID, contactID);
        setApptsTable(AppointmentDAO.getAllAppointments());
        clearApptFields();
        allApptsRadio.setSelected(true);
        saveButton.setDisable(true);
        updateButton.setDisable(false);
        newAppointmentButton.setDisable(false);
    }

    public int getContactID(String contactName) throws SQLException {
        ObservableList<Contact> allContacts = ContactDAO.getAllContacts();
        for(int i = 0; i < allContacts.size(); i++){
            if(allContacts.get(i).getContactName().equals(contactName)) return allContacts.get(i).getContactID();

        }
        return -1;
    }

    public void exitClicked(ActionEvent actionEvent) throws SQLException {
        DBConnection.closeConnection();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void deleteApptClicked() throws SQLException {
        int apptID = appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentID();
        AppointmentDAO.delete(apptID);
        setApptsTable(AppointmentDAO.getAllAppointments());
        clearApptFields();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment ID: " + apptID + " has been cancelled.");
        alert.showAndWait();
    }

    public void updateApptClicked(ActionEvent actionEvent) throws SQLException {
        int apptID = appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentID();
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        ZonedDateTime start = ZonedDateTime.of(dateFieldPicker.getValue(), LocalTime.parse(startTimeCombo.getValue()), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(dateFieldPicker.getValue(), LocalTime.parse(endTimeCombo.getValue()), ZoneId.systemDefault());
        int customerID = Integer.parseInt(customerIDCombo.getValue());
        int userID = Integer.parseInt(userIDCombo.getValue());
        int contactID = getContactID(contactCombo.getValue());
        AppointmentDAO.updateAppointment(apptID, title, description, location, type, start, end, customerID, userID, contactID);
        setApptsTable(AppointmentDAO.getAllAppointments());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment ID: " + apptID + " has been updated.");
        alert.showAndWait();
    }
}
