//For when you don't have a graphics setting i.e. chromebooks >:(

public class FakeGUI {
    
    Board board;
    //Colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public FakeGUI(Board b) {
        board = b;
    }

    /*
    * This is what the board looks like
    
          a   b   c   d   e   f   g   h
        ---------------------------------
      8 | R | N | B | Q | K | B | N | R | 8
        ---------------------------------
      7 | P | P | P | P | P | P | P | P | 7
        ---------------------------------
      6 | + | + | + | + | + | + | + | + | 6
        ---------------------------------
      5 | + | + | + | + | + | + | + | + | 5
        ---------------------------------
      4 | + | + | + | + | + | + | + | + | 4
        ---------------------------------
      3 | + | + | + | + | + | + | + | + | 3
        ---------------------------------
      2 | P | P | P | P | P | P | P | P | 2
        ---------------------------------
      1 | R | N | B | Q | K | B | N | R | 1
        ---------------------------------
          a   b   c   d   e   f   g   h
    */

    //Draw board as pictured above
    public void drawBoard() {
		
		String s = "      a   b   c   d   e   f   g   h" + "\n";
        s += "    ---------------------------------" + "\n";

        //Loop through every rank
        for (int y = 0; y < 8; y++) {

            s += "  " + (8 - y) + " | ";
            //Loop through every file
            for (int x = 0; x < 8; x++) {

                //Get index from position
                int index = board.getBoard()[x][y];

                //Get piece type from index
                boolean isWhite = (index & 8) > 7;

				String pieceLetter = "";
                switch (index & 7) {
                	case 1: pieceLetter = "P"; break;
                    case 2: pieceLetter = "R"; break;
                    case 3: pieceLetter = "N"; break;
                    case 4: pieceLetter = "B"; break;
                    case 5: pieceLetter = "Q"; break;
                    case 6: pieceLetter = "K"; break;
				}

				pieceLetter = isWhite ? ANSI_BLACK + pieceLetter + ANSI_RESET: ANSI_WHITE + pieceLetter + ANSI_RESET;
				
            }
        }
    }

}
