package com.potionquest.gui.gamecontrol;

import com.potionquest.gui.entity.*;

public class CollisionChecker {

  private GamePanel gp;

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

    int tileNum1L1, tileNum2L1, tileNum1L2, tileNum2L2, tileNum1L3, tileNum2L3;

    switch (entity.direction) {
      case "up":
        entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
        tileNum1L1 = gp.tileMLayer1.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2L1 = gp.tileMLayer1.mapTileNum[entityRightCol][entityTopRow];
        tileNum1L2 = gp.tileMLayer2.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2L2 = gp.tileMLayer2.mapTileNum[entityRightCol][entityTopRow];
        tileNum1L3 = gp.tileMLayer3.mapTileNum[entityLeftCol][entityTopRow];;
        tileNum2L3 = gp.tileMLayer3.mapTileNum[entityRightCol][entityTopRow];;

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

        if (gp.tileMLayer1.tile[tileNum1L1].collision ||
            gp.tileMLayer1.tile[tileNum2L1].collision ||
            gp.tileMLayer2.tile[tileNum1L2].collision ||
            gp.tileMLayer2.tile[tileNum2L2].collision ||
            gp.tileMLayer3.tile[tileNum1L3].collision ||
            gp.tileMLayer3.tile[tileNum2L3].collision) {
          entity.collisionOn = true;
        }
        break;
      case "down":
        entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
        tileNum1L1 = gp.tileMLayer1.mapTileNum[entityLeftCol][entityBottomRow];
        tileNum2L1 = gp.tileMLayer1.mapTileNum[entityRightCol][entityBottomRow];
        tileNum1L2 = gp.tileMLayer2.mapTileNum[entityLeftCol][entityBottomRow];
        tileNum2L2 = gp.tileMLayer2.mapTileNum[entityRightCol][entityBottomRow];
        tileNum1L3 = gp.tileMLayer3.mapTileNum[entityLeftCol][entityBottomRow];;
        tileNum2L3 = gp.tileMLayer3.mapTileNum[entityRightCol][entityBottomRow];;

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

        if (gp.tileMLayer1.tile[tileNum1L1].collision ||
            gp.tileMLayer1.tile[tileNum2L1].collision ||
            gp.tileMLayer2.tile[tileNum1L2].collision ||
            gp.tileMLayer2.tile[tileNum2L2].collision ||
            gp.tileMLayer3.tile[tileNum1L3].collision ||
            gp.tileMLayer3.tile[tileNum2L3].collision) {
          entity.collisionOn = true;
        }
        break;
      case "left":
        entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
        tileNum1L1 = gp.tileMLayer1.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2L1 = gp.tileMLayer1.mapTileNum[entityLeftCol][entityBottomRow];
        tileNum1L2 = gp.tileMLayer2.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2L2 = gp.tileMLayer2.mapTileNum[entityLeftCol][entityBottomRow];
        tileNum1L3 = gp.tileMLayer3.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2L3 = gp.tileMLayer3.mapTileNum[entityLeftCol][entityBottomRow];

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

        if (gp.tileMLayer1.tile[tileNum1L1].collision ||
            gp.tileMLayer1.tile[tileNum2L1].collision ||
            gp.tileMLayer2.tile[tileNum1L2].collision ||
            gp.tileMLayer2.tile[tileNum2L2].collision ||
            gp.tileMLayer3.tile[tileNum1L3].collision ||
            gp.tileMLayer3.tile[tileNum2L3].collision) {
          entity.collisionOn = true;
        }
//        entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
//        tileNum1L2 = gp.tileMLayer2.mapTileNum[entityLeftCol][entityTopRow];
//        tileNum2L2 = gp.tileMLayer2.mapTileNum[entityLeftCol][entityBottomRow];
//
//        if (tileNum1L2 == -1 || tileNum2L2 == -1) {
//          if (tileNum1L2 == -1) {
//            tileNum1L2 = 24;
//          }
//          if (tileNum2L2 == -1) {
//            tileNum2L2 = 24;
//          }
//        }
//
//        if (gp.tileMLayer2.tile[tileNum1L2].collision || gp.tileMLayer2.tile[tileNum2L2].collision) {
//          entity.collisionOn = true;
//        }
        break;
      case "right":
        entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
        tileNum1L1 = gp.tileMLayer1.mapTileNum[entityRightCol][entityTopRow];
        tileNum2L1 = gp.tileMLayer1.mapTileNum[entityRightCol][entityBottomRow];
        tileNum1L2 = gp.tileMLayer2.mapTileNum[entityRightCol][entityTopRow];
        tileNum2L2 = gp.tileMLayer2.mapTileNum[entityRightCol][entityBottomRow];
        tileNum1L3 = gp.tileMLayer3.mapTileNum[entityRightCol][entityTopRow];
        tileNum2L3 = gp.tileMLayer3.mapTileNum[entityRightCol][entityBottomRow];

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

        if (gp.tileMLayer1.tile[tileNum1L1].collision ||
            gp.tileMLayer1.tile[tileNum2L1].collision ||
            gp.tileMLayer2.tile[tileNum1L2].collision ||
            gp.tileMLayer2.tile[tileNum2L2].collision ||
            gp.tileMLayer3.tile[tileNum1L3].collision ||
            gp.tileMLayer3.tile[tileNum2L3].collision) {
          entity.collisionOn = true;
        }
//        entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
//        tileNum1L2 = gp.tileMLayer2.mapTileNum[entityRightCol][entityTopRow];
//        tileNum2L2 = gp.tileMLayer2.mapTileNum[entityRightCol][entityBottomRow];
//
//        if (tileNum1L2 == -1 || tileNum2L2 == -1) {
//          if (tileNum1L2 == -1) {
//            tileNum1L2 = 24;
//          }
//          if (tileNum2L2 == -1) {
//            tileNum2L2 = 24;
//          }
//        }
//
//        if (gp.tileMLayer2.tile[tileNum1L2].collision || gp.tileMLayer2.tile[tileNum2L2].collision) {
//          entity.collisionOn = true;
//        }
        break;
    }
  }
}