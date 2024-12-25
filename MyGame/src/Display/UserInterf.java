package Display;

import Entity.Player;
import GameState.State;


import java.awt.*;

public class UserInterf {
    public Panel panel;
    Font fontPause;
    Font font2;
    Font font3;
    Font fontGreet;
    Font fontInstr;
    Font fontScore;
    public int timeM = 0;
    public int timeT = 0;
    String msg = null;
    String Tip = null;
    //ConnectR panel.dBConnection = new ConnectR();
    int active_seconds = 0;
    int advance = 0;
    String f;
    public double timeInSec = 0;
    int placement = 30;
    //TimerIncrementation timer;
    public boolean correctGuess;
    public double time;

    public UserInterf(Panel panel) {
        this.panel = panel;
        fontPause = new Font("Arial", Font.BOLD, 100);
        font2 = new Font("Arial", Font.PLAIN, 30);
        font3 = new Font("Arial", Font.PLAIN, 20);
        fontGreet = new Font("Arial", Font.BOLD, 70);
        fontInstr = new Font("Arial", Font.BOLD, 40);
        fontScore = new Font("Arial", Font.PLAIN, 40);

        correctGuess = false;
        //timer = new TimerIncrementation(this);

    }

    public void setUI(Graphics2D g2D, Player player) {
        //System.out.println("setez UI");
        placement = 30;
        //g2D.setFont(new Font("Mantinia", Font.PLAIN, 40));
        g2D.setFont(font2);
        g2D.setColor(Color.white);
        if(panel.state == State.PAUSE)drawPause(g2D);

        if (timeT > active_seconds) {
            if (active_seconds < 0) {
                switch (active_seconds) {
                    case -60:
                        if (player.keyHandler.Enter) {
                            System.out.println("nig");
                            advance = 1;
                        } else advance = 0;
                        break;
                    case -120:
                        if (player.hasCompass || player.hasDarkCompass) {
                            System.out.println("nig");
                            advance = 1;
                        } else advance = 0;
                        break;
                }

            } else advance = 1;
            if (advance == 1 && panel.state != State.PAUSE) {
                active_seconds = panel.dBConnection.getTipTime() * 60;
                //System.out.println(active_seconds);
                Tip = panel.dBConnection.getTip();
                panel.dBConnection.currentTip++;
            }

            timeT = 0;

        } else advance = 0;

        if (timeM > 300 && panel.player.keyHandler.Enter && panel.state != State.PAUSE) {
            timeM = 0;
            msg = panel.dBConnection.getMessage();
            System.out.println("did");
        }
        placement = 40;
        //timer.start();

        if (Tip != null) {
            g2D.setFont(font3);
            g2D.drawString(Tip, 0, placement);
            placement += 40;
        }

        if (msg != null) {
            g2D.setFont(font3);
            g2D.drawString(msg, 0, placement);
            placement += 40;
        }

        if (panel.state == State.MAP2) {
            drawMap2(g2D, placement);
        }

        g2D.setFont(font2);
        if (timeInSec < 60) {
            //System.out.println(timeInSec);
            g2D.drawString(String.format("Time: %.2f", timeInSec), 10 * panel.actualSize, 40);
        } else {
            double minutes = Math.floor(timeInSec / 60);
            double seconds = Math.floor(timeInSec % 60); // Explicitly floor the seconds

            g2D.drawString(String.format("Time: %.0f(m) %.0f", minutes, seconds), 10 * panel.actualSize - (int) (Math.log10(minutes) + 1) * 30, 40);
            //System.out.println(minutes+" "+seconds);
            //System.out.println(10 * panel.actualSize-(int)(Math.log10( minutes )+1)*30);
        }
        if (correctGuess && (panel.player.fragmentNumber < 4 && !panel.player.hasDarkCompass)) {
            if (System.currentTimeMillis() - time > 5000) {
                correctGuess = false;
            }
            g2D.setFont(fontPause);
            g2D.drawString("CORRECT", panel.screenWidth / 2 - 230, panel.screenHeight / 3);
        }
        //if (panel.state == State.GAME_END2) drawScoreBoard();
    }

    public void drawPause(Graphics2D g2D) {
        g2D.setFont(fontPause);
        g2D.drawString("PAUSED", panel.screenWidth / 2 - 200, panel.screenHeight / 3);
    }

    public void drawMap2(Graphics2D g2D, int placement) {

        f = "Number of fragments: " + (panel.player.fragmentNumber);
        g2D.setFont(font3);
        g2D.drawString(f, 0, placement);

    }



    //TODO MOVE INTO PANEL + UPDATE CONDITION FOR GAME END
    public void updateTimer() {
        if (panel.state != State.PAUSE) {
            //System.out.println(timeInSec);
            timeInSec += 1.0 / 60.0; // Increment time by 1/60 seconds
            timeM++;
            timeT++;
        }
    }
}
