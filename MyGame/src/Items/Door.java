package Items;

import javax.imageio.ImageIO;
import java.util.Objects;

public class Door extends Item {
    public Door() {

        name = "DoorN.png";

        try {

            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Objects/DoorN.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
