package com.potionquest.gui.gamecontrol;

import com.potionquest.gui.entity.*;

public class CollisionChecker {

  public void checkTile(Entity entity) {

    int entityLeftWorldX = entity.worldX + entity.solidArea.x;
    int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
    int entityTopWorldY = entity.worldY + entity.solidArea.y;
    int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

    int entityLeftCol = entityLeftWorldX / GamePanel.tileSize;
    int entityRightCol = entityRightWorldX / GamePanel.tileSize;
    int entityTopRow = entityTopWorldY / GamePanel.tileSize;
    int entityBottomRow = entityBottomWorldY / GamePanel.tileSize;

    int tileNum1L1=0, tileNum2L1=0, tileNum1L2=0, tileNum2L2=0, tileNum1L3=0, tileNum2L3=0;

    switch (entity.direction) {
      case "up":
        entityTopRow = (entityTopWorldY - entity.speed) / GamePanel.tileSize;

        tileNum1L1 = GamePanel.tileMLayer1.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2L1 = GamePanel.tileMLayer1.mapTileNum[entityRightCol][entityTopRow];
        tileNum1L2 = GamePanel.tileMLayer2.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2L2 = GamePanel.tileMLayer2.mapTileNum[entityRightCol][entityTopRow];
        tileNum1L3 = GamePanel.tileMLayer3.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2L3 = GamePanel.tileMLayer3.mapTileNum[entityRightCol][entityTopRow];

        if (tileNum1L2 == -1 || tileNum2L2 == -1 || tileNum1L3 == -1 || tileNum2L3 == -1) {
          if (tileNum1L2 == -1) {
            tileNum1L2 = 24;
          }
          if (tileNum2L2 == -1) {
            tileNum2L2 = 24;
          }
          if (tileNum1L3 == -1) {
            tileNum1L3 = 24;
          }
          if (tileNum2L3 == -1) {
            tileNum2L3 = 24;
          }
        }

        if (GamePanel.tileMLayer1.tile[tileNum1L1].collision ||
            GamePanel.tileMLayer1.tile[tileNum2L1].collision ||
            GamePanel.tileMLayer2.tile[tileNum1L2].collision ||
            GamePanel.tileMLayer2.tile[tileNum2L2].collision ||
            GamePanel.tileMLayer3.tile[tileNum1L3].collision ||
            GamePanel.tileMLayer3.tile[tileNum2L3].collision) {
          entity.collisionOn = true;
        }
        break;
      case "down":
        entityBottomRow = (entityBottomWorldY + entity.speed) / GamePanel.tileSize;

        tileNum1L1 = GamePanel.tileMLayer1.mapTileNum[entityLeftCol][entityBottomRow];
        tileNum2L1 = GamePanel.tileMLayer1.mapTileNum[entityRightCol][entityBottomRow];
        tileNum1L2 = GamePanel.tileMLayer2.mapTileNum[entityLeftCol][entityBottomRow];
        tileNum2L2 = GamePanel.tileMLayer2.mapTileNum[entityRightCol][entityBottomRow];
        tileNum1L3 = GamePanel.tileMLayer3.mapTileNum[entityLeftCol][entityBottomRow];
        tileNum2L3 = GamePanel.tileMLayer3.mapTileNum[entityRightCol][entityBottomRow];

        if (tileNum1L2 == -1 || tileNum2L2 == -1 || tileNum1L3 == -1 || tileNum2L3 == -1) {
          if (tileNum1L2 == -1) {
            tileNum1L2 = 24;
          }
          if (tileNum2L2 == -1) {
            tileNum2L2 = 24;
          }
          if (tileNum1L3 == -1) {
            tileNum1L3 = 24;
          }
          if (tileNum2L3 == -1) {
            tileNum2L3 = 24;
          }
        }
        if (GamePanel.tileMLayer1.tile[tileNum1L1].collision ||
            GamePanel.tileMLayer1.tile[tileNum2L1].collision ||
            GamePanel.tileMLayer2.tile[tileNum1L2].collision ||
            GamePanel.tileMLayer2.tile[tileNum2L2].collision ||
            GamePanel.tileMLayer3.tile[tileNum1L3].collision ||
            GamePanel.tileMLayer3.tile[tileNum2L3].collision) {
          entity.collisionOn = true;
        }
        break;
      case "left":
        entityLeftCol = (entityLeftWorldX - entity.speed) / GamePanel.tileSize;
        tileNum1L1 = GamePanel.tileMLayer1.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2L1 = GamePanel.tileMLayer1.mapTileNum[entityLeftCol][entityBottomRow];
        tileNum1L2 = GamePanel.tileMLayer2.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2L2 = GamePanel.tileMLayer2.mapTileNum[entityLeftCol][entityBottomRow];
        tileNum1L3 = GamePanel.tileMLayer3.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2L3 = GamePanel.tileMLayer3.mapTileNum[entityLeftCol][entityBottomRow];

        if (tileNum1L2 == -1 || tileNum2L2 == -1 || tileNum1L3 == -1 || tileNum2L3 == -1) {
          if (tileNum1L2 == -1) {
            tileNum1L2 = 24;
          }
          if (tileNum2L2 == -1) {
            tileNum2L2 = 24;
          }
          if (tileNum1L3 == -1) {
            tileNum1L3 = 24;
          }
          if (tileNum2L3 == -1) {
            tileNum2L3 = 24;
          }
        }

        if (GamePanel.tileMLayer1.tile[tileNum1L1].collision ||
            GamePanel.tileMLayer1.tile[tileNum2L1].collision ||
            GamePanel.tileMLayer2.tile[tileNum1L2].collision ||
            GamePanel.tileMLayer2.tile[tileNum2L2].collision ||
            GamePanel.tileMLayer3.tile[tileNum1L3].collision ||
            GamePanel.tileMLayer3.tile[tileNum2L3].collision) {
          entity.collisionOn = true;
        }
        break;
      case "right":
        entityRightCol = (entityRightWorldX + entity.speed) / GamePanel.tileSize;
        tileNum1L1 = GamePanel.tileMLayer1.mapTileNum[entityRightCol][entityTopRow];
        tileNum2L1 = GamePanel.tileMLayer1.mapTileNum[entityRightCol][entityBottomRow];
        tileNum1L2 = GamePanel.tileMLayer2.mapTileNum[entityRightCol][entityTopRow];
        tileNum2L2 = GamePanel.tileMLayer2.mapTileNum[entityRightCol][entityBottomRow];
        tileNum1L3 = GamePanel.tileMLayer3.mapTileNum[entityRightCol][entityTopRow];
        tileNum2L3 = GamePanel.tileMLayer3.mapTileNum[entityRightCol][entityBottomRow];

        if (tileNum1L2 == -1 || tileNum2L2 == -1 || tileNum1L3 == -1 || tileNum2L3 == -1) {
          if (tileNum1L2 == -1) {
            tileNum1L2 = 24;
          }
          if (tileNum2L2 == -1) {
            tileNum2L2 = 24;
          }
          if (tileNum1L3 == -1) {
            tileNum1L3 = 24;
          }
          if (tileNum2L3 == -1) {
            tileNum2L3 = 24;
          }
        }

        if (GamePanel.tileMLayer1.tile[tileNum1L1].collision ||
            GamePanel.tileMLayer1.tile[tileNum2L1].collision ||
            GamePanel.tileMLayer2.tile[tileNum1L2].collision ||
            GamePanel.tileMLayer2.tile[tileNum2L2].collision ||
            GamePanel.tileMLayer3.tile[tileNum1L3].collision ||
            GamePanel.tileMLayer3.tile[tileNum2L3].collision) {
          entity.collisionOn = true;
        }
        break;
    }
  }

  // CHECK NPC OR MONSTER COLLISION
  public int checkEntity(Entity entity, Entity[] target) {

    int index = 999;

    for (int i = 0; i < target.length; i++) {

      if (target[i] != null) {
        // get entity's solid area position
        entity.solidArea.x = entity.worldX - entity.solidArea.x + 20;
        entity.solidArea.y = entity.worldY - entity.solidArea.y + 60;
        // get target's solid area position
        target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
        target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

        switch (entity.direction) {
          case "up":
            entity.solidArea.y -= entity.speed;
            if (entity.solidArea.intersects(target[i].solidArea)) {
              entity.collisionOn = true;
              index = i;
            }
            break;
          case "down":
            entity.solidArea.y += entity.speed;
            if (entity.solidArea.intersects(target[i].solidArea)) {
              entity.collisionOn = true;
              index = i;
            }
            break;
          case "left":
            entity.solidArea.x -= entity.speed;
            if (entity.solidArea.intersects(target[i].solidArea)) {
              entity.collisionOn = true;
              index = i;
            }
            break;
          case "right":
            entity.solidArea.x += entity.speed;
            if (entity.solidArea.intersects(target[i].solidArea)) {
              entity.collisionOn = true;
              index = i;
            }
            break;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        target[i].solidArea.x = target[i].solidAreaDefaultX;
        target[i].solidArea.y = target[i].solidAreaDefaultY;
      }
    }
    return index;
  }

  public void checkTargetsCollision(Entity entity) {
    // get entity's solid area position
    entity.solidArea.x = entity.worldX - entity.solidArea.x;
    entity.solidArea.y = entity.worldY - entity.solidArea.y;
    // get target's solid area position
    GamePanel.player.solidArea.x = GamePanel.player.worldX + GamePanel.player.solidArea.x;
    GamePanel.player.solidArea.y = GamePanel.player.worldY + GamePanel.player.solidArea.y;

    switch (entity.direction) {
      case "up":
        entity.solidArea.y -= entity.speed;
        if (entity.solidArea.intersects(GamePanel.player.solidArea)) {
          entity.collisionOn = true;
        }
        break;
      case "down":
        entity.solidArea.y += entity.speed;
        if (entity.solidArea.intersects(GamePanel.player.solidArea)) {
          entity.collisionOn = true;
        }
        break;
      case "left":
        entity.solidArea.x -= entity.speed;
        if (entity.solidArea.intersects(GamePanel.player.solidArea)) {
          entity.collisionOn = true;
        }
        break;
      case "right":
        entity.solidArea.x += entity.speed;
        if (entity.solidArea.intersects(GamePanel.player.solidArea)) {
          entity.collisionOn = true;
        }
        break;
    }
    entity.solidArea.x = entity.solidAreaDefaultX;
    entity.solidArea.y = entity.solidAreaDefaultY;
    GamePanel.player.solidArea.x = GamePanel.player.solidAreaDefaultX;
    GamePanel.player.solidArea.y = GamePanel.player.solidAreaDefaultY;
  }
}
