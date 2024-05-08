package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import logic.GamePanel;
import util.RenderScaleTool;

public class TileManager {

    GamePanel gamePanel;
    private Tile[] tile;
    private int[][] mapTileNum;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[50];
        mapTileNum = new int[gamePanel.getMaxWorldCol()][gamePanel.getMaxWorldRow()];
        getTileImage();
        loadMap("/maps/map.txt");
    }

    public int[][] getMapTileNum() {
        return mapTileNum;
    }

    public Tile[] getTile() {
        return tile;
    }

    public void getTileImage() {
        setup(0, "grass", false);
        setup(1, "wall", true);
        setup(2, "water", true);
        setup(3, "earth", false);
        setup(4, "tree", true);
        setup(5, "sand", false);
    }

    public void setup(int index, String imageName, boolean collision) {
        RenderScaleTool scaleTool = new RenderScaleTool();

        try {
            tile[index] = new Tile();
            tile[index].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].setImage(
                    scaleTool.scaleImage(tile[index].getImage(), gamePanel.getTileSize(), gamePanel.getTileSize())
            );
            tile[index].setCollision(collision);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try (InputStream inputStream = getClass().getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        ) {


            int col = 0;
            int row = 0;
            while (col < gamePanel.getMaxWorldCol() && row < gamePanel.getMaxWorldRow()) {
                String line = br.readLine();
                while (col < gamePanel.getMaxWorldCol()) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gamePanel.getMaxWorldCol()) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.getMaxWorldCol() && worldRow < gamePanel.getMaxWorldRow()) {

            int worldX = worldCol * gamePanel.getTileSize();
            int worldY = worldRow * gamePanel.getTileSize();
            int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
            int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();
            int tileNum = mapTileNum[worldCol][worldRow];

            if (worldX + gamePanel.getTileSize()
                    > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX()
                    && worldX - gamePanel.getTileSize()
                    < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX()
                    && worldY + gamePanel.getTileSize()
                    > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getScreenY()
                    && worldY - gamePanel.getTileSize()
                    < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY()) {
                graphics2D.drawImage(tile[tileNum].getImage(), screenX, screenY, null);
            }
            worldCol++;
            if (worldCol == gamePanel.getMaxWorldCol()) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
