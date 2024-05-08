package logic;

import entity.Entity;
import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int maxWorldCol = 50;
    private final int maxWorldRow = 50;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    private final int worldWidth = tileSize * maxWorldCol;
    private final int worldHeight = tileSize * maxWorldRow;

    private KeyHandler keyHandler = new KeyHandler(this);
    private Thread gameThread;
    private TileManager tileManager = new TileManager(this);
    private CollisionChecker collisionChecker = new CollisionChecker(this);
    private AssetSetter assetSetter = new AssetSetter(this);
    private int gameState;
    private UserInterface userInterface = new UserInterface(this);
    private Player player = new Player(this , keyHandler);
    private Entity[] npc = new Entity[10];
    private SuperObject[] object = new SuperObject[1000];
    private final int playState = 1;
    private final int pauseState = 2;
    private final int dialogueState = 3;
    private int fps = 60;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObject();
        assetSetter.setNpc();
        gameState = playState;
    }

    public AssetSetter getAssetSetter() {
        return assetSetter;
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    public Player getPlayer() {
        return player;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public SuperObject[] getObject() {
        return object;
    }

    public void setObject(int i, SuperObject superObject, int worldX, int worldY) {
        object[i] = superObject;
        object[i].setWorldX(worldX);
        object[i].setWorldY(worldY);
    }

    public void setObjectNull(int i) {
        object[i] = null;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getFps() {
        return fps;
    }

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public int getDialogueState() {
        return dialogueState;
    }

    public int getPlayState() {
        return playState;
    }

    public int getPauseState() {
        return pauseState;
    }

    public UserInterface getUserInterface() {
        return userInterface;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGameThread() {
        gameThread = null;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public Entity[] getNpc() {
        return npc;
    }

    public void setNpc(Entity innerNpc, int index) {
        this.npc[index] = innerNpc;
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / fps;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void update() {
        if (gameState == playState) {
            player.update();
            for (Entity innerNpc : npc) {
                if (innerNpc != null) {
                    innerNpc.update();
                }
            }
        }
    }

    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        tileManager.draw(graphics2D);

        for (SuperObject superObject : object) {
            if (superObject != null) {
                superObject.draw(graphics2D, this);
            }
        }

        for (Entity entity : npc) {
            if (entity != null) {
                entity.draw(graphics2D);
            }
        }

        player.draw(graphics2D);
        userInterface.draw(graphics2D);

        graphics2D.dispose();
    }

}
