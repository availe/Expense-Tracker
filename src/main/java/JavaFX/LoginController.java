package JavaFX;

import Core.CurrentSession;
import Core.UserRecord;
import Database.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private TextField insert_confirm_password, insert_email, insert_last_name,
            insert_password, insert_first_name, login_email, login_password;
    @FXML private Button login_btn, signup_btn;

    void login() throws SQLException, IOException {
        if (!login_email.getText().isEmpty() && !login_password.getText().isEmpty()) {
            UserRecord user = UserManager.getInstance().authenticateUser(login_email.getText(), login_password.getText());
            login_email.setText("");
            login_password.setText("");
            if (user != null) {
                CurrentSession.getInstance().setCurrentUser(user);
                MainApplication.switchToMainScene();
            }
        }
    }

    void signUp() {
        if ((insert_password.getText().equals(insert_confirm_password.getText())) && (!insert_email.getText().isEmpty() && !insert_first_name.getText().isEmpty() && !insert_last_name.getText().isEmpty() && !insert_password.getText().isEmpty() && !insert_confirm_password.getText().isEmpty())) {
            UserRecord newUser = new UserRecord(null, insert_first_name.getText(), insert_last_name.getText(), insert_email.getText(), null, null, false, false, false, insert_password.getText());
            try {
                UserManager.getInstance().addUser(newUser);
                signup_btn.setStyle("-fx-background-color: #42f572");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            insert_password.setText("");
            insert_confirm_password.setText("");
            insert_first_name.setText("");
            insert_last_name.setText("");
            insert_email.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_btn.setOnAction(e-> {
            try {
                login();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        signup_btn.setOnAction(e-> signUp());
    }
}
