package Controller;

import DAO.*;
import Model.*;
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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static DAO.AppointmentDAO.dtf;


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
    public Button saveApptButton;
    public Button updateApptButton;
    public Button deleteApptButton;
    public Button exitApptButton;
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
    public TableView<Customer> customerTable;
    public TableColumn customerIDColumn;
    public TableColumn nameColumn;
    public TableColumn addressColumn;
    public TableColumn postalCodeColumn;
    public TableColumn phoneColumn;
    public TableColumn countryColumn;
    public TableColumn divisionColumn;
    public TextField nameField;
    public TextField addressField;
    public TextField postalCodeField;
    public Label customerIDLabel;
    public TextField phoneField;
    public ComboBox countryCombo;
    public ComboBox divisionCombo;
    public Button newCustomerButton;
    public Button saveCustomerButton;
    public Button updateCustomerButton;
    public Button deleteCustomerButton;
    public Button exitCustomerButton;
    public Label custStatusLabel;
    

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
        saveApptButton.setDisable(true);
        saveCustomerButton.setDisable(true);
        statusLabel.setText("Currently logged in as: " + getCurrentUser().getUserName());
        custStatusLabel.setText("Currently logged in as: " + getCurrentUser().getUserName());
        try {
            setApptsTable(AppointmentDAO.getAllAppointments());
            setCustomerTable(CustomerDAO.getAllCustomers());
            contactsList = ContactDAO.getAllContacts();
            contactNameList = FXCollections.observableArrayList();
            ContactDAO.getAllContacts().stream().forEach(contact -> contactNameList.add(contact.getContactName()));
            /*for(int i = 0; i < contactsList.size(); i++){
                contactNameList.add(contactsList.get(i).getContactName());
            }*/
            contactCombo.setItems(contactNameList);

            userList = UserDAO.getAllUsers();
            userIDList = FXCollections.observableArrayList();
            UserDAO.getAllUsers().stream().forEach(user -> userIDList.add(Integer.toString(user.getUserID())));
            /*for(int i = 0; i < userList.size(); i++){
                userIDList.add(Integer.toString(userList.get(i).getUserID()));
            }*/
            userIDCombo.setItems(userIDList);

            customerList = CustomerDAO.getAllCustomers();
            customerIDList = FXCollections.observableArrayList();
            CustomerDAO.getAllCustomers().stream().forEach(customer -> customerIDList.add(Integer.toString(customer.getCustomerID())));
            /*for(int i = 0; i < customerList.size(); i++){
                customerIDList.add(Integer.toString(customerList.get(i).getCustomerID()));
            }*/
            customerIDCombo.setItems(customerIDList);

            ObservableList<String> allCountries = FXCollections.observableArrayList();
            CountryDAO.getAllCountryNames().stream().forEach(country -> allCountries.add(country));
            countryCombo.setItems(allCountries);

            ObservableList<String> allDivisions = FXCollections.observableArrayList();
            DivisionDAO.getAllDivisionNames().stream().forEach(division -> allDivisions.add(division));
            divisionCombo.setItems(allDivisions);


            //dateFieldPicker = new DatePicker();
            dateFieldPicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(empty || ((date.getDayOfWeek() == DayOfWeek.SATURDAY) || (date.getDayOfWeek() == DayOfWeek.SUNDAY) || (date.isBefore(LocalDate.now()))));

                }
            });
            dateFieldPicker.setEditable(false);
            //TODO fix apptTimes list for timezones.

            ZonedDateTime openTime = ZonedDateTime.of(2022,1,1,8,0,0,0,ZoneId.of("America/New_York"));
            ObservableList<String> apptTimes = FXCollections.observableArrayList();
            while(openTime.getHour() < 22){
                apptTimes.add(openTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime().toString());
                openTime = openTime.plusMinutes(15);
            }



            /*apptTimes = FXCollections.observableArrayList();
            for (int i = 0; i < 14; i++){
                for (int j = 0; j < 4; j++){
                    if (j == 0) apptTimes.add(Integer.toString((13 + i)%24) + ":00");
                    else apptTimes.add(Integer.toString((13 + i)%24) + ":" + Integer.toString(15 * j));
                }

            }*/
            startTimeCombo.setItems(apptTimes);
            endTimeCombo.setItems(apptTimes);
            appointmentsTable.getSortOrder().add(appointmentIDColumn);


            ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointmentsByUser(getCurrentUser());
            for (int i = 0; i < allAppointments.size(); i++){
                //System.out.println(ZonedDateTime.now().plusMinutes(15).toInstant() + ", " + allAppointments.get(i).getStartZDT().withZoneSameInstant(ZoneId.of("Europe/London")).toInstant());
                if (ZonedDateTime.now().plusMinutes(15).isAfter(allAppointments.get(i).getStartZDT().withZoneSameLocal(ZoneId.of("Europe/London"))) && ZonedDateTime.now().isBefore(allAppointments.get(i).getStartZDT().withZoneSameLocal(ZoneId.of("Europe/London")))){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have an appointment soon.\nAppointment ID: " + allAppointments.get(i).getAppointmentID() + "\nDate and Time: " +
                            dtf.format(allAppointments.get(i).getStartZDT().withZoneSameInstant(ZoneId.systemDefault())));
                    alert.showAndWait();
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You have no upcoming appointments within the next 15 minutes.");
            alert.showAndWait();

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
        endColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("end"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentsTable.getSortOrder().add(appointmentIDColumn);
    }

    public void setCustomerTable(ObservableList<Customer> customerList){
        customerTable.setItems(customerList);

        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        customerTable.getSortOrder().add(customerIDColumn);
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
            saveApptButton.setDisable(true);
            updateApptButton.setDisable(false);
            newAppointmentButton.setDisable(false);
        }
    }

    public void populateCustFields() {
        if(!customerTable.getSelectionModel().isEmpty()) {
            customerIDLabel.setText(Integer.toString(customerTable.getSelectionModel().getSelectedItem().getCustomerID()));
            nameField.setText(customerTable.getSelectionModel().getSelectedItem().getCustomerName());
            addressField.setText(customerTable.getSelectionModel().getSelectedItem().getAddress());
            postalCodeField.setText(customerTable.getSelectionModel().getSelectedItem().getPostalCode());
            phoneField.setText(customerTable.getSelectionModel().getSelectedItem().getPhone());
            divisionCombo.setValue(customerTable.getSelectionModel().getSelectedItem().getDivision());
            countryCombo.setValue(customerTable.getSelectionModel().getSelectedItem().getCountry());
            saveCustomerButton.setDisable(true);
            updateCustomerButton.setDisable(false);
            newCustomerButton.setDisable(false);
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

    public static int nextCustomerID() throws SQLException {
        ObservableList<Customer> allCustomers = CustomerDAO.getAllCustomers();
        int max = 0;
        for (int i = 0; i < allCustomers.size(); i++){
            if (allCustomers.get(i).getCustomerID() > max){
                max = allCustomers.get(i).getCustomerID();
            }
        }
        return max + 1;
    }

    public void newAppointmentClicked() throws SQLException {
        clearApptFields();
        appointmentIDLabel.setText(Integer.toString(nextAppointmentID()));
        newAppointmentButton.setDisable(true);
        saveApptButton.setDisable(false);
    }

    public void newCustomerClicked() throws SQLException {
        clearCustFields();
        customerIDLabel.setText((Integer.toString(nextCustomerID())));
        newCustomerButton.setDisable(true);
        saveCustomerButton.setDisable(false);
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
        saveApptButton.setDisable(true);
        updateApptButton.setDisable(true);
        appointmentsTable.getSelectionModel().clearSelection();
    }

    public void clearCustFields(){
        customerIDLabel.setText("");
        nameField.clear();
        addressField.clear();
        postalCodeField.clear();
        phoneField.clear();
        divisionCombo.setValue("");
        countryCombo.setValue("");
        saveCustomerButton.setDisable(true);
        updateCustomerButton.setDisable(true);
        customerTable.getSelectionModel().clearSelection();
    }

    public void saveApptClicked() throws SQLException { //TODO write form validation for all forms and implement at beginning of this method and updateApptClicked
        if(!validateAppointmentForms()) return;
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


        if(hasConflict(new Appointment(apptID, customerID, userID, Integer.toString(contactID), title, description, location, type, start, end))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Customer " + CustomerDAO.getCustomerByID(customerID).getCustomerName() + " already has an appointment scheduled at this time.  Choose another time.");
            alert.showAndWait();
            return;
        }

        AppointmentDAO.createNewAppointment(apptID, title, description, location, type, start, end, customerID, userID, contactID);
        setApptsTable(AppointmentDAO.getAllAppointments());
        clearApptFields();
        allApptsRadio.setSelected(true);
        saveApptButton.setDisable(true);
        updateApptButton.setDisable(false);
        newAppointmentButton.setDisable(false);

    }

    private boolean validateAppointmentForms() {
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");
        if (appointmentIDLabel.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Appointment ID field is empty.  Click on the \"+Create New Appointment\" button to create a new Appointment ID");
            alert.showAndWait();
            return false;
        }
        if (titleField.getText().trim().equals("")){
            isEmptyAlert("Title");
            return false;
        }
        if (descriptionField.getText().trim().equals("")){
            isEmptyAlert("Description");
            return false;
        }
        if (locationField.getText().trim().equals("")){
            isEmptyAlert("Location");
            return false;
        }
        if (contactCombo.getSelectionModel().isEmpty()){
            isEmptyAlert("Contact");
            return false;
        }
        if (typeField.getText().trim().equals("")){
            isEmptyAlert("Type");
            return false;
        }
        try{
            String date = dateFieldPicker.getValue().toString();
        }
        catch (NullPointerException e){
            isEmptyAlert("Date");
            return false;
        }
        if (startTimeCombo.getSelectionModel().isEmpty()){
            isEmptyAlert("Start Time");
            return false;
        }
        if (endTimeCombo.getSelectionModel().isEmpty()){
            isEmptyAlert("End Time");
            return false;
        }
        if (customerIDCombo.getSelectionModel().isEmpty()){
            isEmptyAlert("Customer ID");
            return false;
        }
        if (userIDCombo.getSelectionModel().isEmpty()){
            isEmptyAlert("User ID");
            return false;
        }
        if (LocalTime.parse(startTimeCombo.getValue(), tf).isAfter(LocalTime.parse(endTimeCombo.getValue(), tf))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Start time must be before End time.");
            alert.showAndWait();
            return false;
        }
        if (LocalTime.parse(startTimeCombo.getValue(), tf).equals(LocalTime.parse(endTimeCombo.getValue(), tf))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("End time must be after Start time.");
            alert.showAndWait();
            return false;
        }
        else return true;
    }

    public void isEmptyAlert(String field){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(field + " field is empty.  All fields must be completed before saving or updating.");
        alert.showAndWait();
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

    public void exitCustomerClicked(ActionEvent actionEvent) throws SQLException {
        DBConnection.closeConnection();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void deleteApptClicked() throws SQLException {
        try {
            int apptID = appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentID();
            String type = appointmentsTable.getSelectionModel().getSelectedItem().getType();
            AppointmentDAO.delete(apptID);
            setApptsTable(AppointmentDAO.getAllAppointments());
            clearApptFields();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment ID: " + apptID + " of type: " + type +"  has been cancelled.");
            alert.showAndWait();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select an appointment to delete before pressing the delete button.");
            alert.showAndWait();
        }
    }

    public void updateApptClicked(ActionEvent actionEvent) throws SQLException { //TODO implement form validation
        try {
            if(!validateAppointmentForms()) return;
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
            clearApptFields();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select an appointment to update before pressing the update button.");
            alert.showAndWait();
        }
    }

    public void onCountrySelected() throws SQLException {

            ObservableList<String> divisions = FXCollections.observableArrayList();
            DivisionDAO.getDivisionsOfCountry((String)countryCombo.getSelectionModel().getSelectedItem()).stream().forEach(division -> divisions.add(division));
            divisionCombo.setItems(divisions);

    }

    public void updateCustomerClicked() throws SQLException { //TODO form validation
        int divisionID = 0;
        ObservableList<Division> allDivisions = DivisionDAO.getAllDivisions();
        for (int i = 0; i < allDivisions.size(); i++){
            if (allDivisions.get(i).getDivision().equals(divisionCombo.getSelectionModel().getSelectedItem().toString())){
                divisionID = allDivisions.get(i).getDivisionID();
                break;
            }
        }
        CustomerDAO.updateCustomer(nameField.getText(), addressField.getText(), postalCodeField.getText(), phoneField.getText(), getCurrentUser().getUserName(), divisionID, customerIDLabel.getText());
        setCustomerTable(CustomerDAO.getAllCustomers());
        clearCustFields();
    }

    public void saveCustomerClicked() throws SQLException { //TODO form validation
        if(!validateCustomerForms()) return;
        int divisionID = 0;
        ObservableList<Division> allDivisions = DivisionDAO.getAllDivisions();
        for (int i = 0; i < allDivisions.size(); i++){
            if (allDivisions.get(i).getDivision().equals(divisionCombo.getSelectionModel().getSelectedItem().toString())){
                divisionID = allDivisions.get(i).getDivisionID();
                break;
            }
        }
        CustomerDAO.createNewCustomer(Integer.parseInt(customerIDLabel.getText()), nameField.getText(),addressField.getText(), postalCodeField.getText(), phoneField.getText(), getCurrentUser().getUserName(), divisionID );
        setCustomerTable(CustomerDAO.getAllCustomers());
        saveCustomerButton.setDisable(true);
        updateCustomerButton.setDisable(false);
        newCustomerButton.setDisable(false);
        clearCustFields();
    }

    private boolean validateCustomerForms() {
        if(nameField.getText().trim().equals("")){
            isEmptyAlert("Name");
            return false;
        }
        if(addressField.getText().trim().equals("")){
            isEmptyAlert("Address");
            return false;
        }
        if(postalCodeField.getText().trim().equals("")){
            isEmptyAlert("Postal Code");
            return false;
        }
        if(phoneField.getText().trim().equals("")){
            isEmptyAlert("Phone");
            return false;
        }
        if(countryCombo.getSelectionModel().isEmpty()){
            isEmptyAlert("Country");
            return false;
        }
        if(divisionCombo.getSelectionModel().isEmpty()){
            isEmptyAlert("Division");
            return false;
        }
        else return true;

    }

    public void deleteCustomerClicked() throws SQLException {
        if(customerTable.getSelectionModel().isEmpty()) return;
        if(!hasAppointments(customerTable.getSelectionModel().getSelectedItem().getCustomerID())) {
            CustomerDAO.deleteCustomer(customerTable.getSelectionModel().getSelectedItem().getCustomerID());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Customer '" + customerTable.getSelectionModel().getSelectedItem().getCustomerName() + "' has been deleted");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Cannot Delete Customer\nCustomer has appointments scheduled.\nDelete customer's appointments first.");
            alert.showAndWait();
        }
        setCustomerTable(CustomerDAO.getAllCustomers());
        saveCustomerButton.setDisable(true);
        updateCustomerButton.setDisable(false);
        newCustomerButton.setDisable(false);
        clearCustFields();
    }

    public boolean hasAppointments(int customerID) throws SQLException {
        return AppointmentDAO.getAllAppointments().stream().anyMatch(appointment -> customerID == appointment.getCustomerID());
    }

    public boolean hasConflict(Appointment appointment) throws SQLException {
        /*Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(Integer.toString(AppointmentDAO.getAppointmentsByCustomer(CustomerDAO.getCustomerByID(appointment.getCustomerID())).size()));
        alert.showAndWait();*/
        ObservableList<Appointment> customersAppointments = AppointmentDAO.getAppointmentsByCustomer(CustomerDAO.getCustomerByID(appointment.getCustomerID()));
        boolean hasConflict = false;
        for (int i = 0; i < customersAppointments.size(); i++) {
            Instant startInstant = appointment.getStartZDT().toInstant();
            Instant endInstant = appointment.getEndZDT().toInstant();
            Instant startListInstant = customersAppointments.get(i).getStartZDT().withZoneSameLocal(ZoneId.of("Europe/London")).toInstant();
            Instant endListInstant = customersAppointments.get(i).getEndZDT().withZoneSameLocal(ZoneId.of("Europe/London")).toInstant();

            boolean startTimeConflict = startInstant.isBefore(endListInstant) && startInstant.isAfter(startListInstant);
            boolean endTimeConflict = endInstant.isBefore(endListInstant) && endInstant.isAfter(startListInstant);
            boolean startTimeConflict2 = startListInstant.isBefore(endInstant) && startListInstant.isAfter(startInstant);
            boolean endTimeConflict2 = endListInstant.isBefore(endInstant) && endListInstant.isAfter(startInstant);
            /*System.out.println(startInstant.toString() + " " + endInstant.toString() + " " + startListInstant.toString() + " " + endListInstant.toString());
            System.out.println(Boolean.toString(startTimeConflict) + Boolean.toString(endTimeConflict) + Boolean.toString(startTimeConflict2) + Boolean.toString(endTimeConflict2));*/
            if(startTimeConflict || startTimeConflict2 || endTimeConflict || endTimeConflict2) hasConflict = true;

        }
        /*alert.setContentText(Boolean.toString(hasConflict));
        alert.showAndWait();*/
        return hasConflict;
    }

    public void refreshCustomerCombo() throws SQLException {
        customerList = CustomerDAO.getAllCustomers();
        customerIDList = FXCollections.observableArrayList();
        CustomerDAO.getAllCustomers().stream().forEach(customer -> customerIDList.add(Integer.toString(customer.getCustomerID())));
            /*for(int i = 0; i < customerList.size(); i++){
                customerIDList.add(Integer.toString(customerList.get(i).getCustomerID()));
            }*/
        customerIDCombo.setItems(customerIDList);
    }
}
