package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
                if (btnAdminFormBtn.getText().equals("UPDATE")) {
                    boolean isUpdate = model.updateAdmin(userDto);
                    if (isUpdate) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Admin updated!!").show();
                        clearFields();
                    }
                } else if (btnAdminFormBtn.getText().equals("SAVE")) {
                    boolean isSaved = model.saveAdmin(userDto);

                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Admin saved!").show();
                        clearFields();
                    }
                }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
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
