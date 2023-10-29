package Core;

import Database.DatabaseUtil;
import javafx.application.Application;

public class Main {
    private static String pluginsPath = "src/main/java/plugins/";
    private static String fxmlPath = "/com/main.fxml";
    private static String databasePath = "/src/main/resources/database/";
    public static void main(String[] args) {
        System.out.println(databasePath);
        DatabaseUtil.getInstance().setDatabasePath(databasePath);
        //DatabaseUtil.getInstance().setDatabasePath(databasePath);
        PluginManager.getInstance().loadPlugins(pluginsPath);
        PluginManager.getInstance().executePlugins();
        JavaFX.MainApplication.setFxmlPath(fxmlPath);
        Application.launch(JavaFX.MainApplication.class, args);    }
}