package logic;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import object.ObjKey;

public class UserInterface {

    GamePanel gamePanel;

    private final Font textInGame;
    private final Font titleInGame;
    private BufferedImage keyImage;

    private boolean messageOn = false;
    private String message = "";
    private int messageCounter = 0;
    private double timer;
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    private boolean isGameComplete = false;
    private Graphics2D graphics2D;
    private String currentDialogue = "";

    public UserInterface(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        textInGame = new Font("Arial", Font.PLAIN, 40);
        titleInGame = new Font("Arial", Font.BOLD, 80);
        ObjKey key = new ObjKey(gamePanel);
        keyImage = key.getImage();
    }

    public void setGameComplete() {
        isGameComplete = true;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        graphics2D.setColor(c);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255, 255, 255, 255);
        graphics2D.setColor(c);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void drawDialogueScreen() {
        int x = gamePanel.getTileSize() * 2;
        int y = gamePanel.getTileSize() * 2;
        int width = gamePanel.getScreenWidth() - (gamePanel.getTileSize() * 6);
        int height = gamePanel.getTileSize() * 4;

        drawSubWindow(x, y, width, height);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 28F));
        x += gamePanel.getTileSize();
        y += gamePanel.getTileSize();

        for (String line : currentDialogue.split("\n")) {
            graphics2D.drawString(line, x, y);
            y += 40;
        }
    }

    public void draw(Graphics2D innnerGraphics2D) {

        this.graphics2D = innnerGraphics2D;
        this.graphics2D.setFont(new Font("Arial", Font.PLAIN, 40));
        this.graphics2D.setColor(Color.white);

        if (gamePanel.getGameState() == gamePanel.getPauseState()) {
            drawPauseScreen();
        }

        if (gamePanel.getGameState() == gamePanel.getDialogueState()) {
            drawDialogueScreen();
        }

        if (isGameComplete) {
            String text;
            int textLength;
            int x;
            int y;

            graphics2D.setFont(textInGame);
            graphics2D.setColor(Color.white);
            text = "You have found the treasure!";
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gamePanel.getScreenWidth() / 2 - textLength / 2;
            y = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() * 3);
            graphics2D.drawString(text, x, y);

            text = "Your time is:" + decimalFormat.format(timer) + "!";
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gamePanel.getScreenWidth() / 2 - textLength / 2;
            y = gamePanel.getScreenHeight() / 2 + (gamePanel.getTileSize() * 4);
            graphics2D.drawString(text, x, y);

            graphics2D.setFont(titleInGame);
            graphics2D.setColor(Color.yellow);
            text = "Congratulations!";
            textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
            x = gamePanel.getScreenWidth() / 2 - textLength / 2;
            y = gamePanel.getScreenHeight() / 2 + (gamePanel.getTileSize() * 2);

            gamePanel.stopGameThread();

        } else {
            graphics2D.setFont(textInGame);
            graphics2D.setColor(Color.white);
            graphics2D.drawImage(keyImage, gamePanel.getTileSize() / 2, gamePanel.getTileSize() / 2,
                    gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            graphics2D.drawString("x = " + gamePanel.getPlayer().getHasKey(), 72, 65);

            // TIMER
            if (gamePanel.getGameState() == gamePanel.getPlayState()) {
                timer += (double) 1 / 60;
            }
            graphics2D.drawString("Time: " + decimalFormat.format(timer), gamePanel.getTileSize() * 11, 65);



            // MESSAGE
            if (messageOn) {

                graphics2D.setFont(graphics2D.getFont().deriveFont(30F));
                graphics2D.drawString(message, gamePanel.getTileSize() / 2, gamePanel.getTileSize() * 6);

                messageCounter++;

                if (messageCounter > (gamePanel.getFps() * 2)) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }

    }

    public void drawPauseScreen() {

        String text = "PAUSED";
        int x = getXforCenteredText(text);

        int y = gamePanel.getScreenHeight() / 2;

        graphics2D.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        int x = gamePanel.getScreenWidth() / 2 - length / 2;
        return x;
    }

    public String getCurrentDialogue() {
        return currentDialogue;
    }

    public void setCurrentDialogue(String currentDialogue) {
        this.currentDialogue = currentDialogue;
    }

}
