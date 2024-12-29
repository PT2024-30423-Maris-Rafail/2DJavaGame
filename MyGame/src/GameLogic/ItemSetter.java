package GameLogic;

import ConnecToDB.ConnectR;
import Components.GamePanel;
import Items.*;

import java.util.Random;


public class ItemSetter {
    GamePanel panel;
    ConnectR connection = new ConnectR();
    Random rand = new Random();

    public ItemSetter(GamePanel panel) {
        this.panel = panel;
    }

    /**
     * Places the items onto the map
     */
    public void setItem() {

        //items is the array of the compasses and the door that is shut. they are loaded and placed
        panel.objectManager.items[0] = new Compass();
        panel.objectManager.items[0].collision = connection.gameConnect.getCollisionForObjects(panel.objectManager.items[0].name);
        //System.out.println(panel.objectManager.items[0].collision);
        panel.objectManager.items[1] = new DarkCompass();
        panel.objectManager.items[1].collision = connection.gameConnect.getCollisionForObjects(panel.objectManager.items[1].name);
        //System.out.println(panel.objectManager.items[1].collision);
        panel.objectManager.items[2] = new Door();
        panel.objectManager.items[2].collision = connection.gameConnect.getCollisionForObjects(panel.objectManager.items[2].name);
        //System.out.println(panel.objectManager.items[2].collision);

        panel.objectManager.items[0].mapX = 15 * GamePanel.ACTUAL_SIZE;
        panel.objectManager.items[0].mapY = 21 * GamePanel.ACTUAL_SIZE;

        panel.objectManager.items[1].mapX = 25 * GamePanel.ACTUAL_SIZE;
        panel.objectManager.items[1].mapY = 25 * GamePanel.ACTUAL_SIZE;

        panel.objectManager.items[2].mapX = 24 * GamePanel.ACTUAL_SIZE;
        panel.objectManager.items[2].mapY = 0;

        setFragments();

    }

    /**
     * randomly chooses a location for the items
     * logic can be optimized, but, in practice, we are sure a correct placement is reached
     *
     * @param i index of item to set
     */
    public void setFragment(int i) {
        panel.objectManager.fragments.fragments[i] = new FragObj(i);
        panel.objectManager.fragments.fragments[i].collision = connection.gameConnect.getCollisionForObjects(panel.objectManager.fragments.fragments[i].name);
        int x, y;
        if (i == 0) {//for testing purposes, this item was on 10, 10 always
            x = 23;
            y = 24;
        } else {
            do {
                x = rand.nextInt(50);
                y = rand.nextInt(50);

            } while (panel.map.tilesManager[1][x][y] != 1);
        }
        //System.out.println("For " + i + " x:" + x + " y:" + y);
        panel.objectManager.fragments.fragments[i].mapX = x * GamePanel.ACTUAL_SIZE;
        panel.objectManager.fragments.fragments[i].mapY = y * GamePanel.ACTUAL_SIZE;
        panel.map.tilesManager[1][x][y] = 13;
    }

    public void setFragments() {
        for (int i = 0; i < 4; i++) {
            setFragment(i);
        }

    }
}
