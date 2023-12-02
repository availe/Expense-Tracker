package JavaFX;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
}
