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

        // Create a CardLayout to switch between panels (awesome class)
        JPanel mainPanel = new JPanel(new CardLayout());

        // Login Panel
        Login login = new Login(window);
        mainPanel.add(login.LoginPanel, "Login");
        // Reg Panel
        Register register = new Register(window);
        mainPanel.add(register.RegisterPanel, "Register");

        // Game Panel
        MusicPlayer musicPlayer = new MusicPlayer();
        Panel gamePanel = new Panel(musicPlayer);
        mainPanel.add(gamePanel, "Game");


        window.setContentPane(mainPanel);
        window.setPreferredSize(new Dimension(600, 400)); // Set new size
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);


        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        musicPlayer.playSound(MusicName.ELEVATOR_PERMIT);

        login.setLoginSuccessListener(
                ()->{
                    window.setPreferredSize(new Dimension(1152, 960));
                    window.pack();
                    window.setLocationRelativeTo(null);
                    gamePanel.username = login.username;
                    gamePanel.isEmail = login.isEmail;
                    // Switch to the game panel
                    cardLayout.show(mainPanel, "Game");
                    gamePanel.requestFocusInWindow();
                    gamePanel.startTheThread();
                }
        );
        login.setRegisterSuccessListener(
                ()->{
                    window.setPreferredSize(new Dimension(600, 500));
                    window.pack();
                    window.setLocationRelativeTo(null);

                    cardLayout.show(mainPanel, "Register");
                    register.requestFocusInWindow();

                }
        );
        register.setBackToLoginListener(
                ()->{
                    window.setPreferredSize(new Dimension(600, 400));
                    window.pack();
                    window.setLocationRelativeTo(null);

                    cardLayout.show(mainPanel, "Login");
                    register.requestFocusInWindow();
                }
        );
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