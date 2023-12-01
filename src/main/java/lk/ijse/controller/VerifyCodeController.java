package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class VerifyCodeController {
    @FXML
    private AnchorPane subAnchorPane;

    @FXML
    private TextField txtOTPfirst;

    @FXML
    private TextField txtOTPforth;

    @FXML
    private TextField txtOTPsecond;

    @FXML
    private TextField txtOTPthird;

    String OTP;

    String uName;

    @FXML
    void btnConfirmOnAction(ActionEvent event) {
        String firstNum = txtOTPfirst.getText();
        String secondNum = txtOTPsecond.getText();
        String thirdNum = txtOTPthird.getText();
        String forthNum = txtOTPforth.getText();

        String txtValue = firstNum+secondNum+thirdNum+forthNum;
        System.out.println("txt value....."+txtValue);

        try {
            if(txtValue.equals(OTP)){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ResetPassword.fxml"));
                AnchorPane resetPwd = loader.load();

                ResetPasswordController resetPasswordController = loader.getController();

                resetPasswordController.setUserName(uName);

                subAnchorPane.getChildren().setAll(resetPwd);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setOTP(int otp, String userName) {
        OTP = String.valueOf(otp);
        uName = userName;
    }
}
