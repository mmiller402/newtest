import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Board {

    private int[][] board;
    private int enPassant, canCastle;
    private ArrayList<Integer> whiteCaptures, blackCaptures;

    //Current selected piece and movelist
    private int selectedPieceX, selectedPieceY;
    private ArrayList<Integer> selectedMoves;

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

        //Selected piece
        selectedPieceX = -1;
        selectedPieceY = -1;
        selectedMoves = new ArrayList<Integer>();
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

    public int getSelectedPieceX() {
        return selectedPieceX;
    }
    public int getSelectedPieceY() {
        return selectedPieceY;
    }

    public ArrayList<Integer> getSelectedMoves() {
        return selectedMoves;
    }

    //Sets board position
    public void setBoard(int[][] b) {
        board = b;
    }

    //Move a piece, works on any piece and any square, so you must verify the move before
    public void movePiece(int curx, int cury, int newx, int newy) {

        //Get index and piece from current position
        int pieceIndex = board[curx][cury];

        //Check for En Passant
        int prevEnPassant = enPassant;
        if ((pieceIndex & 7) == 1 && Math.abs(newy - cury) == 2) {
            
            //Get location that En Passant can happen in (row 1 or 5)
            int enPassantY = cury - (cury - newy) / 2;
            enPassant = curx + enPassantY * 8;
        } else {

            //Otherwise, reset En Passant
            enPassant = -1;
        }

        ////Check for castling changes
        //Rook
        if ((pieceIndex & 7) == 2) {
        
            //White rook kingside
            if (curx == 7 && cury == 7) {
                canCastle = canCastle & 13;
            }
            //White rook queenside
            else if (curx == 0 && cury == 7) {
                canCastle = canCastle & 14;
            }
            
            //Black rook kingside
            else if (curx == 7 && cury == 0) {
                canCastle = canCastle & 7;
            }
            //Black rook queenside
            else if (curx == 0 && cury == 0) {
                canCastle = canCastle & 11;
            }
        }
        //King
        else if ((pieceIndex & 7) == 6) {

            //White king moves
            if (curx == 4 && cury == 7) {
                canCastle = canCastle & 12;
            }
            //Black king moves
            else if (curx == 4 && cury == 0) {
                canCastle = canCastle & 3;
            }
        }

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

        //En passant (check by seeing if pawn moved horizontally onto an unoccupied spot)
        else if ((pieceIndex & 7) == 1 && Math.abs(newx - curx) > 0) {

            //Get piece that was En-Passant'ed
            int enPassantedX = newx;
            int enPassantedY = ((pieceIndex & 8) > 7) ? newy + 1 : newy - 1;
            int enPassantedPieceIndex = board[enPassantedX][enPassantedY];

            //Delete piece
            if ((enPassantedPieceIndex & 8) > 7)
                blackCaptures.add(enPassantedPieceIndex);
            else
                whiteCaptures.add(enPassantedPieceIndex);

            board[enPassantedX][enPassantedY] = 0;
        }

        //Move piece and update index
        board[newx][newy] = pieceIndex;
    }

    //Select a piece and update its position and moves
    public void selectPiece(int xpos, int ypos) {

        //Check to make sure a piece occupies this square
        if (board[xpos][ypos] <= 0) {

            //If so, reset variables
            selectedPieceX = -1;
            selectedPieceY = -1;
            selectedMoves.clear();

            return;
        }

        //Update position
        selectedPieceX = xpos;
        selectedPieceY = ypos;

        //Get moves list
        selectedMoves = Piece.getLegalMoves(this, xpos, ypos);
    }

    //Reset a selected piece
    public void resetSelectedPiece() {

        selectedPieceX = -1;
        selectedPieceY = -1;
        selectedMoves.clear();
    }
}