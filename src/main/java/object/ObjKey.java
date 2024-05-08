package object;

import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import logic.GamePanel;

public class ObjKey extends SuperObject {

    GamePanel gamePanel;

    public ObjKey(GamePanel gamePanel) {

        setName("Key");
        try {
            setImage(getScaleTool().scaleImage(
                    ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png"))),
                    gamePanel.getTileSize(),
                    gamePanel.getTileSize()
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
