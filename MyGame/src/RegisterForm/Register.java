package RegisterForm;

import ConnecToDB.ConnectR;
import GameState.UserLoginStatus;

import javax.swing.*;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


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


    public Register(JFrame parent) {
        passWF.setEchoChar('●');
        confpassF.setEchoChar('●');
        parent.setContentPane(RegisterPanel);
        RegisterPanel.setVisible(true);


        registerButton.addActionListener(e -> {
            String name = textFUserN.getText();
            String password = passWF.getText();
            String confPassword = confpassF.getText();
            String email = textFmail.getText();
            System.out.println(name + password + confPassword + email);

            if (name.isEmpty() || password.isEmpty() || confPassword.isEmpty() || email.isEmpty() ) {

                JOptionPane.showMessageDialog(null, "Please fill all the fields");

            } else {
                status = LogInRegister.registerUser(name, password, confPassword, email);
                switch (status) {
                    case TAKEN_NAME -> {
                        JOptionPane.showMessageDialog(RegisterPanel, "Taken Username");
                        textFUserN.setText("");
                        textFmail.setText("");
                        passWF.setText("");
                        confpassF.setText("");
                    }
                    case TAKEN_EMAIL -> {
                        JOptionPane.showMessageDialog(RegisterPanel, "Taken email");
                        textFUserN.setText("");
                        textFmail.setText("");
                        passWF.setText("");
                        confpassF.setText("");
                    }
                    case INVALID_EMAIL -> {
                        JOptionPane.showMessageDialog(RegisterPanel, "Invalid email");
                        textFmail.setText("");
                    }
                    case PASSWORD_MISMATCH -> {
                        JOptionPane.showMessageDialog(RegisterPanel, "Password Mismatch");
                        passWF.setText("");
                        confpassF.setText("");
                    }
                    case VALID -> {

                        connectR.registerUser(name, password, email);
                        JOptionPane.showMessageDialog(RegisterPanel, "Registration Successful! Please return to log in screen");

                        textFUserN.setText("");
                        passWF.setText("");
                        confpassF.setText("");

                        textFmail.setText("");

                    }

                }
            }
        });
        backToLoginButton.addActionListener(e -> {
            if (backToLoginListener != null) {
                backToLoginListener.backToLogin();
            }
        });
        allowView.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    passWF.setEchoChar((char) 0); // Make the password visible
                    confpassF.setEchoChar((char) 0);
                } else {
                    passWF.setEchoChar('●'); // Mask the password
                    confpassF.setEchoChar('●');
                }
            }
        });
    }
}
