package object;

import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import logic.GamePanel;

public class ObjDoor extends SuperObject {

    private GamePanel gamePanel;

    public ObjDoor(GamePanel gamePanel) {
        setName("Door");
        try {
            setImage(getScaleTool().scaleImage(
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door.png"))),
                    gamePanel.getTileSize(),
                    gamePanel.getTileSize()
            ));

        } catch (IOException e) {
            e.printStackTrace();
        }
        setCollision(true);
    }

}
