
/* REMINDER:
UP LEFT: 0,0
        (0,0) X -> (MAX,0)
        Y
        |
        V
        (0,MAX)
 */

        package Display;

import ConnecToDB.ConnectR;
import Entity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import GameState.MusicName;
import GameState.State;
import Items.ObjectManager;
import Map.*;
import Music.MusicPlayer;
import geoGame.GuessLocation;
import geoGame.Location;
import geoGame.StreetViewImage;


public class Panel extends JPanel implements Runnable {
    //All hail what Java developers give us
    public final int TileSize = 32;//we want tiles of 16 pixels, but our screens are too god
    //suffering from succes.
    public final int scale = 3;//We must scale this to fit our needs

    public final int actualSize = scale * TileSize; //each pixel we want is to be a 3*3 pixel on the panel

    public final int screenCol = 12;
    public final int screenRow = 10;
    public final int screenWidth = actualSize * screenCol;//16*16=576
    public final int screenHeight = actualSize * screenRow;// -||-
    public boolean playsGeo = false;
    //but the game doesn't wait for user input, it keeps running
    //we need to set an FPS to see how fast we update

    public Thread gameThread;
    public Thread timerThread;//used to measure the timem for updates

    public KeyboardInputSolver keyHandler = new KeyboardInputSolver();//like scanner, detects input
    //reminds me of assembly, yikes

    //but where does the player sit? If we do the logical, seemingly realistic action of loading the map and moving across
    //it, we'd run into memory issues along the road, since a lot of games (Elden Ring, my beloved, are very detailed)
    //In 3D it's a bit more complicated, but in 2D, we can simply not move the character, but the map itself

    int speedMultiplier = 20;//i add this for the future. if we add a 2* speed feature

    public MusicPlayer musicPlayer;// = new MusicPlayer();

    public final int[] wordlCol = {51,50};
    public final int[] wordlRow = {51,50};
    //public final int worldWidth = actualSize * wordlCol;
    //public final int worldHeight = actualSize * wordlRow;
    public int nrOfMaps = 20;
    public int currentMap = 0;
    public int playedSong = 0;
    public Map map = new Map(this);

    public State state = State.TITLE_SCREEN;
    //public State previousState = State.MAP1;
    int FPS = 60;
    public Player player = new Player(this, keyHandler, speedMultiplier);

    public CheckCollision checkCollision = new CheckCollision(this);

    public ObjectManager objectManager = new ObjectManager(this);
    public ItemSetter itemSetter = new ItemSetter(this);
    public UserInterf UI = new UserInterf(this);

    /// FOR GEO
    public ConnectR dBConnection;
    public Location location;
    public StreetViewImage geoGame = new StreetViewImage(this);
    public boolean needsReload = true;
    public boolean hasCountry = false, hasCountry1 = false;
    public int sliderValue,sliderValue1;
    int size ;
    Random random = new Random();
    //int index = random.nextInt(size)+1;
    GuessLocation guesser;
    public int guesses,correctGuesses;
    public boolean moveCurrent = false;
    public State previousState;
    public boolean firstContact = true;
    public boolean isReady = false;
    public JSlider headingSlider;
    public JTextField guessField;
    public JButton button;
    public JPanel textFieldPanel = new JPanel();
    public int guessTime;
    ///LOG IN
    //public Register register;

    public Panel(MusicPlayer musicPlayer) {
//        register = new Register();
//        this.add(register);
//        register.setVisible(true);
        //this.frame = window;
        //constructer sets parameters relative to the JPanel class
        this.musicPlayer = musicPlayer;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);//for better rendering performance

        this.setLayout(new BorderLayout());///NIGGUS
        // Initialize the slider
        headingSlider = new JSlider(0, 360, 5);
        headingSlider.setMajorTickSpacing(90);
        headingSlider.setPaintTicks(true);
        headingSlider.setPaintLabels(true);
        sliderValue1 = sliderValue;
        // Add listener to the slider
        headingSlider.addChangeListener(e -> {
            sliderValue = headingSlider.getValue();
            //System.out.println("Slider value: " + sliderValue+" "+ sliderValue1);
            repaint(); // Trigger a repaint if necessary
        });
        // Add the slider to the bottom of the panel
        //headingSlider.setBounds(0,screenHeight,screenWidth,100);
        this.add(headingSlider, BorderLayout.SOUTH);
        headingSlider.setVisible(false);
        // Call revalidate to refresh layout
        this.revalidate();

        this.addKeyListener(keyHandler);//lets panel recognize the input

        this.requestFocusInWindow();
        //objectManager.
        itemSetter.setItem();
        //clipEmpty = musicPlayer.getsClip(MusicName.Empty);
        //clipPause = musicPlayer.getsClip(MusicName.Geom_Dash);

        ///FOR GEO
        dBConnection = new ConnectR();
        size = dBConnection.getSizeOfTable("locations");
        dBConnection.resetTable(size);
        guesser = new GuessLocation(this);
        location = new Location();


        guessField = new JTextField(16);
        guessField.setEditable(true);

// Set preferred size for the JTextField


// Use a panel with FlowLayout to respect the preferred size
        //JPanel textFieldPanel = new JPanel(new FlowLayout());
        //textFieldPanel.add(guessField);

        textFieldPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Align components to the right
        textFieldPanel.setOpaque(false); // Make the panel transparent
        guessField.setPreferredSize(new Dimension(250, 30)); // Set the size of the text field
        guessField.setBackground(Color.BLACK);
        guessField.setForeground(new Color(0x00FF00));
        guessField.setCaretColor(new Color(0x00FF00));
        guessField.setFont(new Font("Consolas",Font.PLAIN,25));
        guessField.setHorizontalAlignment(JTextField.CENTER);
        //guessField.addKeyListener(keyHandler);
        guessField.addActionListener(e -> {
            String guess = guessField.getText();
            synchronized (gameThread){
                gameThread.notify();
            }
        });
// Add the panel to the top of the parent panel
        this.add(textFieldPanel, BorderLayout.NORTH);
        textFieldPanel.add(guessField);
        //guessField.addKeyListener(keyHandler);
        textFieldPanel.setVisible(false);
        guessTime = 0;
    }

    public void startTheThread() {

        gameThread = new Thread(this);//we offer the Panel class to the Thread
        gameThread.start();//this automatically calls the run method, so we use the
        //run method to execute our paint/repaint game loop

    }

    @Override
    public void run() {

        //System.out.println(size);
        //System.out.println(player.playerX + " " + player.playerY);

        double drawInterval = 1000000000 / FPS; ///redwar the screen each 0.166... seconds
        //for our game loop, due to how the methods are used, the timem is in nanoseconds
        //nano = 10^(-9), so the draw interval is 1sec/ FPS, so every one 60th of a second, we want to do the update and redrawing

        double nextDrawTime = System.nanoTime() + drawInterval;///System.nanoTime-> current timem
        //our loop starts, let's say, at a timem t, so we start measuring from that onward.
        //the next cycle must be after a drawInterval has passed=> t + drawInterval

        //second method: use a delta timer
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        boolean doMap = true;
        //musicPlayer.play(state,5);
        //this must run indefinitely aka: the thread is not null

        State beforePause = State.CHILLING;
        while (gameThread.isAlive()) {
            //if(state!=State.PAUSE)guessTime++;
            //System.out.println(guessTime/60);
            State currentState = state;
            //TO DO: implement how the state is changed

            if(state == State.MAP1) {
//                if(clipEmpty == null) {
//                    clipEmpty = musicPlayer.getsClip(MusicName.Empty);
//                }
                musicPlayer.playSound(MusicName.Empty);
                //clipEmpty.setFramePosition(0);
                //clip = musicPlayer.playSad();
                //clipEmpty.start();
                System.out.println("AAAAAAAAAAAAAA");}
            else {

                if(state == State.MAP2) {musicPlayer.playSound(MusicName.Aria_Math);}
                if(state == State.TITLE_SCREEN){
                    //musicPlayer.playSound(MusicName.JOYRIDE);
                }

            }
            while (gameThread != null && state == currentState) {//or gameThread != null
                //System.out.println(guessTime/60);
                //if(state!=State.PAUSE)guessTime++;
                //System.out.println("here "+state);
                //System.out.println(state+" "+previousState);
                if(player.hasDarkCompass && (state == State.MAP1 || state == State.MAP1_PORTAL_OPENED)) {
                    map.tiles[0][1].isCollision=false;
                }
                //musicPlayer.play(State.MAP1);
                //we must:
                //1. make updates each division of a second (the division process is to be implemented)
                //2. draw as express by the given updates
                //the methods used for this are defined below (paintComponent and update)
                //to call paintComponent, we must use repaint... confusing :D

                ///to ensure the 1/FPS game loop cycle, we have 2 methods: making the thread sleep until the necessary timem has passed:
            /*try {
            //put in a try - catch due to the InterruptedException that might occur
                double remainingTime = nextDrawTime - System.nanoTime(); //how much timem untill the next repaint?

                remainingTime /= 1000000;//the sleep method accepts only MILISECONDS!!!

                if (remainingTime < 0) {//if some bullshit quantum physics happen and we spent more than needed on paint/update, we start again
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);// nb-ul. problem: sleep method accepts only long => type casting
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
                ///calculating the timem in which we should start the process: aka the DELTA method
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                lastTime = currentTime;
                if (delta >= 1) {
                    update();
                    repaint();
                    delta--;
                }
                if(keyHandler.PAUSED && state!=State.TITLE_SCREEN){
                    if(beforePause==State.CHILLING) beforePause= state;
                    state = State.PAUSE;}
                else{
                    if(beforePause != State.CHILLING) {
                        //clipPause.stop();
                        musicPlayer.stopSound(MusicName.Geom_Dash);
                        //clipEmpty.start();
                        if(state==State.MAP1 || state == State.MAP1_PORTAL_OPENED) musicPlayer.playSound(MusicName.Empty);
                        else musicPlayer.playSound(MusicName.Aria_Math);
                        currentState = State.PAUSE;state = beforePause;beforePause = State.CHILLING; }


                }
                //System.out.println(state.toString());
            }
            //System.out.println(beforePause.toString());
            //clipEmpty.stop();
            musicPlayer.stopSound(MusicName.Empty);
            musicPlayer.stopSound(MusicName.Aria_Math);
            if(state == State.PAUSE){
                //if(clipPause == null) clipPause = musicPlayer.getsClip(MusicName.Geom_Dash);
                //clipPause.setFramePosition(0);
                //clipPause.start();
                musicPlayer.playSound(MusicName.Geom_Dash);
            }
            if(currentState == State.TITLE_SCREEN){
                musicPlayer.stopSound(MusicName.Geom_Dash);
            }
            //if()

            System.out.println("state change");
        }

    }

    public void update() {
        if(state!=State.TITLE_SCREEN){
            if(!playsGeo){
                //System.out.println(keyHandler.PAUSED+" "+keyHandler.goRight+" "+keyHandler.goLeft+" "+keyHandler.goUp+" "+keyHandler.goDown );
                if(!keyHandler.PAUSED ) {
                    //System.out.println("negru");

                    player.updatePlayerWithBoolean(state);
                }
                //guessTime = 0;
            }
            else{
                if(isReady)
                {
                    isReady = false;
                    guesser.guess(player.objectOfCol);
                }
                //guessTime++;
                //System.out.println(guessTime/60);
            }
            UI.updateTimer();
        }
        else{
            if(keyHandler.Enter){state = State.MAP1;musicPlayer.stopSound(MusicName.ELEVATOR_PERMIT);}
        }
        //else System.out.println("pisic");


        //player.updatePlayer();
    }

    //the paintComponent method is pre-implemented... thank you, Java. The graphics class is used to draw stuff to screen
    public void paintComponent(Graphics g) {
        //we must call the super method

        super.paintComponent(g);
        //we need to work in 2D, so we get the Graphics2 class
        //pretty much like how wrappers have their own specific methods, idk...
        Graphics2D g2d = (Graphics2D) g;
        if(state == State.TITLE_SCREEN){

        }
        else{

            if(!playsGeo){
                //if(guessField.getParent() == textFieldPanel)textFieldPanel.remove(textFieldPanel);

                map.drawTiles(g2d);
                objectManager.draw(state, g2d);
                player.drawPlayer(g2d);
                UI.setUI(g2d,player);
                g2d.dispose();
                //System.out.println("CIOARA");
            }
            else {
                textFieldPanel.setVisible(true);
                //UI.setUI(g2d,player);
                System.out.println("here");
                if(!hasCountry){
                    if(firstContact){
                        guesses = 0;
                        correctGuesses = 0;
                        firstContact = false;
                    }
                    int niggus = dBConnection.getValidIndex(size);
                    //System.out.println(niggus);
                    location = dBConnection.getCoordinates(niggus);
                    guesser.setCountry(location.getCountry());
                    guesser.setLocations(niggus);
                    hasCountry = true;
                    System.out.println("In panel location: " + location.getCountry());


                    isReady = true;
                }
                if(!hasCountry1){
                    //location =


                    //location = new Location();

                    hasCountry1 = true;
                    //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");

                }
                //System.out.println("niggus");
                if( needsReload ){
                    needsReload = false;
                    geoGame.updateImageAsync(location.getCoordinates());
                    //geoGame.updateImageAsync();
                    //System.out.println("nigeer");
                    sliderValue1 = sliderValue;
                }
                //this.setFocusable(true);
//                    if(isReady){
//
//                    }
                geoGame.drawGeo(g2d);
                //isReady = false;

            }
        }
    }
}













