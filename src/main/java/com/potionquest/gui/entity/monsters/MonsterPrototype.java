package com.potionquest.gui.entity.monsters;

import com.potionquest.gui.entity.Entity;
import com.potionquest.gui.gamecontrol.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.imageio.ImageIO;

public class MonsterPrototype extends Entity {
  private final String name;

  private final int monsterSizeX;
  private final int monsterSizeY;

  public MonsterPrototype() {
    this.name = "vampire";
    super.MAX_HP = 10;
    super.HP = super.MAX_HP;

    monsterSizeX = 24;
    monsterSizeY = 32;

    super.speed = 1;

    setDefaultStatus();

    getImage();
  }

  public MonsterPrototype(String name, int maxLife, int monsterSizeX, int monsterSizeY,  int speed) {
    this.name = name;
    super.MAX_HP = maxLife;
    super.HP = maxLife;

    this.monsterSizeX = monsterSizeX;
    this.monsterSizeY = monsterSizeY;
    super.speed = speed;

    setDefaultStatus();

    getImage();
  }

  public String getName() {
    return name;
  }

  private void setDefaultStatus() {
    super.entityType = 2;

    super.solidArea.x = 8;
    super.solidArea.y = 12;
    super.solidArea.width = 32;
    super.solidArea.height = 36;

    super.solidAreaDefaultX = solidArea.x;
    super.solidAreaDefaultY = solidArea.y;

    super.direction = "down";
  }

  public void getImage() {
    try(InputStream inputStream = getClass().getResourceAsStream("/24x32/vampire-f-001.png")) {
      if(inputStream!=null) {
        BufferedImage image = ImageIO.read(inputStream);

        for(int i = 0; i< goUp.length; i++) {
          int periodic = (i%2) * (int) Math.pow(-1, (i+1)/2.0) + 1;

          var up =  image.getSubimage(periodic * 24,0, monsterSizeX, monsterSizeY);
          var right = image.getSubimage(periodic * 24,32, monsterSizeX, monsterSizeY);
          var down = image.getSubimage(periodic * 24,64, monsterSizeX, monsterSizeY);
          var left = image.getSubimage(periodic * 24,96, monsterSizeX, monsterSizeY);

          goUp[i] = up;
          goRight[i] = right;
          goDown[i] = down;
          goLeft[i] = left;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setAction() {
    actionTimeOut++;

    if(actionTimeOut == 60) {
      Random random = new Random();
      int i = random.nextInt(100) + 1;

      if(i <= 25) {
        direction = "up";
      } else if(i <= 50) {
        direction = "down";
      } else if (i<=75) {
        direction = "left";
      } else {
        direction = "right";
      }

      actionTimeOut = 0;
    }
  }

  public void update() {
    setAction();

    collisionOn = false;
    GamePanel.collider.checkTile(this);
    // add object collision detect here later
    GamePanel.collider.checkTargetsCollision(this);

    // IF COLLISION IS FALSE, PLAYER CAN MOVE
    if (!collisionOn) {
      switch (direction) {
        case "up":
          if(worldY - speed >= 0) {
            worldY -= speed;
          }
          break;
        case "down":
          if(worldY + speed <= GamePanel.maxWorldRow * GamePanel.tileSize) {
            worldY += speed;
          }
          break;
        case "left":
          if(worldX - speed >= 0) {
            worldX -= speed;
          }
          break;
        case "right":
          if(worldX + speed <= GamePanel.maxWorldCol * GamePanel.tileSize) {
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
    }

    g2D.drawImage(image, screenX, screenY, monsterSizeX*2, monsterSizeY*2,null);
  }


}
