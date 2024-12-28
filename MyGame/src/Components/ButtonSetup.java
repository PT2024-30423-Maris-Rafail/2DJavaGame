package Components;

import RegisterForm.LogInRegister;

import javax.swing.*;
import java.awt.*;

public class ButtonSetup {
    private static void setLoginListener(GameWindow gameWindow, MainPanel mainPanel) {
        GamePanel gamePanel = mainPanel.getGamePanel();
        gameWindow.getLogin().setLoginSuccessListener(() -> {
            //System.out.println("nig");
            // weird update order, but, the Game panel must be invoked after a delay since we have a small glitch with the login panel at first
            // Switch to the "Game" panel
            gameWindow.cardLayout.show(mainPanel, "Game");

            gamePanel.username = gameWindow.getLogin().username;
            gamePanel.isEmail = gameWindow.getLogin().isEmail;
            //admin account for easy logic checks
            if (LogInRegister.checksAdmin(gamePanel.username)) {
                gamePanel.isAdmin = true;
                gamePanel.player.speed = 20;
            }
            // Resize after a small delay - if not, the Login screen glitches and is shown in the new panel without being dropped... just small nitpicking
            SwingUtilities.invokeLater(() -> {
                gameWindow.setPreferredSize(new Dimension(1152, 960));
                gameWindow.revalidate();
                gameWindow.pack();
                gameWindow.setLocationRelativeTo(null);
            });
            //gamePanel.isAdmin = true;
            gamePanel.requestFocusInWindow();
            gamePanel.startTheThread();
        });
    }

    private static void setRegisterListener(GameWindow gameWindow, MainPanel mainPanel) {
        gameWindow.getLogin().setRegisterSuccessListener(
                () -> {
                    //the register Panel is shown
                    gameWindow.setPreferredSize(new Dimension(600, 500));
                    gameWindow.pack();
                    gameWindow.setLocationRelativeTo(null);

                    gameWindow.cardLayout.show(mainPanel, "Register");
                    gameWindow.getRegister().requestFocusInWindow();

                }
        );
    }

    private static void setReturnToLoginListener(GameWindow gameWindow, MainPanel mainPanel) {
        gameWindow.getRegister().setBackToLoginListener(
                () -> {
                    //after registering, user is prompted to go back to Login
                    gameWindow.setPreferredSize(new Dimension(600, 400));
                    gameWindow.pack();
                    gameWindow.setLocationRelativeTo(null);

                    gameWindow.cardLayout.show(mainPanel, "Login");
                    gameWindow.getRegister().requestFocusInWindow();
                }
        );
    }

    public static void setupActionListeners(GameWindow gameWindow, MainPanel mainPanel) {
        setLoginListener(gameWindow, mainPanel);
        setRegisterListener(gameWindow, mainPanel);
        setReturnToLoginListener(gameWindow, mainPanel);
    }
}