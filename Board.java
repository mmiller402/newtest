import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Board {

    private int[][] board;
    private ArrayList<Piece> pieces;
    private int enPassant, canCastle;
    private ArrayList<Piece> whiteCaptures, blackCaptures;

    public Board() {
        //Initialize board matrix
        board = new int[8][8];
        //Initalize pieces arraylist
        pieces = new ArrayList<Piece>();

        //Int for a position where En Passant is available (updated every 2-square pawn move)
        enPassant = -1;
        //Int for indicating castle opportunity (stored as bitwise)
        canCastle = 15;

        //Arraylists for captured pieces
        whiteCaptures = new ArrayList<Piece>();
        blackCaptures = new ArrayList<Piece>();
    }

    public int[][] getBoard() {
        return board;
    }

    public int getEnPassant() {
        return enPassant;
    }

    public int getCanCastle() {
        return canCastle;
    }

    //Get a piece at a certain position
    public Piece getPieceAt(int x, int y) {

        //Get index from position
        int index = board[x][y];

        //Square is empty
        if (index <= 0) {
            return null;
        }

        //Loop through available pieces, looking for same index and coords
        for (Piece p : pieces) {
            if (p.getIndex() == index && p.getX() == x && p.getY() == y) {
                return p;
            }
        }

        //Should never get this far, return null anyway
        return null;
    }

    //Move a piece, works on any piece and any square, so you must verify the move before
    public void movePiece(int curx, int cury, int newx, int newy) {

        //Get index and piece from current position
        int index = board[curx][cury];
        Piece movingPiece = getPieceAt(curx, cury);

        //Reset current board position
        board[curx][cury] = 0;

        //Find occupying piece and delete it
        Piece occupyingPiece = getPieceAt(newx, newy);
        if (occupyingPiece != null) {

            occupyingPiece.take();

            //White
            if ((occupyingPiece.getIndex() & 8) > 7) {
                blackCaptures.add(occupyingPiece);
            }
            //Black
            else {
                whiteCaptures.add(occupyingPiece);
            }
        }

        //Move piece and update index
        board[newx][newy] = index;
        movingPiece.move(newx, newy, this);
    }
}