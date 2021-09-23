package tictactoe;

import java.util.*;

public class Menu {

    public void run() {
        var scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input command: ");
            var parameters = scanner.nextLine().split(" ");
            if (!checkParameters(parameters))
                continue;
            else if (parameters[0].equals("exit"))
                break;
            var game = new Game(getSetup(parameters));
            game.start();
        }
    }

    private boolean checkParameters(String[] input) {
        if (input[0].equals("exit") && input.length != 1 || input[0].equals("start") && input.length != 3) {
            System.out.println("Bad parameters");
            return false;
        }
        return true;
    }

    private HashMap<String, String> getSetup(String[] parameters) {
        return new HashMap<>(Map.of("playerX", parameters[1], "playerO", parameters[2]));
    }
}
