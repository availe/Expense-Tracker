package JavaFX;

import Core.ExpenseRecord;
import Core.UserRecord;
import Database.ExpenseManager;
import Database.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML private Button addManager, addUser, removeManager, removeUser;
    @FXML private ImageView homeIcon, profileIcon, settingsIcon;
    @FXML private Label companyName;
    @FXML private Text hasPermissions;
    @FXML private TableView<UserRecord> table;
    @FXML private TableColumn<UserRecord, Integer> id, is_manager, is_root, has_approval;
    @FXML private TableColumn<UserRecord, String> first_name, last_name, email, created_on, last_login;
    private ObservableList<UserRecord> usersObservableList = FXCollections.observableArrayList();
    private UserRecord lastSelectedRow;
    private final List<Integer> primaryKeysList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeScenesListener();
        tableInit();
        loadTable();
    }

    private void tableInit() {
        try {
            List<UserRecord> usersTable = UserManager.getInstance().readUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        id.setCellValueFactory(new PropertyValueFactory<>("userId"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        first_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        created_on.setCellValueFactory(new PropertyValueFactory<>("createdOn"));
        last_login.setCellValueFactory(new PropertyValueFactory<>("lastLogin"));
        is_manager.setCellValueFactory(new PropertyValueFactory<>("isManager"));
        is_root.setCellValueFactory(new PropertyValueFactory<>("isRoot"));
        has_approval.setCellValueFactory(new PropertyValueFactory<>("hasApproval"));
        table.setItems(usersObservableList);
    }

    public void loadTable() {
        List<UserRecord> loadList = null;
        try {
            loadList = UserManager.getInstance().readUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        usersObservableList.addAll(loadList);
        for (UserRecord user : loadList) {
            primaryKeysList.add(user.getUserId());
        }
    }

    void changeScenesListener() {
        homeIcon.setOnMouseClicked(e-> {
            try {
                MainApplication.switchToMainScene();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        profileIcon.setOnMouseClicked(e-> {
            try {
                MainApplication.switchToProfileScene();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
