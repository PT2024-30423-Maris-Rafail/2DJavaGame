
package geoGame;

import Components.GamePanel;

public class GuessLocation {
    private String country;
    final GamePanel panel;
    int[] locations;
    int correctGuessesLimit = 2;
    double timeBeforeGeo = 0;
    double timeAfterGeo = 0;
    String currentGuess = null;

    public GuessLocation(GamePanel panel) {
        this.panel = panel;
        locations = new int[5];
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLocations(int current) {
        locations[panel.geoGameManager.guesses] = current;
        System.out.println("For guess #" + panel.geoGameManager.guesses + ": " + current);
    }

    private void getInput() {
        synchronized (panel.gameThread) {
            try {
                timeBeforeGeo = System.nanoTime();
                panel.gameThread.wait(); // Wait for input
                timeAfterGeo = System.nanoTime();
                currentGuess = panel.geoGameManager.guessField.getText(); // get input
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void notifyPanel() {
        panel.geoGameManager.playsGeo = false;
        panel.state = panel.previousState;
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        panel.player.fragmentNumber++;
        panel.geoGameManager.firstContact = true;
    }

    private void correctGuess(int currentFragment) {
        if (panel.player.fragmentNumber == 3) panel.timeInSec += (timeAfterGeo - timeBeforeGeo) / 1000000000.0;

        notifyPanel();

        for (int i = 0; i < panel.geoGameManager.guesses; i++) panel.dbConnection.markUNVisited(locations[i]);
        if (!panel.player.hasDarkCompass) {
            panel.objectManager.fragments.fragments[currentFragment] = null;
        } else {
            panel.map.tilesManager[1][panel.objectManager.fragments.fragments[currentFragment].mapX / GamePanel.ACTUAL_SIZE][panel.objectManager.fragments.fragments[currentFragment].mapY / GamePanel.ACTUAL_SIZE] = 1;
            panel.itemSetter.setFragment(currentFragment);
        }

        panel.geoGameManager.textFieldPanel.setVisible(false);
        panel.UI.time = System.currentTimeMillis();
        panel.UI.correctGuess = true;
        panel.headingSlider.setVisible(false);
    }

    private void guessLimitReached(int currentFragment) {
        panel.geoGameManager.playsGeo = false;
        panel.state = panel.previousState;
        panel.setFocusable(true); // Allow the panel to gain focus
        panel.requestFocusInWindow(); // Request focus for key events
        //panel.player.fragmentNumber++;
        panel.geoGameManager.firstContact = true;
        for (int i = 0; i < panel.geoGameManager.guesses; i++) panel.dbConnection.markUNVisited(locations[i]);
        panel.map.tilesManager[1][panel.objectManager.fragments.fragments[currentFragment].mapX / GamePanel.ACTUAL_SIZE][panel.objectManager.fragments.fragments[currentFragment].mapY / GamePanel.ACTUAL_SIZE] = 1;
        panel.itemSetter.setFragment(currentFragment);
        panel.geoGameManager.textFieldPanel.setVisible(false);
        panel.headingSlider.setVisible(false);
    }

    private void guessCheck(int currentFragment) {
        if (currentGuess != null && currentGuess.equals(country)) {
            //System.out.println("You guessed the correct guess!");
            panel.geoGameManager.correctGuesses++;
        }
        if (panel.geoGameManager.correctGuesses == correctGuessesLimit) {
            correctGuess(currentFragment);
            panel.geoGameManager.geoGame.emptyImages();
        } else if (panel.geoGameManager.guesses == 5) {
            guessLimitReached(currentFragment);
            panel.geoGameManager.geoGame.emptyImages();
        }
    }

    public void guess(int currentFragment) {
        panel.headingSlider.setVisible(true);
        panel.headingSlider.setFocusable(true);
        currentGuess = null;
        timeBeforeGeo = 0;
        timeAfterGeo = 0;
        getInput();

        panel.geoGameManager.guesses++;
        panel.geoGameManager.guessField.setText("");

        guessCheck(currentFragment);
        panel.geoGameManager.hasCountry = false;

        panel.needsReload = true;

        panel.geoGameManager.isReady = false;
        panel.headingSlider.setValue(0);
        //System.out.println("CG: " + panel.geoGameManager.correctGuesses + " G:" + panel.geoGameManager.guesses);

    }
}
