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
        for (String[] row : board) {
            Arrays.fill(row, " ");
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
        var order = true;
        while (finalResult(board).equals("Game not finished")) {
            printBoard();
            if (order) {
                if (playerX.equals("user")) {
                    while (!moveUser(getCoordinates(), true)) {
                        cellOccupiedWarning();
                    }
                } else {
                    moveAI(true, playerX);
                }
            } else {
                if (playerO.equals("user")) {
                    while (!moveUser(getCoordinates(), false)) {
                        cellOccupiedWarning();
                    }
                } else {
                    moveAI(false, playerO);
                }
            }
            order = !order;
        }
        printBoard();
        System.out.println(finalResult(board));
    }

    void cellOccupiedWarning() {
        System.out.println("This cell is occupied! Choose another one!");
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
            return false;
        }
    }

    private void moveAI(boolean order, String difficultyLevel) {
        System.out.println("Making move level \"" + difficultyLevel + "\"");
        if ("hard".equals(difficultyLevel)) {
            hardMove(order);
        } else if ("medium".equals(difficultyLevel)) {
            mediumMove(order);
        } else {
            easyMove(order);
        }
    }

    String move(boolean order) {
        return order ? "X" : "O";
    }

    private void easyMove(boolean order) {
        var random = new Random();
        while (true) {
            var row = random.nextInt(3);
            var column = random.nextInt(3);
            if (board[row][column].equals(" ")) {
                board[row][column] = move(order);
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
        var tmp = move(order);
        var empty = 0;
        for (String[] row : board) {
            int[] xoe = new int[]{0, 0, 0};
            for (int i = 0; i < 3; i++) {
                if (row[i].equals("X"))
                    xoe[0]++;
                else if (row[i].equals("O"))
                    xoe[1]++;
                else {
                    xoe[2]++;
                    empty = i;
                }
            }
            if ((xoe[0] == 2 || xoe[1] == 2) && xoe[2] == 1) {
                row[empty] = tmp;
                return true;
            }
        }
        return false;
    }

    private boolean winOrBlockColumn(boolean order) {
        var tmp = move(order);
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
        var tmp = move(order);
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

    private int minMax(String[][] board, boolean order, ArrayList<int[]> cells, int depth) {
        int[] decisions = new int[cells.size()];
        var decisionID = 0;

        for (int[] cell : cells) {
            board[cell[0]][cell[1]] = move(order);
            switch (finalResult(board)) {
                case "Game not finished":
                    decisions[decisionID++] = minMax(board, !order, empty(board), depth);
                    break;
                case "X wins":
                    decisions[decisionID++] = 1;
                    break;
                case "O wins":
                    decisions[decisionID++] = -1;
                    break;
                case "Draw":
                    decisions[decisionID++] = 0;
                    break;
            }
            board[cell[0]][cell[1]] = " ";
        }

        if (decisionID == depth) {
            var listOfDecisions = convert(decisions);
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
            } else {
                return 0;
            }
        } else {
            return order ? Arrays.stream(decisions).max().getAsInt() : Arrays.stream(decisions).min().getAsInt();
        }
    }

    private ArrayList<Integer> convert(int[] array) {
        var list = new ArrayList<Integer>();
        for (Integer a : array) {
            list.add(a);
        }
        return list;
    }

    private ArrayList<int[]> empty(String[][] board) {
        var list = new ArrayList<int[]>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals(" ")) {
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
    }


}


