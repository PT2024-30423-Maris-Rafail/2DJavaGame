package Items;

import javax.imageio.ImageIO;
import java.util.Objects;

public class Compass extends Item {
    public Compass() {
        name = "Compass";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Objects/Compass.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
