package com.potionquest.gui.gamecontrol;

import com.potionquest.gui.entity.*;

public class CollisionChecker {

  GamePanel gp;

  public CollisionChecker(GamePanel gp) {

    this.gp = gp;
  }

  public void checkTile(Entity entity) {

    int entityLeftWorldX = entity.worldX + entity.solidArea.x;
    int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
    int entityTopWorldY = entity.worldY + entity.solidArea.y;
    int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

    int entityLeftCol = entityLeftWorldX / gp.tileSize;
    int entityRightCol = entityRightWorldX / gp.tileSize;
    int entityTopRow = entityTopWorldY / gp.tileSize;
    int entityBottomRow = entityBottomWorldY / gp.tileSize;

    int tileNum1, tileNum2;

    switch (entity.direction) {
      case "up":
        entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
        tileNum1 = gp.tileMLayer2.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2 = gp.tileMLayer2.mapTileNum[entityRightCol][entityTopRow];

        if (tileNum1 == -1 || tileNum2 == -1) {
          if (tileNum1 == -1) {
            tileNum1 = 39;
          }
          if (tileNum2 == -1) {
            tileNum2 = 39;
          }
        }

        if (gp.tileMLayer2.tile[tileNum1].collision || gp.tileMLayer2.tile[tileNum2].collision) {
          entity.collisionOn = true;
        }
        break;
      case "down":
        entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
        tileNum1 = gp.tileMLayer2.mapTileNum[entityLeftCol][entityBottomRow];
        tileNum2 = gp.tileMLayer2.mapTileNum[entityRightCol][entityBottomRow];

        if (tileNum1 == -1 || tileNum2 == -1) {
          if (tileNum1 == -1) {
            tileNum1 = 39;
          }
          if (tileNum2 == -1) {
            tileNum2 = 39;
          }
        }

        if (gp.tileMLayer2.tile[tileNum1].collision || gp.tileMLayer2.tile[tileNum2].collision) {
          entity.collisionOn = true;
        }
        break;
      case "left":
        entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
        tileNum1 = gp.tileMLayer2.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2 = gp.tileMLayer2.mapTileNum[entityLeftCol][entityBottomRow];

        if (tileNum1 == -1 || tileNum2 == -1) {
          if (tileNum1 == -1) {
            tileNum1 = 39;
          }
          if (tileNum2 == -1) {
            tileNum2 = 39;
          }
        }

        if (gp.tileMLayer2.tile[tileNum1].collision || gp.tileMLayer2.tile[tileNum2].collision) {
          entity.collisionOn = true;
        }
        break;
      case "right":
        entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
        tileNum1 = gp.tileMLayer2.mapTileNum[entityRightCol][entityTopRow];
        tileNum2 = gp.tileMLayer2.mapTileNum[entityRightCol][entityBottomRow];

        if (tileNum1 == -1 || tileNum2 == -1) {
          if (tileNum1 == -1) {
            tileNum1 = 39;
          }
          if (tileNum2 == -1) {
            tileNum2 = 39;
          }
        }

        if (gp.tileMLayer2.tile[tileNum1].collision || gp.tileMLayer2.tile[tileNum2].collision) {
          entity.collisionOn = true;
        }
        break;
    }
  }
}
