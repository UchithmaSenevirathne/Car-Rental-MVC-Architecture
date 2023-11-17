package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField fieldPassword;

    @FXML
    private TextField txtUserName;

    /*@FXML
    void btnSignUpLoginPgOnAction(ActionEvent event) throws IOException {

        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/RegisterForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setTitle("Register Form");
        stage.setScene(scene);
        stage.centerOnScreen();
    }*/

    @FXML
    void btnSignInOnAction(ActionEvent event) throws IOException {
        String userName = txtUserName.getText();
        String password = fieldPassword.getText();
        try {
            boolean isIn = UserModel.searchUser(userName, password);
            if (!isIn){
                new Alert(Alert.AlertType.WARNING,"Invalid UserName or Password").show();
                return;
            }else  {
                isAdmin(userName,password);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void isAdmin(String userName, String password) throws IOException {
        if(userName.equals("admin") && password.equals("1234")){
            Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"));

            Scene scene = new Scene(rootNode);
            Stage stage = (Stage) this.rootNode.getScene().getWindow();
            stage.setTitle("Dashboard Form");
            stage.setScene(scene);
            stage.centerOnScreen();
        }else {
            Parent rootNode = FXMLLoader.load(getClass().getResource("/view/DriverSchedule.fxml"));

            Scene scene = new Scene(rootNode);
            Stage stage = (Stage) this.rootNode.getScene().getWindow();
            stage.setTitle("Driver Schedule Form");
            stage.setScene(scene);
            stage.centerOnScreen();
        }
    }
}
