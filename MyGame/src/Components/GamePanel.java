/* REMINDER:
UP LEFT: 0,0
        (0,0) X -> (MAX,0)
        Y
        |
        V
        (0,MAX)
 */
package Components;

import ConnecToDB.ConnectR;
import Display.CustomFonts;
import Display.GeoSlider;
import Display.StringDrawer;
import Display.UserInterf;
import Entity.Player;

import javax.swing.*;
import java.awt.*;

import GameLogic.CheckCollision;
import GameLogic.ItemSetter;
import GameLogic.KeyboardInputSolver;
import GameState.MusicName;
import GameState.State;
import GameState.StateManager;
import GameState.TimeScore;
import Items.ObjectManager;
import Map.*;
import Music.MusicPlayer;
import geoGame.*;

public class GamePanel extends JPanel implements Runnable {

    //All hail what Java developers give us
    /// Screen settings
    public static final int TILE_SIZE = 32;//we want tiles of 16 pixels, but our screens are too god: suffering from success.
    public static final int SCALE = 3;//We must scale this to fit our needs
    public static final int ACTUAL_SIZE = SCALE * TILE_SIZE; //each pixel we want is to be a 3*3 pixel on the panel
    public static final int SCREEN_COLS = 12;
    public static final int SCREEN_ROWS = 10;
    public static final int SCREEN_WIDTH = ACTUAL_SIZE * SCREEN_COLS;
    public static final int SCREEN_HEIGHT = ACTUAL_SIZE * SCREEN_ROWS;

    /// Game loop
    private static final int FPS = 60;
    public Thread gameThread = new Thread(this);
    public double timeInSec = 0;
    /// Game state
    public State state = State.TITLE_SCREEN;
    public State previousState;
    public State currentState;
    /// Player and map
    public Player player;
    public int speedMultiplier = 6;
    public int nrOfMaps = 2;
    public int currentMap = 0;
    public final int[] wordlCol = {51, 50};
    public final int[] wordlRow = {51, 50};
    public Map map;

    /// Utilities
    public KeyboardInputSolver keyHandler;
    public MusicPlayer musicPlayer;
    public CheckCollision checkCollision;
    public ObjectManager objectManager;
    public ItemSetter itemSetter;
    public UserInterf UI;
    public int playedSong = 0;
    public boolean needsReload = true;
    public int tableSize;
    public GeoSlider headingSlider;
    public CustomFonts customFonts;
    public State beforePause;

    /// Geo game
    public GeoGameManager geoGameManager;
    public int sliderValue;
    //public int sliderValue1;
    /// LOG IN
    public ConnectR dbConnection;
    public String username;
    public boolean isEmail;
    public boolean isAdmin = false;

    /// Leader Board
    public TimeScore currentScore;
    public TimeScore bestTimeScore;
    public boolean isFirstRound = false;
    public MainPanel mainPanel;
    CardLayout cardLayout;
    GameWindow window;

    private void initializeComponents() {
        map = new Map(this);
        gameThread = new Thread(this);
        keyHandler = new KeyboardInputSolver();
        checkCollision = new CheckCollision(this);
        objectManager = new ObjectManager(this);
        itemSetter = new ItemSetter(this);
        player = new Player(this, keyHandler, speedMultiplier);
        headingSlider = new GeoSlider(0, 360, 5);
        headingSlider.addChangeListener(_ -> {
            sliderValue = headingSlider.getValue();
            //System.out.println("Slider value: " + sliderValue+" "+ sliderValue1);
            repaint(); // Trigger a repaint if necessary
        });
        itemSetter.setItem();
        customFonts = new CustomFonts();
        UI = new UserInterf(this);
    }

    private void setPanelProperties() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);//for better rendering performance
        this.setLayout(new BorderLayout());///NIGGUS
    }

    private void geoSetter() {
        geoGameManager = new GeoGameManager(this);
        dbConnection = new ConnectR();
        tableSize = dbConnection.uiConnect.getSizeOfTable("locations");
        dbConnection.geoConnect.resetTable(tableSize);
    }

    public GamePanel(MainPanel mainPanel, GameWindow window) {
        this.window = window;
        this.mainPanel = mainPanel;
        this.cardLayout = window.getCardLayout();
        this.musicPlayer = window.getMusicPlayer();

        initializeComponents();

        setPanelProperties();

        this.add(headingSlider, BorderLayout.SOUTH);
        this.revalidate();
        this.addKeyListener(keyHandler);//lets panel recognize the input
        this.requestFocusInWindow();

        geoSetter();
    }

    public void startTheThread() {
        gameThread.start();
    }

    private void pauseCheck() {
        if (keyHandler.PAUSED && state != State.TITLE_SCREEN) {
            if (beforePause == State.CHILLING) beforePause = state;
            state = State.PAUSE;
        } else {
            StateManager.runManager(this);
        }
    }

    private void musicUpdate() {
        musicPlayer.stopSound(MusicName.Empty);
        musicPlayer.stopSound(MusicName.Aria_Math);

        if (state == State.PAUSE) {
            musicPlayer.playSound(MusicName.Geom_Dash);
            musicPlayer.stopSound(MusicName.Spuder_Man_Fade);
            musicPlayer.stopSound(MusicName.PortalSFX);
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS; ///redwar the screen each 0.166... seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        beforePause = State.CHILLING;
        while (gameThread.isAlive()) {
            currentState = state;
            musicPlayer.playMain(state);
            while (state == currentState) {
                if (state == State.GAME_END1 && keyHandler.Enter) {
                    StateManager.finalStatePrepare(window, this);
                }
                if (player.hasDarkCompass && (state == State.MAP1 || state == State.MAP1_PORTAL_OPENED)) {
                    map.tiles[0][1].isCollision = false;
                }

                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                lastTime = currentTime;
                if (delta >= 1) {
                    update();
                    repaint();
                    delta--;
                }
                pauseCheck();
            }
            musicUpdate();
        }
    }

    private void update() {
        //System.out.println(player.mapX+" "+player.mapY);
        if (state != State.TITLE_SCREEN) {
            if (!geoGameManager.playsGeo) {
                //System.out.println(keyHandler.PAUSED+" "+keyHandler.goRight+" "+keyHandler.goLeft+" "+keyHandler.goUp+" "+keyHandler.goDown );
                if (!keyHandler.PAUSED && state != State.GAME_END1 && state != State.GAME_END2) {
                    player.updatePlayerWithBoolean(state);
                }
            } else {
                if (geoGameManager.isReady) {
                    geoGameManager.isReady = false;
                    geoGameManager.guesser.guess(player.objectOfCol);
                }
            }
            if (state != State.GAME_END2 && state != State.GAME_END1 && !player.hasDarkCompass && player.fragmentNumber == 4) {
                mainPanel.getEndingPanel().prepareEndingPanel();
            }
            if (state != State.GAME_END1 && state != State.PLAYS_GEO && state != State.GAME_END2) updateTimer();
        } else {

            if (keyHandler.Enter) {
                state = State.MAP1;
                musicPlayer.stopSound(MusicName.ELEVATOR_PERMIT);
            }
        }
    }

    public void updateTimer() {
        if (state != State.PAUSE) {
            timeInSec += 1.0 / 60.0; // Increment time by 1/60 seconds
            UI.timeM++;
            UI.timeT++;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (state == State.GAME_END1 || state == State.GAME_END2) {
            if (state == State.GAME_END1) StringDrawer.paintEndGameInfo(g2d, this);

        } else {
            if (state == State.TITLE_SCREEN) {
                StringDrawer.paintTitleScreen(g2d, this);
            } else {
                if (!geoGameManager.playsGeo) {
                    paintNormalGame(g2d);
                } else {
                    geoGameManager.setPlaysGeo(this);
                    geoGameManager.geoGame.drawGeo(g2d);
                    //System.out.println("N GEG NWEJKG WG");
                }
            }
        }
    }

    private void paintNormalGame(Graphics2D g2d) {
        map.drawTiles(g2d);
        objectManager.draw(state, g2d);
        player.drawPlayer(g2d);
        UI.setUI(g2d);
        g2d.dispose();
    }
}
