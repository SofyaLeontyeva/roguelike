package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Objects;
import logic.GamePanel;
import logic.KeyHandler;

public class Player extends Entity {
    int hasKey = 0;
    KeyHandler keyHandler;
    private final int screenX;
    private final int screenY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        super(gamePanel);

        this.keyHandler = keyHandler;

        screenX = gamePanel.getScreenWidth() / 2 - gamePanel.getTileSize() / 2;
        screenY = gamePanel.getScreenHeight() / 2 - gamePanel.getTileSize() / 2;

        setSolidArea(new Rectangle(14, 18, 18, 18));
        setSolidAreaDefaultX(getSolidArea().x);
        setSolidAreaDefaultY(getSolidArea().y);
        setDefaultValue();
        getPlayerImage();
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setDefaultValue() {

        setWorldX(gamePanel.getTileSize() * 23);
        setWorldY(gamePanel.getTileSize() * 21);
        setSpeed(5);
        setDirectionHeight("down");
        setDirectionWidth("left");
    }

    public void getPlayerImage() {

        setBufferedImageContainer(setup("/player/up1"), 0);
        setBufferedImageContainer(setup("/player/down1"), 1);
        setBufferedImageContainer(setup("/player/left1"), 2);
        setBufferedImageContainer(setup("/player/right1"), 3);
        setBufferedImageContainer(setup("/player/up2"), 4);
        setBufferedImageContainer(setup("/player/down2"), 5);
        setBufferedImageContainer(setup("/player/left2"), 6);
        setBufferedImageContainer(setup("/player/right2"), 7);
    }

    public void update() {
        if (keyHandler.isUpPressed() || keyHandler.isDownPressed()
                || keyHandler.isLeftPressed() || keyHandler.isRightPressed()) {
            setDirectionWidth("neutral");
            setDirectionHeight("neutral");
            if (keyHandler.isUpPressed()) {
                setDirectionHeight("up");
            }
            if (keyHandler.isDownPressed()) {
                setDirectionHeight("down");
            }
            if (keyHandler.isLeftPressed()) {
                setDirectionWidth("left");
            }
            if (keyHandler.isRightPressed()) {
                setDirectionWidth("right");
            }

            setCollisionOn(false);
            gamePanel.getCollisionChecker().checkTile(this);

            int objIndex = gamePanel.getCollisionChecker().checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getNpc());
            interactNpc(npcIndex);

            if (!isCollisionOn()) {

                if (Objects.equals(getDirectionHeight(), "up")) {
                    setWorldY(getWorldY() - getSpeed());
                }
                if (Objects.equals(getDirectionHeight(), "down")) {
                    setWorldY(getWorldY() + getSpeed());
                }
                if (Objects.equals(getDirectionWidth(), "left")) {
                    setWorldX(getWorldX() - getSpeed());
                }
                if (Objects.equals(getDirectionWidth(), "right")) {
                    setWorldX(getWorldX() + getSpeed());
                }
            }

            setSpriteCounter(getSpriteCounter() + 1);
            if (getSpriteCounter() > 20) {
                if (getSpriteNum() == 1) {
                    setSpriteNum(2);
                } else if (getSpriteNum() == 2) {
                    setSpriteNum(1);
                }
                setSpriteCounter(0);
            }
        }
    }

    public void pickUpObject(int index) {
        String objectName = "";
        if (gamePanel.getObject()[index] != null) {
            objectName = gamePanel.getObject()[index].getName();
        }

        switch (objectName) {
            case "Key" -> {
                hasKey++;
                gamePanel.setObjectNull(index);
                gamePanel.getUserInterface().showMessage("You find a key!");
            }
            case "Door" -> {
                if (hasKey > 0) {
                    gamePanel.setObjectNull(index);
                    hasKey--;
                    gamePanel.getUserInterface().showMessage("You opened the door!");
                }
                gamePanel.getUserInterface().showMessage("You need a key!");
            }
            case "Chest" -> gamePanel.getUserInterface().setGameComplete();
            default -> {
            }
        }
    }

    public void interactNpc(int index) {
        if (index != 999) {
            if (gamePanel.getKeyHandler().isEnterPressed()) {
                gamePanel.setGameState(gamePanel.getDialogueState());
                gamePanel.getNpc()[index].speak();
            }
        }
        gamePanel.getKeyHandler().setEnterPressed(false);
    }

    public void draw(Graphics2D graphics2D) {

        BufferedImage image = null;

        switch (getDirectionHeight()) {
            case "up" -> {
                if (getSpriteNum() == 1) {
                    image = getBufferedImageContainer(0);
                }
                if (getSpriteNum() == 2) {
                    image = getBufferedImageContainer(4);
                }
            }
            case "down" -> {
                if (getSpriteNum() == 1) {
                    image = getBufferedImageContainer(1);
                }
                if (getSpriteNum() == 2) {
                    image = getBufferedImageContainer(5);
                }
            }
            default -> {
                if (Objects.equals(getDirectionWidth(), "neutral")) {
                    if (getSpriteNum() == 1) {
                        image = getBufferedImageContainer(1);
                    }
                    if (getSpriteNum() == 2) {
                        image = getBufferedImageContainer(5);
                    }
                }
            }
        }
        switch (getDirectionWidth()) {
            case "left" -> {
                if (getSpriteNum() == 1) {
                    image = getBufferedImageContainer(2);
                }
                if (getSpriteNum() == 2) {
                    image = getBufferedImageContainer(6);
                }
            }
            case "right" -> {
                if (getSpriteNum() == 1) {
                    image = getBufferedImageContainer(3);
                }
                if (getSpriteNum() == 2) {
                    image = getBufferedImageContainer(7);
                }
            }
            default -> {
                // Do nothing
            }
        }
        graphics2D.drawImage(image, screenX, screenY, null);
    }

    public int getHasKey() {
        return hasKey;
    }

}
