package com.potionquest.gui.entity;


import static com.potionquest.gui.gamecontrol.GamePanel.keyH;

import com.potionquest.gui.gamecontrol.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Player extends Entity {

  private final int sizeX = 48;
  private final int sizeY = 96;
//  private final int scaleFactor = 3;

  private final int playerSizeX = sizeX;
  private final int playerSizeY = sizeY;

  public final int screenX;
  public final int screenY;

  public Player() {
    screenX = GamePanel.screenWidth/2 - (playerSizeX/2);
    screenY = GamePanel.screenHeight/2 - (playerSizeY/2);

    solidArea = new Rectangle();
    solidArea.x = 8;
    solidArea.y = 48;

    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.width = 32;
    solidArea.height = 32;

    setDefaultValues();
    getPlayerImage();
  }

  public void setDefaultValues() {

    // start game spawn point
//    worldX = GamePanel.tileSize * 4;
//    worldY = GamePanel.tileSize * 38;
    // near teleporter at river
//    worldX = GamePanel.tileSize * 48;
//    worldY = GamePanel.tileSize * 70;
    // near hermit
    worldX = GamePanel.tileSize * 21;
    worldY = GamePanel.tileSize * 82;
    speed = 4;
    direction = "down";
  }

  public void getPlayerImage() {

    try (InputStream inputStream = getClass().getResourceAsStream("/player/characterResized.png")) {

      //noinspection ConstantConditions
      BufferedImage playerImage = ImageIO.read(inputStream);

      int imageIndexX = 0;

      for (int i = 0; i < 4; i++) {
        BufferedImage up = playerImage.getSubimage(imageIndexX, 192, 48, 96);
        BufferedImage down = playerImage.getSubimage(imageIndexX, 0, 48, 96);
        BufferedImage left = playerImage.getSubimage(imageIndexX, 288, 48, 96);
        BufferedImage right = playerImage.getSubimage(imageIndexX, 96, 48, 96);

        goUp[i] = up;
        goDown[i] = down;
        goLeft[i] = left;
        goRight[i] = right;
        imageIndexX += 48;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void update(GamePanel gp) {

    if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
      if (keyH.upPressed) {
        direction = "up";
      } else if (keyH.downPressed) {
        direction = "down";
      } else if (keyH.leftPressed) {
        direction = "left";
      } else //noinspection ConstantConditions
        if (keyH.rightPressed) {
          direction = "right";
        }

      //CHECK TILE COLLISION
      collisionOn = false;
      GamePanel.collider.checkTile(this);

      // CHECK NPC COLLISION
      int npcIndex = GamePanel.collider.checkEntity(this, GamePanel.npc);
      talkNPC(npcIndex);

      //CHECK EVENT
      GamePanel.eHandler.checkEvent();

      // IF COLLISION IS FALSE, PLAYER CAN MOVE
      if (!collisionOn) {
        switch (direction) {
          case "up":
            if(worldY - speed >= 0) {
              worldY -= speed;
            }
            break;
          case "down":
            if(worldY + speed + playerSizeY <= GamePanel.maxWorldRow * GamePanel.tileSize) {
              worldY += speed;
            }
            break;
          case "left":
            if(worldX - speed >= 0) {
              worldX -= speed;
            }
            break;
          case "right":
            if(worldX + speed + playerSizeX <= GamePanel.maxWorldCol * GamePanel.tileSize) {
              worldX += speed;
            }
            break;
        }
      }

      spriteCounter++;
      if (spriteCounter > 12) {
        if (spriteNum == 1) {
          spriteNum = 2;
        } else if (spriteNum == 2) {
          spriteNum = 3;
        } else if (spriteNum == 3) {
          spriteNum = 4;
        } else if (spriteNum == 4) {
          spriteNum = 1;
        }
        spriteCounter = 0;
      }
    }
  }

  public void pickUpObject(int i) {

    if (i != 999) {

    }
  }

  public void talkNPC(int i) {

    if (i != 999) {
      System.out.println(i);
      System.out.println("You are running into NPC!");
      if (keyH.zPressed) {
        GamePanel.gameState = GamePanel.dialogueState;
        GamePanel.npc[i].talk();
      }
    }
    keyH.zPressed = false;
  }

  public void draw(Graphics2D g2D) {

    BufferedImage image = null;

    switch (direction) {
      case "up":
        image = goUp[spriteNum-1];
        break;
      case "down":
        image = goDown[spriteNum-1];
        break;
      case "left":
        image = goLeft[spriteNum-1];
        break;
      case "right":
        image = goRight[spriteNum-1];
        break;
    }

    g2D.drawImage(image, screenX, screenY, playerSizeX, playerSizeY, null);
  }
}


