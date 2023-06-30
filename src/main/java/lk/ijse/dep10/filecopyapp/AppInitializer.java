package lk.ijse.dep10.filecopyapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(new FXMLLoader(getClass().getResource("/view/CopyScene.fxml")).load()));
        primaryStage.setHeight(200);
        primaryStage.setResizable(false);
        primaryStage.setTitle("File/Directory Handling App");
        primaryStage.show();
        primaryStage.centerOnScreen();

    }
}
