package com.company;

import java.io.IOException;
import java.util.*;

import static com.company.Result.*;

public class Game {
    private final String[][] board;
    private final String playerX;
    private final String playerO;
    private Integer recentMove;
    private boolean finalState;

    public static final String EMPTY = " ";
    public static final String X = "X";
    public static final String O = "O";
    public static final String PLAYER_X = "playerX";
    public static final String PLAYER_O = "playerO";

    public Game(HashMap<String, String> parameters) {
        this.board = new String[3][3];
        this.playerX = parameters.get(PLAYER_X);
        this.playerO = parameters.get(PLAYER_O);
        this.recentMove = null;
        this.finalState = false;
        for (String[] row : board) {
            Arrays.fill(row, EMPTY);
        }
    }

    private void printBoard() {
        printPadding();
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++)
                System.out.print(board[i][j] + EMPTY);
            System.out.println("|");
        }
        printPadding();
    }

    private void printPadding() {
        System.out.println(String.format("%1$" + 9 + "s", "").replace(' ', '-'));
    }

    public void start() {
        var order = true;
        while (finalResult(board).equals(NOT_FINISHED)) {
            printBoard();
//            if (order) {
//                if (playerX.equals("user")) {
//                    while (!moveUser(getCoordinates(), true)) {
//                        cellOccupiedWarning();
//                    }
//                } else {
//                    moveAI(true, playerX);
//                }
//            } else {
//                if (playerO.equals("user")) {
//                    while (!moveUser(getCoordinates(), false)) {
//                        cellOccupiedWarning();
//                    }
//                } else {
//                    moveAI(false, playerO);
//                }
//            }
            order = !order;
        }
        printBoard();
        System.out.println(finalResult(board));
    }

    void cellOccupiedWarning() {
        System.out.println("This cell is occupied! Choose another one!");
    }

    public void moveAI(boolean order, String difficultyLevel) throws IOException {
        if (difficultyLevel.equals("hard")) {
            hardMove(order);
        } else if (difficultyLevel.equals("medium")) {
            mediumMove(order);
        } else {
            easyMove(order);
        }
        finalState();
    }

    public void moveUser(int[] coordinates, boolean playerX) throws IOException {
        var row = coordinates[0];
        var column = coordinates[1];
        if (board[row][column].equals(EMPTY)) {
            board[row][column] = playerX ? X : O;
        }
        setRecentMove(row, column);
        finalState();
    }

    String move(boolean order) {
        return order ? X : O;
    }

    private void easyMove(boolean order) {
        var random = new Random();
        while (true) {
            var row = random.nextInt(3);
            var column = random.nextInt(3);
            if (board[row][column].equals(EMPTY)) {
                board[row][column] = move(order);
                setRecentMove(row, column);
                break;
            }
        }
    }

    private void mediumMove(boolean order) {
        if (!winOrBlockRow(order))
            if (!winOrBlockColumn(order))
                if (!winOrBlockDiagonal(order))
                    easyMove(order);
    }

    private boolean winOrBlockRow(boolean order) {
        var row = 0;
        for (String[] b : board) {
            if ((countStrings(b, X) == 2 || countStrings(b, O) == 2) && countStrings(b, EMPTY) == 1) {
                var column = convertStrings(b).indexOf(EMPTY);
                b[column] = move(order);
                setRecentMove(row, column);
                return true;
            }
            row++;
        }
        return false;
    }

    private int countStrings(String[] array, String string) {
        var number = 0;
        for (String a : array) {
            if (a.equals(string)) {
                number++;
            }
        }
        return number;
    }

    private boolean winOrBlockColumn(boolean order) {
        var variable = getColumns(board);
        for (int column = 0; column < 3; column++) {
            var c = variable.get(column);
            var tmp = convertStrings(c);
            if ((countStrings(c, X) == 2 || countStrings(c, O) == 2) && countStrings(c, EMPTY) == 1) {
                var row = tmp.indexOf(EMPTY);
                board[row][column] = move(order);
                setRecentMove(row, column);
                return true;
            }
        }
        return false;
    }

    private boolean winOrBlockDiagonal(boolean order) {
        var diagonals = getDiagonals(board);
        //2 iterations because there are only 2 diagonals
        for (int i = 0; i < 2; i++) {
            var tmp = analyzeDiagonal(diagonals.get(i));
            if (tmp != null) {
                var column = 0;
                if (i == 0 || tmp == 1) {
                    board[tmp][tmp] = move(order);
                    column = tmp;
                } else {
                    if (tmp == 0) {
                        board[tmp][tmp + 2] = move(order);
                        column = tmp + 2;
                    } else if (tmp == 2) {
                        board[tmp][tmp - 2] = move(order);
                        column = 0;
                    }
                }
                setRecentMove(tmp, column);
                return true;
            }
        }
        return false;
    }

    private Integer analyzeDiagonal(String[] diagonal) {
        if ((countStrings(diagonal, X) == 2 || countStrings(diagonal, O) == 2) && countStrings(diagonal, EMPTY) == 1) {
            return convertStrings(diagonal).indexOf(EMPTY);
        }
        return null;
    }

    private int minMax(String[][] board, boolean order, ArrayList<int[]> cells, int depth) {
        int[] decisions = new int[cells.size()];
        var decisionID = 0;

        for (int[] cell : cells) {
            board[cell[0]][cell[1]] = move(order);
            switch (finalResult(board)) {
                case NOT_FINISHED -> decisions[decisionID++] = minMax(board, !order, empty(board), depth);
                case X_WINS -> decisions[decisionID++] = 1;
                case O_WINS -> decisions[decisionID++] = -1;
                case DRAW -> decisions[decisionID++] = 0;
            }
            board[cell[0]][cell[1]] = EMPTY;
        }

        if (decisionID == depth) {
            return indexOfDecision(convertInt(decisions), order);
        }
        return order ? Arrays.stream(decisions).max().getAsInt() : Arrays.stream(decisions).min().getAsInt();

    }

    private int indexOfDecision(ArrayList<Integer> listOfDecisions, boolean order) {
        if (order && listOfDecisions.contains(1)) {
            return listOfDecisions.indexOf(1);
        } else if (!order && listOfDecisions.contains(-1)) {
            return listOfDecisions.indexOf(-1);
        } else if (listOfDecisions.contains(0)) {
            return listOfDecisions.indexOf(0);
        } else if (order && listOfDecisions.contains(-1)) {
            return listOfDecisions.indexOf(-1);
        } else if (!order && listOfDecisions.contains(1)) {
            return listOfDecisions.indexOf(1);
        }
        return 0;
    }

    private ArrayList<Integer> convertInt(int[] array) {
        var list = new ArrayList<Integer>();
        for (Integer a : array) {
            list.add(a);
        }
        return list;
    }

    private ArrayList<String> convertStrings(String[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    private ArrayList<int[]> empty(String[][] board) {
        var list = new ArrayList<int[]>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals(EMPTY)) {
                    list.add(new int[]{i, j});
                }
            }
        }
        return list;
    }

    private void hardMove(boolean order) {
        var cells = empty(board);
        var tmp = minMax(board, order, cells, cells.size());
        board[cells.get(tmp)[0]][cells.get(tmp)[1]] = move(order);
        setRecentMove(cells.get(tmp)[0], cells.get(tmp)[1]);
    }

    public String getPlayerX() {
        return playerX;
    }

    public String getPlayerO() {
        return playerO;
    }

    private void setRecentMove(int row, int column) {
        this.recentMove = 3 * row + column;
    }

    public Integer getRecentMove() {
        return recentMove;
    }

    public boolean isFinalState() {
        return finalState;
    }

    private void finalState() throws IOException {
        if (!finalResult(board).equals(NOT_FINISHED)) {
            finalState = true;
            Result.displayResult();
        }
    }
}


