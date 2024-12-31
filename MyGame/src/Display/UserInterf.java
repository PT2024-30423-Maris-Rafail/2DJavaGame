package Display;

import Components.GamePanel;
import GameState.State;

import java.awt.*;

public class UserInterf {
    public GamePanel panel;
    public int timeM = 0;//time for messages
    public int timeT = 0;//time for tips
    String msg = "I heard pressing Enter is cool!";
    String Tip = null;
    int active_seconds = 0;
    int advance = 0;
    String f;
    //    public double timeInSec = 0;
    int placement = 30;
    public boolean correctGuess;
    public double time;
    CustomFonts customFonts;
    private boolean enterPressed = false;

    public UserInterf(GamePanel panel) {
        this.panel = panel;
        customFonts = panel.customFonts;
        correctGuess = false;
    }

    private void infiniteMessage() {
        if (active_seconds < 0) {
            switch (active_seconds) {
                case -60:
                    if (panel.player.keyHandler.Enter) {
                        //System.out.println("nig");
                        advance = 1;
                    } else advance = 0;
                    break;
                case -120://this is for when we print ,,I wonder where the key is..." since it must stay on until we pick up a compass
                    if (panel.player.hasCompass || panel.player.hasDarkCompass) {
                        //System.out.println("nig");
                        advance = 1;
                    } else advance = 0;
                    break;
            }
        } else advance = 1;
    }

    private void nextMessage() {
        if (advance == 1 && panel.state != State.PAUSE) {
            active_seconds = panel.dbConnection.uiConnect.getTipTime() * 60;
            Tip = panel.dbConnection.uiConnect.getTip();//randomly selected
            panel.dbConnection.uiConnect.currentTip++;
        }
    }

    private void solvesTips() {
        //if the active_seconds is negative, we must print something until receiving user input, else, we print for 5 seconds
        if (panel.keyHandler.Enter) enterPressed = true;
        if (timeT > active_seconds) {
            infiniteMessage();
            nextMessage();
            //else Tip = "I heard pressing Enter is cool!";

            timeT = 0;
        } else advance = 0;
    }

    private void solvesMessage() {
        if (timeM > 120 && panel.player.keyHandler.Enter && panel.state != State.PAUSE) {
            timeM = 0;
            if (enterPressed) {
                msg = panel.dbConnection.uiConnect.getMessage();
            } else msg = "I heard pressing Enter is cool!";
        }
    }

    private void setUp(Graphics2D g2D) {
        placement = 40;
        g2D.setFont(customFonts.font2);
        g2D.setColor(Color.white);
        if (panel.state == State.PAUSE) drawPause(g2D);
    }

    private void stringDisplay(String string, Graphics2D g2D) {
        if (string != null) {
            g2D.setFont(customFonts.font3);
            g2D.drawString(string, 0, placement);
            placement += 40;
        }
    }

    private void displayTime(Graphics2D g2D) {
        if (panel.timeInSec < 60) {
            //System.out.println(timeInSec);
            g2D.drawString(String.format("Time: %.2f", panel.timeInSec), 10 * GamePanel.ACTUAL_SIZE, 40);
        } else {
            double minutes = Math.floor(panel.timeInSec / 60);
            double seconds = Math.floor(panel.timeInSec % 60); // Explicitly floor the seconds

            g2D.drawString(String.format("Time: %.0f(m) %.0f", minutes, seconds), 10 * GamePanel.ACTUAL_SIZE - (int) (Math.log10(minutes) + 1) * 30, 40);
            //System.out.println(minutes+" "+seconds);
        }
    }

    private void displayCorrectMessage(Graphics2D g2D) {
        if (correctGuess && (panel.player.fragmentNumber < 4 && !panel.player.hasDarkCompass)) {
            if (System.currentTimeMillis() - time > 5000) {
                correctGuess = false;
            }
            //g2D.setFont(customFonts.fontPause);
            StringDrawer.drawString(g2D,customFonts.fontPause,"Fragment obtained",GamePanel.SCREEN_WIDTH, 200);
            //g2D.drawString("Fragment obtained", GamePanel.SCREEN_WIDTH / 2 - 230, GamePanel.SCREEN_HEIGHT / 3);
        }
    }

    private void display(Graphics2D g2D) {
        stringDisplay(Tip, g2D);
        stringDisplay(msg, g2D);
        if (panel.state == State.MAP2 || panel.beforePause == State.MAP2) {
            drawMap2(g2D, placement);
        }
        g2D.setFont(customFonts.font2);
        displayTime(g2D);
        displayCorrectMessage(g2D);
    }

    public void setUI(Graphics2D g2D) {
        setUp(g2D);
        solvesTips();
        solvesMessage();
        display(g2D);
    }

    public void drawPause(Graphics2D g2D) {
        g2D.setFont(customFonts.fontPause);
        g2D.drawString("PAUSED", GamePanel.SCREEN_WIDTH / 2 - 200, GamePanel.SCREEN_HEIGHT / 2 - 50);
    }

    public void drawMap2(Graphics2D g2D, int placement) {
        f = "Number of fragments: " + (panel.player.fragmentNumber);
        g2D.setFont(customFonts.font3);
        g2D.drawString(f, 0, placement);
    }

}
