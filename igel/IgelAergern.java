/**
 * Model an Igel Aergern Game.
 * 
 * @author: Alissa Tsay
 * @version: Winter 2024
 * I affirm that I have carried out my academic endeavors
 * with full academic honesty. [Alissa Tsay]
 */
package igel;

import java.util.ArrayList;
import java.util.Random;

public class IgelAergern {

    private Board board;
    private IgelView view;
    private ArrayList<Player> players;
    private Player winner;

    private final int MIN_PLAYERS = 3;
    private final int MAX_PLAYERS = 6;
    private final int PLAYER_TOKENS = 4;
    private final int DIE_TYPE = 6;

    private final Random rand = new Random();

    public IgelAergern() {
        this.board = new Board(); // DO NOT MODIFY THIS LINE
        this.view = new IgelView(); // DO NOT MODIFY THIS LINE
        this.players = new ArrayList<Player>(); // DO NOT MODIFY THIS LINE
        this.winner = null; // DO NOT MODIFY THIS LINE
        // You may add additional code here, if you want to.
    }

    public IgelAergern(String bStr) {
        this.board = new Board(bStr); // DO NOT MODIFY THIS LINE
        this.view = new IgelView(); // DO NOT MODIFY THIS LINE
        this.players = new ArrayList<Player>(); // DO NOT MODIFY THIS LINE
        this.winner = null; // DO NOT MODIFY THIS LINE
        // You may add additional code here, if you want to.
    }

    /**
     * Play one game of Igel Aergern.
     * 
     * DO NOT MODIFY THIS METHOD.
     */
    public void play() {
        introduction();
        setupPhase();
        mainPhase();
        end();
    }

    /**
     * Play one test game of Igel Aergern from a given position.
     * 
     * DO NOT MODIFY THIS METHOD.
     */
    public void testPlay() {
        introduction();
        view.nextChapter();
        mainPhase();
        end();
    }
    
    /**
     * Set up an Igel ergern board and players.
     */
    private void introduction() {
        view.refresh(null); // DO NOT MODIFY THIS LINE
        createPlayers(); // DO NOT MODIFY THIS LINE
        // You may add additional code here, if you want to.
    }

    /**
     * Play full set up phase of Igel Aergern.
     */
    private void setupPhase() {
        view.nextChapter();
        view.refresh(board.toString());
        int currentPlayer = 0;
        for (int tokensPlaced = 0; tokensPlaced < players.size() * PLAYER_TOKENS; tokensPlaced++) {
            setupTurn(currentPlayer);
            view.refresh(board.toString());
            currentPlayer = nextPlayer(currentPlayer);
        }
    }

    /**
     * Play full main phase of Igel Aergern.
     */
    private void mainPhase() {
        view.nextChapter();  // DO NOT MODIFY THIS LINE
        view.refresh(board.toString());  // DO NOT MODIFY THIS LINE
        int currentPlayer = 0;
        while (winner == null){
            mainTurn(currentPlayer);
            getWinner();
            view.refresh(board.toString());
            currentPlayer = nextPlayer(currentPlayer);
        }
    }

    /**
     * End the game and announce the winner.
     */
    private void end() {
        view.nextChapter(); // DO NOT MODIFY THIS LINE
        view.refresh(board.toString(), winner.toString()); // DO NOT MODIFY THIS LINE
        // You may add additional code here, if you want to.
    }

    /**
     * Create players of the game.
     */
    private void createPlayers() {
        int numPlayers = getPlayerNumber();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(Player.COLORS[i]));
        }
    }

    /**
     * Return the nest player.
     * @param current the current player.
     * @return next player.
     */
    private int nextPlayer(int current) {
        return (current + 1) % players.size();
    }

    /**
     * Set a winner if there is one.
     */
    private void getWinner(){
        for (int player = 0;player < players.size(); player ++){
            char color = players.get(player).getColor();
            if (board.countTokensInColumn(Board.COLUMNS-1,color) >= PLAYER_TOKENS -1 ){
                winner = players.get(player);
            }
        }
    }

    /**
     * Play one set up turn of Igel Aergern.
     * @param currentPlayer the player who;s turn it is.
     */
    private void setupTurn(int currentPlayer) {
        view.inform("Player " + players.get(currentPlayer).getColor() + ", your turn.");
        int row = getSetupMove(currentPlayer);
        board.placeToken(row, 0, players.get(currentPlayer).getColor());
    }

    /**
     * Roll a die and return the result.
     * @return die roll
     */
    private int dieRoll(){
        int value = rand.nextInt(DIE_TYPE);
        return value;
    }

    /**
     * Play one turn of the main phase.
     * @param currentPlayer - player's turn
     */
    private void mainTurn(int currentPlayer){
        view.inform("Player " + players.get(currentPlayer).getColor() + ", your turn.");
        int row = dieRoll();
        view.inform("Die Roll: " + (row+1));
        view.inform("Do you want to slide a hedgehog token sideways?");
        board.adjustAllTraps();
        int slideRow = getSlideRow(currentPlayer);
        if (slideRow >= 0){
            int slideCol = getSlideColumn(currentPlayer,slideRow);
            int dir = getSlideDirection(slideRow, currentPlayer);
            board.moveSideways(slideRow, slideCol,dir);
            view.refresh(board.toString());
        }
        if (board.isEmptyRow(row)){
            view.inform("The rolled row is empty.");
        }
        else{
            board.adjustAllTraps();
            view.inform("Which hedgehog from row " + (row+1) + " do you want to move forward?");
            int col = getMainMove(currentPlayer,row);
            board.moveForward(row, col);
        }
    }



    /**** Input validation ****/

    /**
     * Get the number of players.
     * @return the number of players.
     */
    private int getPlayerNumber() {
        // Prepare messages
        String requestMessage = "How many players? (" + MIN_PLAYERS + "-" + MAX_PLAYERS + ")";
        String errorMessage = "Please input a number between " + MIN_PLAYERS + " and " + MAX_PLAYERS + ".";
        // Request input (repeatedly until the player provides a valid answer)
        String numPlayersInput = view.requestInput(requestMessage);
        while (! isDigit(numPlayersInput) || ! isInRange(Integer.decode(numPlayersInput), MIN_PLAYERS, MAX_PLAYERS)) {
            numPlayersInput = view.requestInput(errorMessage);
        }
        return Integer.decode(numPlayersInput);
    }

    /**
     * Request a move row in the Setup phase.
     * @return the row.
     */
    private int getSetupMove(int currentPlayer) {
        // Prepare messages
        String requestMessage = "In which row do you want to place your token? (1-6)  ";
        ArrayList<Integer> options = collectSetupOptions(currentPlayer);
        String optionsString = options.toString();
        String errorMessage = "Please input a valid row number: " + optionsString;
        // Request input (repeatedly until the player provides a valid answer)
        String rowInput = requestAndValidateInput(requestMessage, errorMessage, options);
        return Integer.decode(rowInput) - 1;
    }

    /**
     * Request a move column in the Main phase.
     * @return the column.
     */
    private int getMainMove(int currentPlayer,int row) {
        // Prepare messages
        String requestMessage = "Input the column number for the token you want to move forward. ";
        ArrayList<Integer> options = collectMoveOptions(row);
        String optionsString = options.toString();
        String errorMessage = "Please input a valid column number: " + optionsString;
        // Request input (repeatedly until the player provides a valid answer)
        String colInput = requestAndValidateInput(requestMessage, errorMessage, options);
        return Integer.decode(colInput) - 1;
    }

    /**
     * Request a move row for the slide request in the Main phase.
     * @return the column.
     */
    private int getSlideRow(int currentPlayer){
        // Prepare messages
        String requestRowMessage = "Input 0 to pass or the row number for the token you want to slide. ";
        ArrayList<Integer> rowOptions = collectSlideRowOptions(currentPlayer);
        String rowOptionsString = rowOptions.toString();
        String errorMessage = "Please input a valid row number: " + rowOptionsString;
        // Request row input (repeatedly until the player provides a valid answer)
        String rowInput = requestAndValidateInput(requestRowMessage, errorMessage, rowOptions);
        return Integer.decode(rowInput) - 1;
    }

    /**
     * Request a move column for the slide request in the Main phase.
     * @return the column.
     */
    private int getSlideColumn(int currentPlayer, int row){
        // Prepare messages
        String requestColMessage = "Now input the column number for the token you want to slide. ";
        ArrayList<Integer> colOptions = collectSlideColOptions(row, currentPlayer);
        String colOptionsString = colOptions.toString();
        String errorMessage = "Please input a valid column number: " + colOptionsString;
        // Request column input (repeatedly until the player provides a valid answer)
        String colInput = requestAndValidateInput(requestColMessage, errorMessage, colOptions);
        return Integer.decode(colInput) - 1;
    }

    /**
     * Request a move direction for the slide request in the Main phase.
     * @return the column.
     */
    private int getSlideDirection(int currentPlayer, int row){
        // Prepare messages
        String requestDirMessage = "Do you want to slide this hedgehog up or down? Choose U or D: ";
        ArrayList<Integer> dirOptions = collectSlideDirOptions(row, currentPlayer);
        String dirOptionsString = decodeDirOptions(dirOptions).toString();
        String errorMessage = "Please input a valid direction: " + dirOptionsString;
        // Request direction input (repeatedly until the player provides a valid answer)
        String dirInput = view.requestInput(requestDirMessage);
        while (! dirOptions.contains(decodeDir(dirInput))) {
            dirInput = view.requestInput(errorMessage);
        }
        return decodeDir(dirInput);
    }

    /**
     * Set possible row placing options in the Setup phase.
     * @return options.
     */
    private ArrayList<Integer> collectSetupOptions(int currentPlayer) {
        // Collect rows with lowest stacks (-> options) while also keeping track of
        // whether one of the lowest stacks does NOT contain the current player's color
        ArrayList<Integer> options = new ArrayList<>();
        ArrayList<Integer> allRows = new ArrayList<>();
        boolean nonBlockingOptionExists = false;
        int minStackHeight = board.stackHeight(0, 0);
        for (int r = 0; r < Board.ROWS; r++) {
            int rStackHeight = board.stackHeight(r, 0);
            if (rStackHeight < minStackHeight) {
                options.clear();
                //options.add(r+1);
                minStackHeight = rStackHeight;
            }
            if (rStackHeight == minStackHeight) {
                options.add(r+1);
                if (! board.containsColor(r, 0, players.get(currentPlayer).getColor())) {
                    nonBlockingOptionExists = true;
                }
            }
            allRows.add(r+1);
        }
        if (nonBlockingOptionExists) {
            return options;
        }
        else {
            return allRows;
        }
    }

    /**
     * Set possible column move options in the Main phase.
     * @return options.
     */
    private ArrayList<Integer> collectMoveOptions(int row) {
        ArrayList<Integer> options = new ArrayList<>();
        for (int c = 0; c < Board.COLUMNS - 1; c++){
            if (!board.isEmpty(row,c) && !board.isActiveTrap(row,c)){
                options.add(c+1);
            }
        }
        return options;
    }

    /**
     * Set possible row options for the slide move in the Main phase.
     * @return options.
     */
    private ArrayList<Integer> collectSlideRowOptions(int currentPlayer){
        ArrayList<Integer> options = new ArrayList<>();
        options.add(0);
        for (int r = 0; r < Board.ROWS; r++){
            if (!board.isEmptyRow(r) && board.containsColorOnTop(r,players.get(currentPlayer).getColor())){
                options.add(r+1);
            }
        }
        return options;
    }

    /**
     * Set possible column options for the slide move in the Main phase.
     * @return options.
     */
    private ArrayList<Integer> collectSlideColOptions(int row, int currentPlayer){
        ArrayList<Integer> options = new ArrayList<>();
        for (int c = 0; c < Board.COLUMNS - 1; c++){
            if (!board.isActiveTrap(row,c) && board.colorOnTop(row,c,players.get(currentPlayer).getColor())){
                options.add(c+1);
            }
        }
        return options;
    }

    /**
     * Set possible direction options for the slide move in the Main phase.
     * @return options.
     */
    private ArrayList<Integer> collectSlideDirOptions(int row, int currentPlayer){
        ArrayList<Integer> options = new ArrayList<>();
        if (row == 0){
            options.add(1);
        }
        else if (row == Board.ROWS - 1){
            options.add(-1);
        }
        else{
            options.add(1);
            options.add(-1);
        }
        return options;
    }

    /**
     * Check if an entered string is a digit.
     * @param str the input string
     * @return true or false
     */
    private boolean isDigit(String str) {
        return str.matches("\\d");
    }

    /**
     * Check if the number is in a certain range.
     * @param numToTest the number to test
     * @param start the lowest number in the range
     * @param end the highest number in the range
     * @return true or false
     */
    private boolean isInRange(int numToTest, int start, int end) {
        return numToTest >= start && numToTest <= end;
    }

    /**
     * Validate and return the input for row or column request in the Main Phase.
     * @return validated input
     */
    private String requestAndValidateInput(String requestMessage, String errorMessage, ArrayList<Integer> options){
        String input = view.requestInput(requestMessage);
        while (! isDigit(input) || ! options.contains(Integer.decode(input))) {
            input = view.requestInput(errorMessage);
        }
        return input;
    }


    /**** Converter Methods ****/
    private int decodeDir(String dir){
        int value = 0;
        if (dir.equals("U")){
            value = -1;
        }
        else if (dir.equals("D")){
            value = 1;
        }
        return value;
    }

    private ArrayList<String> decodeDirOptions(ArrayList<Integer> options){
        ArrayList<String> newOptions = new ArrayList<>();
        for (int option:options){
            if (option == -1){
                newOptions.add("U");
            }
            else if (option == 1){
                newOptions.add("D");
            }
        }
        return newOptions;
    }
}
