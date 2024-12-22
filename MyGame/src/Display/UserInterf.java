package Display;

import ConnecToDB.ConnectR;
import Entity.Player;
import GameState.State;

import java.awt.*;
import java.sql.ResultSet;

public class UserInterf {
    public Panel panel;
    Font fontPause;
    Font font2;
    Font font3;
    public int timeM = 0;
    public int timeT = 0;
    String msg = null;
    String Tip = null;
    ConnectR connection = new ConnectR();
    ResultSet rs;
    int active_seconds=0;
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
        font2 = new Font("Arial",Font.PLAIN,30);
        font3 = new Font("Arial",Font.PLAIN,20);
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
                drawTT();
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
                        active_seconds = connection.getTipTime()*60;
                //System.out.println(active_seconds);
                        Tip = connection.getTip();
                        connection.currentTip++;
            }

            timeT = 0;

        }
        else advance=0;

        if(timeM >300 && panel.player.keyHandler.Enter && panel.state!=State.PAUSE){
            timeM = 0;
            msg = connection.getMessage();
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
            drawMap2(g2D,player,placement);
        }

        g2D.setFont(font2);
        if (timeInSec < 60) {
            //System.out.println(timeInSec);
            g2D.drawString(String.format("Time: %.2f", timeInSec), 10 * panel.actualSize, 40);
        } else {
            double minutes = Math.floor(timeInSec / 60);
            double seconds = Math.floor(timeInSec % 60); // Explicitly floor the seconds

            g2D.drawString(String.format("Time: %.0f(m) %.0f", minutes, seconds), 10 * panel.actualSize-(int)(Math.log10( minutes ))*15, 40);
            //System.out.println(minutes+" "+seconds);
        }
        if(correctGuess){
            if(System.currentTimeMillis()-time >5000){
                correctGuess = false;
            }
            g2D.setFont(fontPause);
            g2D.drawString(String.format("CORRECT"),panel.screenWidth/2-230,panel.screenHeight/3);
        }
    }

    public void drawPause(Graphics2D g2D){
        g2D.setFont(fontPause);
            g2D.drawString(String.format("PAUSED"),panel.screenWidth/2-200,panel.screenHeight/3);
    }
    public void drawTT(){

    }
    public void drawMap2(Graphics2D g2D,Player player,int placement){

            f="Number of fragments: "+(player.fragmentNumber);
            g2D.setFont(font3);
            g2D.drawString(f,0,placement);
        
    }
    public void updateTimer(){
        if (panel.state != State.PAUSE) {
            timeInSec += 1.0 / 60.0; // Increment time by 1/60 seconds
            timeM++;
            timeT++;
        }
    }
}
