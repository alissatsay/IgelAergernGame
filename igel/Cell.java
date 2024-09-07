/**
 * Model a cell of an Igel Aergern Board.
 * 
 * @author: Alissa Tsay
 * @version: Winter 2024
 * I affirm that I have carried out my academic endeavors
 * with full academic honesty. [Alissa Tsay]
 */
package igel;

//import java.util.ArrayList;

public class Cell {
    private String cell;

    /**
     * Create a single cell of an Igel Aergern board.
     */
    public Cell() {
        this.cell = "";
    }

    /**
     * Create a single cell of an Igel Aergern board from agiven string.
     */
    public Cell(String cellStr) {
        this.cell = cellStr;
    }

    /**
     * Toggle whether or not this cell is a trap. If the cell is NOT a trap, 
     * call this method turns it into one. If the cell IS a trap, calling this
     * method turns it into a regular cell. 
     */
    public void toggleTrap() {
        int id = this.cell.indexOf("@");
        if (id != -1){
            this.cell = this.cell.replace("@", "");
        }
        else{
            this.cell = "@" + this.cell;
        }
    }

    /**
     * Checks whether the cell is a trap.
     * 
     * @return
     */
    public boolean isTrap() {
        boolean flag;
        if (this.cell.contains("@")){
            flag = true;
        }
        else{
            flag = false;
        }
        return flag;
    }

    /**
     * Return a string representation of the cell that is suitable for the IgelView
     * interface. E.g. 'RBO' represents a stack of red at the bottom, blue, and orange.
     * '@BY' represents a cell that is a trap with a stack of blue at the bottom and
     * yellow on top.
     */
    public String toString() {
        String repr = "";
        repr = this.cell;
        return repr;
    }

    /**
     * Add a token of a player into the cell.
     * 
     * @param color the color of the player who's token you want to add
     */
    public void addColor(char color) {
        this.cell += color;
    }

    /**
     * Remove a token of a player from the top of the stack in the cell.
     */
    public void removeColor() {
        this.cell = this.cell.substring(0,this.cell.length()-1);
    }

    /**
     * Return the color of the player on top of the stack.
     */
    public char topColor() {
        String stringColor = this.cell.substring(this.cell.length()-1,this.cell.length());
        char color = stringColor.charAt(0);
        return color;
    }

    /**
     * Check how many tokens of the player are in the stack.
     * 
     * @param color the color of the player who's tokens you want to count
     * @return int the number of times the colors appers in the stack
     */
    public int containsColor(char color) {
        int count = 0;
    
        for(int i=0; i < cell.length(); i++){   
            if(cell.charAt(i) == color)
                count++;
            }
        return count;
    }

    /**
     * Check how many tokens there are in a stack.
     * @return int number of tokens
     */
    public int countColors() {
        int count = 0;
    
        for(int i=0; i < cell.length(); i++){   
            if(cell.charAt(i) != '@')
                count++;
            }
        return count;
    }
    
}
