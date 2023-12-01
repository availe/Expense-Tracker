package JavaFX;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class MainApplication extends Application {
    private static String fxmlMainPath = "/com/main.fxml";
    private static String fxmlProfilePath = "/com/profile.fxml";
    private static String fxmlSettingsPath = "/com/settings.fxml";
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        stage.setTitle("Expense Tracker");
        stage.setMaximized(true);
        switchToMainScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // scene switchers
    private static void switchSceneLogic(String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        Object controller = fxmlLoader.getController();
    }
    private static void switchToMainScene() throws IOException {
        switchSceneLogic(fxmlMainPath);
    }
    private static void switchToProfileScene() throws IOException {
        switchSceneLogic(fxmlMainPath);
    }
    private static void switchToSettingsScene() throws IOException {
        switchSceneLogic(fxmlSettingsPath);
    }
}