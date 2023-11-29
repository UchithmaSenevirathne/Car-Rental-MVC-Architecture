package lk.ijse.Validation;

import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class Validate{

    public static boolean validation(String detail, TextField txtDetail, String regex) {
        if(!Pattern.matches(regex, detail)){
            txtDetail.setStyle("-fx-background-color: rgba(255,0,0,0.2)");
            return false;
        }
        txtDetail.setStyle(null);
        return true;
    }

}
