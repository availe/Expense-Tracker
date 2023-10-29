package Core;

import Database.DatabaseUtil;
import javafx.application.Application;

public class Main {
    private static String pluginsPath = "src/main/java/plugins/";
    private static String fxmlPath = "/com/main.fxml";
    private static String databasePath = "src/main/resources/database/";
    private static String defaultDatabaseName = "database";
    public static void main(String[] args) {
        DatabaseUtil.getInstance().setDatabasePath(databasePath);
        DatabaseUtil.getInstance().setDatabaseName(defaultDatabaseName);
        DatabaseUtil.getInstance().setConnection(DatabaseUtil.getInstance().connectToDatabase());
        PluginManager.getInstance().loadPlugins(pluginsPath);
        PluginManager.getInstance().executePlugins();
        JavaFX.MainApplication.setFxmlPath(fxmlPath);
        Application.launch(JavaFX.MainApplication.class, args);    }
}