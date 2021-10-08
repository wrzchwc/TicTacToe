package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MenuController {

    @FXML
    private ChoiceBox<String> levelX;
    @FXML
    private TextField nameX;
    @FXML
    private ChoiceBox<String> levelO;
    @FXML
    private TextField nameO;
    @FXML
    private Button play;
    @FXML
    ObservableList<String> levels = FXCollections.observableArrayList(List.of("easy", "medium", "hard"));

    private boolean visibleNameO = false;
    private boolean disableLevelO = false;
    private boolean visibleNameX = false;
    private boolean disableLevelX = false;
    private static final String[] names = new String[2];

    @FXML
    void initialize() {
        levelX.setItems(levels);
        levelO.setItems(levels);
        nameX.setText(null);
        nameO.setText(null);
    }

    @FXML
    public void userXClicked() {
        visibleNameX = !visibleNameX;
        disableLevelX = !disableLevelX;
        nameX.setVisible(visibleNameX);
        levelX.setDisable(disableLevelX);
        levelX.setValue(null);
        nameX.setText(null);
    }

    @FXML
    public void userOClicked() {
        visibleNameO = !visibleNameO;
        disableLevelO = !disableLevelO;
        nameO.setVisible(visibleNameO);
        levelO.setDisable(disableLevelO);
        levelO.setValue(null);
        nameO.setText(null);
    }

    @FXML
    public void playGame() throws IOException {
        names[0] = nameX.getText() == null ? levelX.getValue() : nameX.getText();
        names[1] = nameO.getText() == null ? levelO.getValue() : nameO.getText();
        changeScene("fxml/game.fxml", "Tic-Tac-Toe with AI");
    }

    public static String[] getNames() {
        return names;
    }


    public static void changeScene(String templateName, String title) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource(templateName)));
        Scene scene = new Scene(view);
        Stage window = new Stage();

        window.setScene(scene);
        window.setTitle(title);
        window.show();
    }
}
