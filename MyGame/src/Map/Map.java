package Map;

import Components.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

import ConnecToDB.*;

public class Map {
    GamePanel panel;
    public Tile[][] tiles;

    File folder;
    public File[][] images;//I officially love java!!!

    public int[][][] tilesManager;
    public int[] wrldCol, wrldRow;
    public int[] wrldWidth, wrldHeight;

    public Map(GamePanel panel) {

        this.panel = panel;

        wrldCol = new int[panel.nrOfMaps];
        wrldRow = new int[panel.nrOfMaps];
        wrldWidth = new int[panel.nrOfMaps];
        wrldHeight = new int[panel.nrOfMaps];

        for (int i = 0; i < panel.nrOfMaps; i++) {
            wrldCol[i] = panel.wordlCol[i];
            wrldRow[i] = panel.wordlRow[i];
            wrldWidth[i] = wrldCol[i] * GamePanel.ACTUAL_SIZE;
            wrldHeight[i] = wrldRow[i] * GamePanel.ACTUAL_SIZE;
        }

        images = new File[panel.nrOfMaps][];
        folder = new File("images/Tiles/voiD");
        images[0] = folder.listFiles((_, name) -> name.endsWith(".png"));
        folder = new File("images/Tiles/overworld");
        images[1] = folder.listFiles((_, name) -> name.endsWith(".png"));
        //System.out.println(images[1][0].getName());
        tiles = new Tile[panel.nrOfMaps][];
        tilesManager = new int[panel.nrOfMaps][][];
        createMap("Maps/map1.txt", 0);
        createMap("Maps/map2.txt", 1);
    }

    public static boolean okToDraw(GamePanel panel, int mapX, int mapY) {
        return (mapX >= panel.player.mapX - panel.player.playerX - GamePanel.ACTUAL_SIZE && mapX <= panel.player.mapX + panel.player.playerX + GamePanel.ACTUAL_SIZE &&
                mapY >= panel.player.mapY - panel.player.playerY - GamePanel.ACTUAL_SIZE && mapY <= panel.player.mapY + panel.player.playerY + GamePanel.ACTUAL_SIZE);
    }

    private void createTiles(int whichMap) {
        tilesManager[whichMap] = new int[wrldCol[whichMap]][wrldRow[whichMap]];
        tiles[whichMap] = new Tile[images[whichMap].length];
        for (int i = 0; i < images[whichMap].length; i++) {
            tiles[whichMap][i] = new Tile();
        }
    }

    private void getMapFromFile(String path, int whichMap) {
        try {
            InputStream read = getClass().getClassLoader().getResourceAsStream(path);
            assert read != null;
            BufferedReader fscanf = new BufferedReader(new InputStreamReader(read));
            int col = 0;
            int row = 0;
            while (col < wrldCol[whichMap] || row < wrldRow[whichMap]) {
                col = 0;
                String line = fscanf.readLine();
                while (col < wrldCol[whichMap]) {

                    String[] tiles = line.split(" ");
                    int x = Integer.parseInt(tiles[col]);
                    tilesManager[whichMap][col][row] = x;
                    col++;
                }
                row++;
            }
            fscanf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTileCollision(int whichMap) {
        tiles[whichMap][0].isCollision = true;
        for (int i = 2; i <= 6; i++) tiles[whichMap][i].isCollision = true;
    }

    private void createMap(String path, int whichMap) {
        createTiles(whichMap);

        setTileCollision(whichMap);
        preloadImages(whichMap);
        getMapFromFile(path, whichMap);
    }

    private void preloadImages(int whichMap) {
        ConnectR connection = new ConnectR();
        try {
            for (int i = 0; i < images[whichMap].length; i++) {
                tiles[whichMap][i].image = ImageIO.read(images[whichMap][i]);
                tiles[whichMap][i].imageName = images[whichMap][i].getName();
                tiles[whichMap][i].isCollision = connection.getCollision(images[whichMap][i].getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int which = 0;

    private void swapMap() {
        if (!panel.player.keyHandler.PAUSED) {
            switch (panel.state) {
                case MAP1, MAP1_PORTAL_OPENED -> which = 0;
                case MAP2 -> which = 1;

            }
        }
    }

    public void drawTiles(Graphics2D g2d) {
        swapMap();
        int col = 0;
        int row = 0;
        while (col < wrldCol[which] && row < wrldRow[which]) {
            int mapX = col * GamePanel.ACTUAL_SIZE;
            int mapY = row * GamePanel.ACTUAL_SIZE;

            int screenX = mapX - panel.player.mapX + panel.player.playerX;
            int screenY = mapY - panel.player.mapY + panel.player.playerY;
            //System.out.println(col+" "+row);
            int tileNum = tilesManager[which][col][row];
            if (okToDraw(panel, mapX, mapY)) {
                g2d.drawImage(tiles[which][tileNum].image, screenX, screenY, GamePanel.ACTUAL_SIZE, GamePanel.ACTUAL_SIZE, null);
            }
            col++;
            if (col == wrldCol[which]) {//FUDGE OFF
                col = 0;
                row++;

            }
        }
    }
}




