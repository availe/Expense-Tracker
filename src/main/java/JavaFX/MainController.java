package JavaFX;

import Database.DatabaseUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

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

        loadValuesKeys();
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
            updateValues();
        } catch (Exception e) {
            System.out.println("Something went wrong while inserting new row");
            e.printStackTrace();
        }
    }

    public void deleteRow() {
        if (lastSelectedRow == null) {
            return;
        }
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        DatabaseUtil.getInstance().deleteRecord(lastSelectedRow);
        observableList.remove(lastSelectedRow);
        table.refresh();
        updateValues();
        if (selectedIndex == table.getItems().size() && table.getItems().size() > 0) {
            table.getSelectionModel().select(selectedIndex);
        } else {
            table.getSelectionModel().clearSelection();
            lastSelectedRow = null;
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

    // values and keys FXML

    @FXML
    private Label currDate;

    @FXML
    private Label keyl1;

    @FXML
    private Label keyl2;

    @FXML
    private Label keyl3;

    @FXML
    private Label keyl4;

    @FXML
    private Label keyr1;

    @FXML
    private Label keyr2;

    @FXML
    private Label keyr3;

    @FXML
    private Label keyr4;

    @FXML
    private Label valuel1;

    @FXML
    private Label valuel2;

    @FXML
    private Label valuel3;

    @FXML
    private Label valuel4;

    @FXML
    private Label valuer1;

    @FXML
    private Label valuer2;

    @FXML
    private Label valuer3;

    @FXML
    private Label valuer4;

    public void  loadValuesKeys() {
        LocalDate tempTime = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String insertDate = tempTime.format(dateFormat);
        currDate.setText(insertDate);

        keyl1.setText("Net revenue");
        keyl2.setText("Revenue");
        keyl3.setText("Costs");
        keyl4.setText("Estimated taxes");

        keyr1.setText("Stock price");
        keyr2.setText("Interest rate");
        keyr3.setText("Employee headcount");
        keyr4.setText("Department budget");

        valuel1.setText("$240,000");
        valuel2.setText("$300,000");
        System.out.println("Try this" + valuel2.getText());
        updateValues();
        valuel4.setText("$75,000");

        valuer1.setText("$240.45");
        valuer2.setText("$11.77%");
        valuer3.setText("$1,350");
        valuer4.setText("15,000");
    }

    public void updateValues() {
        double cost = 0;
        for (ExpenseRecord record : observableList) {
            cost += record.getAmount();
        }
        String insertCost = String.format("$%,.2f", cost);
        valuel3.setText(insertCost);
        System.out.println(valuel2.getText());
        String revenueTxt = valuel2.getText().replace("$","").replace(",","");
        System.out.println(revenueTxt);
        double revenue = Double.parseDouble(revenueTxt);
        double netIncome = revenue - cost;
        String insertNetIncome = String.format("%,.2f", netIncome);
        valuel1.setText(insertNetIncome);

        companyName.setText("blueStart");
    }

    // more FXML

    @FXML
    private Label info0;

    @FXML
    private Label companyName;

    @FXML
    private Label info1;

    @FXML
    private Label info2;

    @FXML
    private ImageView newsImage0;

    @FXML
    private ImageView newsImage1;

    @FXML
    private ImageView newsImage2;

    @FXML
    private ImageView notificationIcon;

    @FXML
    private ImageView profileIcon;

    @FXML
    private ImageView settingsIcon;

    @FXML
    private ImageView settingsPage;

    @FXML
    private Label title0;

    @FXML
    private Label title1;

    @FXML
    private Label title2;
}


