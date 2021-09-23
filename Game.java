package tictactoe;

import java.util.*;

import static tictactoe.Result.finalResult;

public class Game {
    private final String[][] board;
    private final String playerX;
    private final String playerO;

    public Game(HashMap<String, String> parameters) {
        this.board = new String[3][3];
        this.playerX = parameters.get("playerX");
        this.playerO = parameters.get("playerO");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                board[i][j] = " ";
        }
    }

    private void printBoard() {
        printPadding();
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++)
                System.out.print(board[i][j] + " ");
            System.out.println("|");
        }
        printPadding();
    }

    private void printPadding() {
        System.out.println(String.format("%1$" + 9 + "s", "").replace(' ', '-'));
    }

    public void start() {
        var gameOrder = true;
        while (finalResult(board).equals("Game not finished")) {
            printBoard();
            if (playerX.equals("user")) {
                if (moveUser(getCoordinates(), true)) {
                    printBoard();
                    moveAI(false, playerO);
                }
            } else if (playerO.equals("user")) {
                moveAI(true, playerX);
                printBoard();
                while (true)
                    if (moveUser(getCoordinates(), false))
                        break;
            } else {
                if (gameOrder)
                    moveAI(true, playerX);
                else
                    moveAI(false, playerO);
                gameOrder = !gameOrder;
            }
        }
        printBoard();
        System.out.println(finalResult(board));
    }

    //coordinates[0] - row, coordinates[1] - column
    private int[] getCoordinates() {
        var scanner = new Scanner(System.in);
        var x = 0;
        var y = 0;
        while (true) {
            System.out.print("Enter the coordinates: ");
            try {
                x = Integer.parseInt(scanner.next());
                y = Integer.parseInt(scanner.next());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("You should enter numbers!");
                continue;
            }
            if (x > 3 || y > 3 || x < 1 || y < 1) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            break;
        }
        return new int[]{--x, --y};
    }

    private boolean moveUser(int[] coordinates, boolean playerX) {
        var row = coordinates[0];
        var column = coordinates[1];
        if (board[row][column].equals(" ")) {
            board[row][column] = playerX ? "X" : "O";
            return true;
        } else {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }
    }

    private void moveAI(boolean order, String difficultyLevel) {
        System.out.println("Making move level \"" + difficultyLevel + "\"");
        if ("medium".equals(difficultyLevel))
            mediumMove(order);
        else
            easyMove(order);
    }

    private void easyMove(boolean order) {
        var random = new Random();
        if (!finalResult(board).equals("Draw")) {
            while (true) {
                var row = random.nextInt(3);
                var column = random.nextInt(3);
                if (board[row][column].equals(" ")) {
                    board[row][column] = order ? "X" : "O";
                    break;
                }
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
        var tmp = order ? "X" : "O";
        var empty = 0;
        for (String[] row : board) {
            int[] x_o_e = new int[]{0, 0, 0};
            for (int i = 0; i < 3; i++) {
                if (row[i].equals("X"))
                    x_o_e[0]++;
                else if (row[i].equals("O"))
                    x_o_e[1]++;
                else {
                    x_o_e[2]++;
                    empty = i;
                }
            }
            if ((x_o_e[0] == 2 || x_o_e[1] == 2) && x_o_e[2] == 1) {
                row[empty] = tmp;
                return true;
            }
        }
        return false;
    }

    private boolean winOrBlockColumn(boolean order) {
        var tmp = order ? "X" : "O";
        var empty = 0;
        for (int i = 0; i < 3; i++) {
            int[] x_o_e = new int[]{0, 0, 0};
            for (int j = 0; j < 3; j++) {
                if (board[j][i].equals("X"))
                    x_o_e[0]++;
                else if (board[j][i].equals("O"))
                    x_o_e[1]++;
                else {
                    x_o_e[2]++;
                    empty = j;
                }
            }
            if ((x_o_e[0] == 2 || x_o_e[1] == 2) && x_o_e[2] == 1) {
                board[empty][i] = tmp;
                return true;
            }
        }
        return false;
    }

    private boolean winOrBlockDiagonal(boolean order) {
        var tmp = order ? "X" : "O";
        String[] leftDownDiagonal = new String[]{board[0][0], board[1][1], board[2][2]};
        String[] rightDownDiagonal = new String[]{board[0][2], board[1][1], board[2][0]};
        var lDD = analyzeDiagonal(leftDownDiagonal);
        var rDD = analyzeDiagonal(rightDownDiagonal);
        if (lDD != null) {
            board[lDD][lDD] = tmp;
            return true;
        } else if (rDD != null) {
            if (rDD == 0)
                board[rDD][rDD + 2] = tmp;
            else if (rDD == 1)
                board[rDD][rDD] = tmp;
            else
                board[rDD][rDD - 2] = tmp;
            return true;
        }
        return false;
    }

    private Integer analyzeDiagonal(String[] diagonal) {
        int[] x_o_e = new int[]{0, 0, 0};
        for (String d : diagonal) {
            if (d.equals("X"))
                x_o_e[0]++;
            else if (d.equals("O"))
                x_o_e[1]++;
            else
                x_o_e[2]++;
        }
        if ((x_o_e[0] == 2 || x_o_e[1] == 2) && x_o_e[2] == 1)
            for (int i = 0; i < diagonal.length; i++)
                if (diagonal[i].equals(" "))
                    return i;
        return null;
    }


}
