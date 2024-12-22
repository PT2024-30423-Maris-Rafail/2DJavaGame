package Display;

import javax.swing.*;
//TO DO:
///after updating the tiles: in map update which cause collisions
public class Main {
    //Connection connection = null;
    public static void game1() {
        //allows the window to appear on screen
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//if this were not here, program would run even after closing the window
        window.setResizable(false);
        window.setTitle("Lost");
        //frame.setVisible(true);
        // I AM GOING INSANE. I forgot that order matters and didn't know why the dot wasn't moving
        //Since i had the panel declaration after the settings, it wasn't registered for the keyHandlerr and it only worked
        //on some rare occasions... jeez
        Panel panel = new Panel();//the panel is what is displayed in the window we oppened
        //panel.setLayout(null);
        window.add(panel);//we add it to a JFrame

        window.setLocationRelativeTo(null);
        window.setVisible(true);


        panel.requestFocusInWindow();

        window.pack();//this is done to actually see the panel
        //we can display 16 48*48 tiles

        panel.startTheThread();
    }

    public static void game2() {
        //Principal.main();
    }

    public static void main(String[] args) {
        MazeGenerator.main();
        game1();
        //MapSimulator.simulateMap2();
        //game2();
        //StreetViewImage.main();
        //WebpageScreenshot.main(args);
        //GoogleLoginScreenshot.main();
    }

}
/*
what to add now???
Text
menu
load screen
the game
score
 */
/*
TO DO

 */