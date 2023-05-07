package fxControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Manager;
import model.User;
import utils.DbUtils;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class LoginPage implements Initializable {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseWork");

    public void validate() throws IOException, SQLException, ClassNotFoundException {
        User user = DbUtils.validateUser(loginField.getText(),passwordField.getText());

        if(user!=null) {

            FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml")); //getting things
            Parent parent = fxmlLoader.load(); //loading things

            MainPage mainPage = fxmlLoader.getController();
            mainPage.setData(entityManagerFactory);
            mainPage.setInfo(user);
            mainPage.loadAll();

            Scene scene = new Scene(parent);
            Stage stage = (Stage) loginField.getScene().getWindow();
            stage.setTitle("Application");
            stage.setScene(scene);
            stage.show();

        }else{
            JOptionPane.showMessageDialog(null, "No such user", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
