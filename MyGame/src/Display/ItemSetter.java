package Display;

import ConnecToDB.ConnectR;
import Items.*;

import java.util.Random;


public class ItemSetter {
    Panel panel;
    ConnectR connection = new ConnectR();
    Random rand = new Random();
    public ItemSetter(Panel panel) {
        this.panel = panel;
    }
    public void setItem(){


        panel.objectManager.items[0] = new Compass();
        panel.objectManager.items[0].collision = connection.getCollisionForObjects(panel.objectManager.items[0].name);
        //System.out.println(panel.objectManager.items[0].collision);
        panel.objectManager.items[1] = new DarkCompass();
        panel.objectManager.items[1].collision = connection.getCollisionForObjects(panel.objectManager.items[1].name);
        //System.out.println(panel.objectManager.items[1].collision);
        panel.objectManager.items[2] = new Door();
        panel.objectManager.items[2].collision = connection.getCollisionForObjects(panel.objectManager.items[2].name);
        //System.out.println(panel.objectManager.items[2].collision);

        panel.objectManager.items[0].mapX=15*panel.actualSize;
        panel.objectManager.items[0].mapY=21*panel.actualSize;

        panel.objectManager.items[1].mapX=25*panel.actualSize;
        panel.objectManager.items[1].mapY=25*panel.actualSize;

        panel.objectManager.items[2].mapX=24*panel.actualSize;
        panel.objectManager.items[2].mapY=0;

        setFragments();

    }

    public void setFragment(int i){
        panel.objectManager.fragments.fragments[i]=new FragObj(i);
        panel.objectManager.fragments.fragments[i].collision=connection.getCollisionForObjects(panel.objectManager.fragments.fragments[0].name);
        int x,y;
        if(i==0){
            x=10;
            y=10;
        }
        else{
            do{
                x=rand.nextInt(50);
                y=rand.nextInt(50);

            }while(panel.map.tilesManager[1][x][y]!=1);
        }

        System.out.println("For "+i+" x:"+x+" y:"+y);
        panel.objectManager.fragments.fragments[i].mapX=x*panel.actualSize;
        panel.objectManager.fragments.fragments[i].mapY=y*panel.actualSize;
        panel.map.tilesManager[1][x][y]=13;
    }
    public void setFragments(){
        for(int i = 0;i<4;i++){
            setFragment(i);
        }
        
    }
}
