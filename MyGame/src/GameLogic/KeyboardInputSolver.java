package GameLogic;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

//This is when I realised why interfaces were somewhat useful
public class KeyboardInputSolver implements KeyListener {
    /*From SO:
    keyPressed - when the key goes down
    keyReleased - when the key comes up
    keyTyped - when the Unicode character represented by this key is sent by the keyboard to system input. (?) :D
     */
    public boolean goUp, goDown, goLeft, goRight;
    //FOR UI
    public boolean Enter;
    public boolean PAUSED;
    public boolean RESET;
    //private Direction direction = Direction.STOP; // i wanted this to be used instead of the boolean values, but i let the idea go down the road

    @Override
    public void keyTyped(KeyEvent e) {//not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("Key pressed: " + e.getKeyChar());
        int code = e.getKeyCode();
        //System.out.println("Key Pressed: " + KeyEvent.getKeyText(code));
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            goUp = true;
//            System.out.println("UP");
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            goLeft = true;
            //System.out.println("LEFT");
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            goDown = true;
            //System.out.println("DOWN");
        }
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            goRight = true;
            //System.out.println("RIGHT");
        }
        if (code == KeyEvent.VK_ENTER) {
            Enter = true;
        }
        if (code == KeyEvent.VK_P) {
            PAUSED = !PAUSED;
        }
        if (code == KeyEvent.VK_R) {
            RESET = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            goUp = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            goLeft = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            goDown = false;
        }
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            goRight = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            Enter = false;
        }
        //direction = Direction.STOP;
    }

    public void setAllFalse() {
        //for when guessing
        goUp = false;
        goDown = false;
        goLeft = false;
        goRight = false;
        //FOR UI
        Enter = false;
        PAUSED = false;
    }

}

