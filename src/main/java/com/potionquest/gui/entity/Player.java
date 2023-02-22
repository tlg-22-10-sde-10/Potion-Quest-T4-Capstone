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

  private final int sizeX = 48;
  private final int sizeY = 96;
//  private final int scaleFactor = 3;

  private final int playerSizeX = sizeX;
  private final int playerSizeY = sizeY;

  public final int screenX;
  public final int screenY;

  private boolean isAttacking = false;

  public Player() {
    MAX_HP = 20;
    HP = MAX_HP;

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
    getPlayerAttackImage();
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

    try (InputStream inputStream = getClass().getResourceAsStream("/player/characterResized.png")) {
      //noinspection ConstantConditions
      BufferedImage playerImage = ImageIO.read(inputStream);
      
      int imageIndexX = 0;
      
     for (int i = 0; i < 4; i++) {
        BufferedImage up = playerImage.getSubimage(imageIndexX, 192, 48, 96);
        BufferedImage down = playerImage.getSubimage(imageIndexX, 0, 48, 96);
        BufferedImage left= playerImage.getSubimage(imageIndexX, 288, 48, 96);
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

  private void getPlayerAttackImage() {
    try (InputStream inputStream = getClass().getResourceAsStream("/player/fight.png")) {
      //noinspection ConstantConditions
      BufferedImage playerImage = ImageIO.read(inputStream);
      UtilityTool uTool = new UtilityTool();

      for (int i = 0; i < 4; i++) {
        BufferedImage up = playerImage.getSubimage(96*i, 96, 96, 96);
        BufferedImage down = playerImage.getSubimage(96*i, 0, 96, 96);
        BufferedImage  right= playerImage.getSubimage(96*i, 192, 96, 96);
        BufferedImage left = playerImage.getSubimage(96*i, 288, 96, 96);

        fightUp[i] = uTool.scaleImage(up, GamePanel.tileSize, GamePanel.tileSize);
        fightDown[i] = uTool.scaleImage(down, GamePanel.tileSize, GamePanel.tileSize);
        fightLeft[i] = uTool.scaleImage(left, GamePanel.tileSize, GamePanel.tileSize);
        fightRight[i] =uTool.scaleImage(right, GamePanel.tileSize, GamePanel.tileSize);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public void update() {
    if(isAttacking) {
      attacking();
    }

    if(keyH.spacePressed) {
      isAttacking = true;
    } else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed ) {
      if (keyH.upPressed) { direction = "up";}
      if (keyH.downPressed) { direction = "down";}
      if (keyH.leftPressed) { direction = "left";}
      if (keyH.rightPressed) {direction = "right";}
      
      //CHECK TILE COLLISION
      collisionOn = false;
      GamePanel.collider.checkTile(this);

      // CHECK NPC COLLISION
      int npcIndex = GamePanel.collider.checkEntity(this, GamePanel.npc);
      talkNPC(npcIndex);

      //CHECK MONSTER COLLISION
      int monsterIndex = GamePanel.collider.checkEntity(GamePanel.player, GamePanel.monsters);
      contactMonster(monsterIndex);

      //CHECK EVENT
      GamePanel.eHandler.checkEvent();

      // IF COLLISION IS FALSE and player not making attack move, PLAYER CAN MOVE
      if (!collisionOn && !isAttacking) {
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
        } else {
          spriteNum++;
        }
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

  private void attacking() {
    spriteCounterAttack++;
    System.out.println(spriteCounterAttack);
    if(spriteCounterAttack <= 8) {
      spriteNumAttack = 1;
    } else if(spriteCounterAttack <=16) {
      spriteNumAttack = 2;
    } else if(spriteCounterAttack <= 20){
      spriteNumAttack = 3;
    } else if(spriteCounterAttack <= 24) {
      spriteNumAttack =4;
    } else {
      spriteNumAttack = 1;
      spriteCounterAttack = 0;
      isAttacking = false;
    }
  }

  private void contactMonster(int monsterIndex) {
    if (monsterIndex != 999) {
      if(!invincible) {
        if(HP > 0) {
          HP -= 1;
        }
        invincible = true;
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
        if(isAttacking) {
          image = fightUp[spriteNumAttack-1];
        } else {
          image = goUp[spriteNum-1];
        }
        break;
      case "down":
        if(isAttacking) {
          image = fightDown[spriteNumAttack-1];
        } else {
          image = goDown[spriteNum-1];
        }
        break;
      case "left":
        if(isAttacking) {
          image = fightLeft[spriteNumAttack-1];
        } else {
          image = goLeft[spriteNum-1];
        }
        break;
      case "right":
        if(isAttacking) {
          image = fightRight[spriteNumAttack-1];
        } else {
          image = goRight[spriteNum-1];
        }
        break;
    }

    if(invincible) {
      g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
    }

    if(isAttacking) {
      g2D.drawImage(image, screenX, screenY, playerSizeX*2, playerSizeY, null);
    } else {
      g2D.drawImage(image, screenX, screenY, playerSizeX, playerSizeY, null);
    }

    g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
  }

  public int getHP() {
    return HP;
  }

  public void setHP(int HP) {
    this.HP = HP;
  }
}


