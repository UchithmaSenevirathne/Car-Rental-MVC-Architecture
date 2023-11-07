package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.CustomerDto;
import lk.ijse.model.CarModel;
import lk.ijse.model.CustomerModel;

public class DeleteCarFormController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtCarBrand;

    @FXML
    private TextField txtCarNo;

    Stage stage;

    @FXML
    void btnCancelCarOnAction(ActionEvent event) {
        stage = (Stage) rootNode.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnDeleteCarOnAction(ActionEvent event) {
        String carNo = txtCarNo.getText();

        var model = new CarModel();

        try {
            boolean isDeleted = model.deleteCar(carNo);

            if (isDeleted) {
                //tableView.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "car deleted!").show();
                clearFields();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtCarNo.setText("");
        txtCarBrand.setText("");
    }

    @FXML
    void txtCarNoOnAction(ActionEvent event) {
        String carNo = txtCarNo.getText();

        var model = new CarModel();

        try{
            CarDto dto  = model.searchCar(carNo);

            if (dto != null) {
                fillfields(dto);
            }else{
                new Alert(Alert.AlertType.INFORMATION, "car not found!").show();
            }

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillfields(CarDto dto) {
        txtCarNo.setText(dto.getCarNo());
        txtCarBrand.setText(dto.getBrand());
    }

}
