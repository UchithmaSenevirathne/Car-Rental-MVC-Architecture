package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.dto.UserDTO;
import lk.ijse.dto.tm.AdminTm;
import lk.ijse.model.UserModel;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AdminManageController {
    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private TableView<AdminTm> tableView;

    private Integer index;

    private final ObservableList<AdminTm> obList = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        loadAllAdmins();
    }

    private void setCellValueFactory(){
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
    }

    @FXML
    void SetData(MouseEvent event) {
        var model = new UserModel();

        index = tableView.getSelectionModel().getSelectedIndex();

        if(index <= -1){
            return;
        }

        String userName = colUserName.getCellData(index).toString();
        String email = colEmail.getCellData(index).toString();

        try {
            String password = model.getPassword(userName);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addAdmin.fxml"));

            Parent rootNode = loader.load();

            AddAdminController addAdminController = loader.getController();

            addAdminController.btnAdminFormBtn.setText("UPDATE");

            addAdminController.setAdminData(
                    userName,
                    email,
                    password
            );

            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Update Admin Form");
            stage.centerOnScreen();
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadAllAdmins(){
        obList.clear();

        var model = new UserModel();

        try {
            List<UserDTO> dtoList = model.getAllAdmins();

            for(UserDTO dto : dtoList){
                Button deleteButton = new Button("Delete");

                deleteButton.setStyle("-fx-background-color: white; -fx-text-fill: #d71010; -fx-font-weight: bold;");

                deleteButton.setOnAction(event -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        deleteAdmin(dto.getUserName());
                    }
                }); //*********** getId()
                obList.add(
                        new AdminTm(
                                dto.getUserName(),
                                dto.getEmail(),
                                deleteButton
                        )
                );
            }
            tableView.setItems(obList);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void deleteAdmin(String userName) {
        var model = new UserModel();

        try {
            boolean b = model.deleteAdmin(userName);

            if(b){
                loadAllAdmins();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Alert/Confirmation.fxml"));

                Parent rootNode = loader.load();

                ConfirmationController confirmationController = loader.getController();

                confirmationController.lblConfirm.setText("Admin deleted successfully");

                Scene scene = new Scene(rootNode);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.centerOnScreen();
                stage.show();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnADDAdminOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/addAdmin.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Admin Form");
        stage.centerOnScreen();
        stage.show();
    }
}