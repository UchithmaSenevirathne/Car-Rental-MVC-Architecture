package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class ConfirmationController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    public Label lblConfirm;

    public void initialize(){
        Duration duration = Duration.seconds(3);
        KeyFrame keyFrame = new KeyFrame(duration, event -> {

            anchorPane.getScene().getWindow().hide();
        });

        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }
}
