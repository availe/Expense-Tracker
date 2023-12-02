package JavaFX;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeIcon.setOnMouseClicked(e-> homeIconClick());
        settingsIcon.setOnMouseClicked(e-> settingsIconClick());
    }

    @FXML
    private Text company, createdOn, email, firstName, isManager, lastName;
    @FXML
    private Label companyName;
    @FXML
    private TextField confirmPassInput, newPassInput;
    @FXML
    private ImageView homeIcon, profileIcon, settingsIcon;
    @FXML
    private Button logoutBtn;

    // change scenes
    private void homeIconClick() {
        try {
            MainApplication.switchToMainScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void settingsIconClick() {
        try {
            MainApplication.switchToSettingsScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
