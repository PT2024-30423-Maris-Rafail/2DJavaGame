package Items;

import javax.imageio.ImageIO;

public class Compass extends  Item{
    public Compass(){
        name = "Compass";
        try{
            image  = ImageIO.read(getClass().getResourceAsStream("/Objects/Compass.png"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
