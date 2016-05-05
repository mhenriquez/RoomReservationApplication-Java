package reserveroomapplication;

import javax.swing.*;

/**
 * @author Moises Henriquez
 * @date April 24, 2016
 */
public class ReserveRoomApplication {
    
    public static void main(String[] args) {
        //Instatiate new GUI object
        ReserveRoomGUI window = new ReserveRoomGUI("Reserve Room", true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
