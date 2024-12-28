package Components;

import java.awt.*;

public class MainPanel extends javax.swing.JPanel {
    private final GamePanel gamePanel;
    private final EndingPanel endingPanel;

    public MainPanel(GameWindow window) {

        this.setLayout(new CardLayout());
        window.cardLayout = (CardLayout) this.getLayout();
        // Login Panel
        this.add(window.getLogin().LoginPanel, "Login");
        // Reg Panel
        this.add(window.getRegister().RegisterPanel, "Register");
        gamePanel = new GamePanel(this, window);
        this.add(gamePanel, "Game");

        endingPanel = new EndingPanel(gamePanel);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public EndingPanel getEndingPanel() {
        return endingPanel;
    }


}
