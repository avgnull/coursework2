package fxControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Comment;
import model.Manager;
import model.User;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class ForumPage {

    public TextField commentTextField;
    public VBox commentBox;
    private User loggedUser;
    public void setInfo(User user) {
        this.loggedUser = user;
    }

    public void goToMain(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml")); //getting things
        Parent parent = fxmlLoader.load(); //loading things

        MainPage mainPage = fxmlLoader.getController();
        mainPage.setInfo(loggedUser);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) commentBox.getScene().getWindow();
        stage.setTitle("Application");
        stage.setScene(scene);
        stage.show();
    }

    public void submitComment(ActionEvent actionEvent) {
        String commentText = commentTextField.getText();
        if (!commentText.isEmpty()) {
            Comment comment = new Comment(commentText, loggedUser);
            commentTextField.clear();
        }
        else{
            JOptionPane.showMessageDialog(null, "You need to write something to submit comment", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
