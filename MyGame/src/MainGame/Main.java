package MainGame;

import Components.ButtonSetup;
import Components.GameWindow;
import Components.MainPanel;
import Map.MazeGeneratorBFS;
//TO DO:

/// after updating the tiles: in map update which cause collisions
public class Main {

    public static void game1() {

        GameWindow gameWindow = new GameWindow();

        MainPanel mainPanel = new MainPanel(gameWindow);

        gameWindow.setProperties(mainPanel);

        ButtonSetup.setupActionListeners(gameWindow, mainPanel);
    }

    public static void main(String[] args) {
        //MazeGeneratorDFS.main();
        MazeGeneratorBFS.makeMaze();
        game1();
    }

}
