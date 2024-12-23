package Display;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

//Was always thinking that implements seemed really useless, since, if we know what to implement
//why would we use it... but here's the answer: WE MIGHT NOT KNOW WHAT TO IMPLEMENT
public class KeyboardInputSolver implements KeyListener {
    /*From SO:
    keyPressed - when the key goes down
    keyReleased - when the key comes up
    keyTyped - when the Unicode character represented by this key is sent by the keyboard to system input.
     */
    public boolean goUp, goDown, goLeft, goRight;
    //FOR UI
    public boolean Enter;//-new tip
    public boolean PAUSED;
    private Direction directionVertical = Direction.STOP;
    private Direction directionHorizontal = Direction.STOP;
    private Direction direction = Direction.STOP;
    public Direction getDirectionVertical() {
        return directionVertical;
    }
    public Direction getDirectionHorizontal() {
        return directionHorizontal;
    }
    public Direction getDirection() {
        return direction;
    }
    @Override
    public void keyTyped(KeyEvent e) {//not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("Key pressed: " + e.getKeyChar());
        int code = e.getKeyCode();
        //System.out.println("Key Pressed: " + KeyEvent.getKeyText(code));
        if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            goUp = true;
//            System.out.println("UP");
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            goLeft = true;
            //System.out.println("LEFT");
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            goDown = true;
            //System.out.println("DOWN");
        }
        if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            goRight = true;
            //System.out.println("RIGHT");
        }
        if(code == KeyEvent.VK_ENTER) {
            Enter = true;
        }
        if(code == KeyEvent.VK_P ) {
            PAUSED = !PAUSED;
        }
        switch (code) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> direction = Direction.UP;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> direction = Direction.DOWN;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> direction = Direction.LEFT;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> direction = Direction.RIGHT;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            goUp = false;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            goLeft = false;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            goDown = false;
        }
        if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            goRight = false;
        }
        if(code == KeyEvent.VK_ENTER) {
            Enter = false;
        }

        //System.out.println("Key Released: " + KeyEvent.getKeyText(code));
//        switch (code) {
//            case KeyEvent.VK_W, KeyEvent.VK_UP, KeyEvent.VK_S, KeyEvent.VK_DOWN -> directionVertical = Direction.STOP;
//
//            case KeyEvent.VK_A, KeyEvent.VK_LEFT, KeyEvent.VK_D, KeyEvent.VK_RIGHT -> directionHorizontal = Direction.STOP;
//        }
        direction = Direction.STOP;
    }
    public void setAllFalse(){
         goUp = false;
         goDown =false;
                 goLeft= false;
                 goRight = false;
        //FOR UI
        Enter = false;
        PAUSED = false;
    }

}

