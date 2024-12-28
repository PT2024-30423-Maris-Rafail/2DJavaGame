package Display;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends javax.swing.JPanel {

    public ScorePanel(JScrollPane scrollPane) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        LabelHolder l1 = new LabelHolder("Game Over! These are the top 10 scores:");
        LabelHolder l2 = new LabelHolder("Your scores are highlighted with red");
        add(l1, BorderLayout.NORTH);
        add(l2, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
