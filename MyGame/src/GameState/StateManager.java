package GameState;

import Components.GameWindow;
import Components.GamePanel;

import java.awt.*;

public class StateManager {
    public static void finalStatePrepare(GameWindow window, GamePanel panel) {
        panel.state = State.GAME_END2;
        window.requestFocusInWindow();
        window.setPreferredSize(new Dimension(400, 390));
        window.pack();
        window.setLocationRelativeTo(null);
        window.cardLayout.show(panel.mainPanel, "Score");
    }

    public static void runManager(GamePanel panel) {
        if (panel.beforePause != State.CHILLING) {
            panel.musicPlayer.stopSound(MusicName.Geom_Dash);
            if (panel.state == State.MAP1 || panel.state == State.MAP1_PORTAL_OPENED)
                panel.musicPlayer.playSound(MusicName.Empty);
            else panel.musicPlayer.playSound(MusicName.Aria_Math);
            panel.currentState = State.PAUSE;
            panel.state = panel.beforePause;
            panel.beforePause = State.CHILLING;
        }
    }
}
