package geoGame;

import Components.GamePanel;
import Display.GuessTextField;
import Display.TextPanel;

import java.awt.*;

public class GeoGameManager {
    public boolean playsGeo = false;
    //public ConnectR dBConnection;
    public Location location;
    public StreetViewImage geoGame;
    public boolean hasCountry = false;

    public GuessLocation guesser;
    public int guesses, correctGuesses;
    public boolean firstContact = true;
    public boolean isReady = false;

    public GuessTextField guessField;
    public TextPanel textFieldPanel;
    public int guessTime;

    public GeoGameManager(GamePanel gamePanel) {
        geoGame = new StreetViewImage(gamePanel);
        guesser = new GuessLocation(gamePanel);
        location = new Location();
        textFieldPanel = new TextPanel();
        guessField = new GuessTextField(16);
        guessField.actionListenerGuessTextField(gamePanel.gameThread);
// Add the panel to the top of the parent panel
        gamePanel.add(textFieldPanel, BorderLayout.NORTH);
        textFieldPanel.add(guessField);
        //guessField.addKeyListener(keyHandler);
        textFieldPanel.setVisible(false);
        guessTime = 0;
    }

    public void setPlaysGeo(GamePanel gamePanel) {
        textFieldPanel.setVisible(true);
        if (!hasCountry) {
            //TODO
            if (firstContact) {
                guesses = 0;
                correctGuesses = 0;
                firstContact = false;
            }
            int validIndex = gamePanel.dbConnection.getValidIndex(gamePanel.tableSize);
            //System.out.println(niggus);
            location = gamePanel.dbConnection.getCoordinates(validIndex);
            guesser.setCountry(location.getCountry());
            guesser.setLocations(validIndex);
            hasCountry = true;
            //System.out.println("In panel location: " + location.getCountry());
            isReady = true;
        }
        if (gamePanel.needsReload) {
            gamePanel.needsReload = false;
            geoGame.updateImageAsync(location.getCoordinates());
            //gamePanel.sliderValue1 = gamePanel.sliderValue;
        }
        //geoGame.drawGeo(g2d);
    }
}
