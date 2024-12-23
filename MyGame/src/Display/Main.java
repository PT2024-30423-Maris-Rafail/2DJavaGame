package Display;

import GameState.MusicName;
import Music.MusicPlayer;
import RegisterForm.Login;
import RegisterForm.Register;

import javax.swing.*;
import java.awt.*;
//TO DO:
///after updating the tiles: in map update which cause collisions
public class Main {
    //Connection connection = null;
    public static void game1() {
        // Create and configure the JFrame
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Lost");

        // Create a CardLayout to switch between panels
        JPanel mainPanel = new JPanel(new CardLayout());

        // Login Panel
        Login login = new Login(window); // Instance of the login panel
        mainPanel.add(login.LoginPanel, "Login");
        // Login Panel
        Register register = new Register(window); // Instance of the Register panel
        mainPanel.add(register.RegisterPanel, "Register");

        // Game Panel
        MusicPlayer musicPlayer = new MusicPlayer();
        Panel gamePanel = new Panel(musicPlayer);
        mainPanel.add(gamePanel, "Game");

        // Set main panel to the JFrame
        window.setContentPane(mainPanel);
        window.setPreferredSize(new Dimension(600, 400)); // Set new size
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Transition to the game panel after 3 seconds
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        musicPlayer.playSound(MusicName.ELEVATOR_PERMIT);

        login.setLoginSuccessListener(
                ()->{
                    window.setPreferredSize(new Dimension(1152, 960)); // Set new size
                    window.pack(); // Adjust the size to fit new preferred dimensions
                    window.setLocationRelativeTo(null); // Center the window again

                    // Switch to the game panel
                    cardLayout.show(mainPanel, "Game");
                    gamePanel.requestFocusInWindow(); // Ensure focus is on the game panel
                    gamePanel.startTheThread(); // Start game thread
                }
        );
        login.setRegisterSuccessListener(
                ()->{
                    window.setPreferredSize(new Dimension(600, 500)); // Set new size
                    window.pack();
                    window.setLocationRelativeTo(null);

                    cardLayout.show(mainPanel, "Register");
                    register.requestFocusInWindow();

                }
        );
        register.setBackToLoginListener(
                ()->{
                    window.setPreferredSize(new Dimension(600, 400)); // Set new size
                    window.pack();
                    window.setLocationRelativeTo(null);

                    cardLayout.show(mainPanel, "Login");
                    register.requestFocusInWindow();
                }
        );
//        Timer timer = new Timer(30000, e -> {
//            // Resize the JFrame for the game panel
//
//            //musicPlayer.stopSound(MusicName.ELEVATOR_PERMIT);
//        });
//        timer.setRepeats(false); // Ensure it only runs once
//        timer.start();
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