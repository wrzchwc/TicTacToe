package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;

public class Result {
    private static final String[] X = new String[]{"X", "X", "X"};
    private static final String[] O = new String[]{"O", "O", "O"};

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
        return "Draw";
    }


    private static String checkRows(String[][] board) {
        for (String[] row : board) {
            if (Arrays.equals(row, X)) {
                return "X wins";
            } else if (Arrays.equals(row, O)) {
                return "O wins";
            }
        }
        return null;
    }

    private static String checkColumns(String[][] board) {
        for (String[] column : getColumns(board)) {
            if (Arrays.equals(column, X)) {
                return "X wins";
            } else if (Arrays.equals(column, O)) {
                return "O wins";
            }
        }
        return null;
    }

    public static ArrayList<String[]> getColumns(String[][] board) {
        var columns = new ArrayList<String[]>();
        for (int i = 0; i < 3; i++) {
            String[] tmp = new String[3];
            for (int j = 0; j < 3; j++) {
                tmp[j] = board[j][i];
            }
            columns.add(tmp);
        }
        return columns;
    }

    private static String checkDiagonals(String[][] board) {
        String[] leftDownDiagonal = new String[]{board[0][0], board[1][1], board[2][2]};
        String[] rightDownDiagonal = new String[]{board[0][2], board[1][1], board[2][0]};

        if (Arrays.equals(leftDownDiagonal, X) || Arrays.equals(rightDownDiagonal, X)) {
            return "X wins";
        } else if (Arrays.equals(leftDownDiagonal, O) || Arrays.equals(rightDownDiagonal, O)) {
            return "O wins";
        }
        return null;
    }

    private static String checkOver(String[][] board) {
        for (String[] row : board) {
            for (String item : row) {
                if (item.equals(" ")) {
                    return "Game not finished";
                }
            }
        }
        return null;
    }
}
