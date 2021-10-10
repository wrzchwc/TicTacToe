package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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
    public ColorPicker colorX;
    @FXML
    public ColorPicker colorO;
    @FXML
    public Label title;
    @FXML
    public Label left_label;
    @FXML
    public Label right_label;
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
    private boolean visibleColorX = false;
    private boolean visibleColorO = false;
    private static final String[] names = new String[2];
    private static final Color[] colors = new Color[2];

    @FXML
    void initialize() {
        levelX.setItems(levels);
        levelO.setItems(levels);
        levelX.setValue(levels.get(0));
        levelO.setValue(levels.get(0));
        colorX.setValue(Color.WHITE);
        colorO.setValue(Color.WHITE);
    }

    @FXML
    public void userXClicked() {
        visibleNameX = !visibleNameX;
        disableLevelX = !disableLevelX;
        visibleColorX = !visibleColorX;
        nameX.setVisible(visibleNameX);
        levelX.setDisable(disableLevelX);
        colorX.setVisible(visibleColorX);

    }

    @FXML
    public void userOClicked() {
        visibleNameO = !visibleNameO;
        disableLevelO = !disableLevelO;
        visibleColorO = !visibleColorO;
        nameO.setVisible(visibleNameO);
        levelO.setDisable(disableLevelO);
        colorO.setVisible(visibleColorO);
    }

    @FXML
    public void playGame() throws IOException {
        names[0] = visibleNameX ? nameX.getText() : levelX.getValue();
        names[1] = visibleNameO ? nameO.getText() : levelO.getValue();


        colors[0] = colorX.getValue();
        colors[1] = colorO.getValue();
        changeScene("fxml/game.fxml", "Tic-Tac-Toe with AI");

    }


    public static String[] getNames() {
        return names;
    }


    public static void changeScene(String templateName, String title) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource(templateName)));
        Scene scene = new Scene(view);
        Stage window = new Stage();

        window.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/icon.png"))));
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

    public static Color[] getColors() {
        return colors;
    }

}
