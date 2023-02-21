package com.potionquest.gui.entity;


import com.potionquest.gui.gamecontrol.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Player extends Entity {

  private final int sizeX = 16;
  private final int sizeY = 20;
  private final int scaleFactor = 2;

  private final int playerSizeX = sizeX * scaleFactor;
  private final int playerSizeY = sizeY * scaleFactor;

  public final int screenX;
  public final int screenY;


  public Player() {
    screenX = GamePanel.screenWidth/2 - (playerSizeX/2);
    screenY = GamePanel.screenHeight/2 - (playerSizeY/2);

    solidArea = new Rectangle();
    solidArea.x = 10;
    solidArea.y = 20;

    solidArea.width = 12;
    solidArea.height = 20;

    setDefaultValues();
    getPlayerImage();
  }

  public void setDefaultValues() {

    worldX = GamePanel.tileSize * 4;
    worldY = GamePanel.tileSize * 38;
    speed = 4;
    direction = "down";
  }

  public void getPlayerImage() {

    try (InputStream inputStream = getClass().getResourceAsStream("/player/character.png")) {

      //noinspection ConstantConditions
      BufferedImage playerImage = ImageIO.read(inputStream);

      int imageIndexX = 0;

      for (int i = 0; i < 4; i++) {
        BufferedImage up = playerImage.getSubimage(imageIndexX, 40, 16, 20);
        BufferedImage down = playerImage.getSubimage(imageIndexX, 0, 16, 20);
        BufferedImage left = playerImage.getSubimage(imageIndexX, 60, 16, 20);
        BufferedImage right = playerImage.getSubimage(imageIndexX, 20, 16, 20);

        goUp[i] = up;
        goDown[i] = down;
        goLeft[i] = left;
        goRight[i] = right;
        imageIndexX += 16;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void update(GamePanel gp) {

    if (gp.keyH.upPressed || gp.keyH.downPressed || gp.keyH.leftPressed || gp.keyH.rightPressed) {
      if (gp.keyH.upPressed) {
        direction = "up";
      } else if (gp.keyH.downPressed) {
        direction = "down";
      } else if (gp.keyH.leftPressed) {
        direction = "left";
      } else //noinspection ConstantConditions
        if (gp.keyH.rightPressed) {
          direction = "right";
        }

      //CHECK TILE COLLISION
      collisionOn = false;
      GamePanel.collider.checkTile(this);

      // CHECK NPC COLLISION
      int npcIndex = GamePanel.collider.checkEntity(this, GamePanel.npc);
      collideNPC(npcIndex);

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
      if (spriteCounter >= 12) {
        spriteNum++;
        if (spriteNum >= 3) {
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

  public void collideNPC(int i) {

    if (i != 999) {
      System.out.println("You are hitting an npc!");
    }
  }

  public void draw(Graphics2D g2D) {

    BufferedImage image = null;

    switch (direction) {
      case "up":
        image = goUp[spriteNum - 1];
        break;
      case "down":
        image = goDown[spriteNum - 1];
        break;
      case "left":
        image = goLeft[spriteNum - 1];
        break;
      case "right":
        image = goRight[spriteNum - 1];
        break;
    }

    g2D.drawImage(image, screenX, screenY, playerSizeX, playerSizeY, null);
  }
}


