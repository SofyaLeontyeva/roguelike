package logic;

import entity.Npc;
import object.ObjChest;
import object.ObjDoor;
import object.ObjKey;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        gamePanel.setObject(0, new ObjKey(gamePanel),
                23 * gamePanel.getTileSize(), 7 * gamePanel.getTileSize());
        gamePanel.setObject(1, new ObjKey(gamePanel),
                23 * gamePanel.getTileSize(), 40 * gamePanel.getTileSize());
        gamePanel.setObject(2, new ObjKey(gamePanel),
                37 * gamePanel.getTileSize(), 7 * gamePanel.getTileSize());
        gamePanel.setObject(3, new ObjDoor(gamePanel),
                10 * gamePanel.getTileSize(), 11 * gamePanel.getTileSize());
        gamePanel.setObject(4, new ObjDoor(gamePanel),
                8 * gamePanel.getTileSize(), 28 * gamePanel.getTileSize());
        gamePanel.setObject(5, new ObjDoor(gamePanel),
                12 * gamePanel.getTileSize(), 22 * gamePanel.getTileSize());
        gamePanel.setObject(6, new ObjChest(gamePanel),
                10 * gamePanel.getTileSize(), 7 * gamePanel.getTileSize());
    }

    public void setNpc() {

        gamePanel.setNpc(new Npc(gamePanel), 0);
        gamePanel.getNpc()[0].setWorldX(gamePanel.getTileSize() * 21);
        gamePanel.getNpc()[0].setWorldY(gamePanel.getTileSize() * 21);

        gamePanel.setNpc(new Npc(gamePanel), 1);
        gamePanel.getNpc()[1].setWorldX(gamePanel.getTileSize() * 25);
        gamePanel.getNpc()[1].setWorldY(gamePanel.getTileSize() * 25);
    }
}
