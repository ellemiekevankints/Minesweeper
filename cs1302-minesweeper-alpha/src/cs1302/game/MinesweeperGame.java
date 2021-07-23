package cs1302.game;

import java.util.*;
import java.io.*;
import java.text.*;

/**
 * This class represents the game Minesweeper.
 */
public class MinesweeperGame {
    private int rows;
    private int cols;
    private int totalMines;
    private int totalNotMines = 0;
    private int roundsCompleted = 0;
    private boolean loss = false;
    private double score = 0.0;
    private boolean fogOfWar = false;
    private int counter = 0;
    private boolean[][] mineLocations;
    private String[][] minefield;

    Scanner prompt = new Scanner(System.in);

    /**
     * This method constructs a {@code MinesweeperGame} object with the specified seed path.
     * @param seed the path to the seed file
     */
    public MinesweeperGame(String seed) {
        try {
            // read in the seed file
            File seedFile = new File(seed);
            Scanner sc = new Scanner(seedFile);
            rows = sc.nextInt();
            cols = sc.nextInt();
            totalMines = sc.nextInt();

            // if wrong information is provided in the seed file
            if (rows < 5 || cols < 5) {
                System.err.print("\nSeedfile Value Error: Cannot create a mine field ");
                System.err.print("with that many rows and/or columns!");
                System.exit(3);
            }
            if (totalMines > (rows * cols)) {
                System.err.print("Seedfile Format Error: Cannot create game with ");
                System.err.print(seed + ", because it is not formatted correctly.\n");
                System.exit(1);
            }

            // create a 2D boolean array to hold the locations of the mines
            mineLocations = new boolean[rows][cols];
            int locationCounter = 0;
            while (locationCounter < totalMines) {
                int locationRows = sc.nextInt();
                int locationCols = sc.nextInt();
                mineLocations[locationRows][locationCols] = true;
                locationCounter++;
            }

            minefield = new String[rows][cols];
            for (int i = 0; i < minefield.length; i++) {
                for (int j = 0; j < minefield[i].length; j++) {
                    minefield[i][j] = "";
                }
            }
        } catch (ArrayIndexOutOfBoundsException aobe) {
            System.err.print("Seedfile Format Error: Cannot create game with " + seed);
            System.err.print(", because it is not formatted correctly.\n");
            System.exit(1);
        } catch (FileNotFoundException fnfe) {
            System.err.print("Seedfile Not Found Error: Cannot create game with " + seed);
            System.err.print(", because it cannot be found or cannot be read due to permission.\n");
            System.exit(1);
        } catch (NullPointerException npe) {
            System.err.print("Seedfile Format Error: Cannot create game with " + seed);
            System.err.print(", because it is not formatted correctly.\n");
            System.exit(1);
        }
    }

    /**
     * Prints the welcome message when user firsts enters the game.
     */
    public void printWelcome() {
        System.out.print("\n");
        System.out.println("        _");
        System.out.println("  /\\/\\ (_)_ __   ___  _____      _____  ___ _ __   ___ _ __");
        System.out.print(" /    \\| | '_ \\ / _ \\/ __\\ \\ /\\ ");
        System.out.println("/ / _ \\/ _ \\ '_ \\ / _ \\ '__|");
        System.out.println("/ /\\/\\ \\ | | | |  __/\\__ \\\\ V  V /  __/  __/ |_) |  __/ |");
        System.out.print("\\/    \\/_|_| |_|\\___||___/ \\_/\\_/ \\___");
        System.out.println("|\\___| .__/ \\___|_|");
        System.out.println("                 A L P H A   E D I T I O N |_| v2020.sp");
        System.out.print("\n");
    }

    /**
     * This method prints the minefield using the information provided in the seed file.
     */
    public void printMineField() {
        // calculates the number of individual digits in row and columns
        int rowSpaces = (int)Math.ceil(Math.log10(rows));
        int colSpaces = (int)Math.ceil(Math.log10(cols));
        for (int a = 0; a < minefield.length; a++) {
            // at the beginning of each row, print the row number with proper padding
            System.out.printf("\n %" + rowSpaces + "d |", a);
            for (int b = 0; b < minefield[a].length; b++) {
                // reveals the mine locations if the "nofog" command has been entered by the player
                if (fogOfWar && mineLocations[a][b]) {
                    System.out.printf("<%" + colSpaces + "s>|", minefield[a][b]);
                } else {
                    System.out.printf(" %" + colSpaces + "s |", minefield[a][b]);
                }
            }
        }
        System.out.printf("\n%" + (rowSpaces + 2) + "s", "");
        // prints the column number with proper padding
        for (int c = 0; c < cols; c++) {
            System.out.printf("  %" + colSpaces + "d ", c);
        }
    }

    /**
     * This method prompts the user for an input command.
     */
    public void promptUser() {
        int input2;
        int input3;
        System.out.println("\n Rounds Completed: " + roundsCompleted);
        printMineField();
        fogOfWar = false;
        System.out.print("\n\nminesweeper-alpha: ");
        // the entire inputted line
        String input = prompt.nextLine().trim();
        // scan the inputted line token by token
        Scanner promptString = new Scanner(input);
        String input1 = promptString.next();
        // checks to see if the entered command matches a predisposed command
        if (input1.equals("h") || input1.equals("help")) {
            if (promptString.hasNext()) {
                System.err.println("\nInput Error: Command not recognized!");
            } else {
                System.out.println("\nCommands Available...");
                System.out.println(" - Reveal: r/reveal row col\n -   Mark: m/mark   row col");
                System.out.println(" -  Guess: g/guess  row col\n -   Help: h/help\n");
                System.out.println(" -   Quit: q/quit");
                roundsCompleted++;
            }
        } else if (input1.equals("q") || input1.equals("quit")) {
            if (promptString.hasNext()) {
                System.err.println("\nInput Error: Command not recognized!");
            } else {
                System.out.println("\nQuitting the game...\nBye!\n");
                System.exit(0);
            }
        } else if (input1.equals("r") || input1.equals("reveal")) {
            if (!promptString.hasNext()) {
                System.err.println("\nInput Error: Command not recognized!");
            } else {
                input2 = promptString.nextInt();
                input3 = promptString.nextInt();
                reveal(input2, input3);
                roundsCompleted++;
            }
        } else if (input1.equals("m") || input1.equals("mark")) {
            input2 = promptString.nextInt();
            input3 = promptString.nextInt();
            mark(input2, input3);
            roundsCompleted++;
        } else if (input1.equals("g") || input1.equals("guess")) {
            input2 = promptString.nextInt();
            input3 = promptString.nextInt();
            guess(input2, input3);
            roundsCompleted++;
        } else if (input1.equals("nofog")) {
            if (promptString.hasNext()) {
                System.err.println("\nInput Error: Command not recognized!");
            } else {
                fogOfWar = true;
                roundsCompleted++;
            }
        } else {
            System.err.println("\nInput Error: Command not recognized!");
        }
    }

    /**
     * This method calculates the number of mines adjacent to a cell in the minefield.
     * @param row the row of the revealed cell
     * @param col the column of the revealed cell
     * @return counter the total number of mines adjacent to the cell
     */
    private int getNumAdjMines(int row, int col) {
        int count = 0;
        for (int a = row - 1; a <= row + 1; a++) {
            for (int b = col - 1; b <= col + 1; b++) {
                // checks to see if the adjacent cells are in the bounds of the grid
                if (a >= 0 && a < rows && b >= 0 && b < cols) {
                    // counts the number of adjacent cells containing mines
                    if (mineLocations[a][b] == true) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * This method reveals the number of mines adjacent to an inputted location in the minefield.
     * @param r the row of the cell to be revealed in the minefield
     * @param c the column of the cell to be revealed in the minefield
     */
    private void reveal(int r, int c) {
        // checks to see if the entered coordinates are in the bounds of the grid
        if (r < 0 || r > rows - 1 || c < 0 || c > cols - 1) {
            System.err.println("\nInput Error: Command not recognized!");
        } else {
            if (mineLocations[r][c] == false) {
                // store the number of adjacent mines in the minefield String array
                String adjMines = Integer.toString(getNumAdjMines(r, c));
                minefield[r][c] = adjMines;
                totalNotMines++;
            } else {
                // the player reveals a mine
                loss = true;
            }
        }
    }

    /**
     * This method marks the inputted location in the minefield as containing a mine.
     * @param r the row of the cell to be marked in the minefield
     * @param c the column of the cell to be marked in the minefield
    */
    private void mark(int r, int c) {
        // checks to see if the entered coordinates are in the bounds of the grid
        if (r < 0 || r > rows - 1 || c < 0 || c > cols - 1) {
            System.err.println("\nInput Error: Command not recognized!");
        } else {
            minefield[r][c] = "F";
        }
    }

    /**
     * This method marks the inputted location in the minefield as potentially containing a mine.
     * @param r the row of the cell to be guessed in the minefield
     * @param c the column of the cell to be guessed in the minefield
     */
    private void guess(int r, int c) {
        // checks to see if the entered coordinates are in the bounds of the grid
        if (r < 0 || r > rows - 1 || c < 0 || c > cols - 1) {
            System.err.println("\nInput Error: Command not recognized!");
        } else {
            minefield[r][c] = "?";
        }
    }

    /**
     * This method determined if the user has won the game.
     * @return true if user has won the game, false otherwise
     */
    public boolean isWon() {
        int mineCounter = 0;
        for (int i = 0; i < minefield.length; i++) {
            for (int j = 0; j < minefield[i].length; j++) {
                // if all the cells containing mines are marked as flagged
                if (minefield[i][j].equals("F") && mineLocations[i][j] == true) {
                    mineCounter++;
                }
            }
        }
        // if all the cells not containing mines are revealed
        if (totalNotMines == (rows * cols) - totalMines && totalMines == mineCounter) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method determines if the user had lost the game.
     * @return true if user has lost the game, false otherwise
     */
    public boolean isLost() {
        return loss;
    }

    /**
     * Prints the winning message to standard output and the user's calucated score for that game.
     */
    public void printWin() {
        // calculates the score to two decimal places
        score = 100.0 * rows * cols / roundsCompleted;
        DecimalFormat f = new DecimalFormat("##.00");
        System.out.print("\n");
        System.out.println(" ░░░░░░░░░▄░░░░░░░░░░░░░░▄░░░░ \"So Doge\"");
        System.out.println(" ░░░░░░░░▌▒█░░░░░░░░░░░▄▀▒▌░░░");
        System.out.println(" ░░░░░░░░▌▒▒█░░░░░░░░▄▀▒▒▒▐░░░ \"Such Score\"");
        System.out.println(" ░░░░░░░▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐░░░");
        System.out.println(" ░░░░░▄▄▀▒░▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐░░░ \"Much Minesweeping\"");
        System.out.println(" ░░░▄▀▒▒▒░░░▒▒▒░░░▒▒▒▀██▀▒▌░░░");
        System.out.println(" ░░▐▒▒▒▄▄▒▒▒▒░░░▒▒▒▒▒▒▒▀▄▒▒▌░░ \"Wow\"");
        System.out.println(" ░░▌░░▌█▀▒▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐░░");
        System.out.println(" ░▐░░░▒▒▒▒▒▒▒▒▌██▀▒▒░░░▒▒▒▀▄▌░");
        System.out.println(" ░▌░▒▄██▄▒▒▒▒▒▒▒▒▒░░░░░░▒▒▒▒▌░");
        System.out.println(" ▀▒▀▐▄█▄█▌▄░▀▒▒░░░░░░░░░░▒▒▒▐░");
        System.out.println(" ▐▒▒▐▀▐▀▒░▄▄▒▄▒▒▒▒▒▒░▒░▒░▒▒▒▒▌");
        System.out.println(" ▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒▒▒░▒░▒░▒▒▐░");
        System.out.println(" ░▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒░▒░▒░▒░▒▒▒▌░");
        System.out.println(" ░▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▒▄▒▒▐░░");
        System.out.println(" ░░▀▄▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▄▒▒▒▒▌░░");
        System.out.println(" ░░░░▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀░░░ CONGRATULATIONS!");
        System.out.println(" ░░░░░░▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀░░░░░ YOU HAVE WON!");
        System.out.println(" ░░░░░░░░░▒▒▒▒▒▒▒▒▒▒▀▀░░░░░░░░ SCORE: " + f.format(score));
        System.out.print("\n");
        System.exit(0);
    }

    /**
     * Prints the "game over" message to standard output.
     */
    public void printLoss() {
        System.out.print("\n");
        System.out.println("Oh no... You revealed a mine!");
        System.out.println("");
        System.out.println("  __ _  __ _ _ __ ___   ___    _____   _____ _ __");
        System.out.print(" / _` |/ _` | '_ ` _ ");
        System.out.println("\\ / _ \\  / _ \\ \\ / / _ \\ '__|");
        System.out.println("| (_| | (_| | | | | | |  __/ | (_) \\ V /  __/ |");
        System.out.println(" \\__, |\\__,_|_| |_| |_|\\___|  \\___/ \\_/ \\___|_|");
        System.out.println(" |___/");
        System.out.print("\n");
        System.exit(0);
    }

    /**
     * This method acts as the main game loop for Minesweeper.
     */
    public void play() {
        printWelcome();
        // while the player has neither lost nor won
        while (!isLost() && !isWon()) {
            try {
                promptUser();
            } catch (NoSuchElementException nsee) {
                System.err.println("\nInput Error: Command not recognized!");
            }
            if (isLost()) {
                printLoss();
            }
            if (isWon()) {
                printWin();
            }
        }
    }
}
