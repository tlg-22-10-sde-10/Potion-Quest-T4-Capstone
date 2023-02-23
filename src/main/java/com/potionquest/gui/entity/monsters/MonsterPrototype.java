package com.potionquest.gui.entity.monsters;

import static com.potionquest.gui.gamecontrol.GamePanel.FPS;
import static com.potionquest.gui.gamecontrol.GamePanel.items;

import com.potionquest.gui.entity.Entity;
import com.potionquest.gui.entity.inventoryobjects.GoldCoin;
import com.potionquest.gui.entity.inventoryobjects.InventoryItem;
import com.potionquest.gui.gamecontrol.GamePanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.imageio.ImageIO;

public abstract class MonsterPrototype extends Entity {
  protected String name;
  protected int monsterSizeX;
  protected int monsterSizeY;

  protected int deathAnimationFrameCount = 0;

  public MonsterPrototype() {}

  public MonsterPrototype(String name, int maxLife, int monsterSizeX, int monsterSizeY,  int speed) {
    this.name = name;
    super.MAX_HP = maxLife;
    super.HP = maxLife;

    this.monsterSizeX = monsterSizeX;
    this.monsterSizeY = monsterSizeY;
    super.speed = speed;
  }

  public String getName() {
    return name;
  }

  protected void setDefaultStatus() {
    super.entityType = 2;

    super.solidArea.x = 0;
    super.solidArea.y = 0;
    super.solidArea.width = 48;
    super.solidArea.height = 48;

    super.solidAreaDefaultX = solidArea.x;
    super.solidAreaDefaultY = solidArea.y;

    super.direction = "down";
  }

  public void getImage(String path, int x, int y, int row) {
    try(InputStream inputStream = getClass().getResourceAsStream(path)) {
      assert inputStream != null;
      BufferedImage image = ImageIO.read(inputStream);

      AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
      tx.translate(-monsterSizeX, 0);
      AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

      var up0 =  image.getSubimage(x*1 ,row * y, x, y);
      var right0 = image.getSubimage( x*2,row * y, x, y);
      var down0 = image.getSubimage(0,row * y, x, y);
      var left0 =  op.filter(right0, null);

      goUp[0] = up0;
      goRight[0] = right0;
      goDown[0] = down0;
      goLeft[0] = left0;

      var up1 =  image.getSubimage(x*6 ,row * y, x, y);
      var right1 = image.getSubimage( x*8,row * y, x, y);
      var down1 = image.getSubimage(x*4,row * y, x, y);
      var left1 = op.filter(right1, null);

      goUp[1] = up1;
      goRight[1] = right1;
      goDown[1] = down1;
      goLeft[1] = left1;

      var up2 =  image.getSubimage(x*7 ,row * y, x, y);
      var right2 = image.getSubimage( x*9,row * y, x, y);
      var down2 = image.getSubimage(x*5,row * y, x, y);
      var left2 = op.filter(right2, null);

      goUp[2] = up2;
      goRight[2] = right2;
      goDown[2] = down2;
      goLeft[2] = left2;

      var up3 =  image.getSubimage(x*1 ,row * y, x, y);
      var right3 = image.getSubimage( x*2,row * y, x, y);
      var down3 = image.getSubimage(0,row * y, x, y);
      var left3 = op.filter(right3, null);

      goUp[3] = up3;
      goRight[3] = right3;
      goDown[3] = down3;
      goLeft[3] = left3;

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void update() {
    if(HP>0) {
      handleEnemyBehaviour();
      // IF COLLISION IS FALSE, PLAYER CAN MOVE
      handleMonsterCollision();

      handleMonsterWalkAnimation();

      handleMonsterTouchPlayer();

      handleMonsterBeingHit();

      handleMonsterHeal();
    }
  }

  protected void handleDamageReaction() {

  }

  protected void handleEnemyBehaviour() {
    actionTimeOut++;

    if(actionTimeOut == FPS) {
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

  protected void handleMonsterCollision() {
    collisionOn = false;
    GamePanel.collider.checkTile(this);
    // add object collision detect here later
    GamePanel.collider.checkTargetsCollision(this);

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
  }

  protected void handleMonsterWalkAnimation() {
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

  protected void handleMonsterTouchPlayer() {
    boolean contactPlayer = GamePanel.collider.checkTargetsCollision(this);
    if (this.entityType == 2 && contactPlayer) {
      if (!GamePanel.player.invincible) {
        GamePanel.player.setHP(GamePanel.player.getHP() - 1);
        GamePanel.player.invincible = true;
      }
    }
  }

  protected void handleMonsterBeingHit() {
    if (this.invincible) {
      invincibleCounter++;
      if (invincibleCounter >= FPS/2) {
        this.invincible = false;
        invincibleCounter = 0;
      }
    }
  }

  protected void handleMonsterHeal() {
    if(HP > MAX_HP) {
      HP = MAX_HP;
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

      if(HP <= 0) {
        //drawDeathAnimation(g2D);
        deathAnimationFrameCount++;
        if(deathAnimationFrameCount >= FPS) {
          enemyDie();
          dropCoin();
        }
      } else {
        if(displayHPBar) {
          drawMonsterHPBar(g2D, screenX, screenY);
          if(displayHPFrameCount++ > FPS * 8){
            displayHPFrameCount=0;
            displayHPBar = false;
          }
        }
      }

      g2D.drawImage(image, screenX, screenY, null);
    }
  }

  protected void drawAnimation(Graphics2D g2D, float f) {
    g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, f));
  }

  protected void drawDeathAnimation(Graphics2D g2D){
    deathAnimationFrameCount++;
    if((deathAnimationFrameCount/10)%2==0){
      drawAnimation(g2D, 0f);
    } else {
      drawAnimation(g2D, 1f);
    }
  }

  protected void drawMonsterHPBar(Graphics2D g2D, int screenX, int screenY) {
    //set background
    g2D.setColor(new Color(45,45,45));
    g2D.fillRect(screenX-2, screenY-17, GamePanel.tileSize + 4, 14);

    Color healthy = new Color(30,210,30);
    Color wounded = new Color(210,150,30);
    Color danger = new Color(210, 30, 30);

    Color hpBar;

    if(HP * 3 > 2 * MAX_HP) {
      hpBar = healthy;
    } else if ( HP *3 > MAX_HP) {
      hpBar = wounded;
    } else {
      hpBar = danger;
    }

    g2D.setColor(hpBar);
    g2D.fillRect(screenX, screenY-15, GamePanel.tileSize * HP/MAX_HP, 10);
  }

  protected void enemyDie() {
    deathAnimationFrameCount =0;
    GamePanel.monsters[this.entityID] = null;
  }

  protected void dropCoin() {
    InventoryItem coin = new GoldCoin();
    coin.worldX = worldX;
    coin.worldY = worldY;
    for(int i=0; i< items.length; i++) {
      if(items[i]==null) {
        items[i] = coin;
        break;
      }
    }
  }
}
