package object;

import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import logic.GamePanel;

public class ObjChest extends SuperObject {

    private GamePanel gamePanel;

    public ObjChest(GamePanel gamePanel) {

        setName("Chest");
        try {
            setImage(getScaleTool().scaleImage(
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest.png"))),
                    gamePanel.getTileSize(),
                    gamePanel.getTileSize()
            ));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
