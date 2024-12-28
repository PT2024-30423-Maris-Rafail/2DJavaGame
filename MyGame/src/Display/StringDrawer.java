package Display;

import Components.GamePanel;
import GameState.TimeScore;

import java.awt.*;

public class StringDrawer {
    private static void drawString(Graphics2D g2d, Font font, String string, int screen_width, int screen_height) {
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics(font);//I am always in awe of what methods are available to us... praise the devs
        int welcomeWidth = fm.stringWidth(string);
        int welcomeX = (screen_width - welcomeWidth) / 2; // Calculate X-coordinate for centering
        g2d.drawString(string, welcomeX, screen_height);
    }

    public static void paintEndGameInfo(Graphics2D g2d, GamePanel panel) {
        int SCREEN_WIDTH = GamePanel.SCREEN_WIDTH;
        //UserInterf UI = panel.UI;
        TimeScore bestTimeScore = panel.bestTimeScore;
        TimeScore currentScore = panel.currentScore;
        CustomFonts customFonts = panel.customFonts;
        g2d.setColor(Color.WHITE);
        String welcomeText1 = "Your score:";
        StringDrawer.drawString(g2d, customFonts.fontInstr, welcomeText1, SCREEN_WIDTH, 300);

        g2d.setColor(new Color(64, 191, 34));
        String score1 = currentScore.hour + "h " + currentScore.minutes + "m " + currentScore.seconds + "s " + currentScore.milliseconds + "ms";
        StringDrawer.drawString(g2d, customFonts.fontInstr, score1, SCREEN_WIDTH, 350);


        if (!panel.isFirstRound && bestTimeScore != null) {
            g2d.setColor(Color.WHITE);
            String welcomeText2 = "Your PB:";
            StringDrawer.drawString(g2d, customFonts.fontInstr, welcomeText2, SCREEN_WIDTH, 400);

            g2d.setColor(new Color(197, 44, 17));
            String score2 = bestTimeScore.hour + "h " + bestTimeScore.minutes + "m " + bestTimeScore.seconds + "s " + bestTimeScore.milliseconds + "ms";
            StringDrawer.drawString(g2d, customFonts.fontInstr, score2, SCREEN_WIDTH, 450);
        }

        g2d.setColor(Color.WHITE);
        String instr = "To continue, press Enter";
        StringDrawer.drawString(g2d, customFonts.fontInstr, instr, SCREEN_WIDTH, 500);

    }

    public static void paintTitleScreen(Graphics2D g2d, GamePanel panel) {

        String welcomeText = "Welcome,";
        StringDrawer.drawString(g2d, panel.customFonts.fontPause, welcomeText, GamePanel.SCREEN_WIDTH, 300);

        String usernameText = panel.username + "!";
        StringDrawer.drawString(g2d, panel.customFonts.fontPause, usernameText, GamePanel.SCREEN_WIDTH, 400);

        //g2d.setFont(UI.fontInstr);
        String instr = "To continue, press Enter";
        StringDrawer.drawString(g2d, panel.customFonts.fontInstr, instr, GamePanel.SCREEN_WIDTH, 500);
    }
}
