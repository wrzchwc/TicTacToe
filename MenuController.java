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
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.company.Game.PLAYER_O;
import static com.company.Game.PLAYER_X;

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

        window.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icon.png"))));
        window.setScene(scene);
        window.setTitle(title);
        window.show();
    }

    public static HashMap<String, String> getSetup(String[] parameters) {
        for (String parameter : parameters) {
            if (!GameController.LEVELS.contains(parameter)) {
                parameter = "user";
            }
        }
        return new HashMap<>(Map.of(PLAYER_X, parameters[0], PLAYER_O, parameters[1]));
    }
}
