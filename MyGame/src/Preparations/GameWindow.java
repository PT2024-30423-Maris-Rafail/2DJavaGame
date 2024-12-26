package Preparations;

import Display.Main;
import RegisterForm.Login;
import RegisterForm.Register;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends javax.swing.JFrame {
    private JFrame window = new JFrame();
    private Login login;
    private Register register;
    public GameWindow() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Lost");
        window.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/GeneralUtility/geo.png")));

        login = new Login(window);
        register = new Register(window);
    }
    public JFrame getWindow() {
        return window;
    }
    public Login getLogin() {
        return login;
    }
    public Register getRegister() {
        return register;
    }

}
