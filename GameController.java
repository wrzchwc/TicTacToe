package com.company;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    @FXML
    public Button nextX;
    @FXML
    public Button nextO;
    @FXML
    private Label nameX;
    @FXML
    private Label nameO;
    @FXML
    private Label board00;
    @FXML
    private Label board01;
    @FXML
    private Label board02;
    @FXML
    private Label board10;
    @FXML
    private Label board11;
    @FXML
    private Label board12;
    @FXML
    private Label board20;
    @FXML
    private Label board21;
    @FXML
    private Label board22;

    private boolean order = true;
    public static final ArrayList<String> LEVELS = new ArrayList<>(List.of("easy", "medium", "hard"));
    private ArrayList<Label> BOARD;
    private Game game;


    @FXML
    void initialize() {
        var names = MenuController.getNames();
        BOARD = new ArrayList<>(List.of(board00, board01, board02, board10, board11, board12, board20, board21, board22));
        nameX.setText(analyzePlayer(names[0], nextX, false));
        nameO.setText(analyzePlayer(names[1], nextO, true));

        var index = 0;
        for (Label label : BOARD) {
            int tmp = index;
            label.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                if (label.getText().equals("") && (order && !LEVELS.contains(names[0]) || !order && !LEVELS.contains(names[1])) && !game.isFinalState()) {
                    label.setText(order ? "X" : "O");
                    try {
                        game.moveUser(new int[]{tmp / 3, tmp % 3}, order);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (LEVELS.contains(names[1])) {
                            nextO.setDisable(false);
                        } else if (LEVELS.contains(names[0])) {
                            nextX.setDisable(false);
                        }
                        order = !order;
                    }
                }
            });
            index++;
        }
        game = new Game(MenuController.getSetup(names));

    }

    //"Next move" buttons become visible if they are needed
    private String analyzePlayer(String name, Button button, boolean disabled) {
        if (LEVELS.contains(name)) {
            button.setVisible(true);
            button.setDisable(disabled);
            return "AI (" + name + ")";
        }
        return name;
    }

    @FXML
    private void nextClicked() throws IOException {
        //O's turn
        if (nextX.isDisabled() || !nextX.isVisible()) {
            nextX.setDisable(false);
            nextO.setDisable(true);
            game.moveAI(order, game.getPlayerO());
            BOARD.get(game.getRecentMove()).setText(Game.O);
        }
        //X's turn
        else {
            nextO.setDisable(false);
            nextX.setDisable(true);
            game.moveAI(order, game.getPlayerX());
            BOARD.get(game.getRecentMove()).setText(Game.X);
        }
        order = !order;
        if (game.isFinalState()) {
            nextX.setDisable(true);
            nextO.setDisable(true);
        }
    }


}
