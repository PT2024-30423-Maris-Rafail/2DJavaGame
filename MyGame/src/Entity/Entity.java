package Entity;

import Display.Direction;

import java.awt.image.BufferedImage;

public class Entity {
    public int mapX;
    public int mapY;
    public int speed;

    public BufferedImage down1, down2, up1, up2, left1, left2, right1, right2;
    public Direction direction;
    int whichImage = 1, imageCounter = 0;
    public int collisionX,collisionY;
    public int widthCollision, heightCollision;
    public boolean collides = false;
    //public String
}
