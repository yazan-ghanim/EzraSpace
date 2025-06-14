package com.example.ezraspace_m;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        URL fxmlUrl = getClass().getResource("/view/LoginView.fxml");
        System.out.println("FXML path: " + fxmlUrl);


        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Scene scene = new Scene(loader.load());

        stage.setTitle("Ezra Space - Login");
        URL iconUrl = getClass().getResource("/images/logo.jpeg");
        if (iconUrl != null) {
            stage.getIcons().add(new Image(iconUrl.toExternalForm()));
        } else {
            System.err.println("Icon image not found at /images/logo.jpeg");
        }
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
