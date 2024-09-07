/**
 * Model a player in Igel Aergern game.
 * @author: Kristina Striegnitz, Alissa Tsay
 * @version: Winter 2024
    I affirm that I have carried out my academic endeavors
    with full academic honesty. [Alissa Tsay]
 */
package igel;

public class Player {
    public static final char[] COLORS = {'Y', 'R', 'G', 'P', 'B', 'O'};
    private char color;

    /**
     * Create a player. The player's tokens are of the given color. The color 
     * has to be one of Player.COLORS.
     * 
     * @param color
     */
    public Player(char color) {
        this.color = color;
    }

    /**
     * Get the color of the player.
     * 
     * @return color
     */
    public char getColor() {
        return color;
    }

    /**
     * Create a string representation of the player.
     * 
     * @return color as a string
     */
    public String toString(){
        return Character.toString(color);
    }
}
