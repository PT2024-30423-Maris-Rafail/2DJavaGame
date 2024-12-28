package Components;

import MainGame.Main;
import GameState.MusicName;
import Music.MusicPlayer;
import RegisterForm.Login;
import RegisterForm.Register;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends javax.swing.JFrame {
    //private JFrame window;
    private final Login login;
    private final Register register;
    public CardLayout cardLayout = new CardLayout();//CardLayout to switch between panels (awesome)
    private final MusicPlayer musicPlayer;

    public GameWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Lost");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/GeneralUtility/geo.png")));

        login = new Login(this);
        register = new Register(this);

        //cardLayout = new CardLayout();
        musicPlayer = new MusicPlayer();
    }

    public Login getLogin() {
        return login;
    }

    public Register getRegister() {
        return register;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    public void setProperties(MainPanel mainPanel) {

        this.setContentPane(mainPanel);
        this.setPreferredSize(new Dimension(600, 400)); // Set new tableSize
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        musicPlayer.playSound(MusicName.ELEVATOR_PERMIT);
    }

}
