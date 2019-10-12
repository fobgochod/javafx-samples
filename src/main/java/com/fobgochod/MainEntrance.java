package com.fobgochod;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainEntrance extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));

        Scene scene = new Scene(root, 600, 300);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("sample.css").toExternalForm());

        primaryStage.setTitle("JavaFX Tool");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
