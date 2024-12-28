package Display;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {
    public TextPanel() {
        super();
        this.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Align components to the right
        this.setOpaque(false); // Make the panel transparent
    }
}