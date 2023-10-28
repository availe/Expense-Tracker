package JavaFX;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableController {
    @FXML
    private TableColumn<?, ?> amount;

    @FXML
    private TableColumn<?, ?> category;

    @FXML
    private TableColumn<?, ?> date;

    @FXML
    private TableColumn<?, ?> department;

    @FXML
    private TableColumn<?, ?> description;

    @FXML
    private TableColumn<?, ?> receipt;

    @FXML
    private TableView<?> table;
}