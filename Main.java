import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Board b = new Board();
        //Starting board configuration
        int[][] startBoard = {
            { 2, 1, 0, 0, 0, 0, 9, 10},
            { 3, 1, 0, 0, 0, 0, 9, 11},
            { 4, 1, 0, 0, 0, 0, 9, 12},
            { 6, 1, 0, 0, 0, 0, 9, 14},
            { 5, 1, 0, 0, 0, 0, 9, 13},
            { 4, 1, 0, 0, 0, 0, 9, 12},
            { 3, 1, 0, 0, 0, 0, 9, 11},
            { 2, 1, 0, 0, 0, 0, 9, 10}
        };

        b.setBoard(startBoard);
        GUI gui = new GUI(b);
    }
}