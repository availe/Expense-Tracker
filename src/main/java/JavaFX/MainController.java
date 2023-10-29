package JavaFX;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableInit();
    }

    // expense Table

    @FXML
    private TableColumn<ExpenseRecord, Double> amount;

    @FXML
    private TableColumn<ExpenseRecord, String> category;

    @FXML
    private TableColumn<ExpenseRecord, String> date;

    @FXML
    private TableColumn<ExpenseRecord, String> department;

    @FXML
    private TableColumn<ExpenseRecord, String> description;

    @FXML
    private TableColumn<ExpenseRecord, String> receipt;

    @FXML
    private TableView<ExpenseRecord> table;

    ObservableList<ExpenseRecord> list = FXCollections.observableArrayList(
            // amount, category, date, department, description, receipt
            new ExpenseRecord(20.0, "Lunch", "April", "Khols", "Big Mac")
    );

    public void tableInit() {
        amount.setCellValueFactory(new PropertyValueFactory<ExpenseRecord, Double>("amount"));
        category.setCellValueFactory(new PropertyValueFactory<ExpenseRecord, String>("category"));
        date.setCellValueFactory(new PropertyValueFactory<ExpenseRecord, String>("date"));
        department.setCellValueFactory(new PropertyValueFactory<ExpenseRecord, String>("department"));
        description.setCellValueFactory(new PropertyValueFactory<ExpenseRecord, String>("description"));

        table.setItems(list);
    }
}