package Controller;

import DAO.DBConnection;
import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
/**This is the controller class for loginform(dot)fxml. */
public class LoginController implements Initializable{

    public Label topLabel;
    public TextField userField;
    public PasswordField passwordField;
    public Label userLabel;
    public Label passwordLabel;
    public Button loginButton;
    public Button cancelButton;
    public ResourceBundle rb = ResourceBundle.getBundle("Resources/lang", Locale.getDefault());

    /**This method sets up the login screen.
     * Sets text for all labels and buttons in english or french and creates a connection to the database.*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userLabel.setText(rb.getString("Username"));
        passwordLabel.setText(rb.getString("Password"));
        cancelButton.setText(rb.getString("Cancel"));
        loginButton.setText(rb.getString("Login"));
        ZoneId zoneID = ZoneId.systemDefault();
        String zID = zoneID.getId();
        topLabel.setText("Timezone: " + zID);

        try {
            DBConnection.makeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**This method changes the scene to the mainform.
     * It gets the stage from actionEvent and sets a new scene on it.*/
    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/mainform.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root, 1160, 638));
        stage.show();
    }

    /**This method exits the program when cancel is clicked.
     * Closes the database connection, gets the stage from actionEvent, and closes the stage.*/
    public void cancelClicked(ActionEvent actionEvent) throws Exception {
        DBConnection.closeConnection();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**This method is called when login button is clicked.
     * It verifies the username and password against the database, writes the login attempt to a log file, and calls
     * toMain() if login credentials are verified.*/
    public void loginClicked(ActionEvent actionEvent) throws SQLException, IOException {
        String userName = userField.getText();
        String password = passwordField.getText();
        for (int i = 0; i < UserDAO.getAllUsers().size(); i++) {
            if (UserDAO.getAllUsers().get(i).getUserName().equals(userName) && UserDAO.getAllUsers().get(i).getPassword().equals(password)) {
                String logEntry = userName + " logged in at " + LocalDateTime.now().toString();
                File loginRecord = new File("login_activity.txt");
                try {
                    if (!loginRecord.exists()) loginRecord.createNewFile();
                    FileOutputStream oStream = new FileOutputStream("login_activity.txt", true);
                    oStream.write((logEntry + "\n").getBytes(StandardCharsets.UTF_8));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MainController.setCurrentUser(UserDAO.getAllUsers().get(i));
                toMain(actionEvent);
                return;
            }
        }
        String logEntry = userName + " was denied access at " + LocalDateTime.now().toString();
        File loginRecord = new File("login_activity.txt");
        try {
            if (!loginRecord.exists()) loginRecord.createNewFile();
            FileOutputStream oStream = new FileOutputStream("login_activity.txt", true);
            oStream.write((logEntry + "\n").getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(rb.getString("Error"));
        alert.showAndWait();
    }
}
