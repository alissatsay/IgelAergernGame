/**
 * Model an Igel Aergern Board.
 * 
 * @author: Alissa Tsay
 * @version: Winter 2024
 * I affirm that I have carried out my academic endeavors
 * with full academic honesty. [Alissa Tsay]
 */
package igel;

public class Board {
    
    private Cell[][] board;
    public static final int ROWS = 6;
    public static final int COLUMNS = 9;
    public static final int[] TRAPS = {3, 6, 4, 5, 2, 7};

    /**
     * Create an empty Igel Aergern board. The board has 6 rows and 9 columns. 
     * The traps are in colums 3, 6, 4, 5, 2, 7 (one per row from row 1 to row 6).
     */
    public Board () {
        board = new Cell[ROWS][1];
        for (int i = 0; i < ROWS; i++){
            Cell[] row = new Cell[COLUMNS];
            board[i] = row;
            for (int j= 0; j< COLUMNS; j++){
                Cell cell = new Cell();
                row[j] = cell;
                for (int id = 0; id < TRAPS.length; id++){
                    if (id == i && TRAPS[id] == j){
                        cell.toggleTrap();
                    }
                }
            }
        }
    }

    /**
     * Create an Igel Aergern board from a given string. The board has 6 rows and 9 columns. 
     * The traps are in colums 3, 6, 4, 5, 2, 7 (one per row from row 1 to row 6).
     */
    public Board (String bStr) {
        board = new Cell[ROWS][1];
        for (int i = 0; i < ROWS; i++){
            int rowIndex = bStr.indexOf("//");
            String rowStr;
            if(rowIndex != -1){
                rowStr = bStr.substring(0,rowIndex);
            }
            else{
                rowStr = bStr;
            }
            Cell[] row = new Cell[COLUMNS];
            board[i] = row;
            for (int j= 0; j< COLUMNS; j++){
                int colIndex = rowStr.indexOf("|");
                String cellStr;
                if (colIndex != -1){
                    cellStr = rowStr.substring(0,colIndex);
                }
                else{
                    cellStr= rowStr;
                }
                Cell cell = new Cell(cellStr);
                row[j] = cell;
                rowStr = rowStr.substring(colIndex+1, rowStr.length());
            }
            bStr = bStr.substring(rowIndex+2, bStr.length());
        }
    }

    /**
     * Return a string representation of the board that is suitable to be passed 
     * to an IgelView interface.
     * 
     * The tracks/rows are separated from each other using // and cells within one 
     * track are separated using |. Empty cells are represented using an empty string. 
     * Otherwise they are represented as a sequence of the stacked colors. E.g. 'RBO' 
     * represents a stack of red at the bottom, blue, and orange.
     * 
     * Here is an example of a valid board representation:
     * "Y||||||||//||||||||Y//Y||R||B||O||//YR|RBO||RBOO|||||//||||||||//||BR||||||O"
     */
    public String toString() {
        String repr = "";
         for (int r = 0; r < ROWS; r++) {
             for (int c = 0; c < COLUMNS; c++) {
                 repr += board[r][c].toString();
                 if (c < COLUMNS-1) {
                     repr += "|";
                 }
             }
             if (r < ROWS-1) {
                 repr += "//";
             }
         }
        return repr;
    }

    /**
     * Create a new token of the given color and place it on the board.
     * 
     * @param row row number where to place the token
     * @param col column number where to place the token
     * @param color character indicating the color; has to be from Players.COLORS
     */
    public void placeToken(int row, int col, char color) {
        Cell cell = board[row][col];
        cell.addColor(color);
    }

    /**
     * Move the top token in the cell indicated by the row and column numbers sideways.
     * 
     * @param row row number of token to move
     * @param col column number of token to move
     * @param dir a negative number indicates a sideways move to row-1; otherwise move to row+1
     */
    public void moveSideways(int row, int col, int dir) {
        Cell cell = board[row][col];
        Cell targetCell;
        char color = cell.topColor();
        cell.removeColor();
        if (dir < 0 && row > 0){
            targetCell = board[row-1][col];
            targetCell.addColor(color);
        }
        else if (dir >= 0 && row < ROWS-1){
            targetCell = board[row+1][col];
            targetCell.addColor(color);
        }
    }

    /**
     * Move the top token in the cell indicated by the row and column numbers 1 step forward.
     * 
     * Note: This method does no checking whether there is a token in that cell and whether 
     * it is legal to move it forward.
     * 
     * @param row row number of token to move
     * @param col column number of token to move
     */
    public void moveForward(int row, int col) {
        Cell cell = board[row][col];
        Cell targetCell = board[row][col+1];
        char color = cell.topColor();
        cell.removeColor();
        targetCell.addColor(color);
    }

    /**
     * Return how many tokens of the given color are in the given column.
     * 
     * @param col
     * @param color  
     * @return 
     */
    public int countTokensInColumn(int col, char color) {
        int count = 0;
        for (int r = 0; r < ROWS; r++){
            Cell cell = board[r][col];
            count += cell.containsColor(color);
        }
        return count;
    }

    /**
     * Check whether the stack in the cell indicated by row and col contains a
     * token of the given color.
     * 
     * @param row
     * @param col
     * @param color
     * @return
     */
    public boolean containsColor(int row, int col, char color) {
        boolean flag = false;
        int count = 0;
        Cell cell = board[row][col];
        count = cell.containsColor(color);
        if (count > 0){
            flag = true;
        }
        return flag;
    }

    /**
     * Check whether the top most token in the cell indicated by row and col is 
     * of the given color.
     * 
     * @param row
     * @param col
     * @param player
     * @return
     */
    public boolean colorOnTop(int row, int col, char color) {
        boolean flag = false;
        Cell cell = board[row][col];
        if (!isEmpty(row,col)){
            char realColor = cell.topColor();
            if (realColor == color){
                flag = true;
        }
        }
        return flag;
    }

    /**
     * Return the number of tokens in the stack in the cell indicated by row and col.
     * @param row
     * @param col
     * @return
     */
    public int stackHeight(int row, int col) {
        int count = 0;
        Cell cell = board[row][col];
        count = cell.countColors();
        return count;
    }

    /**
     * Check whether the cell indicated by row and col is empty.
     * @param row
     * @param col
     * @return
     */
    public boolean isEmpty(int row, int col) {
        boolean flag = false;
        int count = 0;
        Cell cell = board[row][col];
        count = cell.countColors();
        if (count == 0){
            flag = true;
        }
        return flag;
    }

    /**
     * Check whether the cell indicated by row and col is an active trap.
     * @param row
     * @param col
     * @return
     */
    public boolean isActiveTrap(int row, int col) {
        boolean flag = false;
        Cell cell = board[row][col];
        flag = cell.isTrap();
        return flag;
    }    

    /**
     * Deactivate the trap at the cell indicated by row and col (if that cell is a trap).
     * @param row
     * @param col
     * @return
     */
    public void deactivateTrap(int row, int col) {
        Cell cell = board[row][col];
        cell.toggleTrap();
    }

    /**
     * Return is the specified row has no tokens in columns 1-8.
     * 
     * @param row 
     * @return boolean
     */
    public boolean isEmptyRow(int row) {
        int count = 0;
        boolean flag = false;
        for (int c = 0; c < COLUMNS-1; c++){
            Cell cell = board[row][c];
            count += cell.countColors();
        }
        if (count == 0){
            flag = true;
        }
        return flag;
    }

    /**
     * Return is the specified column has no tokens.
     * 
     * @param row 
     * @return boolean
     */
    public boolean isEmptyColumn(int col) {
        int count = 0;
        boolean flag = false;
        for (int r = 0; r < ROWS; r++){
            Cell cell = board[r][col];
            count += cell.countColors();
        }
        if (count == 0){
            flag = true;
        }
        return flag;
    }

    /**
     * Return is the specified row has at least one token of color on top of the stack in columns 1-8.
     * 
     * @param row
     * @param color 
     * @return boolean
     */
    public boolean containsColorOnTop(int row, char color) {
        boolean flag = false;
        for (int c = 0; c < COLUMNS-1; c++){
            if (colorOnTop(row,c,color) && !isActiveTrap(row,c)){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Check if all non-trap cells in the row are empty.
     * @param row
     * @return boolean
     */
    public boolean noTokensLeftBehind(int row){
        boolean flag = true;
        for (int c = 0; c < TRAPS[row]; c++){
            if (!isEmptyColumn(c)){
                flag = false;
            }
        }
        return flag;
    }
    
    /**
     * Deactivate traps in any row, if all non-trap cells in the row are empty.
     * @param row
     */
    public void adjustAllTraps(){
        for (int r = 0; r < ROWS; r++){
            if (noTokensLeftBehind(r)){
                for (int c = 0; c < COLUMNS-1; c++){
                    if (isActiveTrap(r,c)){
                        deactivateTrap(r,c);
                    }
                }
            }
        }
    }

}
