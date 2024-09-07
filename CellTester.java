import igel.Cell;

public class CellTester {

    private static void testConstructor() {
        Cell cell = new Cell();
        String expected = "";
        String actual = cell.toString();
        Testing.assertEquals("Constructing a new empty cell.", expected, actual);
    }

    private static void testTrap() {
        Cell cell = new Cell();

        cell.toggleTrap();
        String expected = "@";
        String actual = cell.toString();
        Testing.assertEquals("Making a newly created cell a trap.", expected, actual);

        cell.toggleTrap();
        expected = "";
        actual = cell.toString();
        Testing.assertEquals("Making a trap cell a regular cell.", expected, actual);
    }

    private static void testIsTrap() {
        Cell cell = new Cell();
        boolean expected = false;
        boolean actual = cell.isTrap();
        Testing.assertEquals("Checking if an empty cell is a trap.", expected, actual);

        cell.toggleTrap();
        expected = true;
        actual = cell.isTrap();
        Testing.assertEquals("Checking if an trap cell is a trap.", expected, actual);
    }

    private static void testAddColor() {
        Cell cell = new Cell();
        cell.addColor('R');
        String expected = "R";
        String actual = cell.toString();
        Testing.assertEquals("Adding a color into the cell.", expected, actual);
    }

    private static void testRemoveColor() {
        Cell cell = new Cell();
        cell.addColor('R');
        cell.addColor('Y');
        cell.removeColor();
        String expected = "R";
        String actual = cell.toString();
        Testing.assertEquals("Removing a color from the cell.", expected, actual);
    }

    private static void testTopColor() {
        Cell cell = new Cell();
        cell.addColor('R');
        cell.addColor('Y');
        char expected = 'Y';
        char actual = cell.topColor();
        Testing.assertEquals("The color on top of the stack in a cell.", expected, actual);
    }

    private static void testContainsColor() {
        // Checking method on an empty cell
        Cell cell = new Cell();
        int expected = 0;
        int actual = cell.containsColor('R');
        Testing.assertEquals("How many 'R' tokens in an empty cell.", expected, actual);

        // Testing the method on a cell with one token of given color
        cell.addColor('R');
        cell.addColor('Y');
        expected = 1;
        actual = cell.containsColor('R');
        Testing.assertEquals("How many 'R' tokens in a cell 'RY'.", expected, actual);

        // Testing the method on a cell with multiple tokens of given color
        cell.addColor('R');
        expected = 2;
        actual = cell.containsColor('R');
        Testing.assertEquals("How many 'R' tokens in a cell 'RYR'.", expected, actual);
    }

    private static void testCountColors() {
        // Checking method on an empty cell
        Cell cell = new Cell();
        int expected = 0;
        int actual = cell.countColors();
        Testing.assertEquals("Counting tokens in an empty cell.", expected, actual);

        // Testing the method on a cell with multiple tokens
        cell.addColor('R');
        cell.addColor('Y');
        cell.addColor('R');
        expected = 3;
        actual = cell.countColors();
        Testing.assertEquals("Counting tokens in a cell 'RYR'.", expected, actual);

        // Testing the method on a cell with multiple tokens
        cell.toggleTrap();
        expected = 3;
        actual = cell.countColors();
        Testing.assertEquals("Counting tokens in a cell 'RYR' with a trap.", expected, actual);
    }

    public static void main(String[] args) {
        Testing.setVerbose(true);
        Testing.startTests();
        Testing.testSection("Cell class");
        testConstructor();
        testTrap();
        testIsTrap();
        testAddColor();
        testRemoveColor();
        testTopColor();
        testContainsColor();
        testCountColors();
        Testing.finishTests();
    }
}
