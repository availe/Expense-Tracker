package JavaFX;

import Core.CurrentSession;
import Core.Main;
import Database.UserManager;
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
    @FXML private Text company, createdOn, email, firstName, isManager, lastName;
    @FXML private Label companyName;
    @FXML private TextField confirmPassInput, newPassInput;
    @FXML private ImageView homeIcon, profileIcon, settingsIcon;
    @FXML private Button logoutBtn, changePasswordBtn;

    public void loadData() {
        firstName.setText("First Name: " + CurrentSession.getInstance().getCurrentUser().getFirstName());
        lastName.setText("Last Name: " + CurrentSession.getInstance().getCurrentUser().getLastName());
        email.setText("Email: " + CurrentSession.getInstance().getCurrentUser().getEmail());
        createdOn.setText("Created On: " + CurrentSession.getInstance().getCurrentUser().getCreatedOn().toString());
        company.setText("Company: blueStart");
        if (CurrentSession.getInstance().getCurrentUser().getIsManager()) {
            isManager.setText("Manger: Yes");
        } else {
            isManager.setText("Manager: No");
        }
    }

    public void changePassword() {
        if (newPassInput.getText().equals(confirmPassInput.getText())) {
            CurrentSession.getInstance().getCurrentUser().setPassHash(newPassInput.getText());
            UserManager.getInstance().updatePassword(CurrentSession.getInstance().getCurrentUser().getUserId(), newPassInput.getText());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        changePasswordBtn.setOnAction(e-> changePassword());
        homeIcon.setOnMouseClicked(e-> homeIconClick());
        settingsIcon.setOnMouseClicked(e-> settingsIconClick());
        logoutBtn.setOnAction(e-> {
            try {
                CurrentSession.getInstance().setCurrentUser(null);
                MainApplication.switchToLoginScene();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

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
