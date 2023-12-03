module com.example.expensetracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;


    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    exports JavaFX;
    exports Database;
    opens JavaFX to javafx.fxml;
    exports Core;
    opens Core to javafx.fxml;
}