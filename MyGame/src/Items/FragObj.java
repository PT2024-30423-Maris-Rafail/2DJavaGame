package Items;

import javax.imageio.ImageIO;
import java.util.Objects;

public class FragObj extends Item {

    public FragObj(int part) {
        name = "Doc";
        String filePath;
        filePath = "/Objects/map" + (part + 1) + ".png";

        try {

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(filePath)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
