package Display;

import GameState.MusicName;
import Music.MusicPlayer;
import Preparations.GameWindow;
import RegisterForm.LogInRegister;
import RegisterForm.Login;
import RegisterForm.Register;

import javax.swing.*;
import java.awt.*;
//TO DO:

/// after updating the tiles: in map update which cause collisions
public class Main {
    //Connection connection = null;
    public static JFrame setMainWindow() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Lost");
        window.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/GeneralUtility/geo.png")));
        return window;
    }

    public static void game1() {
        // Create and configure the JFrame
        GameWindow window = new GameWindow();
        Login login = new Login(window);
        Register register = new Register(window);
        //CardLayout to switch between panels (awesome)
        JPanel mainPanel = new JPanel(new CardLayout());

        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        // Login Panel

        mainPanel.add(login.LoginPanel, "Login");
        // Reg Panel

        mainPanel.add(register.RegisterPanel, "Register");

        // Game Panel
        MusicPlayer musicPlayer = new MusicPlayer();
        Panel gamePanel = new Panel(musicPlayer, mainPanel, cardLayout, window);
        mainPanel.add(gamePanel, "Game");

        window.setContentPane(mainPanel);
        window.setPreferredSize(new Dimension(600, 400)); // Set new size
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        musicPlayer.playSound(MusicName.ELEVATOR_PERMIT);

        login.setLoginSuccessListener(() -> {
            // weird update order, but, the Game panel must be invoked after a delay since we have a small glitch with the login panel at first
            // Switch to the "Game" panel first
            cardLayout.show(mainPanel, "Game");

            gamePanel.username = login.username;
            gamePanel.isEmail = login.isEmail;
            if(LogInRegister.checksAdmin(gamePanel.username)) {
                gamePanel.isAdmin = true;
                gamePanel.player.speed = 20;
            }
            // Resize
            SwingUtilities.invokeLater(() -> {
                window.setPreferredSize(new Dimension(1152, 960));
                window.revalidate();
                window.pack();
                window.setLocationRelativeTo(null);
            });
            //gamePanel.isAdmin = true;
            gamePanel.requestFocusInWindow();
            gamePanel.startTheThread();
        });

        login.setRegisterSuccessListener(
                () -> {
                    window.setPreferredSize(new Dimension(600, 500));
                    window.pack();
                    window.setLocationRelativeTo(null);

                    cardLayout.show(mainPanel, "Register");
                    register.requestFocusInWindow();

                }
        );
        register.setBackToLoginListener(
                () -> {
                    window.setPreferredSize(new Dimension(600, 400));
                    window.pack();
                    window.setLocationRelativeTo(null);

                    cardLayout.show(mainPanel, "Login");
                    register.requestFocusInWindow();
                }
        );
    }

    public static void main(String[] args) {
        //MazeGeneratorDFS.main();
        MazeGeneratorBFS.main();
        game1();

    }

}
