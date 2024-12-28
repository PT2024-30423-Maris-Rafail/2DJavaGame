package RegisterForm;

import GameState.UserLoginStatus;

import javax.swing.*;
import java.awt.event.ItemEvent;

public class Login extends JDialog {
    private JTextField textFUserN;
    private JPasswordField passWF;

    public JPanel LoginPanel;
    private JButton submitButton;
    private JButton createAccountButton;
    private JCheckBox allowView;
    public String username;
    public boolean isEmail;
    public UserLoginStatus status;

    //LOG IN
    public interface LoginSuccessListener {
        void onLoginSuccess();
    }

    private LoginSuccessListener loginSuccessListener;

    public void setLoginSuccessListener(LoginSuccessListener listener) {
        this.loginSuccessListener = listener;//sets what should happen if the login is successful
    }

    //REGISTER
    public interface RegisterSuccessListener {
        void onRegisterSuccess();
    }

    private RegisterSuccessListener registerSuccessListener;

    public void setRegisterSuccessListener(RegisterSuccessListener listener) {
        this.registerSuccessListener = listener;
    }

    private void handleLogin() {
        String userN = textFUserN.getText();
        String passW = passWF.getText();
        if (userN.isEmpty() || passW.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields");
        } else {
            //if not empty we check with the database
            status = LogInRegister.checkLogin(userN, passW);
            switch (status) {
                case INVALID_NAME -> {
                    JOptionPane.showMessageDialog(LoginPanel, "Invalid Username");
                    textFUserN.setText("");
                    passWF.setText("");
                }
                case INVALID_PASS -> {
                    JOptionPane.showMessageDialog(LoginPanel, "Invalid Password");
                    passWF.setText("");
                }
                case VALID_USERNAME -> {
                    if (loginSuccessListener != null) {
                        username = textFUserN.getText();
                        isEmail = false;
                        loginSuccessListener.onLoginSuccess(); // Notify the listener
                    }
                }
                case VALID_EMAIL -> {
                    if (loginSuccessListener != null) {
                        username = textFUserN.getText();
                        isEmail = true;
                        loginSuccessListener.onLoginSuccess(); // Notify the listener
                    }
                }
            }
        }
    }

    private void toggleVisibility(boolean visible) {
        if (visible) {
            passWF.setEchoChar((char) 0); // Make the password visible
        } else {
            passWF.setEchoChar('●'); // Password is black, needs mask :D
        }
    }

    public Login(JFrame parent) {
        passWF.setEchoChar('●');//what the password should display. '*' were not very pretty
        parent.setContentPane(LoginPanel);
        LoginPanel.setVisible(true);
        submitButton.addActionListener(_ -> handleLogin());
        createAccountButton.addActionListener(_ -> {
            if (registerSuccessListener != null) {
                registerSuccessListener.onRegisterSuccess(); // Notify listener
            }
        });
        // Make password (in)visible
        allowView.addItemListener(e -> toggleVisibility(e.getStateChange() == ItemEvent.SELECTED));
    }

}
