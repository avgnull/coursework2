import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class StartApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApp.class.getResource("view/login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Application");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        /*
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                // handle the exception
                System.out.println("An uncaught exception occurred in thread " + t.getName() + ": " + e.getMessage());
                JOptionPane.showMessageDialog(null, "An error has occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        */
        launch();
    }
}