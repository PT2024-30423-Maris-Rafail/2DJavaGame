package Entity;

import Display.Direction;
import Display.KeyboardInputSolver;
import Display.Panel;
import GameState.MusicName;
import GameState.State;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Player extends Entity {
    Panel panel;
    public KeyboardInputSolver keyHandler;
    public final int playerX;
    public final int playerY;
    public boolean hasCompass = false;
    public boolean hasDarkCompass = false;
    public int fragmentNumber = 3;
    //TODO MAKE FRAGMENT NUMBER 0
    public int objectOfCol;

    public Player(Panel panel, KeyboardInputSolver keyHandler, int speedMultiplier) {
        this.panel = panel;
        this.keyHandler = keyHandler;

        playerX = panel.screenWidth / 2 - (panel.actualSize) / 2;//subtract bc the drawing begins from the up left corner
        playerY = panel.screenHeight / 2 - (panel.actualSize) / 2;

        initialize(speedMultiplier);
        getPlayerImage();
        //clipOutOfB = panel.musicPlayer.getsClip(MusicName.Whoosh);
        //clipSpuderMan = panel.musicPlayer.getsClip(MusicName.Spuder_Man_Fade);
    }

    public void initialize(int speedMultiplier) {
        //the collision: take a 7*7 circle in the middle
        //collisionUpLeft
        mapX = panel.actualSize * 24;//middle of map
        mapY = panel.actualSize * 24;
        speed = speedMultiplier;
        direction = Direction.STOP;
        collisionX = 26;
        collisionY = 60;
        heightCollision = 30;
        widthCollision = 42;

    }

    public boolean isOutsideOfBounds(State state) {
        //System.out.println(mapY+" "+(panel.map.wrldHeight[panel.currentMap] - panel.actualSize/2));
        //System.out.println(panel.map.wrldHeight[panel.currentMap]+" "+panel.map.wrldWidth[panel.currentMap]);
        if (mapY >= panel.map.wrldHeight[panel.currentMap] - panel.actualSize) {
            if ((state == State.MAP1 || state == State.MAP1_PORTAL_OPENED)) {
                System.out.println("cASE1");

                return true;
            } else collides = true;
        }
        if (mapY <= 0 && (state == State.MAP1 || panel.state == State.MAP1_PORTAL_OPENED) && (mapX / panel.actualSize != 24 && mapX / panel.actualSize != 23 && mapX / panel.actualSize != 22 && mapX / panel.actualSize != 25)) {
            System.out.println("cASE2");
            return true;
        }
        if (mapX >= panel.map.wrldWidth[panel.currentMap] - panel.actualSize) {
            if (state == State.MAP1 || state == State.MAP1_PORTAL_OPENED) {
                System.out.println("case3");
                return true;
            } else collides = true;
        }
        return mapX <= 10 && (state == State.MAP1 || panel.state == State.MAP1_PORTAL_OPENED);
    }

    public void updatePlayerWithBoolean(State state) {
        //System.out.println("MOOOOOOOOOR");
        if (mapY / panel.actualSize == 0 && mapX / panel.actualSize == 24 && (hasDarkCompass || hasCompass) && state == State.MAP1_PORTAL_OPENED) {
            panel.state = State.MAP2;
            panel.musicPlayer.stopSound(MusicName.PortalSFX);
//            mapY=panel.actualSize*24;
//            mapX=panel.actualSize*24;
            mapY = panel.actualSize;
            mapX = panel.actualSize;
            panel.currentMap = 1;
            //hasDarkCompass=false;
            //hasCompass=false;
        }
        if (panel.playedSong == 0) {
            if (mapY / panel.actualSize == 22 && mapX / panel.actualSize == 23) {
                panel.playedSong = 1;
//                Clip clipi = panel.musicPlayer.playIntro();
//                //System.out.println("Suna");
//                clipi.start();
                //clipSpuderMan.setFramePosition(0);
                //clipSpuderMan.start();
                panel.musicPlayer.playSound(MusicName.Spuder_Man_Fade);
            }
        }

        if (isOutsideOfBounds(state)) {
            //System.out.println("put");
            mapY = panel.actualSize * 23;
            mapX = panel.actualSize * 23;
            //if(portalSound == null) portalSound= panel.musicPlayer.tpToStart();

            //System.out.println("NEGRO");
//                clipOutOfB.setFramePosition(0);
//                clipOutOfB.start();
            panel.musicPlayer.playSound(MusicName.Whoosh);

        }


        if (!isOutsideOfBounds(state) && (keyHandler.goUp || keyHandler.goDown || keyHandler.goLeft || keyHandler.goRight)) {
            //System.out.println(panel.map.tiles[1].isCollision + " " + panel.map.tiles[1].imageName);
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
            collides = false;

            panel.checkCollision.checkTile(this);//CHECK COLLISION
            objectOfCol = panel.checkCollision.whichItem(this);
            //System.out.println(objectOfCol);
            if (objectOfCol != -1) {
                //System.out.println("OBJECT "+objectOfCol);
                if (panel.state == State.MAP2) {


                    panel.playsGeo = true;
                    panel.previousState = panel.state;
                    panel.state = State.PLAYS_GEO;

                    //fragmentNumber++;
                    keyHandler.setAllFalse();


                    //TO IMPLEMENT HERE FOR THE FRAGMENTS !!!
                } else {
                    switch (objectOfCol) {
                        case 0:
                            panel.objectManager.items[0] = null;
                            panel.objectManager.items[1] = null;
                            panel.objectManager.items[2] = null;

                            hasCompass = true;
                            panel.musicPlayer.playSound(MusicName.PortalSFX);
                            panel.state = State.MAP1_PORTAL_OPENED;
                            break;

                        case 1:
                            panel.playedSong = 1;
                            panel.objectManager.items[0] = null;
                            panel.objectManager.items[1] = null;
                            panel.objectManager.items[2] = null;

                            hasDarkCompass = true;
                            panel.musicPlayer.playSound(MusicName.PortalSFX);
                            panel.state = State.MAP1_PORTAL_OPENED;
                            break;

                    }

                }
            }

            if (collides) System.out.println("Collision Detected");
            //System.out.println("ce pisici");
            collides = false;
            //TODO remove the collides = false
            if (hasDarkCompass || !collides) {

                //System.out.println("can move");
                //System.out.println(mapY+" "+mapX+" "+(mapY/panel.actualSize)+" "+(mapX/panel.actualSize));
                switch (direction) {
                    case UP:
                        if (mapY - speed >= -panel.actualSize / 2) mapY -= speed;
                        break;
                    case DOWN:
                        if (mapY + speed < panel.map.wrldHeight[panel.currentMap] - panel.actualSize * panel.currentMap)
                            mapY += speed;
                        break;
                    case LEFT:
                        if (mapX - speed >= 0) mapX -= speed;
                        break;
                    case RIGHT:
                        if (mapX + speed < panel.map.wrldWidth[panel.currentMap] - 2 * panel.actualSize / 3 * panel.currentMap)
                            mapX += speed;
                        break;

                }

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

    public void drawPlayer(Graphics g2d) {
        //g2d.setColor(Color.WHITE);
        //g2d.fillRect(100, 100, TileSize, TileSize);
        //g2d.fillRect(x, y, panel.actualSize, panel.actualSize);
        BufferedImage image = null;
        switch (direction) {
            case LEFT:
                if (whichImage == 1) {
                    image = left1;
                } else image = left2;


                break;
            case RIGHT:
                if (whichImage == 1) {
                    image = right1;
                } else image = right2;
                break;
            case UP:
                if (whichImage == 1) {
                    image = up1;
                } else image = up2;
                break;
            case DOWN:
                if (whichImage == 1) {
                    image = down1;
                } else image = down2;
                break;
            case STOP:
                image = down1;
            default:
                break;

        }
        g2d.drawImage(image, playerX, playerY, panel.actualSize, panel.actualSize, null);

    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/up2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/down2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/left2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/right2.png")));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
