import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Board {

    private int[][] board;
    private int enPassant, canCastle;
    private ArrayList<Integer> whiteCaptures, blackCaptures;

    public Board() {
        //Initialize board matrix
        board = new int[8][8];

        //Int for a position where En Passant is available (updated every 2-square pawn move)
        enPassant = -1;
        //Int for indicating castle opportunity (stored as bitwise)
        canCastle = 15;

        //Arraylists for captured pieces
        whiteCaptures = new ArrayList<Integer>();
        blackCaptures = new ArrayList<Integer>();
    }

    //Getters
    public int[][] getBoard() {
        return board;
    }

    public int getEnPassant() {
        return enPassant;
    }

    public int getCanCastle() {
        return canCastle;
    }

    //Sets board position
    public void setBoard(int[][] b) {
        board = b;
    }

    //Move a piece, works on any piece and any square, so you must verify the move before
    public void movePiece(int curx, int cury, int newx, int newy) {

        //Get index and piece from current position
        int pieceIndex = board[curx][cury];

        //Reset current board position
        board[curx][cury] = 0;

        //Find occupying piece and delete it
        int occupyingPieceIndex = board[newx][newy];
        if (occupyingPieceIndex > 0) {

            //White
            if ((occupyingPieceIndex & 8) > 7) {
                blackCaptures.add(occupyingPieceIndex);
            }
            //Black
            else {
                whiteCaptures.add(occupyingPieceIndex);
            }
        }

        //Move piece and update index
        board[newx][newy] = pieceIndex;
    }
}