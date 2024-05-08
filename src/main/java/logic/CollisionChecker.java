package logic;

import entity.Entity;

public class CollisionChecker {

    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
        int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
        int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;

        int entityLeftCol = entityLeftWorldX / gamePanel.getTileSize();
        int entityRightCol = entityRightWorldX / gamePanel.getTileSize();
        int entityTopRow = entityTopWorldY / gamePanel.getTileSize();
        int entityBottomRow = entityBottomWorldY / gamePanel.getTileSize();

        int tileNum1;
        int tileNum2;

        switch (entity.getDirectionHeight()) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityTopRow];
                if (gamePanel.getTileManager().getTile()[tileNum1].isCollision()
                        || gamePanel.getTileManager().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityBottomRow];
                if (gamePanel.getTileManager().getTile()[tileNum1].isCollision()
                        || gamePanel.getTileManager().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            default -> {
                // Do nothing
            }
        }

        switch (entity.getDirectionWidth()) {
            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityLeftCol][entityBottomRow];
                if (gamePanel.getTileManager().getTile()[tileNum1].isCollision()
                        || gamePanel.getTileManager().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gamePanel.getTileSize();
                tileNum1 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.getTileManager().getMapTileNum()[entityRightCol][entityBottomRow];
                if (gamePanel.getTileManager().getTile()[tileNum1].isCollision()
                        || gamePanel.getTileManager().getTile()[tileNum2].isCollision()) {
                    entity.setCollisionOn(true);
                }
            }
            default -> {
                // Do nothing
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for (int i = 0; i < gamePanel.getObject().length; i++) {

            if (gamePanel.getObject()[i] != null) {

                entity.setSolidAreaX(entity.getWorldX() + entity.getSolidArea().x);
                entity.setSolidAreaY(entity.getWorldY() + entity.getSolidArea().y);

                gamePanel.getObject()[i].setSolidAreaX(gamePanel.getObject()[i].getWorldX()
                        + gamePanel.getObject()[i].getSolidArea().x);
                gamePanel.getObject()[i].setSolidAreaY(gamePanel.getObject()[i].getWorldY()
                        + gamePanel.getObject()[i].getSolidArea().y);

                switch (entity.getDirectionHeight()) {
                    case "up" -> {
                        entity.setSolidAreaY(entity.getSolidArea().y - entity.getSpeed());
                        if (entity.getSolidArea().intersects(gamePanel.getObject()[i].getSolidArea())) {
                            if (gamePanel.getObject()[i].isCollision()) {
                                entity.setCollisionOn(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    case "down" -> {
                        entity.setSolidAreaY(entity.getSolidArea().y + entity.getSpeed());
                        if (entity.getSolidArea().intersects(gamePanel.getObject()[i].getSolidArea())) {
                            if (gamePanel.getObject()[i].isCollision()) {
                                entity.setCollisionOn(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    default -> {
                        // Do nothing
                    }
                }

                switch (entity.getDirectionWidth()) {
                    case "left" -> {
                        entity.setSolidAreaX(entity.getSolidArea().x - entity.getSpeed());
                        if (entity.getSolidArea().intersects(gamePanel.getObject()[i].getSolidArea())) {
                            if (gamePanel.getObject()[i].isCollision()) {
                                entity.setCollisionOn(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    case "right" -> {
                        entity.setSolidAreaX(entity.getSolidArea().x + entity.getSpeed());
                        if (entity.getSolidArea().intersects(gamePanel.getObject()[i].getSolidArea())) {
                            if (gamePanel.getObject()[i].isCollision()) {
                                entity.setCollisionOn(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                    }
                    default -> {
                        // Do nothing
                    }
                }
                entity.setSolidAreaX(entity.getSolidAreaDefaultX());
                entity.setSolidAreaY(entity.getSolidAreaDefaultY());
                gamePanel.getObject()[i].setSolidAreaX(gamePanel.getObject()[i].getSolidAreaDefaultX());
                gamePanel.getObject()[i].setSolidAreaY(gamePanel.getObject()[i].getSolidAreaDefaultY());
            }
        }
        return index;
    }

    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {

            if (target[i] != null) {

                entity.setSolidAreaX(entity.getWorldX() + entity.getSolidArea().x);
                entity.setSolidAreaY(entity.getWorldY() + entity.getSolidArea().y);

                target[i].setSolidAreaX(target[i].getWorldX());
                target[i].setSolidAreaY(target[i].getWorldY());

                switch (entity.getDirectionHeight()) {
                    case "up" -> {
                        entity.setSolidAreaY(entity.getSolidArea().y - entity.getSpeed());
                        if (entity.getSolidArea().intersects(target[i].getSolidArea())) {
                            entity.setCollisionOn(true);
                            index = i;
                        }
                    }
                    case "down" -> {
                        entity.setSolidAreaY(entity.getSolidArea().y + entity.getSpeed());
                        if (entity.getSolidArea().intersects(target[i].getSolidArea())) {
                            entity.setCollisionOn(true);
                            index = i;
                        }
                    }
                    default -> {
                        // Do nothing
                    }
                }

                switch (entity.getDirectionWidth()) {
                    case "left" -> {
                        entity.setSolidAreaX(entity.getSolidArea().x - entity.getSpeed());
                        if (entity.getSolidArea().intersects(target[i].getSolidArea())) {
                            entity.setCollisionOn(true);
                            index = i;
                        }
                    }
                    case "right" -> {
                        entity.setSolidAreaX(entity.getSolidArea().x + entity.getSpeed());
                        if (entity.getSolidArea().intersects(target[i].getSolidArea())) {
                            entity.setCollisionOn(true);
                            index = i;
                        }
                    }
                    default -> {
                        // Do nothing
                    }
                }
                entity.setSolidAreaX(entity.getSolidAreaDefaultX());
                entity.setSolidAreaY(entity.getSolidAreaDefaultY());
                target[i].setSolidAreaX(target[i].getSolidAreaDefaultX());
                target[i].setSolidAreaY(target[i].getSolidAreaDefaultY());
            }
        }
        return index;
    }

    public void checkPlayer(Entity entity) {
        if (gamePanel.getPlayer() != null) {

            entity.setSolidAreaX(entity.getWorldX() + entity.getSolidArea().x);
            entity.setSolidAreaY(entity.getWorldY() + entity.getSolidArea().y);

            gamePanel.getPlayer().setSolidAreaX(gamePanel.getPlayer().getWorldX());
            gamePanel.getPlayer().setSolidAreaY(gamePanel.getPlayer().getWorldY());

            switch (entity.getDirectionHeight()) {
                case "up" -> {
                    entity.setSolidAreaY(entity.getSolidArea().y - entity.getSpeed());
                    if (entity.getSolidArea().intersects(gamePanel.getPlayer().getSolidArea())) {
                        entity.setCollisionOn(true);
                    }
                }
                case "down" -> {
                    entity.setSolidAreaY(entity.getSolidArea().y + entity.getSpeed());
                    if (entity.getSolidArea().intersects(gamePanel.getPlayer().getSolidArea())) {
                        entity.setCollisionOn(true);
                    }
                }
                default -> {
                    // Do nothing
                }
            }

            switch (entity.getDirectionWidth()) {
                case "left" -> {
                    entity.setSolidAreaX(entity.getSolidArea().x - entity.getSpeed());
                    if (entity.getSolidArea().intersects(gamePanel.getPlayer().getSolidArea())) {
                        entity.setCollisionOn(true);
                    }
                }
                case "right" -> {
                    entity.setSolidAreaX(entity.getSolidArea().x + entity.getSpeed());
                    if (entity.getSolidArea().intersects(gamePanel.getPlayer().getSolidArea())) {
                        entity.setCollisionOn(true);
                    }
                }
                default -> {
                    // Do nothing
                }
            }
            entity.setSolidAreaX(entity.getSolidAreaDefaultX());
            entity.setSolidAreaY(entity.getSolidAreaDefaultY());
            gamePanel.getPlayer().setSolidAreaX(gamePanel.getPlayer().getSolidAreaDefaultX());
            gamePanel.getPlayer().setSolidAreaY(gamePanel.getPlayer().getSolidAreaDefaultY());
        }
    }
}
