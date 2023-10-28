package JavaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class MainApplication extends Application {
    private static String fxmlPath = "Unchanged FXML Path";
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(fxmlPath);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setMaximized(true);

        TableController tableController = fxmlLoader.getController();

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // getters and setters
    public static void setFxmlPath(String fxmlPath) {
        MainApplication.fxmlPath = fxmlPath;
    }
}