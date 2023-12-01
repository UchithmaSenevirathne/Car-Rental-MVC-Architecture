package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.model.UserModel;

public class ResetPasswordController {
    @FXML
    private AnchorPane subAnchorPane;

    @FXML
    private TextField txtConfirmNewPwd;

    @FXML
    private TextField txtNewPassword;

    String userName;

    @FXML
    void btnResetPwdOnAction(ActionEvent event) {
        String newPwd = txtNewPassword.getText();
        String confirmPwd = txtConfirmNewPwd.getText();

        var model = new UserModel();

        try{
            if(newPwd.equals(confirmPwd)){
                txtConfirmNewPwd.setStyle("-fx-background-color: white");
                boolean isChanged = model.changePwd(confirmPwd,userName);

                if(isChanged){
                    new Alert(Alert.AlertType.CONFIRMATION, "password changed successfully!").show();
                }
            }else{
                txtConfirmNewPwd.setStyle("-fx-background-color: rgba(255,0,0,0.3)");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setUserName(String uName) {
        userName = uName;
    }
}
