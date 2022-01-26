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

public class LoginController implements Initializable{

    public Label topLabel;
    public TextField userField;
    public PasswordField passwordField;
    public Label userLabel;
    public Label passwordLabel;
    public Button loginButton;
    public Button cancelButton;
    public ResourceBundle rb = ResourceBundle.getBundle("Resources/lang", Locale.getDefault());
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userLabel.setText(rb.getString("Username"));
        passwordLabel.setText(rb.getString("Password"));
        cancelButton.setText(rb.getString("Cancel"));
        loginButton.setText(rb.getString("Login"));
        ZoneId zoneID = ZoneId.systemDefault();
        String zID = zoneID.getId();
        topLabel.setText("Zone ID " + zID);

        try {
            DBConnection.makeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/mainform.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointment Scheduler");
        stage.setScene(new Scene(root, 1160, 638));
        stage.show();
    }



    public void cancelClicked(ActionEvent actionEvent) throws Exception {
        DBConnection.closeConnection();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void loginClicked(ActionEvent actionEvent) throws SQLException, IOException {
        String userName = userField.getText();
        String password = passwordField.getText();
        for (int i = 0; i < UserDAO.getAllUsers().size(); i++) {
            if (UserDAO.getAllUsers().get(i).getUserName().equals(userName) && UserDAO.getAllUsers().get(i).getPassword().equals(password)) {
                String logEntry = userName + " logged in at " + LocalDateTime.now().toString();
                File loginRecord = new File("login_activity.txt");
                try {
                    if (!loginRecord.exists())
                    loginRecord.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream oStream = new FileOutputStream("login_activity.txt", true);
                    OutputStreamWriter osWriter = new OutputStreamWriter(oStream);
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
            if (!loginRecord.exists())
                loginRecord.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream oStream = new FileOutputStream("login_activity.txt", true);
            OutputStreamWriter osWriter = new OutputStreamWriter(oStream);
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
