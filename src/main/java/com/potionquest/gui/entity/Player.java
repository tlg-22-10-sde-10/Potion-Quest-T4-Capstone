package com.potionquest.gui.entity;

import static com.potionquest.gui.gamecontrol.GamePanel.keyH;

import com.potionquest.game.Game;
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
  private final int scaleFactor = 3;

  private final int playerSizeX = sizeX * scaleFactor;
  private final int playerSizeY = sizeY * scaleFactor;

  public final int screenX;
  public final int screenY;

  public Player() {
    screenX = GamePanel.screenWidth/2 - (playerSizeX/2);
    screenY = GamePanel.screenHeight/2 - (playerSizeY/2);

    solidArea = new Rectangle();
    solidArea.x = 8;
    solidArea.y = 20;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.width = 32;
    solidArea.height = 40;

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
        goUp[i] = up;
        imageIndexX += 16;
      }

      imageIndexX = 0;
      for (int i = 0; i < 4; i++) {
        BufferedImage down = playerImage.getSubimage(imageIndexX, 0, 16, 20);
        goDown[i] = down;
        imageIndexX += 16;
      }

      imageIndexX = 0;
      for (int i = 0; i < 4; i++) {
        BufferedImage left = playerImage.getSubimage(imageIndexX, 60, 16, 20);
        goLeft[i] = left;
        imageIndexX += 16;
      }

      imageIndexX = 0;
      for (int i = 0; i < 4; i++) {
        BufferedImage right = playerImage.getSubimage(imageIndexX, 20, 16, 20);
        goRight[i] = right;
        imageIndexX += 16;
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void update() {

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
      int npcIndex = GamePanel.collider.checkEntity(GamePanel.player, GamePanel.npc);
      collideNPC(npcIndex);

      // IF COLLISION IS FALSE, PLAYER CAN MOVE
      if (!collisionOn) {
        switch (direction) {
          case "up":
            worldY -= speed;
            break;
          case "down":
            worldY += speed;
            break;
          case "left":
            worldX -= speed;
            break;
          case "right":
            worldX += speed;
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

  public void collideNPC(int i) {

    if (i != 999) {
      System.out.println("You are hitting an npc!");
      // Debug output for collision box debugging
//      System.out.println("Player Solid Area Default X: " + GamePanel.player.solidAreaDefaultX + "\n" +
//          "Player Solid Area Default Y: " + GamePanel.player.solidAreaDefaultY + "\n" +
//          "solidArea.x: " + solidArea.x + "\n" +
//          "solidArea.y: " + solidArea.y + "\n" +
//          "entity solidArea.x: " + GamePanel.npc[i].solidArea.x + "\n" +
//          "entity solidArea.y: " + GamePanel.npc[i].solidArea.y);
    }
  }

  public void draw(Graphics2D g2D) {

    BufferedImage image = null;

    switch (direction) {
      case "up":
        if (spriteNum == 1) {
          image = goUp[0];
        } else if (spriteNum == 2) {
          image = goUp[1];
        } else if (spriteNum == 3) {
          image = goUp[2];
        } else if (spriteNum == 4) {
          image = goUp[3];
        }
        break;
      case "down":
        if (spriteNum == 1) {
          image = goDown[0];
        } else if (spriteNum == 2) {
          image = goDown[1];
        } else if (spriteNum == 3) {
          image = goDown[2];
        } else if (spriteNum == 4) {
          image = goDown[3];
        }
        break;
      case "left":
        if (spriteNum == 1) {
          image = goLeft[0];
        } else if (spriteNum == 2) {
          image = goLeft[1];
        } else if (spriteNum == 3) {
          image = goLeft[2];
        } else if (spriteNum == 4) {
          image = goLeft[3];
        }
        break;
      case "right":
        if (spriteNum == 1) {
          image = goRight[0];
        } else if (spriteNum == 2) {
          image = goRight[1];
        } else if (spriteNum == 3) {
          image = goRight[2];
        } else if (spriteNum == 4) {
          image = goRight[3];
        }
        break;
    }
    g2D.drawImage(image, screenX, screenY, playerSizeX, playerSizeY, null);
  }
}


