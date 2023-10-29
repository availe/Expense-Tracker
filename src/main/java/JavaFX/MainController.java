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
        for (int i : primaryKeysList) {
            System.out.println(i);
        }

        table.getSelectionModel().selectedItemProperty().addListener((observableValue, record, newRecord) -> {
            lastSelectedRow = newRecord;
        });

        addExpense.setOnAction(e-> insertNewRow());
        deleteExpense.setOnAction(e-> deleteRow());

    }

    // expense table database logic
    public void writeToDatabase() {
        /* this might slow down performance

        if (!DatabaseUtil.getInstance().isDatabaseConnected()) {
            System.out.println("Database is not connected.");
            return;
        }
        System.out.println("Database is connected.");*/

        for (ExpenseRecord record : observableList) {
            if (!primaryKeysList.contains(record.getExpenseID())) {
                DatabaseUtil.getInstance().addRecord(record);
                System.out.println(record.getExpenseID());
            }
        }
    }

    public void loadDatabase() {
        List<ExpenseRecord> loadList = DatabaseUtil.getInstance().readRecords();
        observableList.addAll(loadList);
        for (ExpenseRecord record : loadList) {
            primaryKeysList.add(record.getExpenseID());
            System.out.println("Loaded expenseId: " + record.getExpenseID());
        }
    }

    // lists
    private final ObservableList<ExpenseRecord> observableList = FXCollections.observableArrayList();
    private final List<Integer> primaryKeysList = new ArrayList<>();
    private ExpenseRecord lastSelectedRow;


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

            Integer expenseId;
            if (primaryKeysList.isEmpty()) {
                expenseId = 0;
                System.out.println("PrimaryKeyList was empty");
            }
            else {
                System.out.println("PrimaryKeyList was NOT empty");
                System.out.println(primaryKeysList.size());
                expenseId = primaryKeysList.get(primaryKeysList.size()-1) + 1;
                System.out.println(expenseId);
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

    public void deleteRow() {
        if (lastSelectedRow == null) {
            return;
        }
        observableList.remove(lastSelectedRow);
        DatabaseUtil.getInstance().deleteRecord(lastSelectedRow);
        lastSelectedRow = null;
        table.refresh();
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
