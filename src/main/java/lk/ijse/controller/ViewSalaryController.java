package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dto.CarDto;
import lk.ijse.dto.SalaryDTO;
import lk.ijse.dto.tm.CarTm;
import lk.ijse.dto.tm.SalaryTm;
import lk.ijse.model.CarModel;
import lk.ijse.model.SalaryModel;

import java.util.List;

public class ViewSalaryController {
    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDrId;

    @FXML
    private TableColumn<?, ?> colMonth;

    @FXML
    private TableColumn<?, ?> colSalId;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<SalaryTm> tableView;

    public final ObservableList<SalaryTm> obListSal = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        loadAllSalary();
    }

    private void setCellValueFactory(){
        colSalId.setCellValueFactory(new PropertyValueFactory<>("drSalId"));
        colDrId.setCellValueFactory(new PropertyValueFactory<>("drId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
    }

    private void loadAllSalary() {
        obListSal.clear();

        var model = new SalaryModel();

        try {
            List<SalaryDTO> dtoList = model.getAllSalary();

            for (SalaryDTO dto : dtoList){
                obListSal.add(
                        new SalaryTm(
                                dto.getDrSalId(),
                                dto.getDrId(),
                                dto.getAmount(),
                                dto.getMonth()
                        )
                );
            }
            tableView.setItems(obListSal);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
