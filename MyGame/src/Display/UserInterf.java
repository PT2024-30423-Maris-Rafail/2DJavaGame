package Display;

import ConnecToDB.ConnectR;
import Entity.Player;
import GameState.State;
import geoGame.ScoreBoard;

import java.awt.*;
import java.sql.ResultSet;

public class UserInterf {
    public Panel panel;
    Font fontPause;
    Font font2;
    Font font3;
    Font fontGreet;
    Font fontInstr;
    public int timeM = 0;
    public int timeT = 0;
    String msg = null;
    String Tip = null;
    //ConnectR panel.dBConnection = new ConnectR();
    ResultSet rs;
    int active_seconds=0;
    int advance = 0;
    String f;
    public double timeInSec = 0;
    int placement = 30;
    //TimerIncrementation timer;
    public boolean correctGuess;
    public double time;
    public ScoreBoard scoreBoard;
    public UserInterf(Panel panel) {
        this.panel = panel;
        fontPause = new Font("Arial", Font.BOLD, 100);
        font2 = new Font("Arial",Font.PLAIN,30);
        font3 = new Font("Arial",Font.PLAIN,20);
        fontGreet = new Font("Arial", Font.BOLD, 70);
        fontInstr = new Font("Arial", Font.BOLD, 40);
        correctGuess = false;
        //timer = new TimerIncrementation(this);

    }

    public void setUI(Graphics2D g2D, Player player) {
        //System.out.println("setez UI");
        placement = 30;
        //g2D.setFont(new Font("Mantinia", Font.PLAIN, 40));
        g2D.setFont(font2);
        g2D.setColor(Color.white);
        switch(panel.state){
            case TITLE_SCREEN -> {
                drawTT(g2D);
                break;
            }
            case PAUSE -> {
                drawPause(g2D);
                break;
            }

        }

        if(timeT >active_seconds ){
            if(active_seconds<0){
                switch (active_seconds){
                    case -60:
                        if(player.keyHandler.Enter){
                            System.out.println("nig");
                            advance=1;
                        }
                        else advance=0;
                        break;
                    case -120:
                        if(player.hasCompass || player.hasDarkCompass){
                            System.out.println("nig");
                            advance=1;
                        }
                        else advance=0;
                        break;
                }

            }
            else advance=1;
            if(advance==1 && panel.state != State.PAUSE){
                        active_seconds = panel.dBConnection.getTipTime()*60;
                //System.out.println(active_seconds);
                        Tip = panel.dBConnection.getTip();
                        panel.dBConnection.currentTip++;
            }

            timeT = 0;

        }
        else advance=0;

        if(timeM >300 && panel.player.keyHandler.Enter && panel.state!=State.PAUSE){
            timeM = 0;
            msg = panel.dBConnection.getMessage();
            System.out.println("did");
        }
        placement=40;
        //timer.start();

        if(Tip!=null ) {
            g2D.setFont(font3);
            g2D.drawString(Tip,0,placement);
            placement+=40;
        }

        if(msg!=null ) {
            g2D.setFont(font3);
            g2D.drawString(msg,0,placement);
            placement+=40;
        }

        if(panel.state == State.MAP2){
            drawMap2(g2D,placement);
        }

        g2D.setFont(font2);
        if (timeInSec < 60) {
            //System.out.println(timeInSec);
            g2D.drawString(String.format("Time: %.2f", timeInSec), 10 * panel.actualSize, 40);
        } else {
            double minutes = Math.floor(timeInSec / 60);
            double seconds = Math.floor(timeInSec % 60); // Explicitly floor the seconds

            g2D.drawString(String.format("Time: %.0f(m) %.0f", minutes, seconds), 10 * panel.actualSize-(int)(Math.log10( minutes )+1)*30, 40);
            //System.out.println(minutes+" "+seconds);
            //System.out.println(10 * panel.actualSize-(int)(Math.log10( minutes )+1)*30);
        }
        if(correctGuess && (panel.player.fragmentNumber<4 && !panel.player.hasDarkCompass) ){
            if(System.currentTimeMillis()-time >5000){
                correctGuess = false;
            }
            g2D.setFont(fontPause);
            g2D.drawString(String.format("CORRECT"),panel.screenWidth/2-230,panel.screenHeight/3);
        }
        if(panel.state == State.GAME_END2)drawScoreBoard(g2D);
    }

    public void drawPause(Graphics2D g2D){
        g2D.setFont(fontPause);
            g2D.drawString(String.format("PAUSED"),panel.screenWidth/2-200,panel.screenHeight/3);
    }
    public void drawTT(Graphics2D g2D){

    }
    public void drawMap2(Graphics2D g2D,int placement){

            f="Number of fragments: "+(panel.player.fragmentNumber);
            g2D.setFont(font3);
            g2D.drawString(f,0,placement);
        
    }
    public void drawScoreBoard(Graphics2D g2D){
        int player_id;
        if(panel.isEmail){

        }
        for(int i = 0;i<10;i++){
            System.out.println(scoreBoard.scoreBoard[i].getUsername()+" "+scoreBoard.scoreBoard[i].getTime());
        }
    }
    //TODO MOVE INTO PANEL + UPDATE CONDITION FOR GAME END
    public void updateTimer(){
        if (panel.state != State.PAUSE) {
            //System.out.println(timeInSec);
            timeInSec += 1.0 / 60.0; // Increment time by 1/60 seconds
            timeM++;
            timeT++;
        }
    }
}
