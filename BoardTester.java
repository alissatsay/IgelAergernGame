import igel.Board;
import igel.Cell;

public class BoardTester {
    
    private static void testConstructor() {
        Board board = new Board();
        String expected = "|||@|||||//||||||@||//||||@||||//|||||@|||//||@||||||//|||||||@|";
        String actual = board.toString();
        Testing.assertEquals("Constructing a new board with empty cells.", expected, actual);
    }

    private static void testPlaceToken() {
        Board board = new Board();
        board.placeToken(0,0,'R');
        String expected = "R|||@|||||//||||||@||//||||@||||//|||||@|||//||@||||||//|||||||@|";
        String actual = board.toString();
        Testing.assertEquals("Placing a token on the board.", expected, actual);
    }

    private static void testSlide() {
        Board board = new Board();
        board.placeToken(1,0,'R');
        board.placeToken(1,0,'Y');
        board.moveSideways(1,0,-1);
        String expected = "Y|||@|||||//R||||||@||//||||@||||//|||||@|||//||@||||||//|||||||@|";
        String actual = board.toString();
        Testing.assertEquals("Moving a token up.", expected, actual);

        board.moveSideways(0,0,1);
        expected = "|||@|||||//RY||||||@||//||||@||||//|||||@|||//||@||||||//|||||||@|";
        actual = board.toString();
        Testing.assertEquals("Moving a token down.", expected, actual);
    }

    private static void testMove() {
        Board board = new Board();
        board.placeToken(1,0,'R');
        board.placeToken(1,0,'Y');
        board.moveForward(1,0);
        String expected = "|||@|||||//R|Y|||||@||//||||@||||//|||||@|||//||@||||||//|||||||@|";
        String actual = board.toString();
        Testing.assertEquals("Moving a token forward.", expected, actual);
    }

    private static void testCountColumn() {
        // Create board of type: "||||||||//RY||||||||//Y||||||||//||||||||//||||||||//||||||||"
        Board board = new Board();
        board.placeToken(1,0,'R');
        board.placeToken(1,0,'Y');
        board.placeToken(2,0,'Y');
        int expected = 0;
        int actual = board.countTokensInColumn(1, 'Y');
        Testing.assertEquals("Counting 'Y' tokens in an empty column.", expected, actual);

        expected = 2;
        actual = board.countTokensInColumn(0, 'Y');
        Testing.assertEquals("Counting 'Y' tokens in a non-empty column.", expected, actual);
    }

    private static void testContainsColor() {
        // Create board of type: "||||||||//RY||||||||//Y||||||||//||||||||//||||||||//||||||||"
        Board board = new Board();
        board.placeToken(1,0,'R');
        board.placeToken(1,0,'Y');
        board.placeToken(2,0,'Y');
        boolean expected = false;
        boolean actual = board.containsColor(0, 0, 'R');
        Testing.assertEquals("Checking if 'R' token is in an empty cell.", expected, actual);

        expected = false;
        actual = board.containsColor(2, 0, 'R');
        Testing.assertEquals("Checking if 'R' token is in an cell which contains only 'Y'.", expected, actual);

        expected = true;
        actual = board.containsColor(1, 0, 'R');
        Testing.assertEquals("Checking if 'R' token is in an cell which contains 'RY'.", expected, actual);
    }

    private static void testColorOnTop() {
        // Create board of type: "||||||||//RY|Y|||||||//YR||||||||//||||||||//||||||||//||||||||"
        Board board = new Board();
        board.placeToken(1,0,'R');
        board.placeToken(1,0,'Y');
        board.placeToken(1,1,'Y');
        board.placeToken(2,0,'Y');
        board.placeToken(2,0,'R');
        boolean expected = false;
        boolean actual = board.colorOnTop(2, 0, 'Y');
        Testing.assertEquals("Checking if 'Y' token is on top in 'YR'.", expected, actual);

        expected = true;
        actual = board.colorOnTop(1, 0, 'Y');
        Testing.assertEquals("Checking if 'Y' token is on top in 'RY'.", expected, actual);

        expected = true;
        actual = board.colorOnTop(1, 1, 'Y');
        Testing.assertEquals("Checking if 'Y' token is on top in 'Y'.", expected, actual);
    }

    private static void testStackHeight() {
        // Create board of type: "||||||||//RYYR||||||||//YR||||||||//||||||||//||||||||//||||||||"
        Board board = new Board();
        board.placeToken(1,0,'R');
        board.placeToken(1,0,'Y');
        board.placeToken(1,0,'Y');
        board.placeToken(1,0,'R');
        board.placeToken(2,0,'Y');
        board.placeToken(2,0,'R');
        int expected = 0;
        int actual = board.stackHeight(0,0);
        Testing.assertEquals("Counting tokens in an empty cell.", expected, actual);

        expected = 2;
        actual = board.stackHeight(2,0);
        Testing.assertEquals("Counting tokens in an half-filled cell.", expected, actual);

        expected = 4;
        actual = board.stackHeight(1,0);
        Testing.assertEquals("Counting tokens in a filled cell.", expected, actual);
    }

    private static void testIsEmpty() {
        // Create board of type: "|@|||||||//RY||||||||//||||||||//||||||||//||||||||//||||||||"
        Board board = new Board();
        board.placeToken(1,0,'R');
        board.placeToken(1,0,'Y');
        boolean expected = true;
        boolean actual = board.isEmpty(0, 0);
        Testing.assertEquals("Checking if an empty cell is empty.", expected, actual);

        expected = true;
        actual = board.isEmpty(0, 1);
        Testing.assertEquals("Checking if an empty cell with a trap is empty.", expected, actual);

        expected = false;
        actual = board.isEmpty(1, 0);
        Testing.assertEquals("Checking if a non-empty cell is empty.", expected, actual);
    }

    private static void testIsActiveTrap() {
        // Create board of type: "|||@R|||||//|RY|||||@||//||||@||||//|||||@|||//||@||||||//|||||||@|"
        Board board = new Board();
        board.placeToken(0,3,'R');
        board.placeToken(1,2,'R');
        board.placeToken(1,2,'Y');
        boolean expected = true;
        boolean actual = board.isActiveTrap(0, 3);
        Testing.assertEquals("Checking if an active trap with a token is an active trap.", expected, actual);

        expected = true;
        actual = board.isActiveTrap(1, 6);
        Testing.assertEquals("Checking if an active trap without a token is an active trap.", expected, actual);

        expected = false;
        actual = board.isActiveTrap(0, 2);
        Testing.assertEquals("Checking if a non-trap cell without tokens is an active trap.", expected, actual);

        expected = false;
        actual = board.isActiveTrap(1, 2);
        Testing.assertEquals("Checking if a non-trap cell with tokens is an active trap.", expected, actual);
    }

    private static void testDeactivateTrap() {
        // Create board of type: "|||@R|||||//||||||@||//||||@||||//|||||@|||//||@||||||//|||||||@|"
        Board board = new Board();
        board.placeToken(0,3,'R');
        board.deactivateTrap(0,3);
        boolean expected = false;
        boolean actual = board.isActiveTrap(0, 3);
        Testing.assertEquals("Deactivating a trap.", expected, actual);
    }

    private static void testIsEmptyRow(){
        Board board = new Board("|||@R|||||//|R|||||@||//||||@||||Y//|||||@|||//||@||||||//|||||||@|");
        boolean expected = false;
        boolean actual = board.isEmptyRow(0);
        Testing.assertEquals("Checking if a non-empty row with a trap is empty.", expected, actual);

        expected = false;
        actual = board.isEmptyRow(1);
        Testing.assertEquals("Checking if a non-empty row without a trap is empty.", expected, actual);

        expected = true;
        actual = board.isEmptyRow(5);
        Testing.assertEquals("Checking if an empty row is empty.", expected, actual);
    }

    private static void testIsEmptyColumn(){
        Board board = new Board("|||@R|||||//|R|||||@||//||||@||||Y//|||||@|||//||@||||||//|||||||@|");
        boolean expected = true;
        boolean actual = board.isEmptyColumn(6);
        Testing.assertEquals("Checking if an empty column with a trap is empty.", expected, actual);

        expected = false;
        actual = board.isEmptyColumn(1);
        Testing.assertEquals("Checking if a non-empty column without a trap is empty.", expected, actual);

        expected = true;
        actual = board.isEmptyColumn(0);
        Testing.assertEquals("Checking if an empty column is empty.", expected, actual);
    }

    private static void testNoTokensLeftBehind(){
        Board board = new Board("|||@R|||||YR//||||||@||Y//||||@||||Y//|||||@|||//||@||||||//|||||||@|");
        boolean expected = true;
        boolean actual = board.noTokensLeftBehind(0);
        String message = "Checking if there are no tokens in columns 1-3";
        Testing.assertEquals(message, expected, actual);

        expected = false;
        actual = board.noTokensLeftBehind(3);
        message = "Checking if there are no tokens left in columns 1-5.";
        Testing.assertEquals(message, expected, actual);
    }

    public static void testAdjustAllTraps(){
        Board board = new Board("|||@R||||R|YR//||R||||@||Y//||||@||||Y//|||||@|||//||@||||||//|||||||@|");
        boolean expected  = true;
        boolean actual = board.isActiveTrap(0,3);
        String message = "Checking if a newly created trap cell is a trap";
        Testing.assertEquals(message, expected, actual);

        board.moveForward(1,2);
        board.adjustAllTraps();
        expected  = false;
        actual = board.isActiveTrap(0,7);
        message = "Checking if that cell is a trap after adjusting.";
        Testing.assertEquals(message, expected, actual);
    }

    public static void testContainsColorOnTop(){
        Board board = new Board("|||@R||||R|YR//|R|||||@||Y//||||@||||Y//|||||@|||//||@||||||//|||||||@|");
        boolean expected  = true;
        boolean actual = board.containsColorOnTop(0,'R');
        String message = "Checking if a row that contains 'R' on top has 'R' on top";
        Testing.assertEquals(message, expected, actual);

        expected  = false;
        actual = board.containsColorOnTop(2,'R');
        message = "Checking if a row that does not contain 'R' on top has 'R' on top";
        Testing.assertEquals(message, expected, actual);
    }

    public static void main(String[] args) {
        Testing.setVerbose(true);
        Testing.startTests();
        Testing.testSection("Board class");
        testConstructor();
        testPlaceToken();
        testSlide();
        testMove();
        testCountColumn();
        testContainsColor();
        testColorOnTop();
        testStackHeight();
        testIsEmpty();
        testIsActiveTrap();
        testDeactivateTrap();
        testIsEmptyRow();
        testIsEmptyColumn();
        testNoTokensLeftBehind();
        testAdjustAllTraps();
        testContainsColorOnTop();
        Testing.finishTests();
    }
}