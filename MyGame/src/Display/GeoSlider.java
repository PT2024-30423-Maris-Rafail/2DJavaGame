package Display;

import javax.swing.*;

public class GeoSlider extends JSlider {

    public GeoSlider(int min, int max, int value) {
        super(min, max, value);
        //headingSlider = new JSlider(0, 360, 5);
        this.setMajorTickSpacing(90);
        this.setPaintTicks(true);
        this.setPaintLabels(true);

        // Add listener to the slider

        this.setVisible(false);
    }
}
