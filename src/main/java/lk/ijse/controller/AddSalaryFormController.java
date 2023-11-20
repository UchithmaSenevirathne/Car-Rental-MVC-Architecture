package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.SalaryDTO;
import lk.ijse.model.CarModel;
import lk.ijse.model.SalaryModel;

public class AddSalaryFormController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDrId;

    @FXML
    private TextField txtMonth;

    @FXML
    private TextField txtSalId;

    Stage stage;

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        stage = (Stage) rootNode.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String drSalId = txtSalId.getText();
        String drId = txtDrId.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        String month = txtMonth.getText();

        var dto = new SalaryDTO(drSalId, drId, amount, month);

        var model = new SalaryModel();

        try{
            boolean isSaved = model.saveSalary(dto);

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Salary Added").show();
                clearFields();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtSalId.setText("");
        txtDrId.setText("");
        txtAmount.setText("");
        txtMonth.setText("");
    }

    public void setSalaryData(String drId) {
        txtDrId.setText(drId);
    }
}
