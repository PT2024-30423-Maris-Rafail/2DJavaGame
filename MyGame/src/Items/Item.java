package Items;

import java.awt.*;
import java.awt.image.BufferedImage;

import Components.GamePanel;
import Map.Map;

public class Item {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int mapX, mapY;
    public int collisionX = 0, collisionY = 0;
    public int widthCollision = 96, heightCollision = 96;

    public void draw(Graphics2D g2d, GamePanel panel) {

        int screenX = mapX - panel.player.mapX + panel.player.playerX;
        int screenY = mapY - panel.player.mapY + panel.player.playerY;

        if (Map.okToDraw(panel, mapX, mapY)) {
            g2d.drawImage(image, screenX, screenY, GamePanel.ACTUAL_SIZE, GamePanel.ACTUAL_SIZE, null);
        }
    }

}
