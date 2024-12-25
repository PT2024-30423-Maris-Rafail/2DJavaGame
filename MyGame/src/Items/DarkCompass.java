package Items;

import javax.imageio.ImageIO;
import java.util.Objects;

public class DarkCompass extends Item {
    public DarkCompass() {
        name = "Compass";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Objects/CompassD.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
