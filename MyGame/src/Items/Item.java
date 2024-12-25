package Items;

import java.awt.*;
import java.awt.image.BufferedImage;

import Display.Panel;

public class Item {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int mapX, mapY;
    public int collisionX = 0, collisionY = 0;
    public int widthCollision = 96, heightCollision = 96;

    public void draw(Graphics2D g2d, Panel panel) {

        int screenX = mapX - panel.player.mapX + panel.player.playerX;
        int screenY = mapY - panel.player.mapY + panel.player.playerY;

        if (mapX >= panel.player.mapX - panel.player.playerX - panel.actualSize && mapX <= panel.player.mapX + panel.player.playerX + panel.actualSize &&
                mapY >= panel.player.mapY - panel.player.playerY - panel.actualSize && mapY <= panel.player.mapY + panel.player.playerY + panel.actualSize) {
            g2d.drawImage(image, screenX, screenY, panel.actualSize, panel.actualSize, null);
        }
    }

}
