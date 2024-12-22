package Items;

import javax.imageio.ImageIO;

public class DarkCompass extends  Item{
    public DarkCompass(){
        name = "Compass";
        try{
            image  = ImageIO.read(getClass().getResourceAsStream("/Objects/CompassD.png"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
