package Controller;

import DAO.*;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.print.Printable;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static DAO.AppointmentDAO.dtf;

/**This is the controller class for mainform(dot)fxml, the main form of the application.*/
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
    public TableColumn<Appointment, Integer> appointmentIDColumn;
    public TableColumn<Appointment, String> titleColumn;
    public TableColumn<Appointment, String> descriptionColumn;
    public TableColumn<Appointment, String> locationColumn;
    public TableColumn<Appointment, String> contactColumn;
    public TableColumn<Appointment, String> typeColumn;
    public TableColumn<Appointment, String> startColumn;
    public TableColumn<Appointment, String> endColumn;
    public TableColumn<Appointment, String> customerColumn;
    public TableColumn<Appointment, Integer> userIDColumn;
    public DatePicker dateFieldPicker;
    public ObservableList<String> contactNameList;
    public ObservableList<Contact> contactsList;
    public ObservableList<String> userIDList;
    public ObservableList<User> userList;
    public ObservableList<String> customerIDList;
    public ObservableList<Customer> customerList;
    public TableView<Customer> customerTable;
    public TableColumn<Customer, Integer> customerIDColumn;
    public TableColumn<Customer, String> nameColumn;
    public TableColumn<Customer, String> addressColumn;
    public TableColumn<Customer, String> postalCodeColumn;
    public TableColumn<Customer, String> phoneColumn;
    public TableColumn<Customer, String> countryColumn;
    public TableColumn<Customer, String> divisionColumn;
    public TextField nameField;
    public TextField addressField;
    public TextField postalCodeField;
    public Label customerIDLabel;
    public TextField phoneField;
    public ComboBox<String> countryCombo;
    public ComboBox<String> divisionCombo;
    public Button newCustomerButton;
    public Button saveCustomerButton;
    public Button updateCustomerButton;
    public Button deleteCustomerButton;
    public Button exitCustomerButton;
    public Label custStatusLabel;
    public TableView<Appointment> reportApptTable;
    public TableView<ReportData> reportApptByTypeTable;
    public ComboBox<Month> reportMonthCombo;
    public PieChart reportPieChart;
    public ComboBox<Contact> reportsContactCombo;
    public TableColumn<Appointment, Integer> reportsApptIDColumn;
    public TableColumn<Appointment, String> reportsTitleColumn;
    public TableColumn<Appointment, String> reportsTypeColumn;
    public TableColumn<Appointment, String> reportsDescriptionColumn;
    public TableColumn<Appointment, String> reportsStartColumn;
    public TableColumn<Appointment, String> reportsEndColumn;
    public TableColumn<Appointment, Integer> reportsCumstomerIDColumn;
    public ObservableList<ReportData> tableData = FXCollections.observableArrayList();
    public TableColumn<ReportData, String> reportApptByTypeTypeColumn;
    public TableColumn<ReportData, Integer> reportApptByTypeCountColumn;
    public Label reportsStatusLabel;

    /**Gets the currently logged in user.
     * Used by the DAOs to log user changes to DB.
     *
     * @return currentUser the currently logged in user*/
    public static User getCurrentUser() {
        return currentUser;
    }

    /**Sets the currently logged in user.
     * Used by the login form when a user successfully logs in.
     *
     * @param currentUser the User that has been logged in.*/
    public static void setCurrentUser(User currentUser) {
        MainController.currentUser = currentUser;
    }

    /**Sets up initial state of main form.
     * Sets up toggle groups, populates tables and combo boxes, etc.
     * <p><b>
     *     Several lambda expressions are used in this method. A few are used to stream through lists while populating
     *     combo boxes.  They replaced for loops which are still commented out below them.  They
     *     help to make the code more clear and intuitive and they reduce the amount of variables and the amount of
     *     code.
     * </b></p>*/
    @Override
    public void initialize(URL location, ResourceBundle resources){
        //***Set up Appointments Tab***//
        appointmentIDLabel.setText("");
        weekRadio.setToggleGroup(viewToggleGroup);
        monthRadio.setToggleGroup(viewToggleGroup);
        allApptsRadio.setToggleGroup(viewToggleGroup);
        saveApptButton.setDisable(true);
        saveCustomerButton.setDisable(true);
        statusLabel.setText("Currently logged in as: " + getCurrentUser().getUserName());
        custStatusLabel.setText("Currently logged in as: " + getCurrentUser().getUserName());
        reportsStatusLabel.setText("Currently logged in as: " + getCurrentUser().getUserName());
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

            dateFieldPicker.setEditable(false);

            ZonedDateTime openTime = ZonedDateTime.of(2022,1,1,8,0,0,0,ZoneId.of("America/New_York"));
            ObservableList<String> apptTimes = FXCollections.observableArrayList();
            while(openTime.getHour() < 22){
                apptTimes.add(openTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime().toString());
                openTime = openTime.plusMinutes(15);
            }

            startTimeCombo.setItems(apptTimes);
            endTimeCombo.setItems(apptTimes);
            appointmentsTable.getSortOrder().add(appointmentIDColumn);


            ObservableList<Appointment> allAppointments = AppointmentDAO.getAppointmentsByUser(getCurrentUser());
            for (int i = 0; i < allAppointments.size(); i++){
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

        //populates pie chart with piechart.data stuff

        try {
            updatePieChart();
        }catch (SQLException e){
            e.printStackTrace();
        }

        try {
            reportsContactCombo.setItems(ContactDAO.getAllContacts());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        setReportMonthCombo();
    }

    /**This method updates the pie chart on the reports page.
     * Gets all customers, streams the list and uses a lambda expression to create piechart data objects
     * from each appointment in the list.
     * <p><b>
     *     A Lambda expression is used here.  It streams through the list with a forEach(Consumer) and transforms each
     *     appointment object in the list into piechart data.  Using a lambda eliminates the need for a variable to
     *     hold the list, and is a more elegant way to solve the problem with less code.
     * </b></p>*/
    private void updatePieChart() throws SQLException {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        CustomerDAO.getAllCustomers().stream().forEach(customer -> {
                    try {
                        pieChartData.add(new PieChart.Data(customer.getCustomerName(), AppointmentDAO.getAppointmentsByCustomer(customer).size()));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }


        );
        reportPieChart.setData(pieChartData);
        reportPieChart.setTitle("Appointments by Customer");
    }

    /**This method sets up the reportMonthCombo box.
     * Iterates through the months in the Months class and stores in an ObservableList.*/
    public void setReportMonthCombo(){
        ObservableList<Month> months = FXCollections.observableArrayList();
        for(int i = 0; i < Month.values().length; i++){
            months.add(Month.of(i + 1));
        }
        reportMonthCombo.setItems(months);
    }

    /**This is a listener method that updates reportApptByTypeTable onAction of reportMonthCombo.
     * Checks if combo box is empty, and if its not, runs setReportApptByTypeTable with the value of reportMonthCombo.*/
    public void reportsMonthComboClicked() throws SQLException {
        if (!reportMonthCombo.getSelectionModel().isEmpty()) setReportApptByTypeTable(reportMonthCombo.getValue());
    }

    /**This method populates the reportApptByTypeTable.
     * Checks if reportMonthCombo is empty, if not, clears the table and resets it using a lambda expression
     * to stream through list of all appointments.
     * <p><b>
     *     A lambda expression is used here while streaming through the list of all appointments.  Uses forEach(),
     *     and transforms each appointment object into pieChart.data by creating ReportData objects for each
     *     new appointment type, and incrementing count if the type already exists.  Putting this chunk of
     *     code in a lambda expression is more intuitive, more concise, uses less variables, and is very
     *     convenient.  Less code, less mutable states, better code.
     * </b></p>
     *
     * @param month the month selected in reportMonthCombo.*/
    public void setReportApptByTypeTable(Month month) throws SQLException {
        if(reportMonthCombo.getSelectionModel().isEmpty()) return;
        tableData.clear();
        AppointmentDAO.getAllAppointments().stream().forEach(appointment ->
        {
            if(appointment.getStartZDT().getMonth().equals(month))
            tableData = addIfNew(new ReportData(appointment.getType(), 1), tableData);

        });
        reportApptByTypeTable.setItems(tableData);
        reportApptByTypeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportApptByTypeCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));

    }

    /**This method helps setReportApptByTypeTable() in the lambda forEach block.
     * Gets the tableData list and in each appointment above, checks if the appointment type is new
     * or has already been seen in the stream before.  If seen, it increments ReportData.count on the
     * already established type.  Results in count of each different type of appointment by month.
     *
     * @param reportData the new ReportData in question.
     * @param tableData the ObservableList we are adding to.
     * @return tableData the observableList that we have added to.*/
    public ObservableList<ReportData> addIfNew(ReportData reportData, ObservableList<ReportData> tableData){

        try{
            for (int i = 0; i < tableData.size(); i++){
                if (tableData.get(i).getType().equals(reportData.getType())){
                    tableData.get(i).increment();
                    return  tableData;
                }
            }
            tableData.add(reportData);
            return tableData;
        }
        catch (NullPointerException e){
            tableData.add(reportData);
            return tableData;
        }

    }

    /**This method is activated onAction of reportsContatcCombo.
     * IF combo box is not empty runs populateScheduleReportFields() on the value of reportsContactCombo (Contact).*/
    public void reportsContactComboClicked() throws SQLException {
        if(!reportsContactCombo.getSelectionModel().isEmpty()) populateScheduleReportFields(reportsContactCombo.getValue());
    }

    /**This method streams through all appointments list and adds the ones that match contact to appointmentsByContact
     * list. Gets a contact from combo box and filters appointments by that contact, then runs setScheduleTable and
     * passes in the list.
     *<p><b>
     *     This uses a lambda expression to stream through the list of all appointments and inserts a bit of code
     *     inline to check each appointment if its contact matches the contact that was passed in.  It uses less
     *     code, is more intuitive, more easily read and understood, uses less variables, and creates less
     *     mutable states.
     *</b></p>
     *
     * @param contact the selected contact from reportsContactCombo*/
    public void populateScheduleReportFields(Contact contact) throws SQLException {
        ObservableList<Appointment> appointmentsByContact = FXCollections.observableArrayList();
        AppointmentDAO.getAllAppointments().stream().forEach(appointment -> {
            if(appointment.getContact().equals(contact.getContactName())){
                appointmentsByContact.add(appointment);
            }
        });
        setScheduleTable(appointmentsByContact);
    }

    /**This method sets the schedule table.
     * Gets passed in a list of appointments filtered by contact and sets them on reportApptTable and sorts by start.
     *
     * @param appointments the Observablelist of appointments filtered by contact.*/
    public void setScheduleTable(ObservableList<Appointment> appointments){
        reportApptTable.setItems(appointments);

        reportsApptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        reportsTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        reportsTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportsDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        reportsStartColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        reportsEndColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        reportsCumstomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        reportApptTable.getSortOrder().add(reportsStartColumn);
    }

    /**Populates table if allAppts radio button is clicked.
     * Will get all appointments.*/
    public void allApptsClicked() throws SQLException {
        setApptsTable(AppointmentDAO.getAllAppointments());
    }

    /**Populates table if weekly radio button is clicked.
     * Will get appointments for the next 7 days.*/
    public void weeklyClicked() throws SQLException {
        setApptsTable(AppointmentDAO.getWeeklyAppointments());
    }

    /**Populates table if monthly radio button is clicked.
     * Will get appointments for the next month.*/
    public void monthlyClicked() throws SQLException {
        setApptsTable(AppointmentDAO.getMonthlyAppointments());
    }

    /**Sets up and populates appointments table.
     * Will fill it with the observable list passed in.
     *
     * @param currentList the either filtered or unfiltered list of appointments from AppointmentDAO.*/
    public void setApptsTable(ObservableList<Appointment> currentList){
        appointmentsTable.setItems(currentList);

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentsTable.getSortOrder().add(appointmentIDColumn);
    }

    /**Sets up and populates customers table.
     * Will fill it with the observable list passed in.
     *
     * @param customerList the list of customers from customerDAO.*/
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

    /**Fills in appointment fields when table is clicked.
     * If table clicked but no selection, returns before attempting to populate.*/
    public void populateApptFields() {
        if(appointmentsTable.getSelectionModel().isEmpty())  return;
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

    /**Fills in customer fields when table is clicked.
     * If table clicked but no selection, returns before attempting to populate.*/
    public void populateCustFields() {
        if(customerTable.getSelectionModel().isEmpty()) return;
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

    /**Generates an appointment Id and returns it.
     * returns (largest appointment ID in allAppointments + 1).
     *
     * @return the next incremental appointmentID.*/
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

    /**Generates an customer Id and returns it.
     * returns (largest customer ID in allCustomers + 1).
     *
     * @return the next incremental customerID.*/
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

    /**Sets state to accept data for a new appointment.
     * Clears all fields, generates new appointmentID, enables save button.*/
    public void newAppointmentClicked() throws SQLException {
        clearApptFields();
        appointmentIDLabel.setText(Integer.toString(nextAppointmentID()));
        newAppointmentButton.setDisable(true);
        saveApptButton.setDisable(false);
    }

    /**Sets state to accept data for a new customer.
     * Clears all fields, generates new customerID, enables save button.*/
    public void newCustomerClicked() throws SQLException {
        clearCustFields();
        customerIDLabel.setText((Integer.toString(nextCustomerID())));
        newCustomerButton.setDisable(true);
        saveCustomerButton.setDisable(false);
    }

    /**Clears all fields on appointment tab.
     * Clears table selection and disables update button.*/
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

    /**Clears all fields on customer tab.
     * Clears table selection and disables update button.*/
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

    /**Saves new appointment to database.
     * Validates forms, gathers form data, checks for scheduling conflicts, sends it to DAO which inserts into database.*/
    public void saveApptClicked() throws SQLException {
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
        updatePieChart();
    }

    /**Validates that forms are not empty and do not contain errors and shows message if errors are found.
     * Trims spaces off of form strings to prevent " " from being stored, ensures end time is after start time.
     *
     * @return true if it passes all validation tests, false if it fails any.*/
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

    /**Shows alert that a field is empty.
     * Gets passed in a field name and tells the user that it is empty.
     *
     * @param field the name of the field that is empty.*/
    public void isEmptyAlert(String field){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(field + " field is empty.  All fields must be completed before saving or updating.");
        alert.showAndWait();
    }

    /**Gets contactID back from a contact name.
     * Searches allContacts for a match and returns ID, or -1 if no match found.
     *
     * @return the ID, or -1 if no match found.*/
    public int getContactID(String contactName) throws SQLException {
        ObservableList<Contact> allContacts = ContactDAO.getAllContacts();
        for(int i = 0; i < allContacts.size(); i++){
            if(allContacts.get(i).getContactName().equals(contactName)) return allContacts.get(i).getContactID();
        }
        return -1;
    }

    /**Exits the application.
     * Closes the db connection, gets the current stage and closes it.
     *
     * @param actionEvent this is where we get the stage from.*/
    public void exitClicked(ActionEvent actionEvent) throws SQLException {
        DBConnection.closeConnection();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**Exits the application.
     * Closes the db connection, gets the current stage and closes it.
     *
     * @param actionEvent this is where we get the stage from.*/
    public void exitCustomerClicked(ActionEvent actionEvent) throws SQLException {
        DBConnection.closeConnection();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**Deletes the selected appointment.
     * If no appointment is selected, catches a NullPointerException and displays an error message.*/
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

    /**Updates the selected appointment.
     * Validates all forms and sends form data to DAO to update DB.*/
    public void updateApptClicked() throws SQLException {
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

            if(hasConflict(new Appointment(apptID, customerID, userID, Integer.toString(contactID), title, description, location, type, start, end))){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Customer " + CustomerDAO.getCustomerByID(customerID).getCustomerName() + " already has an appointment scheduled at this time.  Choose another time.");
                alert.showAndWait();
                return;
            }
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

    /**Populates division combo box upon country combo box being selected.
     * Gets list of divisions, streams through them and creates a list to populate the division combo box.
     * <p><b>
     *     A lambda expression is used here where a for loop could have been used.  Using a lambda achieved the same
     *     thing with less lines and less variables and less of an opportunity to create a mutable state.
     * </b></p>*/
    public void onCountrySelected() throws SQLException {
            ObservableList<String> divisions = FXCollections.observableArrayList();
            DivisionDAO.getDivisionsOfCountry(countryCombo.getValue()).stream().
                    forEach(division -> divisions.add(division));
            divisionCombo.setItems(divisions);
    }

    /**Updates selected customer with new form data.
     * Validates forms, gets divisionId from divison name in combo box, sends it to DAO to update DB.*/
    public void updateCustomerClicked() throws SQLException {
        if(!validateCustomerForms()) return;
        int divisionID = 0;
        ObservableList<Division> allDivisions = DivisionDAO.getAllDivisions();
        for (int i = 0; i < allDivisions.size(); i++){
            if (allDivisions.get(i).getDivision().equals(divisionCombo.getValue())){
                divisionID = allDivisions.get(i).getDivisionID();
                break;
            }
        }
        CustomerDAO.updateCustomer(nameField.getText(), addressField.getText(), postalCodeField.getText(), phoneField.getText(), getCurrentUser().getUserName(), divisionID, customerIDLabel.getText());
        setCustomerTable(CustomerDAO.getAllCustomers());
        clearCustFields();
    }

    /**Saves new customer to DB.
     * Validates forms, gets divisionID from division name in combo, sends to DAO to update DB.*/
    public void saveCustomerClicked() throws SQLException {
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
        updatePieChart();
    }

    /**Validates fields on customer tab.
     * Checks for blank forms and alerts user if one is empty.
     *
     * @return true if all tests pass, false if any one fails.*/
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

    /**Deletes customer from DB.
     * Checks for selection, checks if selected customer has appointments, calls delete function in DAO.*/
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

    /**Checks to see if customer has appointments scheduled.
     * Gets appointments list, streams through it, returns true if any appointments customerId match this customerID.
     *
     * @param customerID the customer ID to check for.
     * @return true if customer has appointments, false if none match.
     * <p><b>
     *     A lambda expression is used here. Rather than getting the list of appointments and looking through them in a
     *     for loop and creating a boolean to return, a lambda expression does this all in one line with less variables.
     * </b></p>*/
    public boolean hasAppointments(int customerID) throws SQLException {
        return AppointmentDAO.getAllAppointments().stream().anyMatch(appointment -> customerID == appointment.getCustomerID());
    }

    /**Checks to see if an appointment conflicts with other appointments scheduled fot that customer.
     * Gets list of appointments with the same customer, checks if any appointments overlap with the current one passed in.
     *
     * @param appointment the appointment being checked before it is saved.
     * @return true if any conflicts are found, false if none are found.*/
    public boolean hasConflict(Appointment appointment) throws SQLException {
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
            if(startTimeConflict || startTimeConflict2 || endTimeConflict || endTimeConflict2) hasConflict = true;
        }
        return hasConflict;
    }

    /**Repopulates customerID list in combo box.
     * Combo box data can be changed in program, so this combo box refreshes every time it is clicked.
     * <p><b>
     *     A lambda expression is used here while streaming through the customer list from customerDAO.
     *     Each object in the list runs through the forEach(Consumer) and only its customerID attribute is added to the
     *     ObservableList that will populate customerIDCombo.  Using a lambda reduces the amount of code by doing that
     *     process in one line rather than with a traditional for loop and extra variables.
     * </b></p>*/
    public void refreshCustomerCombo() throws SQLException {
        customerIDList = FXCollections.observableArrayList();
        CustomerDAO.getAllCustomers().stream().forEach(customer -> customerIDList.add(Integer.toString(customer.getCustomerID())));
        customerIDCombo.setItems(customerIDList);
    }
}
