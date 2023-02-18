import java.awt.*;
import java.util.*;
import javax.swing.*;

public class GUI {
    public GUI() {
        ////Graphics
        //Create frame
        JFrame frame = new JFrame("Chess");
        frame.setBounds(10, 10, 512, 512);
        frame.setUndecorated(true); //Remove borders

        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {

                //Set size of squares
                int stepsize = 512 / 8;

                //Create squares
                for (int y = 0; y < 8; y++) {
                    for (int x = 0; x < 8; x++) {

                        //Set color, white in top left corner
                        if ((x + y) % 2 == 0) {
                            g.setColor(Color.white);
                        } else {
                            g.setColor(Color.black);
                        }

                        g.fillRect(x * stepsize, y * stepsize, stepsize, stepsize);
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
