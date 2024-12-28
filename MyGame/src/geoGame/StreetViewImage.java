package geoGame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;

import Components.GamePanel;

public class StreetViewImage extends JFrame {

    private final String apiKey = "AIzaSyB4Xh0kkM0YDRuJ-zncqRhjCUUBEa4TIks";
    private final String size = "1152x960";

    int index = -1;
    public final Image[] rotatingImage = new Image[36];
    public GamePanel panel;

    public StreetViewImage(GamePanel panel) {
        this.panel = panel;
    }

    public void drawGeo(Graphics2D g2d) {
        if (index != -1) index = ((int) Math.floor((double) panel.sliderValue / 10)) % 36;
        else {
            for (int i = 0; i < 9; i++) index = i;
        }
        System.out.println(index);
        panel.setFocusable(true);
        g2d.drawImage(rotatingImage[index], 0, 0, GamePanel.ACTUAL_SIZE * 12, GamePanel.ACTUAL_SIZE * 10, null);
    }

    public void emptyImages() {
        for (int i = 0; i < 1; i++) rotatingImage[i] = null;
    }

    private void obtainImage(String location, int heading) {

        try {
            String imageUrl = "https://maps.googleapis.com/maps/api/streetview?size=" + size
                    + "&location=" + location
                    + "&heading=" + heading
                    + "&pitch=0"
                    + "&key=" + apiKey;
            URL url = new URL(imageUrl);
            System.out.println(url.toString());
            Image image = ImageIO.read(url);
            Image scaledImage = image.getScaledInstance(640, 640, Image.SCALE_SMOOTH);
            synchronized (rotatingImage) {
                rotatingImage[heading / 10] = scaledImage;
            }
            SwingUtilities.invokeLater(panel::repaint);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateImageAsync(String location) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 1; i++) {
            //TODO i<36
            int heading = i * 10;
            executor.submit(() -> {
                obtainImage(location, heading);
            });
        }
        executor.shutdown(); // Shutdown the executor after all is solved
    }
}
