package JavaFX;

import Database.DatabaseUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addInputToComboBox();
        tableInit();
        loadDatabase();
        addExpense.setOnAction(e-> insertNewRow());
    }

    // expense table database logic
    public void writeToDatabase() {
        DatabaseUtil.getInstance().connectToDatabase();
        if (!DatabaseUtil.getInstance().isDatabaseConnected()) {
            System.out.println("Database is not connected.");
            return;
        }
        System.out.println("Database is connected.");

        for (ExpenseRecord record : observableList) {
            if (!primaryKeysList.contains(record.getExpenseID())) {
                DatabaseUtil.getInstance().addRecord(record);
            }
        }
    }

    public void loadDatabase() {
        List<ExpenseRecord> loadList = DatabaseUtil.getInstance().readRecords();
        observableList.addAll(loadList);
        for (ExpenseRecord record : loadList) {
            primaryKeysList.add(record.getExpenseID());
        }
    }

    // lists
    ObservableList<ExpenseRecord> observableList = FXCollections.observableArrayList();
    List<Integer> primaryKeysList = new ArrayList<>();


    public void tableInit() {
        amount.setCellValueFactory(new PropertyValueFactory<ExpenseRecord, Double>("amount"));
        category.setCellValueFactory(new PropertyValueFactory<ExpenseRecord, String>("category"));
        date.setCellValueFactory(new PropertyValueFactory<ExpenseRecord, String>("date"));
        department.setCellValueFactory(new PropertyValueFactory<ExpenseRecord, String>("department"));
        description.setCellValueFactory(new PropertyValueFactory<ExpenseRecord, String>("description"));

        table.setItems(observableList);
    }

    // add a new row
    public void insertNewRow() {
        // retrieve from our inserts
        if (insertAmount.getText() == null || insertCategory.getText() == null || insertDate.getValue() == null || insertDepartment.getValue() == null || insertDesc.getText() == null) {
            System.out.println("Cannot insert with null values!");
            return;
        }
        try {
            Double amount = Double.valueOf(insertAmount.getText());
            String category = insertCategory.getText();
            String department = insertDepartment.getValue();
            String desc = insertDesc.getText();

            // when we use Scenebuilder's datepicker, this converts localDate (similar to Calendar in Kotlin) to a String
            LocalDate tempDate = insertDate.getValue();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = tempDate.format(dateFormat);

            int expenseId;
            if (primaryKeysList.isEmpty()) {
                expenseId = 0;
            }
            else {
                expenseId = 55; // this is a placeholder until it can be debugged how to dynamically allocate ids
            }

            ExpenseRecord record = new ExpenseRecord(expenseId, amount, category, date, department, desc);
            observableList.add(record);
            writeToDatabase();
            primaryKeysList.add(expenseId);
        } catch (Exception e) {
            System.out.println("Something went wrong while inserting new row");
            e.printStackTrace();
        }
    }

    // combobox categories
    public void addInputToComboBox() {
        insertDepartment.setItems(FXCollections.observableArrayList("Engineering", "Marketing", "Information Technology", "Human Resources", "Legal", "Customer Support").sorted());
    }

    // FXML variables

    @FXML
    private TextField insertAmount;

    @FXML
    private TextField insertCategory;

    @FXML
    private DatePicker insertDate;

    @FXML
    private ComboBox<String> insertDepartment;

    @FXML
    private TextField insertDesc;

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
    private TableView<ExpenseRecord> table;

    @FXML
    private Button addExpense;

    @FXML
    private Button deleteExpense;
}
