package Display;

import javax.swing.*;
import java.awt.*;

public class LabelHolder extends JLabel {
    public LabelHolder(String label) {
        super(label);
        //JLabel headerLabel = new JLabel("Game Over! These are the top 10 scores:");
        setFont(new Font("Arial", Font.BOLD, 18));
        setHorizontalAlignment(SwingConstants.CENTER);
        setForeground(new Color(50, 50, 150));
    }
}
