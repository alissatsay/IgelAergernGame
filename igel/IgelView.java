/**
 * A terminal interface for playing Igel Aergern.
 * @author: Kristina Striegnitz
 * @version: Winter 2024
 */
package igel;

import java.util.Scanner;

public class IgelView {

    private String[] chapters = {"Intro", "setup", "main", "end"};
    private int chapter;
    private final Scanner input;
    private final int WIDTH = 60;
    private final int CELL_WIDTH = 5;

    public IgelView() {
        this.chapter = 0; // "Intro";
        input = new Scanner(System.in);
    }

    /**
     * Prints a representation of the Igel Aergern board to the terminal.
     * 
     * @param board a representation of the board as a string. The tracks/rows
     *      are separated from each other using // and cells within one track are
     *      separated using |. Empty cells are represented using an empty string.
     *      Otherwise they are represented as a sequence of the stacked colors. E.g.
     *      'RBO' represents a stack of red at the bottom, blue, and orange. 
     *      Here is an example of a valid board representation:
     *      'Y||||||||//||||||||Y//Y||R||B||O||//YR|RBO||RBOO|||||//||||||||//||BR||||||O'
     */
    public void refresh(String board) {
        refresh(board, null);
    }

    /**
     * Prints a representation of the Igel Aergern board to the terminal.
     * 
     * @param board a representation of the board as a string. The tracks/rows
     *      are separated from each other using // and cells within one track are
     *      separated using |. Empty cells are represented using an empty string.
     *      Otherwise they are represented as a sequence of the stacked colors. E.g.
     *      'RBO' represents a stack of red at the bottom, blue, and orange. 
     *      Here is an example of a valid board representation:
     *      'Y||||||||//||||||||Y//Y||R||B||O||//YR|RBO||RBOO|||||//||||||||//||BR||||||O'
     * @param winner the name of the winner, if there is one; null otherwise.
     */
    public void refresh(String board, String winner) {
        switch (chapter) {
            case 0:
                showIntroduction();
                break;
            case 1:
                showSetupPhase(board);
                break;
            case 2:
                showMainPhase(board);
                break;
            default:
                showEnd(board, winner);
        }
    }

    /**
     * Advances the interface to the next "chapter" of the game. There are 
     * four chapters: introduction, setup phase, main phase, end.
     */
    public void nextChapter() {
        if (chapter < chapters.length-1) {
            chapter++;
        }
    }

    /**
     * Requests an answer from the player and returns whatever string the player enters.
     * 
     * @param message the message to display to request input from the player.
     * @return the player's input
     */
    public String requestInput(String message) {
        System.out.println(message);
        return input.nextLine();
    }

    /**
     * Prints the given message to the terminal.
     * 
     * @param message
     */
    public void inform(String message) {
        System.out.println(message);
    }

    /** The displays for the four game "chapters". **/
    
    private void showIntroduction() {
        clear();
        printHeader();
        System.out.println("Welcome. Let's play!");
    }

    private void showSetupPhase(String board) {
        clear();
        printHeader();
        printBoard(board);
    }
    
    private void showMainPhase(String board) {
        clear();
        printHeader();
        printBoard(board);
    }

    private void showEnd(String board, String winner) {
        clear();
        printHeader();
        printCentered("The end.", WIDTH);
        if (winner != null) {
            printCentered("Congratulations! Player " + winner + " has won.", WIDTH);
        }
        else {
            printCentered("Nobody has won.", WIDTH);
        }
        System.out.println();
    }

    private void printHeader() {
        printCentered("/~~~~~~~~~~~~~~\\", WIDTH);
        printCentered("| IGEL AERGERN |", WIDTH);
        printCentered("\\~~~~~~~~~~~~~~/", WIDTH);
        System.out.println();
    }

    /** Displaying the board **/

    private void printBoard(String board) {
        String[] rows = board.split("//");
        int numOfCols = rows[0].split("\\|", -1).length;
        printCentered(startGoalLabels(numOfCols), WIDTH);
        for (int r = 0; r < rows.length; r++) {
            String rowString = formatRow(r, rows[r]);
            String border = "-".repeat(rowString.length());
            printCentered(border, WIDTH);
            printCentered(rowString, WIDTH);
            if (r == rows.length-1) {
                printCentered(border, WIDTH);
            }
        }
        printCentered(columnLabels(numOfCols), WIDTH);
    }

    private String startGoalLabels(int numOfCols) {
        return "    Start " + " ".repeat((CELL_WIDTH + 1) * (numOfCols-2)) + " Goal";
    }

    private String formatRow(int i, String row) {
        String rowString = String.valueOf(i+1) + " |";
        String[] cols = row.split("\\|", -1);
        for (String cell : cols) {
            rowString += leftAlign(cell, CELL_WIDTH);
            rowString += "|";
        }
        return center(rowString, WIDTH);
    }

    private String columnLabels(int numOfCols) {
        String columnLabelString = "     " + " ".repeat(CELL_WIDTH/2);
        for (int i = 1; i < numOfCols + 1; i++) {
            columnLabelString += i + " ".repeat(CELL_WIDTH);
        }
        columnLabelString += " ".repeat(CELL_WIDTH/2);
        return columnLabelString;
    }

    /** Helper methods **/

    private void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush(); 
    }

    private void printCentered(String toPrint, int width) {
        System.out.println(center(toPrint, width));
    }

    private String center(String toCenter, int width) {
        int printWidth = (width - toCenter.length()) / 2 + toCenter.length();
        if (printWidth > 0) {
            String formatTemplate = "%" + printWidth + "s";
            toCenter = String.format(formatTemplate, toCenter);
        }
        return toCenter;
    }

    private String leftAlign(String toAlign, int width) {
        if (toAlign.length() < width) {
            String formatTemplate = "%-" + width + "s";
            toAlign = String.format(formatTemplate, toAlign);
        }
        return toAlign;
    }
}
