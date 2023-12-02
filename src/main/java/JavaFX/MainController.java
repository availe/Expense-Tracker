package JavaFX;

import Database.DatabaseUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
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

        table.getSelectionModel().selectedItemProperty().addListener((observableValue, record, newRecord) -> lastSelectedRow = newRecord);

        loadValuesKeys();
        addExpense.setOnAction(e-> insertNewRow());
        deleteExpense.setOnAction(e-> deleteRow());
        profileIcon.setOnMouseClicked(e-> profileIconClick());
        settingsIcon.setOnMouseClicked(e-> setSettingsIconClick());
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
    private final ObservableList<ExpenseRecord> observableList = FXCollections.observableArrayList();
    private final List<Integer> primaryKeysList = new ArrayList<>();
    private ExpenseRecord lastSelectedRow;


    public void tableInit() {
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.setItems(observableList);
    }

    // add a new row
    public void insertNewRow() {
        // retrieve from our inserts
        if (insertAmount.getText() == null || insertCategory.getText() == null || insertDate.getValue() == null || insertDepartment.getValue() == null || insertDesc.getText() == null) {
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
                expenseId = primaryKeysList.get(primaryKeysList.size()-1) + 1;
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
        updateValues();
        if (selectedIndex == table.getItems().size() && !table.getItems().isEmpty()) {
            table.getSelectionModel().select(selectedIndex-1);
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
    private TextField insertAmount, insertCategory, insertDesc;

    @FXML
    private DatePicker insertDate;

    @FXML
    private ComboBox<String> insertDepartment;

    @FXML
    private TableColumn<ExpenseRecord, Double> amount;

    @FXML
    private TableColumn<ExpenseRecord, String> category, date, department, description;

    @FXML
    private TableView<ExpenseRecord> table;

    @FXML
    private Button addExpense, deleteExpense;

    // values and keys FXML

    @FXML
    private Label currDate, keyl1, keyl2, keyl3, keyl4, keyr1, keyr2, keyr3, keyr4,
            valuel1, valuel2, valuel3, valuel4, valuer1, valuer2, valuer3, valuer4;

    public void  loadValuesKeys() {
        LocalDate tempTime = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String insertDate = tempTime.format(dateFormat);
        currDate.setText(insertDate);

        keyl1.setText("Net Income");
        keyl2.setText("Revenue");
        keyl3.setText("Costs");
        keyl4.setText("Estimated taxes");

        keyr1.setText("Stock price");
        keyr2.setText("Interest rate");
        keyr3.setText("Employee headcount");
        keyr4.setText("Department budget");

        valuel1.setText("$240,000");
        valuel2.setText("$300,000");
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
        String revenueTxt = valuel2.getText().replace("$","").replace(",","");
        double revenue = Double.parseDouble(revenueTxt);
        double netIncome = revenue - cost;
        String insertNetIncome = String.format("%,.2f", netIncome);
        valuel1.setText(insertNetIncome);

        companyName.setText("blueStart");
    }

    // more FXML

    @FXML
    private Label info0, companyName, info1, info2, title0, title1, title2;

    @FXML
    private ImageView newsImage0, newsImage1, newsImage2, profileIcon, settingsIcon, homeIcon;

    // change scenes
    private void profileIconClick() {
        try {
            MainApplication.switchToProfileScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setSettingsIconClick() {
        try {
            MainApplication.switchToSettingsScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}


