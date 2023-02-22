package com.potionquest.gui.entity;


import static com.potionquest.gui.gamecontrol.GamePanel.FPS;
import static com.potionquest.gui.gamecontrol.GamePanel.keyH;

import com.potionquest.gui.gamecontrol.*;
import java.awt.AlphaComposite;
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
    MAX_HP = 20;
    HP = MAX_HP;

    screenX = GamePanel.screenWidth/2 - (playerSizeX/2);
    screenY = GamePanel.screenHeight/2 - (playerSizeY/2);

    solidArea = new Rectangle();
    solidArea.x = 10;
    solidArea.y = 20;

    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.width = 32;
    solidArea.height = 40;


    setDefaultValues();
    getPlayerImage();
  }

  public void setDefaultValues() {

    // start game spawn point
    worldX = GamePanel.tileSize * 4;
    worldY = GamePanel.tileSize * 38;
    // near teleporter at river
//    worldX = GamePanel.tileSize * 48;
//    worldY = GamePanel.tileSize * 70;
    // near hermit
//    worldX = GamePanel.tileSize * 21;
//    worldY = GamePanel.tileSize * 82;
    speed = 4;
    direction = "down";
  }

  public void getPlayerImage() {

    try (InputStream inputStream = getClass().getResourceAsStream("/player/character.png")) {

      //noinspection ConstantConditions
      BufferedImage playerImage = ImageIO.read(inputStream);

      for (int i = 0; i < 4; i++) {
        BufferedImage up = playerImage.getSubimage(i * sizeX, 40, 16, 20);
        BufferedImage down = playerImage.getSubimage(i * sizeX, 0, 16, 20);
        BufferedImage left = playerImage.getSubimage(i * sizeX, 60, 16, 20);
        BufferedImage right = playerImage.getSubimage(i * sizeX, 20, 16, 20);

        goUp[i] = up;
        goDown[i] = down;
        goLeft[i] = left;
        goRight[i] = right;

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void getPlayerImage2() {

    try (InputStream inputStream = getClass().getResourceAsStream("/lidia.png")) {

      //noinspection ConstantConditions
      BufferedImage playerImage = ImageIO.read(inputStream);

      int imageIndexX = 0;

      for (int i = 0; i < 9; i++) {
        BufferedImage up = playerImage.getSubimage(imageIndexX, 0, 64, 64);
        BufferedImage left = playerImage.getSubimage(imageIndexX, 64, 64, 64);
        BufferedImage down = playerImage.getSubimage(imageIndexX, 128, 64, 64);
        BufferedImage right = playerImage.getSubimage(imageIndexX, 192, 64, 64);

        goUp[i] = up;
        goDown[i] = down;
        goLeft[i] = left;
        goRight[i] = right;
        imageIndexX += 64;
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

      //CHECK MONSTER COLLISION
      int monsterIndex = GamePanel.collider.checkEntity(GamePanel.player, GamePanel.monsters);
      contactMonster(monsterIndex);

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
      if (spriteCounter >= 12) {
        if (spriteNum >= goUp.length) {
          spriteNum = 1;
        }
        spriteNum++;
        spriteCounter = 0;
      }
    }

    if(invincible) {
      invincibleCounter++;
      if(invincibleCounter >= FPS/3) {
        invincible = false;
        invincibleCounter = 0;
      }
    }
  }

  private void contactMonster(int monsterIndex) {
    if (monsterIndex != 999) {
      if(!invincible) {
        HP -= 1;
        invincible = true;
      }
    }
  }

  public void pickUpObject(int i) {

    if (i != 999) {

    }
  }

  public void collideNPC(int i) {

    if (i != 999) {
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

    if(invincible) {
      g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
    }

    g2D.drawImage(image, screenX, screenY, playerSizeX, playerSizeY, null);
    g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
  }

  public int getHP() {
    return HP;
  }

  public void setHP(int HP) {
    this.HP = HP;
  }
}


