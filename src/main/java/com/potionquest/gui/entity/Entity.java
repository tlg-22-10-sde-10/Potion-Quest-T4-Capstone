package com.potionquest.gui.entity;

import com.potionquest.gui.gamecontrol.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Entity {

  //  public int sizeX, sizeY;
  //  public int scaleFactor;
  public int worldX, worldY;

  public BufferedImage[] goUp = new BufferedImage[4];
  public BufferedImage[] goDown = new BufferedImage[4];
  public BufferedImage[] goLeft = new BufferedImage[4];
  public BufferedImage[] goRight = new BufferedImage[4];
  public String direction;
  public int speed;
  public boolean collisionOn = false;
  public int spriteCounter = 0;
  public int spriteNum = 1;
  public int actionTimeOut = 0;

  public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
  public int solidAreaDefaultX = 0;
  public int solidAreaDefaultY = -48;

  public BufferedImage imageFetch(String filePath) {

    BufferedImage image = null;
    try (InputStream inputStream = getClass().getResourceAsStream(filePath)) {

      //noinspection ConstantConditions
      image = ImageIO.read(inputStream);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return image;
  }

  public void setBehavior() {}

  public void update() {
    setBehavior();

    collisionOn = false;
    GamePanel.collider.checkTile(this);
    // add object collision detect here later
    GamePanel.collider.checkTargetsCollision(this);

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
        spriteNum = 1;
      }
      spriteCounter = 0;
    }
  }

  public void draw(Graphics2D g2D) {

    BufferedImage image = null;

    int screenX = worldX - GamePanel.player.worldX + GamePanel.player.screenX;
    int screenY = worldY - GamePanel.player.worldY + GamePanel.player.screenY;

    if (worldX + GamePanel.tileSize > GamePanel.player.worldX - GamePanel.player.screenX
        && worldX - GamePanel.tileSize < GamePanel.player.worldX + GamePanel.player.screenX
        && worldY + GamePanel.tileSize * 2 > GamePanel.player.worldY - GamePanel.player.screenY
        && worldY - GamePanel.tileSize * 2 < GamePanel.player.worldY + GamePanel.player.screenY) {

      switch (direction) {
        case "up":
          if (spriteNum == 1) {
            image = goUp[0];
          } else if (spriteNum == 2) {
            image = goUp[1];
          } else if (spriteNum == 3) {
            image = goUp[2];
          }
          break;
        case "down":
          if (spriteNum == 1) {
            image = goDown[0];
          } else if (spriteNum == 2) {
            image = goDown[1];
          } else if (spriteNum == 3) {
            image = goDown[2];
          }
          break;
        case "left":
          if (spriteNum == 1) {
            image = goLeft[0];
          } else if (spriteNum == 2) {
            image = goLeft[1];
          } else if (spriteNum == 3) {
            image = goLeft[2];
          }
          break;
        case "right":
          if (spriteNum == 1) {
            image = goRight[0];
          } else if (spriteNum == 2) {
            image = goRight[1];
          } else if (spriteNum == 3) {
            image = goRight[2];
            break;
          }
      }

      g2D.drawImage(image, screenX, screenY, null);
    }
  }
}
