package Entity;

import GameLogic.Direction;
import GameLogic.KeyboardInputSolver;
import Components.GamePanel;
import GameState.MusicName;
import GameState.State;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Player extends Entity {
    GamePanel panel;
    public KeyboardInputSolver keyHandler;
    public final int playerX;
    public final int playerY;
    public boolean hasCompass = false;
    public boolean hasDarkCompass = false;
    public int fragmentNumber = 1;
    //TODO MAKE FRAGMENT NUMBER 0
    public int objectOfCol;
    private int actualX, actualY;

    public Player(GamePanel panel, KeyboardInputSolver keyHandler, int speedMultiplier) {
        this.panel = panel;
        this.keyHandler = keyHandler;

        playerX = GamePanel.SCREEN_WIDTH / 2 - (GamePanel.ACTUAL_SIZE) / 2;//subtract bc the drawing begins from the up left corner
        playerY = GamePanel.SCREEN_HEIGHT / 2 - (GamePanel.ACTUAL_SIZE) / 2;

        initialize(speedMultiplier);
        getPlayerImage();
    }

    public void initialize(int speedMultiplier) {
        mapX = GamePanel.ACTUAL_SIZE * 24;//middle of map
        mapY = GamePanel.ACTUAL_SIZE * 24;
        speed = speedMultiplier;
        direction = Direction.STOP;
        collisionX = 26;
        collisionY = 60;
        heightCollision = 30;
        widthCollision = 42;
    }

    public boolean isOutsideOfBounds(State state) {
        //System.out.println(mapY+" "+(panel.map.wrldHeight[panel.currentMap] - panel.actualSize/2));
        if (mapY >= panel.map.wrldHeight[panel.currentMap] - GamePanel.ACTUAL_SIZE) {
            if ((state == State.MAP1 || state == State.MAP1_PORTAL_OPENED)) {
                //System.out.println("cASE1");
                return true;
            } else collides = true;
        }
        if (mapY <= 0 && (state == State.MAP1 || panel.state == State.MAP1_PORTAL_OPENED) &&
                (actualX != 24 && actualX != 23 && actualX != 22 && actualX != 25)) {
            //System.out.println("cASE2");
            return true;
        }
        if (mapX >= panel.map.wrldWidth[panel.currentMap] - GamePanel.ACTUAL_SIZE) {
            if (state == State.MAP1 || state == State.MAP1_PORTAL_OPENED) {
                //System.out.println("case3");
                return true;
            } else collides = true;
        }
        return mapX <= 10 && (state == State.MAP1 || panel.state == State.MAP1_PORTAL_OPENED);
    }

    private void pickUpCompass() {
        panel.objectManager.items[0] = null;
        panel.objectManager.items[1] = null;
        panel.objectManager.items[2] = null;

        hasCompass = true;
        panel.musicPlayer.playSound(MusicName.PortalSFX);
        panel.state = State.MAP1_PORTAL_OPENED;
    }

    private void pickUpDarkCompass() {
        panel.playedSong = 1;
        panel.objectManager.items[0] = null;
        panel.objectManager.items[1] = null;
        panel.objectManager.items[2] = null;

        hasDarkCompass = true;
        panel.musicPlayer.playSound(MusicName.PortalSFX);
        panel.state = State.MAP1_PORTAL_OPENED;
    }

    private void getCompass(int objectOfCol) {
        if (objectOfCol == 1) pickUpDarkCompass();
        else if (objectOfCol == 0) pickUpCompass();
    }

    private void moveToMap2() {
        panel.state = State.MAP2;
        panel.playedSong = 1;
        panel.musicPlayer.stopSound(MusicName.PortalSFX);
        mapY = GamePanel.ACTUAL_SIZE * 25;
        mapX = GamePanel.ACTUAL_SIZE * 25;
        panel.currentMap = 1;
    }

    private void passThroughBridge() {
        if (actualY == 22 && actualX == 23) {
            panel.playedSong = 1;
            panel.musicPlayer.playSound(MusicName.Spuder_Man_Fade);
        }
    }

    private void goToStart() {
        //System.out.println("put");
        mapY = GamePanel.ACTUAL_SIZE * 24;
        mapX = GamePanel.ACTUAL_SIZE * 24;
        panel.musicPlayer.playSound(MusicName.Whoosh);
    }

    private void setDirection() {
        if (keyHandler.goUp) {
            //mapY-=speed;
            direction = Direction.UP;
        }
        if (keyHandler.goDown) {
            direction = Direction.DOWN;//mapY+=speed;
        }
        if (keyHandler.goLeft) {
            direction = Direction.LEFT;//mapX-=speed;
        }
        if (keyHandler.goRight) {
            direction = Direction.RIGHT;//mapX+=speed;
        }
    }

    private void pickedUpFragment() {
        panel.geoGameManager.playsGeo = true;
        panel.previousState = panel.state;
        panel.state = State.PLAYS_GEO;
        //fragmentNumber++;
        keyHandler.setAllFalse();

        //TO IMPLEMENT HERE FOR THE FRAGMENTS !!!
    }

    private void movePlayer() {
        switch (direction) {
            case UP:
                if (mapY - speed >= -GamePanel.ACTUAL_SIZE / 2) mapY -= speed;
                break;
            case DOWN:
                //without subtracting a tile, we can get out of bounds and walk on the void
                if (mapY + speed < panel.map.wrldHeight[panel.currentMap] - GamePanel.ACTUAL_SIZE * panel.currentMap)
                    mapY += speed;
                break;
            case LEFT:
                if (mapX - speed >= 0) mapX -= speed;
                break;
            case RIGHT:
                if (mapX + speed < panel.map.wrldWidth[panel.currentMap] - GamePanel.ACTUAL_SIZE * panel.currentMap)
                    mapX += speed;
                break;
        }
    }

    private void handlePlayerEvents(State state) {
        actualX = mapX / GamePanel.ACTUAL_SIZE;
        actualY = mapY / GamePanel.ACTUAL_SIZE;
        //System.out.println("MOOOOOOOOOR");
        if (actualY == 0 && actualX == 24 && (hasDarkCompass || hasCompass) && state == State.MAP1_PORTAL_OPENED) {
            moveToMap2();
        }
        if (panel.playedSong == 0) {
            passThroughBridge();
        }
        if (isOutsideOfBounds(state)) {
            goToStart();
        }
    }

    public void updatePlayerWithBoolean(State state) {
        handlePlayerEvents(state);
        if (!isOutsideOfBounds(state) && (keyHandler.goUp || keyHandler.goDown || keyHandler.goLeft || keyHandler.goRight)) {
            //System.out.println(panel.map.tiles[1].isCollision + " " + panel.map.tiles[1].imageName);
            setDirection();
            collides = false;

            panel.checkCollision.checkTile(this);//CHECK COLLISION
            objectOfCol = panel.checkCollision.whichItem(this);
            //System.out.println(objectOfCol);
            if (objectOfCol != -1) {
                //System.out.println("OBJECT "+objectOfCol);
                if (panel.state == State.MAP2) {
                    pickedUpFragment();
                } else {
                    getCompass(objectOfCol);
                }
            }
            if (panel.isAdmin) {collides = false;
            System.out.println("her");}
            //System.out.println("ce pisici");

            //TODO remove the collides = false
            if (hasDarkCompass || !collides) {
                //System.out.println(mapY+" "+mapX+" "+(mapY/panel.actualSize)+" "+(mapX/panel.actualSize));
                movePlayer();
            }
            //System.out.println(hasDarkCompass+" "+collides);
            imageCounter++;
            if (imageCounter > 13) {
                if (whichImage == 1) whichImage = 2;
                else if (whichImage == 2) whichImage = 1;
                imageCounter = 0;
            }
        }
    }

    private BufferedImage getCurrentImage() {
        return switch (direction) {//Nah, I'd annoy my seniors
            case LEFT -> (whichImage == 1) ? left1 : left2;
            case RIGHT -> (whichImage == 1) ? right1 : right2;
            case UP -> (whichImage == 1) ? up1 : up2;
            case DOWN -> (whichImage == 1) ? down1 : down2;
            case STOP -> down1;
        };
    }

    public void drawPlayer(Graphics g2d) {
        BufferedImage image = getCurrentImage();
        g2d.drawImage(image, playerX, playerY, GamePanel.ACTUAL_SIZE, GamePanel.ACTUAL_SIZE, null);
    }

    private BufferedImage getImage(String location) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(location)));
        } catch (IOException e) {
            System.out.println("Unable to acces " + location);
            e.printStackTrace();
        }
        return null;
    }

    private void getPlayerImage() {
        up1 = getImage("/player/up1.png");
        up2 = getImage("/player/up2.png");
        down1 = getImage("/player/down1.png");
        down2 = getImage("/player/down2.png");
        left1 = getImage("/player/left1.png");
        left2 = getImage("/player/left2.png");
        right1 = getImage("/player/right1.png");
        right2 = getImage("/player/right2.png");
    }
}
