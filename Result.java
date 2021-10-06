package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;

import static tictactoe.Game.*;

public class Result {
    private static final String[] X3 = new String[]{X, X, X};
    private static final String[] O3 = new String[]{O, O, O};

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
        return DRAW;
    }


    private static String checkRows(String[][] board) {
        for (String[] row : board) {
            if (Arrays.equals(row, X3)) {
                return X_WINS;
            } else if (Arrays.equals(row, O3)) {
                return O_WINS;
            }
        }
        return null;
    }

    private static String checkColumns(String[][] board) {
        for (String[] column : getColumns(board)) {
            if (Arrays.equals(column, X3)) {
                return X_WINS;
            } else if (Arrays.equals(column, O3)) {
                return O_WINS;
            }
        }
        return null;
    }

    public static ArrayList<String[]> getColumns(String[][] board) {
        var columns = new ArrayList<String[]>();
        columns.add(new String[]{board[0][0], board[1][0],board[2][0]});
        columns.add(new String[]{board[0][1], board[1][1],board[2][1]});
        columns.add(new String[]{board[0][2], board[1][2],board[2][2]});
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
            return X_WINS;
        } else if (Arrays.equals(diagonals.get(0), O3) || Arrays.equals(diagonals.get(1), O3)) {
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
}
