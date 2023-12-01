package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.dto.ScheduleDTO;
import lk.ijse.model.UserModel;
import lk.ijse.util.SendMail;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ForgetPasswordController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private AnchorPane subAnchorPane;

    @FXML
    private TextField txtUserName;

    int random;
    int OTP;

    @FXML
    void btnSendOTPOnAction(ActionEvent event) {
        String userName = txtUserName.getText();

        var model = new UserModel();
        try {
            boolean isvalidUserName = model.checkUserName(userName);

            if(isvalidUserName) {
                String email = model.getEmail(userName);

                random = new Random().nextInt(9000);
                OTP = 1000+random;

                System.out.println("" + OTP + "");

                SendMail.outMail(""+OTP+"", email, "Apex Auto Rental forget password OTP");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/VerifyCode.fxml"));

                AnchorPane verifyCode = loader.load();

                VerifyCodeController verifyCodeController = loader.getController();

                verifyCodeController.setOTP(OTP, userName);

                subAnchorPane.getChildren().setAll(verifyCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
