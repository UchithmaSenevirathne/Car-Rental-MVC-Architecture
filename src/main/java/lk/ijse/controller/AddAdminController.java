package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.Validation.Validate;
import lk.ijse.dto.UserDTO;
import lk.ijse.model.UserModel;

public class AddAdminController {
    @FXML
    public Button btnAdminFormBtn;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPwd;

    @FXML
    private TextField txtUName;

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String userName = txtUName.getText();
        String email = txtEmail.getText();
        String password = txtPwd.getText();

        var userDto = new UserDTO(userName, password, email, "ADM");

        var model = new UserModel();

        try {
            if(ValidateAdmin(userName,email,password)) {
                if (btnAdminFormBtn.getText().equals("UPDATE")) {
                    boolean isUpdate = model.updateAdmin(userDto);
                    if (isUpdate) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                        Parent rootNode = loader.load();

                        ConfirmationController confirmationController = loader.getController();

                        confirmationController.lblConfirm.setText("Admin updated successfully");

                        Scene scene = new Scene(rootNode);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.centerOnScreen();
                        stage.show();
                        clearFields();
                    }
                } else if (btnAdminFormBtn.getText().equals("SAVE")) {
                    boolean isSaved = model.saveAdmin(userDto);

                    if (isSaved) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                        Parent rootNode = loader.load();

                        ConfirmationController confirmationController = loader.getController();

                        confirmationController.lblConfirm.setText("Admin saved successfully");

                        Scene scene = new Scene(rootNode);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.centerOnScreen();
                        stage.show();
                        clearFields();
                    }
                }
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean ValidateAdmin(String userName, String email, String password) {
        if(!Validate.validation(userName, txtUName,"[A-Za-zA-Z]+")){
            return false;
        }
        if(!Validate.validation(email, txtEmail,"[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")){
            return false;
        }
        if(!Validate.validation(password, txtPwd,"(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}")){    //#001ADpw
            return false;
        }
        return true;
    }

    private void clearFields() {
        txtUName.setText("");
        txtEmail.setText("");
        txtPwd.setText("");
    }

    public void setAdminData(String userName, String email, String password) {
        txtUName.setText(userName);
        txtEmail.setText(email);
        txtPwd.setText(password);
    }
}
