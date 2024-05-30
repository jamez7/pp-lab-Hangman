package com.wisielec;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Hangman extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Hangman.class.getResource("wisielec.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Wisielec");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {

        launch();
    }
}