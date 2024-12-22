
        package geoGame;

import Display.Panel;
import GameState.State;

import java.util.Scanner;

public class GuessLocation {
    private String country;
    final Panel panel;
    int[] locations;
    public GuessLocation(Panel panel) {
        this.panel = panel;
        locations = new int[5];
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setLocations(int current) {
        locations[panel.guesses] = current;
        System.out.println("For guess #"+panel.guesses+": " + current);
    }
    public void guess(int currentFragment){

//        System.out.println("Guessing " + country);
//        Scanner scanner = new Scanner(System.in);
//        String currentGuess = scanner.nextLine();
        String currentGuess = null;

        synchronized (panel.gameThread) {
            try {
                //System.out.println("mata");
                //System.out.println("Waiting for user input...");
                double NIGGERTimeBeforeGeo = System.nanoTime();
                //System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa"+);
                panel.gameThread.wait(); // Wait for input to be provided
                //System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"+System.nanoTime());
                double NIGGERTimeAfterGeo = System.nanoTime();
                //panel.UI.timeInSec+=(NIGGERTimeAfterGeo-NIGGERTimeBeforeGeo)/1000000000.0;
                currentGuess = panel.guessField.getText(); // Retrieve input
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(currentGuess!=null && currentGuess.equals(country)){

            System.out.println("You guessed the correct guess!");
            panel.correctGuesses++;
        }
        if(panel.correctGuesses == 1){
            panel.playsGeo = false;
            panel.state = panel.previousState;
            panel.setFocusable(true); // Allow the panel to gain focus
            panel.requestFocusInWindow(); // Request focus for key events
            panel.player.fragmentNumber++;
            panel.firstContact = true;
            for(int i = 0;i<panel.guesses;i++)panel.dBConnection.markUNVisited(locations[i]);
            if(!panel.player.hasDarkCompass){
                panel.objectManager.fragments.fragments[currentFragment] = null;
            }
            else{
                panel.map.tilesManager[1][panel.objectManager.fragments.fragments[currentFragment].mapX/panel.actualSize][panel.objectManager.fragments.fragments[currentFragment].mapY/panel.actualSize] = 1;
                panel.itemSetter.setFragment(currentFragment);
            }

            panel.textFieldPanel.setVisible(false);
            panel.UI.time = System.currentTimeMillis();
            panel.UI.correctGuess = true;
        }
        else if(panel.guesses == 5){
            panel.playsGeo = false;
            panel.state = panel.previousState;
            panel.setFocusable(true); // Allow the panel to gain focus
            panel.requestFocusInWindow(); // Request focus for key events
            //panel.player.fragmentNumber++;
            panel.firstContact = true;
            for(int i = 0;i<panel.guesses;i++)panel.dBConnection.markUNVisited(locations[i]);
            panel.map.tilesManager[1][panel.objectManager.fragments.fragments[currentFragment].mapX/panel.actualSize][panel.objectManager.fragments.fragments[currentFragment].mapY/panel.actualSize] = 1;
            panel.itemSetter.setFragment(currentFragment);
            panel.textFieldPanel.setVisible(false);
        }
//        else{
        panel.hasCountry = false;
        panel.hasCountry1 = false;
        panel.needsReload = true;
//        }

        panel.isReady = false;
        panel.headingSlider.setValue(0);
        System.out.println("CG: "+panel.correctGuesses+" G:"+panel.guesses);
//        if(panel.correctGuesses == 3){
//            panel.playsGeo = false;
//            panel.player.fragmentNumber++;
//        }
//        else if(panel.guesses == 5){
//            panel.playsGeo = false;
//            panel.moveCurrent = true;
//        }
    }
}
