package com.company;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static com.company.Game.*;
import static com.company.GameController.LEVELS;
import static com.company.MenuController.getNames;

public class Result {
    private static final String[] X3 = new String[]{X, X, X};
    private static final String[] O3 = new String[]{O, O, O};
    private static String gameResult;

    public static final String X_WINS = "X wins";
    public static final String O_WINS = "O wins";
    public static final String DRAW = "Draw";
    public static final String NOT_FINISHED = "Game not finished";

    public static String finalResult(String[][] board) {
        if (checkRows(board) != null) {
            return checkRows(board);
        } else if (checkColumns(board) != null) {
            return checkColumns(board);
        } else if (checkDiagonals(board) != null) {
            return checkDiagonals(board);
        } else if (checkOver(board) != null) {
            return checkOver(board);
        }
        gameResult = DRAW;
        return DRAW;
    }


    private static String checkRows(String[][] board) {
        for (String[] row : board) {
            if (Arrays.equals(row, X3)) {
                setGameResult(LEVELS.contains(getNames()[0]) ? X_WINS : getNames()[0] + " wins");
                return X_WINS;
            } else if (Arrays.equals(row, O3)) {
                setGameResult(LEVELS.contains(getNames()[1]) ? O_WINS : getNames()[1] + " wins");
                return O_WINS;
            }
        }
        return null;
    }

    private static String checkColumns(String[][] board) {
        for (String[] column : getColumns(board)) {
            if (Arrays.equals(column, X3)) {
                setGameResult(LEVELS.contains(getNames()[0]) ? X_WINS : getNames()[0] + " wins");
                return X_WINS;
            } else if (Arrays.equals(column, O3)) {
                setGameResult(LEVELS.contains(getNames()[1]) ? O_WINS : getNames()[1] + " wins");
                return O_WINS;
            }
        }
        return null;
    }

    public static ArrayList<String[]> getColumns(String[][] board) {
        var columns = new ArrayList<String[]>();
        columns.add(new String[]{board[0][0], board[1][0], board[2][0]});
        columns.add(new String[]{board[0][1], board[1][1], board[2][1]});
        columns.add(new String[]{board[0][2], board[1][2], board[2][2]});
        return columns;
    }

    public static ArrayList<String[]> getDiagonals(String[][] board) {
        var diagonals = new ArrayList<String[]>();
        diagonals.add(new String[]{board[0][0], board[1][1], board[2][2]});
        diagonals.add(new String[]{board[0][2], board[1][1], board[2][0]});
        return diagonals;
    }

    private static String checkDiagonals(String[][] board) {
        var diagonals = getDiagonals(board);
        if (Arrays.equals(diagonals.get(0), X3) || Arrays.equals(diagonals.get(1), X3)) {
            setGameResult(LEVELS.contains(getNames()[0]) ? X_WINS : getNames()[0]+" wins");
            return X_WINS;
        } else if (Arrays.equals(diagonals.get(0), O3) || Arrays.equals(diagonals.get(1), O3)) {
            setGameResult(LEVELS.contains(getNames()[1]) ? O_WINS : getNames()[1]+" wins");
            return O_WINS;
        }
        return null;
    }

    private static String checkOver(String[][] board) {
        for (String[] row : board) {
            for (String item : row) {
                if (item.equals(EMPTY)) {
                    return NOT_FINISHED;
                }
            }
        }
        return null;
    }

    public static void displayResult() throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(MenuController.class.getResource("fxml/result.fxml")));
        Scene scene = new Scene(view);
        Stage window = new Stage();

        window.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("images/icon.png"))));
        window.setScene(scene);
        window.setTitle("Game Result");
        window.setIconified(false);
        window.setResizable(false);
        window.show();
    }


    public static String getGameResult() {
        return gameResult;
    }

    private static void setGameResult(String gameResult) {
        Result.gameResult = gameResult;
    }
}
