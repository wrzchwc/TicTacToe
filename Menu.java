package tictactoe;

import java.util.*;

import static tictactoe.Game.*;

public class Menu {
    private static final String EXIT = "exit";

    public void run() {
        var scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Input command: ");
            var parameters = scanner.nextLine().split(EMPTY);
            if (!checkParameters(parameters))
                continue;
            else if (parameters[0].equals(EXIT))
                break;
            var game = new Game(getSetup(parameters));
            game.start();
        }
    }

    private boolean checkParameters(String[] input) {
        if (input[0].equals(EXIT) && input.length != 1 || input[0].equals("start") && input.length != 3) {
            System.out.println("Bad parameters");
            return false;
        }
        return true;
    }

    private HashMap<String, String> getSetup(String[] parameters) {
        return new HashMap<>(Map.of(PLAYER_X, parameters[1], PLAYER_O, parameters[2]));
    }
}
