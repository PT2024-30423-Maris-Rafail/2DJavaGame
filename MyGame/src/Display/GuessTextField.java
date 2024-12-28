package Display;

import javax.swing.*;
import java.awt.*;

public class GuessTextField extends JTextField {
    public GuessTextField(int columns) {
        super(columns);
        //guessField = new JTextField(16);
        this.setEditable(true);


        this.setPreferredSize(new Dimension(250, 30)); // Set the tableSize of the text field
        this.setBackground(Color.BLACK);
        this.setForeground(new Color(0x00FF00));
        this.setCaretColor(new Color(0x00FF00));
        this.setFont(new Font("Consolas", Font.PLAIN, 25));
        this.setHorizontalAlignment(JTextField.CENTER);
        //guessField.addKeyListener(keyHandler);

    }

    public void actionListenerGuessTextField(Thread gameThread) {
        this.addActionListener(_ -> {

            synchronized (gameThread) {
                gameThread.notify();
            }
        });
    }
}
