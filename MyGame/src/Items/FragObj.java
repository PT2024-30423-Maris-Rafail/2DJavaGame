package Items;

import javax.imageio.ImageIO;
import java.util.Objects;

public class FragObj extends Item{

    public FragObj(int part){
        name = "Doc";
        String filePath;
          filePath = "/Objects/map" + (part+1) + ".png";

        //System.out.println(filePath);
        try{
            //System.out.println("here");
            //image = ImageIO.read((getClass().getResourceAsStream(filePath)));
            image = ImageIO.read(getClass().getResourceAsStream(filePath));

        }
        catch(Exception e){
            //System.out.println("Confuse");
            e.printStackTrace();
        }
    }
}
