package Core;

import javafx.application.Application;

public class Main {
    private static String pluginsPath = "src/main/java/plugins/";
    private static String fxmlPath = "/com/main.fxml";
    public static void main(String[] args) {
        PluginManager.getInstance().loadPlugins(pluginsPath);
        PluginManager.getInstance().executePlugins();
        JavaFX.MainApplication.setFxmlPath(fxmlPath);
        Application.launch(JavaFX.MainApplication.class, args);    }
}