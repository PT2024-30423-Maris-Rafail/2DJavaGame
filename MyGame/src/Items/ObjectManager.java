package Items;
import Display.Panel;
import GameState.State;

import java.awt.*;

public class ObjectManager {
    public Item[] items;
    public Fragments fragments;
    public Panel panel;
    public ObjectManager(Panel panel) {
        items = new Item[10];
        this.panel = panel;
        fragments = new Fragments();
    }
    public void draw(State state, Graphics2D g2d) {
        if(state == State.MAP1 || state == State.PAUSE){
            if(items[0] != null && !panel.player.hasDarkCompass && !panel.player.hasCompass)panel.objectManager.items[0].draw(g2d,panel);
            if(items[1] != null && !panel.player.hasDarkCompass && !panel.player.hasCompass) panel.objectManager.items[1].draw(g2d,panel);
            if(items[2] != null) panel.objectManager.items[2].draw(g2d,panel);
        }
        else if(state == State.MAP2){
            for(int i=0; i<fragments.fragments.length; i++) {
                if(fragments.fragments[i]!=null) fragments.fragments[i].draw(g2d,panel);
            }
        }

    }

}
/*
package Map;

import Display.Panel;
import GameState.State;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import ConnecToDB.*;
public class Map {
    Panel panel;
    public Tile[] tiles;
    //BufferedImage[] allTiles;

    String folderPath ;
    String map;
    File folder;
    public File[][] images;//I officially love java!!!

    public int[][][] tilesManager;
    public int wrldCol,wrldRow;
    public int wrldWidth,wrldHeight;

    public Map(Panel panel) {
        this.panel = panel;
        wrldHeight = panel.actualSize * wrldRow;
        wrldWidth = panel.actualSize * wrldCol;
        System.out.println(panel.nrOfMaps);
        tilesManager = new int[panel.nrOfMaps][wrldCol][wrldRow];
        //System.out.println(folderPath);
        //images = new File[panel.nrOfMaps][100];

        folder = new File("images/Tiles/voiD");
        images[0] = folder.listFiles((dir, name) -> name.endsWith(".png"));
        folder = new File("images/Tiles/overworld");
        images[1] = folder.listFiles((dir, name) -> name.endsWith(".png"));
        for(int i=0;i<panel.nrOfMaps;i++) {
            createMap(map,panel.currentMap);
        }


    }

    public void createMap(String path,int whichMap) {

        //allTiles = new BufferedImage[images.length];
        //System.out.println(images.length);
        tiles = new Tile[images.length];
        for (int i = 0; i < images.length; i++) {
            tiles[i] = new Tile();
        }
        //which tiles can't be passed through?
        tiles[0].isCollision=true;
        for(int i=2 ;i<=6;i++) tiles[i].isCollision=true;

        preloadImages(whichMap);
        try {
            InputStream read = getClass().getClassLoader().getResourceAsStream(path);
            BufferedReader fscanf = new BufferedReader(new InputStreamReader(read));

            int col = 0;
            int row = 0;
            while (col < wrldCol || row < wrldRow) {
                col = 0;
                String line = fscanf.readLine();
                while (col < wrldCol) {

                    String[] tiles = line.split(" ");
                    int x = Integer.parseInt(tiles[col]);
                    tilesManager[panel.currentMap][col][row] = x;

                    col++;
                }
                row++;
            }
            fscanf.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void preloadImages(int whichMap) {
        ConnectR connection = new ConnectR();
        try {
            for (int i = 0; i < images.length; i++) {
                //System.out.println(i+"\n");

                tiles[i].image = ImageIO.read(images[whichMap][i]);
                tiles[i].imageName = images[whichMap][i].getName();
                tiles[i].isCollision = connection.getCollision(images[whichMap][i].getName());
                //System.out.println(connection.getCollision(images[i].getName()));
                //System.out.println(tiles[i].imageName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void drawTiles(Graphics2D g2d) {
        int col = 0;
        int row = 0;

        while (col < wrldCol && row < wrldRow) {
            int mapX = col * panel.actualSize;
            int mapY = row * panel.actualSize;

            int screenX = mapX - panel.player.mapX + panel.player.playerX;
            int screenY = mapY - panel.player.mapY + panel.player.playerY;

            int tileNum = tilesManager[panel.currentMap][col][row];
            //System.out.println(tileNum);
            //System.out.println(mapX + " " + panel.player.mapX + " " + panel.player.playerX);
            if(mapX >= panel.player.mapX-panel.player.playerX - panel.actualSize && mapX <= panel.player.mapX+panel.player.playerX + panel.actualSize &&
                    mapY >= panel.player.mapY-panel.player.playerY - panel.actualSize && mapY <= panel.player.mapY+panel.player.playerY + panel.actualSize) {
                g2d.drawImage(tiles[tileNum].image, screenX, screenY, panel.actualSize, panel.actualSize, null);
            }


            col++;

            if (col == wrldCol) {//FUDGE OFF
                col = 0;
                row++;

            }
        }
    }
}





 */
/*
package Map;

import Display.Panel;
import GameState.State;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import ConnecToDB.*;
public class Map {
    Panel panel;
    public Tile[] tiles;
    //BufferedImage[] allTiles;

    String folderPath ;
    String map;
    File folder;
    public File[] images;//I officially love java!!!

    public int[][][] tilesManager;
    public int wrldCol,wrldRow;
    public int wrldWidth,wrldHeight;

    public Map(Panel panel, State state) {

        switch(state){
            case MAP1 -> {folderPath = "images/Tiles/voiD";map = "Maps/map1.txt";wrldCol=panel.wordlCol[0];wrldRow=panel.wordlRow[0];break;}
            case MAP2 -> {folderPath = "images/Tiles/overworld";map = "Maps/map2.txt";wrldCol=panel.wordlCol[1];wrldRow=panel.wordlRow[1];break;}
            default -> {folderPath = "images/Tiles";map="Maps/map1.txt";break;}
        }
        wrldHeight = panel.actualSize * wrldRow;
        wrldWidth = panel.actualSize * wrldCol;
        System.out.println(panel.nrOfMaps);
        tilesManager = new int[panel.nrOfMaps][wrldCol][wrldRow];
        //System.out.println(folderPath);
        folder = new File(folderPath);
        images = folder.listFiles((dir, name) -> name.endsWith(".png"));
        this.panel = panel;
        //allTiles = new BufferedImage[images.length];
        //System.out.println(images.length);
        tiles = new Tile[images.length];
        for (int i = 0; i < images.length; i++) {
            tiles[i] = new Tile();
        }
        //which tiles can't be passed through?
        tiles[0].isCollision=true;
        for(int i=2 ;i<=6;i++) tiles[i].isCollision=true;

        preloadImages();

        createMap(map);

    }

    public void createMap(String path) {
        try {
            InputStream read = getClass().getClassLoader().getResourceAsStream(path);
            BufferedReader fscanf = new BufferedReader(new InputStreamReader(read));

            int col = 0;
            int row = 0;
            while (col < wrldCol || row < wrldRow) {
                col = 0;
                String line = fscanf.readLine();
                while (col < wrldCol) {

                    String[] tiles = line.split(" ");
                    int x = Integer.parseInt(tiles[col]);
                    tilesManager[panel.currentMap][col][row] = x;

                    col++;
                }
                row++;
            }
            fscanf.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void preloadImages() {
        ConnectR connection = new ConnectR();
        try {
            for (int i = 0; i < images.length; i++) {
                //System.out.println(i+"\n");

                tiles[i].image = ImageIO.read(images[i]);
                tiles[i].imageName = images[i].getName();
                tiles[i].isCollision = connection.getCollision(images[i].getName());
                //System.out.println(connection.getCollision(images[i].getName()));
                //System.out.println(tiles[i].imageName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void drawTiles(Graphics2D g2d) {
        int col = 0;
        int row = 0;

        while (col < wrldCol && row < wrldRow) {
            int mapX = col * panel.actualSize;
            int mapY = row * panel.actualSize;

            int screenX = mapX - panel.player.mapX + panel.player.playerX;
            int screenY = mapY - panel.player.mapY + panel.player.playerY;

            int tileNum = tilesManager[panel.currentMap][col][row];
            //System.out.println(tileNum);
            //System.out.println(mapX + " " + panel.player.mapX + " " + panel.player.playerX);
            if(mapX >= panel.player.mapX-panel.player.playerX - panel.actualSize && mapX <= panel.player.mapX+panel.player.playerX + panel.actualSize &&
                    mapY >= panel.player.mapY-panel.player.playerY - panel.actualSize && mapY <= panel.player.mapY+panel.player.playerY + panel.actualSize) {
                g2d.drawImage(tiles[tileNum].image, screenX, screenY, panel.actualSize, panel.actualSize, null);
            }


            col++;

            if (col == wrldCol) {//FUDGE OFF
                col = 0;
                row++;

            }
        }
    }
}





 */