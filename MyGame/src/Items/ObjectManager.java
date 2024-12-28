package Items;

import Components.GamePanel;
import GameState.State;

import java.awt.*;

public class ObjectManager {
    public Item[] items;
    public Fragments fragments;
    public GamePanel panel;

    public ObjectManager(GamePanel panel) {
        items = new Item[10];
        this.panel = panel;
        fragments = new Fragments();
    }

    public void draw(State state, Graphics2D g2d) {
        if (state == State.MAP1 || state == State.PAUSE) {
            if (items[0] != null && !panel.player.hasDarkCompass && !panel.player.hasCompass)
                panel.objectManager.items[0].draw(g2d, panel);
            if (items[1] != null && !panel.player.hasDarkCompass && !panel.player.hasCompass)
                panel.objectManager.items[1].draw(g2d, panel);
            if (items[2] != null) panel.objectManager.items[2].draw(g2d, panel);
        }
        if (state == State.MAP2 || (panel.currentMap == 1 && state == State.PAUSE)) {
            for (int i = 0; i < fragments.fragments.length; i++) {
                if (fragments.fragments[i] != null) fragments.fragments[i].draw(g2d, panel);
            }
        }

    }

}
