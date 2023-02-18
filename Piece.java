import java.util.*;

public class Piece {

    //// Methods for piece moving ////
    public static ArrayList<Integer> getLegalMoves(Board b, int xpos, int ypos) {

        //Figure out which piece is moving
        int index = b.getBoard()[xpos][ypos];

        //Get just the piece identification, no color
        int pieceID = index & 7;

        //No actual piece selected
        if (pieceID <= 0)
            return null;

        //ArrayList of moves in integer form
        ArrayList<Integer> moves = new ArrayList<Integer>();

        //Find which piece is moving
        switch (pieceID) {

            //Pawn
            case 1:
                moves = getPawnMoves(b, xpos, ypos);
            break;

            //Rook
            case 2:
                moves = getRookMoves(b, xpos, ypos);
            break;

            //Knight
            case 3:
                moves = getKnightMoves(b, xpos, ypos);
            break;

            //Bishop
            case 4:
                moves = getBishopMoves(b, xpos, ypos);
            break;

            //Queen
            case 5:
                moves = getQueenMoves(b, xpos, ypos);
            break;

            //King
            case 6:
                moves = getKingMoves(b, xpos, ypos);
            break;
        }

        //No piece is selected
        return moves;
    }

    //Pawn
    public static ArrayList<Integer> getPawnMoves(Board b, int xpos, int ypos) {

        //Start move list
        ArrayList<Integer> moves = new ArrayList<Integer>();

        int[][] board = b.getBoard();

        int index = board[xpos][ypos];

        //Get color from board
        boolean isWhite = (index & 8) > 7;

        //Direction of advancement
        int direction = isWhite ? -1 : 1;

        //Straight forward
        if (board[xpos][ypos + direction] <= 0) {
            moves.add(xpos + (ypos + direction) * 8);
        }

        //Diagonal takes
        if (xpos > 0) {
            int otherIndex = board[xpos - 1][ypos + direction];
            if (otherIndex > 0 && (index ^ otherIndex) > 7)
                moves.add(xpos - 1 + (ypos + direction) * 8);
        }
        if (xpos < 7) {
            int otherIndex = board[xpos + 1][ypos + direction];
            if (otherIndex > 0 && (index ^ otherIndex) > 7)
                moves.add(xpos + 1 + (ypos + direction) * 8);
        }

        //TODO: En Passant logic

        //Return completed move list
        return moves;
    }

    //Rook
    public static ArrayList<Integer> getRookMoves(Board b, int xpos, int ypos) {

        //Literally just linear moves :)
        return getLinearMoves(b, xpos, ypos);
    }

    //Knight
    public static ArrayList<Integer> getKnightMoves(Board b, int xpos, int ypos) {

        //Start move list
        ArrayList<Integer> moves = new ArrayList<Integer>();

        int[][] board = b.getBoard();

        int index = board[xpos][ypos];

        //Array of all l-shapes
        //Order:     NE NE SE SE SW SW NW NW
        int[] xL = { 1, 2, 2, 1,-1,-2,-2,-1 };
        int[] yL = {-2,-1, 1, 2, 2, 1,-1,-2 };

        //Loop through L-shapes and list which ones are legal
        for (int i = 0; i < 8; i++) {

            //Out of bounds
            if (xpos + xL[i] < 0 || xpos + xL[i] >= 8)
                continue;
            if (ypos + yL[i] < 0 || ypos + yL[i] >= 8)
                continue;
            
            //Occupied by same color piece
            int otherIndex = board[xpos + xL[i]][ypos + yL[i]];
            if (otherIndex > 0 && (index ^ otherIndex) < 8)
                continue;

            //Passes all requirements
            moves.add(xpos + xL[i] + (ypos + yL[i]) * 8);
        }

        //Return complete list
        return moves;
    }

    //Bishop
    public static ArrayList<Integer> getBishopMoves(Board b, int xpos, int ypos) {

        //Literally just diagonal moves :)
        return getDiagonalMoves(b, xpos, ypos);
    }

    //Queen
    public static ArrayList<Integer> getQueenMoves(Board b, int xpos, int ypos) {

        //Linear + diagonal moves

        //Linear moves
        ArrayList<Integer> moves = getLinearMoves(b, xpos, ypos);

        //Diagonal moves
        moves.addAll(getDiagonalMoves(b, xpos, ypos));

        //Return complete list
        return moves;
    }

    //King
    public static ArrayList<Integer> getKingMoves(Board b, int xpos, int ypos) {

        //Start move list
        ArrayList<Integer> moves = new ArrayList<Integer>();

        int[][] board = b.getBoard();

        int index = board[xpos][ypos];

        //Loop in a square around the king
        for (int xChange = -1; xChange <= 1; xChange++) {
            for (int yChange = -1; yChange <= 1; yChange++) {

                //Out of bounds
                if (xpos + xChange < 0 || xpos + xChange >= 8)
                    continue;
                if (ypos + yChange < 0 || ypos + yChange >= 8)
                    continue;

                //Occupied by same color piece
                int otherIndex = board[xpos + xChange][ypos + yChange];
                if (otherIndex > 0 && (index ^ otherIndex) < 8)
                    continue;

                //Passes all requirements
                moves.add(xpos + xChange + (ypos + yChange) * 8);
            }
        }

        //Return complete list
        return moves;
    }

    //Method for moving in lines like a rook
    public static ArrayList<Integer> getLinearMoves(Board b, int xpos, int ypos) {

        //Start move list
        ArrayList<Integer> moves = new ArrayList<Integer>();

        int[][] board = b.getBoard();

        //Get index from board
        int index = board[xpos][ypos];

        //Up
        for (int y = ypos; y >= 0; y--) {
            //Check for piece in the way
            if (board[xpos][y] > 0) {
                //Same color
                if ((board[xpos][y] ^ index) < 8) {
                    break;
                } 
                //Different color
                else {
                    moves.add(xpos + y * 8);
                    break;
                }
            }
            
            moves.add(xpos + y * 8);
        }

        //Down
        for (int y = ypos; y < 8; y++) {
            //Check for piece in the way
            if (board[xpos][y] > 0) {
                //Same color
                if ((board[xpos][y] ^ index) < 8) {
                    break;
                } 
                //Different color
                else {
                    moves.add(xpos + y * 8);
                    break;
                }
            }
            
            moves.add(xpos + y * 8);
        }

        //Left
        for (int x = xpos; x >= 0; x--) {
            //Check for piece in the way
            if (board[x][ypos] > 0) {
                //Same color
                if ((board[x][ypos] ^ index) < 8) {
                    break;
                } 
                //Different color
                else {
                    moves.add(x + ypos * 8);
                    break;
                }
            }
            
            moves.add(x + ypos * 8);
        }

        //Right
        for (int x = xpos; x < 8; x++) {
            //Check for piece in the way
            if (board[x][ypos] > 0) {
                //Same color
                if ((board[x][ypos] ^ index) < 8) {
                    break;
                } 
                //Different color
                else {
                    moves.add(x + ypos * 8);
                    break;
                }
            }
            
            moves.add(x + ypos * 8);
        }

        //Return completed list
        return moves;
    }

    //Method for moving diagonally like a bishop
    public static ArrayList<Integer> getDiagonalMoves(Board b, int xpos, int ypos) {

        //Start move list
        ArrayList<Integer> moves = new ArrayList<Integer>();

        int[][] board = b.getBoard();

        //Get index from board
        int index = board[xpos][ypos];

        //Temp position indicators
        int x = xpos, y = ypos;

        //NE
        while (x < 8 && y >= 0) {
            //Check for piece in the way
            if (board[x][y] > 0) {
                //Same color
                if ((board[x][y] ^ index) < 8) {
                    break;
                } 
                //Different color
                else {
                    moves.add(x + y * 8);
                    break;
                }
            }
                
            
            moves.add(x + y * 8);
            
            //Move positions
            x++;
            y--;
        }

        //SE
        x = xpos;
        y = ypos;
        while (x < 8 && y < 8) {
            //Check for piece in the way
            if (board[x][y] > 0) {
                //Same color
                if ((board[x][y] ^ index) < 8) {
                    break;
                } 
                //Different color
                else {
                    moves.add(x + y * 8);
                    break;
                }
            }
            
            moves.add(x + y * 8);
            
            //Move positions
            x++;
            y++;
        }

        //SW
        x = xpos;
        y = ypos;
        while (x >= 0 && y < 8) {
            //Check for piece in the way
            if (board[x][y] > 0) {
                //Same color
                if ((board[x][y] ^ index) < 8) {
                    break;
                } 
                //Different color
                else {
                    moves.add(x + y * 8);
                    break;
                }
            }
            
            moves.add(x + y * 8);
            
            //Move positions
            x--;
            y++;
        }

        //NW
        x = xpos;
        y = ypos;
        while (x >= 0 && y >= 0) {
            //Check for piece in the way
            if (board[x][y] > 0) {
                //Same color
                if ((board[x][y] ^ index) < 8) {
                    break;
                } 
                //Different color
                else {
                    moves.add(x + y * 8);
                    break;
                }
            }
            
            moves.add(x + y * 8);
            
            //Move positions
            x--;
            y--;
        }

        //Return final output
        return moves;
    }
}