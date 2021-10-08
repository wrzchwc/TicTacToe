package com.company;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class GameController {
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
    @FXML
    private GridPane board;

    private boolean order = true;

    @FXML
    void initialize() throws NoSuchFieldException {
        nameX.setText(analyzePlayer(MenuController.getNames()[0]));
        nameO.setText(analyzePlayer(MenuController.getNames()[1]));

        var list = new ArrayList<Label>(List.of(board00, board01, board02, board10, board11, board12, board20, board21, board22));
        for (Label l : list) {
            l.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    l.setText(order ? "X" : "O");
                    order = !order;
                }
            });
        }

        var menu = new Menu();
        menu.run();
    }

    private String analyzePlayer(String name) {
        var levels = new ArrayList<String>(List.of("easy", "medium", "hard"));
        if (levels.contains(name)) {
            return "AI (" + name + ")";
        }
        return name;
    }


}
