package JavaFX;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class MainApplication extends Application {
    private final static String fxmlMainPath = "/com/main.fxml";
    private final static String fxmlProfilePath = "/com/profile.fxml";
    private final static String fxmlSettingsPath = "/com/settings.fxml";
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        switchToMainScene();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // scene switchers
    protected static void switchSceneLogic(String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Expense Tracker");
        fxmlLoader.getController();
    }
    protected static void switchToMainScene() throws IOException {
        switchSceneLogic(fxmlMainPath);
    }
    protected static void switchToProfileScene() throws IOException {
        switchSceneLogic(fxmlProfilePath);
    }
    protected static void switchToSettingsScene() throws IOException {
        switchSceneLogic(fxmlSettingsPath);
    }
}