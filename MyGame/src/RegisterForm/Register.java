package RegisterForm;

import ConnecToDB.ConnectR;
import GameState.UserLoginStatus;

import javax.swing.*;

import java.awt.event.ItemEvent;

public class Register extends JDialog {
    private JTextField textFUserN;
    private JPasswordField passWF;
    private JPasswordField confpassF;
    private JButton backToLoginButton;
    private JButton registerButton;
    private JCheckBox allowView;
    public JPanel RegisterPanel;
    private JTextField textFmail;
    public UserLoginStatus status;
    ConnectR connectR = new ConnectR();

    //I'm sorry interfaces, i wasn't familiar with your game
    //this is so cool!!!!

    /// Back to login button
    public interface backToLoginListener {
        void backToLogin();
    }

    private backToLoginListener backToLoginListener;

    public void setBackToLoginListener(backToLoginListener backToLoginListener) {
        this.backToLoginListener = backToLoginListener;
    }

    private void setEmpty(UserLoginStatus status) {
        switch (status) {
            case INVALID_NAME, TAKEN_EMAIL, VALID -> {
                textFUserN.setText("");
                textFmail.setText("");
                passWF.setText("");
                confpassF.setText("");
            }
            case INVALID_EMAIL -> textFmail.setText("");
            case PASSWORD_MISMATCH -> {
                passWF.setText("");
                confpassF.setText("");
            }
        }
    }

    private void showMessage(String name, String password, String confPassword, String email) {
        status = LogInRegister.registerUser(name, password, confPassword, email);
        setEmpty(status);
        switch (status) {
            case INVALID_NAME -> JOptionPane.showMessageDialog(RegisterPanel, "Invalid Username");
            case TAKEN_EMAIL -> JOptionPane.showMessageDialog(RegisterPanel, "Taken email");
            case INVALID_EMAIL -> JOptionPane.showMessageDialog(RegisterPanel, "Invalid email");
            case PASSWORD_MISMATCH -> JOptionPane.showMessageDialog(RegisterPanel, "Password Mismatch");
            case VALID -> {
                connectR.accountConnect.registerUser(name, password, email);
                JOptionPane.showMessageDialog(RegisterPanel, "Registration Successful! Please return to log in screen");
            }
        }
    }

    private void handleRegister() {
        String name = textFUserN.getText();
        String password = passWF.getText();
        String confPassword = confpassF.getText();
        String email = textFmail.getText();
        //System.out.println(name + password + confPassword + email);

        if (name.isEmpty() || password.isEmpty() || confPassword.isEmpty() || email.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Please fill all the fields");

        } else {
            showMessage(name, password, confPassword, email);
        }
    }

    private void toggleVisibility(boolean visible) {
        if (visible) {
            passWF.setEchoChar((char) 0); // Make the password visible
            confpassF.setEchoChar((char) 0);
        } else {
            passWF.setEchoChar('●'); // Mask the password
            confpassF.setEchoChar('●');
        }
    }

    public Register(JFrame parent) {
        passWF.setEchoChar('●');
        confpassF.setEchoChar('●');
        parent.setContentPane(RegisterPanel);
        RegisterPanel.setVisible(true);

        registerButton.addActionListener(_ -> handleRegister());

        backToLoginButton.addActionListener(_ -> {
            if (backToLoginListener != null) {
                backToLoginListener.backToLogin();
            }
        });
        allowView.addItemListener(e -> toggleVisibility(e.getStateChange() == ItemEvent.SELECTED));
    }
}
