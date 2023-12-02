package JavaFX;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeIcon.setOnMouseClicked(e-> homeIconClick());
        profileIcon.setOnMouseClicked(e-> profileIconClick());
    }

    @FXML
    private Button addManager, addUser;

    @FXML
    private Label companyName;

    @FXML
    private TableColumn<?, ?> created_on;

    @FXML
    private TableColumn<?, ?> email;

    @FXML
    private TableColumn<?, ?> first_name;

    @FXML
    private Text hasPermissions;

    @FXML
    private ImageView homeIcon;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> is_manager;

    @FXML
    private TableColumn<?, ?> is_root;

    @FXML
    private TableColumn<?, ?> last_login;

    @FXML
    private TableColumn<?, ?> last_name;

    @FXML
    private ImageView profileIcon;

    @FXML
    private Button removeManager;

    @FXML
    private Button removeUser;

    @FXML
    private ImageView settingsIcon;

    // change scenes
    private void profileIconClick() {
        try {
            MainApplication.switchToProfileScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void homeIconClick() {
        try {
            MainApplication.switchToMainScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
