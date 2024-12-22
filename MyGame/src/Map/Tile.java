package Map;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean isCollision = false;
    public String imageName;
    public boolean teleported = false;
    public int teleportX;
    public int teleportY;
}
