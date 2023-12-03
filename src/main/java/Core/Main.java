package Core;

import Database.DatabaseUtil;
import javafx.application.Application;

public class Main {
    final private static String pluginsPath = "src/main/java/Plugins/";
    final private static String databasePath = "src/main/resources/database/";
    final private static String defaultDatabaseName = "project.db";
    public static void main(String[] args) {
        DatabaseUtil.getInstance().setDatabasePath(databasePath);
        DatabaseUtil.getInstance().setDatabaseName(defaultDatabaseName);
        DatabaseUtil.getInstance().connectToDatabase();
        Application.launch(JavaFX.MainApplication.class, args);
    }
}