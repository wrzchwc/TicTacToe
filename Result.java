package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;

public class Result {
    private static final String[] xWins = new String[]{"X", "X", "X"};
    private static final String[] oWins = new String[]{"O", "O", "O"};

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
            if (Arrays.equals(row, xWins)) {
                return "X wins";
            } else if (Arrays.equals(row, oWins)) {
                return "O wins";
            }
        }
        return null;
    }

    private static String checkColumns(String[][] board) {
        var columns = new ArrayList<String[]>();
        for (int i = 0; i < 3; i++) {
            String[] tmp = new String[3];
            for (int j = 0; j < 3; j++) {
                tmp[j] = board[j][i];
            }
            columns.add(tmp);
        }
        for (String[] column : columns) {
            if (Arrays.equals(column, xWins)) {
                return "X wins";
            } else if (Arrays.equals(column, oWins)) {
                return "O wins";
            }
        }
        return null;
    }

    private static String checkDiagonals(String[][] board) {
        String[] leftDownDiagonal = new String[]{board[0][0], board[1][1], board[2][2]};
        String[] rightDownDiagonal = new String[]{board[0][2], board[1][1], board[2][0]};

        if (Arrays.equals(leftDownDiagonal, xWins) || Arrays.equals(rightDownDiagonal, xWins)) {
            return "X wins";
        } else if (Arrays.equals(leftDownDiagonal, oWins) || Arrays.equals(rightDownDiagonal, oWins)) {
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
