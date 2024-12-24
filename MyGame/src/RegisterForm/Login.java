package RegisterForm;

import GameState.UserLoginStatus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Login extends JDialog {
    private JTextField textFUserN;
    private JPasswordField passWF;

    public JPanel LoginPanel;
    private JButton submitButton;
    private JButton createAccountButton;
    private JCheckBox allowView;
    //private JButton viewPass;
    public String username;
    public boolean isEmail;
    public UserLoginStatus status;
    //LOG IN
    public interface LoginSuccessListener {
        void onLoginSuccess();
    }
    private LoginSuccessListener loginSuccessListener;
    public void setLoginSuccessListener(LoginSuccessListener listener) {
        this.loginSuccessListener = listener;
    }
    //REGISTER
    public interface RegisterSuccessListener {
        void onRegisterSuccess();
    }
    private RegisterSuccessListener registerSuccessListener;
    public void setRegisterSuccessListener(RegisterSuccessListener listener) {
        this.registerSuccessListener = listener;
    }

    public Login(JFrame parent) {
        passWF.setEchoChar('●');

        parent.setContentPane(LoginPanel);
        LoginPanel.setVisible(true);
        //super(parent);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userN = textFUserN.getText();
                String passW = passWF.getText();
                System.out.println(passW);
                if(userN.isEmpty() || passW.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please fill all the fields");
                }
                else{
                    status = LogInRegister.checkLogin(userN,passW);
                    switch (status) {
                        case INVALID_NAME -> {

                            JOptionPane.showMessageDialog(LoginPanel, "Invalid Username");
                            textFUserN.setText("");
                            passWF.setText("");
                        }
                        case INVALID_PASS ->{
                            JOptionPane.showMessageDialog(LoginPanel, "Invalid Password");
                            passWF.setText("");
                        }

                        case VALID_USERNAME ->{
                            if (loginSuccessListener != null) {
                                username = textFUserN.getText();
                                isEmail = false;
                                loginSuccessListener.onLoginSuccess(); // Notify the listener
                            }
                        }

                        case VALID_EMAIL ->{
                            if (loginSuccessListener != null) {
                                username = textFUserN.getText();
                                isEmail = true;
                                loginSuccessListener.onLoginSuccess(); // Notify the listener
                            }
                        }


                    }
                }


            }
        });

        // Toggle password visibility
        allowView.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    passWF.setEchoChar((char) 0); // Make the password visible
                } else {
                    passWF.setEchoChar('●'); // Mask the password
                }
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (registerSuccessListener != null) {
                    registerSuccessListener.onRegisterSuccess(); // Notify the listener
                }
            }
        });

    }

}
