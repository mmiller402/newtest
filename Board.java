import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Board {

    private int[][] board;
    private int enPassant, canCastle;
    private ArrayList<Integer> whiteCaptures, blackCaptures;

    private boolean whiteTurn;

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

        //Whose turn it is
        whiteTurn = true;
    }

    //Copy of a board
    public Board(Board b) {

        //Clone board into new object
        board = new int[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                board[x][y] = b.getBoard()[x][y];
            }
        }

        enPassant = b.getEnPassant();
        canCastle = b.getCanCastle();

        whiteCaptures = b.getWhiteCaptures();
        blackCaptures = b.getBlackCaptures();

        selectedPieceX = b.getSelectedPieceX();
        selectedPieceY = b.getSelectedPieceY();
        selectedMoves = b.getSelectedMoves();

        whiteTurn = b.getWhiteTurn();
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

    public boolean getWhiteTurn() {
        return whiteTurn;
    }

    public ArrayList<Integer> getWhiteCaptures() {
        return whiteCaptures;
    }
    public ArrayList<Integer> getBlackCaptures() {
        return blackCaptures;
    }

    //Sets board position
    public void setBoard(int[][] b) {
        board = b;
    }

    public void setEnPassant(int e) {
        enPassant = e;
    }

    public void setCanCastle(int c) {
        canCastle = c;
    }

    public void setSelectedPieceX(int x) {
        selectedPieceX = x;
    }
    public void setSelectedPieceY(int y) {
        selectedPieceY = y;
    }

    public void setSelectedMoves(ArrayList<Integer> m) {
        selectedMoves = m;
    }

    public void setWhiteTurn(boolean w) {
        whiteTurn = w;
    }

    //Move a piece, works on any piece and any square, so you must verify the move before
    public void movePiece(int curx, int cury, int newx, int newy) {

        //Get index and piece from current position
        int pieceIndex = board[curx][cury];

        //Check for En Passant
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

                //Check if king castled
                if (newx == 6) {

                    //Move kingside rook
                    movePiece(7, 7, 5, 7);
                } else if (newx == 2) {

                    //Move queenside rook
                    movePiece(0, 7, 3, 7);
                }

                //Remove castle abilities from white
                canCastle = canCastle & 12;
            }
            //Black king moves
            else if (curx == 4 && cury == 0) {

                //Check if king castled
                if (newx == 6) {

                    //Move kingside rook
                    movePiece(7, 0, 5, 0);
                } else if (newx == 2) {

                    //Move queenside rook
                    movePiece(0, 0, 3, 0);
                }

                //Remove castle abilities from black
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

        //Move piece and update turn
        board[newx][newy] = pieceIndex;
        whiteTurn = !whiteTurn;
    }

    //Select a piece and update its position and moves
    public void selectPiece(int xpos, int ypos) {

        //Check to make sure a piece occupies this square and it is their turn
        boolean wrongTurn = ((board[xpos][ypos] & 8) == 8) ^ whiteTurn;
        if (wrongTurn || board[xpos][ypos] <= 0) {

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

    //Check if any moves are legal, return true if no moves are legal
    public boolean noLegalMoves(boolean checkWhite) {

        //Loop through every piece, checking all moves
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                
                //Piece index
                int index = board[x][y];

                if (index <= 0)
                    continue;

                //Check if piece is one you are checking
                boolean isWhite = (index & 8) > 7;
                if (!(isWhite ^ checkWhite)) {

                    //Get moves from piece
                    ArrayList<Integer> moves = Piece.getLegalMoves(this, x, y);

                    //If there is at least one legal move, return false
                    if (moves.size() > 0)
                        return false;
                }
            }
        }

        //No legal moves were found; return true
        return true;
    }

    //Check for a checkmate
    public boolean isCheckmated(boolean checkWhite) {

        //Find if there are any legal moves
        if (noLegalMoves(checkWhite)) {

            //Find if king is in check
            if (Piece.inCheck(this, checkWhite)) {

                //Checkmate
                return true;
            }
        }

        //Didn't pass checkmate conditions
        return false;
    }
}