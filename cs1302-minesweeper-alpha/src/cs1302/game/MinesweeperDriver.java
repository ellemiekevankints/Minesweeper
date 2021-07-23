package cs1302.game;

import cs1302.game.MinesweeperGame;

/**
 * A driver program to test the functionality of {@code cs1302.game.MinesweeperGame}.
 */
public class MinesweeperDriver {

    /**
     * This is the main method.
     * @param args the command-line arguments to the programx
     */
    public static void main(String[] args) {

        try {
            String command = args[0];
            if (command.equals("--seed")) {
                String path = args[1];
                MinesweeperGame test = new MinesweeperGame(path);
                test.play();
            } else if(command.equals("--gen")) {
                System.err.println("Seedfile generation not supported.");
                System.exit(2);
            }
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            System.err.println("Unable to interpret supplied command-line arguments.");
            System.exit(1);
        } // try-catch
    }
}
