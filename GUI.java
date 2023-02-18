import java.awt.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GUI {

    //Piece images initialization
    BufferedImage bking = null, bqueen = null, bbishop = null, bknight = null, brook = null, bpawn = null; 
    BufferedImage wking = null, wqueen = null, wbishop = null, wknight = null, wrook = null, wpawn = null;

    public GUI(Board b) {
        ////Graphics

        //Assign piece images
        try {

            //Black pieces
            bking = ImageIO.read(new File("Images/bking.png"));
            bqueen = ImageIO.read(new File("Images/bqueen.png"));
            bbishop = ImageIO.read(new File("Images/bbishop.png"));
            bknight = ImageIO.read(new File("Images/bknight.png"));
            brook = ImageIO.read(new File("Images/brook.png"));
            bpawn = ImageIO.read(new File("Images/bpawn.png"));

            //White pieces
            wking = ImageIO.read(new File("Images/wking.png"));
            wqueen = ImageIO.read(new File("Images/wqueen.png"));
            wbishop = ImageIO.read(new File("Images/wbishop.png"));
            wknight = ImageIO.read(new File("Images/wknight.png"));
            wrook = ImageIO.read(new File("Images/wrook.png"));
            wpawn = ImageIO.read(new File("Images/wpawn.png"));
        } 
        catch (Exception e) {

            //One or more image files is missing
            System.out.println("Image files missing");
        }
        

        //Create frame
        //Variables
        int squareSize = bking.getWidth();
        int boardsize = squareSize * 8;

        JFrame frame = new JFrame("Chess");
        frame.setBounds(10, 10, boardsize, boardsize);
        frame.setUndecorated(true); //Remove borders

        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {

                //Create squares
                for (int y = 0; y < 8; y++) {
                    for (int x = 0; x < 8; x++) {

                        //Set color, white in top left corner
                        if ((x + y) % 2 == 0) {
                            //Light
                            g.setColor(new Color(240, 190, 100));
                        } else {
                            //Dark
                            g.setColor(new Color(120, 80, 20));
                        }

                        //Background square
                        g.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);

                        //Pieces
                        if (b.getBoard()[x][y] > 0) {

                            BufferedImage img = null;
                            int index = b.getBoard()[x][y];

                            switch (index) {

                                //Black pieces
                                case 1: img = bpawn; break;
                                case 2: img = brook; break;
                                case 3: img = bknight; break;
                                case 4: img = bbishop; break;
                                case 5: img = bqueen; break;
                                case 6: img = bking; break;

                                //White pieces
                                case 9: img = wpawn; break;
                                case 10: img = wrook; break;
                                case 11: img = wknight; break;
                                case 12: img = wbishop; break;
                                case 13: img = wqueen; break;
                                case 14: img = wking; break;
                            }

                            //Draw scaled image in correct spot
                            //Image scaledImg = img.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                            g.drawImage(img, x * squareSize, y * squareSize, null);
                        }
                    }
                }
                
            }
        };

        //Finish frame setup
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
