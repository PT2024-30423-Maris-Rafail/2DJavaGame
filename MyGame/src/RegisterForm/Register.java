package RegisterForm;

import ConnecToDB.ConnectR;
import GameState.UserLoginStatus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

public class Register extends JDialog{
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
    ///Back to login button
    public interface backToLoginListener{
        void backToLogin();
    }
    private backToLoginListener backToLoginListener;
    public void setBackToLoginListener(backToLoginListener backToLoginListener){
        this.backToLoginListener = backToLoginListener;
    }
    ///Registering button
    public interface registerListener{
        void register();
    }
    private registerListener registerListener;
    public void setRegisterListener(registerListener registerListener){
        this.registerListener = registerListener;
    }


    public Register(JFrame parent) {
        passWF.setEchoChar('●');
        confpassF.setEchoChar('●');
        parent.setContentPane(RegisterPanel);
        RegisterPanel.setVisible(true);


        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textFUserN.getText();
                String password = passWF.getText();
                String confPassword = confpassF.getText();
                String email = textFmail.getText();
                System.out.println(name+ password+ confPassword+ email);
                if(name.isEmpty() || password.isEmpty() || confPassword.isEmpty() || email.isEmpty()){
                    if(name.isEmpty())JOptionPane.showMessageDialog(parent, "Please enter your name");
                    if(password.isEmpty())JOptionPane.showMessageDialog(parent, "Please enter your password");
                    if(confPassword.isEmpty())JOptionPane.showMessageDialog(parent, "Please enter your confirm password");
                    if(email.isEmpty())JOptionPane.showMessageDialog(parent, "Please enter your email");

                    //JOptionPane.showMessageDialog(null, "Please fill all the fields");
                }
                else{
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
                        case PASSWORD_MISMATCH -> {
                            JOptionPane.showMessageDialog(RegisterPanel, "Password Mismatch");
                            passWF.setText("");
                            confpassF.setText("");
                        }
                        case VALID -> {
                            try{
                                connectR.registerUser(name, password, email);
                                JOptionPane.showMessageDialog(RegisterPanel, "Registration Successful! Please return to log in screen");
                            }
                        catch (SQLException err){
                                if("23514".equals(err.getSQLState())){
                                    JOptionPane.showMessageDialog(RegisterPanel, "Invalid Email Address");
                                }
                                else {
                                    err.printStackTrace();
                                }
                        }
                            textFUserN.setText("");
                            textFmail.setText("");
                            passWF.setText("");
                            confpassF.setText("");
                        }

                    }
                }
            }
        });
        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(backToLoginListener != null){
                    backToLoginListener.backToLogin();
                }
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
