package Items;

import javax.imageio.ImageIO;
import java.io.File;

public class Door extends Item{
    public Door(){

            name="DoorN.png";

        try{

            image  = ImageIO.read(getClass().getResourceAsStream("/Objects/DoorN.png"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
