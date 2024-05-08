package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import logic.GamePanel;
import util.RenderScaleTool;

public class Entity {

    String[] dialogues = new String[20];
    int dialogueIndex = 0;
    GamePanel gamePanel;
    private int actionLockCounter = 0;
    private int worldX;
    private int worldY;
    private int speed;

    private BufferedImage[] bufferedImageContainer = new BufferedImage[8];
    private String directionHeight;
    private String directionWidth;
    private int spriteCounter = 0;
    private int spriteNum = 1;
    private Rectangle solidArea = new Rectangle(0, 0, 48, 44);
    private int solidAreaDefaultX;
    private int solidAreaDefaultY;
    private boolean collisionOn = false;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setAction() { }

    public void update() {
        setAction();

        setCollisionOn(false);
        gamePanel.getCollisionChecker().checkTile(this);
        gamePanel.getCollisionChecker().checkObject(this, false);
        gamePanel.getCollisionChecker().checkPlayer(this);

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

    public BufferedImage setup(String imagePath) {

        RenderScaleTool scaleTool = new RenderScaleTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = scaleTool.scaleImage(image, gamePanel.getTileSize(), gamePanel.getTileSize());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gamePanel.getUserInterface().setCurrentDialogue(dialogues[dialogueIndex]);
        dialogueIndex++;
    }

    public void draw(Graphics2D graphics2D) {

        BufferedImage image = null;
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();

        if (worldX + gamePanel.getTileSize()
                > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX()
                && worldX - gamePanel.getTileSize()
                < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX()
                && worldY + gamePanel.getTileSize()
                > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getScreenY()
                && worldY - gamePanel.getTileSize()
                < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY()) {

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

            graphics2D.drawImage(image, screenX, screenY,
                    gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        }
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public void setSolidAreaDefaultX(int solidAreaDefaultX) {
        this.solidAreaDefaultX = solidAreaDefaultX;
    }

    public void setSolidAreaDefaultY(int solidAreaDefaultY) {
        this.solidAreaDefaultY = solidAreaDefaultY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public BufferedImage getBufferedImageContainer(int i) {
        return bufferedImageContainer[i];
    }

    public void setBufferedImageContainer(BufferedImage bufferedImage, int i) {
        bufferedImageContainer[i] = bufferedImage;
    }

    public String getDirectionHeight() {
        return directionHeight;
    }

    public void setDirectionHeight(String direction) {
        this.directionHeight = direction;
    }

    public String getDirectionWidth() {
        return directionWidth;
    }

    public void setDirectionWidth(String direction) {
        this.directionWidth = direction;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }

    public int getSpriteNum() {
        return spriteNum;
    }

    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public void setSolidAreaX(int x) {
        this.solidArea.x = x;
    }

    public void setSolidAreaY(int y) {
        this.solidArea.y = y;
    }

    public void setSolidAreaWidth(int width) {
        this.solidArea.width = width;
    }

    public void setSolidAreaHeight(int height) {
        this.solidArea.height = height;
    }

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public void setSolidArea(Rectangle solidArea) {
        this.solidArea = solidArea;
    }

    public int getActionLockCounter() {
        return actionLockCounter;
    }

    public void setActionLockCounter(int actionLockCounter) {
        this.actionLockCounter = actionLockCounter;
    }
}
