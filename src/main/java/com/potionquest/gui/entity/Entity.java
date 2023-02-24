package com.potionquest.gui.entity;


import com.potionquest.gui.gamecontrol.GamePanel;
import java.awt.AlphaComposite;
import com.potionquest.gui.entity.inventoryobjects.InventoryItem;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Entity {

  public int worldX, worldY;

  //SPRITE FRAME ARRAYS
  public BufferedImage[] goUp = new BufferedImage[4];
  public BufferedImage[] goDown = new BufferedImage[4];
  public BufferedImage[] goLeft = new BufferedImage[4];
  public BufferedImage[] goRight = new BufferedImage[4];

  public BufferedImage[] fightUp = new BufferedImage[4];
  public BufferedImage[] fightDown = new BufferedImage[4];
  public BufferedImage[] fightLeft = new BufferedImage[4];
  public BufferedImage[] fightRight = new BufferedImage[4];

  public BufferedImage portrait;

  //SPRITE MOVEMENT FIELDS
  public String name;

  public String direction;
  public int speed;
  public boolean collisionOn = false;
  public int actionTimeOut = 0;
  public int spriteCounter = 0;
  public int spriteNum = 1;

  //SPRITE ATTACK FIELDS
  public int spriteCounterAttack = 0;
  public int spriteNumAttack = 1;
  public boolean invincible = false;
  public int invincibleCounter = 0;
  public int entityType;
  public InventoryItem currentWeapon;
  public int attack;

  //SPRITE DIALOGUE FIELDS
  public String[] responses = new String[10];
  public String[] dialogues = new String[20];
  public boolean firstChat = true;
  public boolean npcKeyDialogueComplete = false;

  //Handle collision and attack
  public Rectangle solidArea = new Rectangle();
  public Rectangle attackArea = new Rectangle();

  public int solidAreaDefaultX;
  public int solidAreaDefaultY;

  public int HP;
  public int MAX_HP;
  //public int attack;
  public int entityID;

  public boolean displayHPBar = false;
  protected int displayHPFrameCount = 0;

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

  public void setBehavior() {
  }

  protected void handleDamageReaction() {

  }

  public void talk() {

    switch (GamePanel.player.direction) {
      case "up":
        this.direction = "down";
        break;
      case "down":
        this.direction = "up";
        break;
      case "left":
        this.direction = "right";
        break;
      case "right":
        this.direction = "left";
        break;
    }
  }

  public void update() {
    setBehavior();

    collisionOn = false;
    GamePanel.collider.checkTile(this);
    // add object collision detect here later
    GamePanel.collider.checkTargetsCollision(this);
    GamePanel.collider.checkEntity(this, GamePanel.npc);
    GamePanel.collider.checkEntity(this, GamePanel.monsters);

    // IF COLLISION IS FALSE, ENTITY CAN MOVE
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

    if (spriteCounter >= 12) {
      if (spriteNum >= goUp.length - 1) {
        spriteNum = 1;
      } else {
        spriteNum++;
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

      if (this.invincible) {
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
      }

      g2D.drawImage(image, screenX, screenY,null);
      g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
  }
}

