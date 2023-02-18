import java.util.*;

public abstract class Piece {
    private int xpos, ypos;
    private int index;
    private boolean isActive;

    //Constructor
    public Piece(int ind, int x, int y) {
        index = ind;
        xpos = x;
        ypos = y;
        isActive = true; //Still in the game
    }

    //Getters
    public int getIndex() {
        return index;
    }
    public int getX() {
        return xpos;
    }
    public int getY() {
        return ypos;
    }

    //Method for moving piece
    public void move(int newx, int newy, Board b) {

    }

    //Method for taking piece
    public void take() {
        isActive = false;
    }

    //Methods for piece moving
    public abstract ArrayList<Integer> getPeacefulMoves();
    public abstract ArrayList<Integer> getPieceTakes();

    //Method for moving in lines like a rook
    public ArrayList<Integer> getLinearMoves(Board b) {

        //Start move list
        ArrayList<Integer> moves = new ArrayList<Integer>();

        int[][] board = b.getBoard();

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
    public ArrayList<Integer> getDiagonalMoves(Board b) {

        //Start move list
        ArrayList<Integer> moves = new ArrayList<Integer>();

        int[][] board = b.getBoard();

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