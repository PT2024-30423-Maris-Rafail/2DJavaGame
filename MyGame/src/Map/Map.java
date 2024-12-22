package Map;

import Display.Panel;
import GameState.State;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import ConnecToDB.*;
public class Map {
    Panel panel;
    public Tile[][] tiles;
    //BufferedImage[] allTiles;

    String folderPath ;
    String map;
    File folder;
    public File[][] images;//I officially love java!!!

    public int[][][] tilesManager;
    public int wrldCol[],wrldRow[];
    public int wrldWidth[],wrldHeight[];

    public Map(Panel panel) {

        this.panel = panel;

        //System.out.println(panel.nrOfMaps);
        wrldCol = new int[panel.nrOfMaps];
        wrldRow = new int[panel.nrOfMaps];
        wrldCol[0]=panel.wordlCol[0];
        wrldRow[0]=panel.wordlRow[0];
        wrldCol[1]=panel.wordlCol[1];
        wrldRow[1]=panel.wordlRow[1];
        wrldWidth=new int[panel.nrOfMaps];
        wrldHeight=new int[panel.nrOfMaps];
        wrldWidth[0]=wrldCol[0]*panel.actualSize;
        wrldHeight[0]=wrldRow[0]*panel.actualSize;
        wrldWidth[1]=wrldCol[1]*panel.actualSize;
        wrldHeight[1]=wrldRow[1]*panel.actualSize;
        //int a=
        //wrldHeight = panel.actualSize * wrldRow;
        //wrldWidth = panel.actualSize * wrldCol;
        //System.out.println(folderPath);
        //images = new File[panel.nrOfMaps][100];
        images = new File[panel.nrOfMaps][];
        folder = new File("images/Tiles/voiD");
        images[0] = folder.listFiles((dir, name) -> name.endsWith(".png"));
        folder = new File("images/Tiles/overworld");
        images[1] = folder.listFiles((dir, name) -> name.endsWith(".png"));
        //System.out.println(images[1][0].getName());
        tiles = new Tile[panel.nrOfMaps][];
        tilesManager = new int[panel.nrOfMaps][][];
        createMap("Maps/map1.txt",0);
        createMap("Maps/map2.txt",1);

//        for(int i=0;i<panel.nrOfMaps;i++) {
//            createMap(map,panel.currentMap);
//        }


    }
    //tilesManager = new int[panel.nrOfMaps][wrldCol[whichMap]+1][wrldRow[whichMap]+1];
    public void createMap(String path,int whichMap) {
        tilesManager[whichMap] = new int[wrldCol[whichMap]][wrldRow[whichMap]];
        //allTiles = new BufferedImage[images.length];
        //System.out.println(images.length);
        tiles[whichMap] = new Tile[images[whichMap].length];
        for (int i = 0; i < images[whichMap].length; i++) {
            tiles[whichMap][i] = new Tile();
        }
        //which tiles can't be passed through?
        tiles[whichMap][0].isCollision=true;
        for(int i=2 ;i<=6;i++) tiles[whichMap][i].isCollision=true;

        preloadImages(whichMap);
        try {
            InputStream read = getClass().getClassLoader().getResourceAsStream(path);
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

    private void preloadImages(int whichMap) {
        ConnectR connection = new ConnectR();
        try {
            for (int i = 0; i < images[whichMap].length; i++) {
                //System.out.println(i+"\n");

                tiles[whichMap][i].image = ImageIO.read(images[whichMap][i]);
                tiles[whichMap][i].imageName = images[whichMap][i].getName();
                tiles[whichMap][i].isCollision = connection.getCollision(images[whichMap][i].getName());
                //System.out.println(connection.getCollision(images[i].getName()));
                //System.out.println(tiles[i].imageName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    int which=0;
    public void drawTiles(Graphics2D g2d) {
        int col = 0;
        int row = 0;

        if(!panel.player.keyHandler.PAUSED){
            switch(panel.state){
                case MAP1,MAP1_PORTAL_OPENED -> which=0;
                case MAP2 -> which=1;

            }
        }
        //System.out.println(which);
        //which=1;
        //System.out.println(which);
        //System.out.println(wrldCol[which]+" "+wrldRow[which]);
        //System.out.println(wrldCol[which]+" "+wrldRow[which]);
        while (col < wrldCol[which] && row < wrldRow[which]) {
            int mapX = col * panel.actualSize;
            int mapY = row * panel.actualSize;

            int screenX = mapX - panel.player.mapX + panel.player.playerX;
            int screenY = mapY - panel.player.mapY + panel.player.playerY;
            //System.out.println(col+" "+row);
            int tileNum = tilesManager[which][col][row];
            //System.out.println(tileNum);
            //System.out.println(mapX + " " + panel.player.mapX + " " + panel.player.playerX);
            if(mapX >= panel.player.mapX-panel.player.playerX - panel.actualSize && mapX <= panel.player.mapX+panel.player.playerX + panel.actualSize &&
                    mapY >= panel.player.mapY-panel.player.playerY - panel.actualSize && mapY <= panel.player.mapY+panel.player.playerY + panel.actualSize) {
                g2d.drawImage(tiles[which][tileNum].image, screenX, screenY, panel.actualSize, panel.actualSize, null);
            }


            col++;

            if (col == wrldCol[which]) {//FUDGE OFF
                col = 0;
                row++;

            }
        }
    }
}




