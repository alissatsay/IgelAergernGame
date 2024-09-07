/**
 * Driver for playeing one full game if Igel Aergern.
 * @author: Kristina Striegnitz, Alissa Tsay
 * @version: Winter 2024
    I affirm that I have carried out my academic endeavors
    with full academic honesty. [Alissa Tsay]
 */
package igel;

public class Driver {
    public static void main(String[] args) {
        //IgelAergern game = new IgelAergern();
        //game.play();
        //Code for testing the game from a given position
        IgelAergern game = new IgelAergern("|||@Y|||||YRG//||||||@|Y|//||||@R||R||//|||||@||GY|//||@G||||||G//||||||G|@|R");
        game.testPlay();
    }
}