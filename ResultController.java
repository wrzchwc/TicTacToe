package com.company;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ResultController {

    @FXML
    private Label result;
    @FXML
    private Button ok;

    @FXML
    void initialize() {
        result.setText(Result.getGameResult() + "!");
    }

    @FXML
    public void back() {
        Stage stage = (Stage) ok.getScene().getWindow();
        stage.close();
    }


}
