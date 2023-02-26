package com.potionquest.gui.entity;

import static com.potionquest.gui.gamecontrol.GamePanel.FPS;


import static com.potionquest.gui.gamecontrol.GamePanel.dialogueState;
import static com.potionquest.gui.gamecontrol.GamePanel.items;

import static com.potionquest.gui.gamecontrol.GamePanel.keyH;

import com.potionquest.gui.entity.inventoryobjects.GoldCoin;
import com.potionquest.gui.entity.inventoryobjects.InventoryItem;
import com.potionquest.gui.gamecontrol.*;
import com.potionquest.gui.entity.inventoryobjects.StarterSword;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Player extends Entity {

  private final int sizeX = 48;
  private final int sizeY = 96;

  private final int playerSizeX = sizeX;
  private final int playerSizeY = sizeY;

  public final int screenX;
  public final int screenY;

  private boolean isAttacking = false;

  //inventory
  public List<InventoryItem> inventory = new ArrayList<>();
  public static final int INVENTORY_SIZE = 5;

  public int inventoryFrameCount = 0;

  public InventoryItem currentWeapon;
  public InventoryItem coin = new GoldCoin();
  public int coinInPocket = 0;

  public boolean haveTalkedToOnceAlready;
  public int npcIndex;

  public Player() {
    MAX_HP = 20;
    HP = MAX_HP;

    attack = 4;

    screenX = GamePanel.screenWidth / 2 - (playerSizeX / 2);
    screenY = GamePanel.screenHeight / 2 - (playerSizeY / 2);

    solidArea = new Rectangle();
    solidArea.x = 8;
    solidArea.y = 48;

    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.width = 32;
    solidArea.height = 32;

    attackArea.width = 36;
    attackArea.height = 36;

    setDefaultValues();
    getPlayerImage();
    getPlayerAttackImage();

    setItems();
  }

  public void setDefaultValues() {

    // start game spawn point
    worldX = GamePanel.tileSize * 4;
    worldY = GamePanel.tileSize * 38;
    // near teleporter at river
//    worldX = GamePanel.tileSize * 48;
//    worldY = GamePanel.tileSize * 70;
    // near hermit
//    worldX = GamePanel.tileSize * 77;
//    worldY = GamePanel.tileSize * 77;
    speed = 4;
    direction = "down";
  }

  private void setItems() {
    this.currentWeapon = new StarterSword();
    inventory.add(currentWeapon);
    attack += currentWeapon.attack;
  }

  public void getPlayerImage() {
    BufferedImage playerImage = TileSheets.playerCharacterResizedTileSheet;

    int imageIndexX = 48;

    for (int i = 0; i < 4; i++) {
      BufferedImage up = playerImage.getSubimage(imageIndexX * i, 192, 48, 96);
      BufferedImage down = playerImage.getSubimage(imageIndexX * i, 0, 48, 96);
      BufferedImage left = playerImage.getSubimage(imageIndexX * i, 288, 48, 96);
      BufferedImage right = playerImage.getSubimage(imageIndexX * i, 96, 48, 96);

      goUp[i] = up;
      goDown[i] = down;
      goLeft[i] = left;
      goRight[i] = right;
    }
  }

  private void getPlayerAttackImage() {
    BufferedImage playerImage = TileSheets.playerFightTileSheet;

    int imageIndexX = 96;

    for (int i = 0; i < 4; i++) {
      BufferedImage up = playerImage.getSubimage(imageIndexX * i, 96, 96, 96);
      BufferedImage down = playerImage.getSubimage(imageIndexX * i, 0, 96, 96);
      BufferedImage right = playerImage.getSubimage(imageIndexX * i, 192, 96, 96);
      BufferedImage left = playerImage.getSubimage(imageIndexX * i, 288, 96, 96);

      fightUp[i] = up;
      fightDown[i] = down;
      fightLeft[i] = left;
      fightRight[i] = right;
    }
  }

  public void update() {

    if (isAttacking) {
      attacking();
    } else {
      if (keyH.spacePressed) {
        isAttacking = true;
      } else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
        if (keyH.upPressed) {
          direction = "up";
        }
        if (keyH.downPressed) {
          direction = "down";
        }
        if (keyH.leftPressed) {
          direction = "left";
        }
        if (keyH.rightPressed) {
          direction = "right";
        }

        //CHECK TILE COLLISION
        collisionOn = false;
        GamePanel.collider.checkTile(this);



        // CHECK NPC COLLISION
        npcIndex = GamePanel.collider.checkEntity(this, GamePanel.npc);
        talkNPC(npcIndex);

        if (npcIndex != 999) {
          GamePanel.ui.keyDialogueComplete = false;
        }

        //CHECK MONSTER COLLISION
        int monsterIndex = GamePanel.collider.checkEntity(GamePanel.player, GamePanel.monsters);
        contactMonster(monsterIndex);

        //check items collision
        int itemIndex = GamePanel.collider.checkObject(this, true);
        pickUpObject(itemIndex);

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
              if (worldY + speed + playerSizeY <= GamePanel.maxWorldRow * GamePanel.tileSize) {
                worldY += speed;
              }
              break;
            case "left":
              if (worldX - speed >= 0) {
                worldX -= speed;
              }
              break;
            case "right":
              if (worldX + speed + playerSizeX <= GamePanel.maxWorldCol * GamePanel.tileSize) {
                worldX += speed;
              }
              break;
          }
        }

        spriteCounter++;

        if (spriteCounter >= FPS / 5) {
          if (spriteNum >= goUp.length) {
            spriteNum = 1;
          } else {
            spriteNum++;
          }
          spriteCounter = 0;
        }
      }

      if (invincible) {
        invincibleCounter++;
        if (invincibleCounter >= FPS / 2) {
          invincible = false;
          invincibleCounter = 0;
        }
      }

      if (HP > MAX_HP) {
        HP = MAX_HP;
      }

      inventoryFrameCount++;

      if(inventoryFrameCount >= FPS * 4 ) {
        inventoryFrameCount = 0;
        UI.statement = "";
      }
    }
  }

  private void attacking() {
    spriteCounterAttack++;
    if (spriteCounterAttack <= 8) {
      spriteNumAttack = 1;
    } else if (spriteCounterAttack <= 16) {

      spriteNumAttack = 2;
      hitMonster();

    } else if (spriteCounterAttack <= 20) {

      spriteNumAttack = 3;
      hitMonster();

    } else if (spriteCounterAttack <= 24) {
      spriteNumAttack = 4;
    } else {
      spriteNumAttack = 1;
      spriteCounterAttack = 0;
      isAttacking = false;
    }
  }

  private void hitMonster() {
    int currentWorldX = worldX;
    int currentWorldY = worldY;
    int solidAreaWidth = solidArea.width;
    int solidAreaHeight = solidArea.height;

    switch (direction) {
      case "up":
        worldY -= attackArea.height;
        break;
      case "down":
        worldY += attackArea.height;
        break;
      case "left":
        worldX -= attackArea.width;
        break;
      case "right":
        worldX += attackArea.width;
        break;
    }

    solidArea.width = attackArea.width;
    solidArea.height = attackArea.height;

    int monsterIndex = GamePanel.collider.checkEntity(this, GamePanel.monsters);
    damageMonster(monsterIndex);

    worldX = currentWorldX;
    worldY = currentWorldY;
    solidArea.width = solidAreaWidth;
    solidArea.height = solidAreaHeight;
  }

  private void damageMonster(int monsterIndex) {
    if (monsterIndex != 999 && !GamePanel.monsters[monsterIndex].invincible) {
      GamePanel.monsters[monsterIndex].invincible = true;
      GamePanel.monsters[monsterIndex].handleDamageReaction();
      GamePanel.monsters[monsterIndex].speed = 8;
      GamePanel.monsters[monsterIndex].HP -= this.attack;
      GamePanel.monsters[monsterIndex].displayHPBar = true;
      GamePanel.monsters[monsterIndex].displayHPFrameCount = 0;
    }
  }

  private void contactMonster(int monsterIndex) {
    if (monsterIndex != 999 && GamePanel.monsters[monsterIndex].HP > 0) {
      if (!invincible) {
        if (HP > 0) {
          HP -= GamePanel.monsters[monsterIndex].attack;
        }
        invincible = true;
      }
    }
  }

  public void pickUpObject(int i) {
    if (i != 999) {
      if (pickUpObject(GamePanel.items[i])) {
        GamePanel.items[i] = null;
      }
    }
  }

  public boolean pickUpObject(InventoryItem item) {
    boolean pickUpSuccess = true;
    if(item.name.equals("Gold Coin")) {
      coinInPocket += item.qty;
    } else {
      if(inventory.size() < INVENTORY_SIZE) {
        inventory.add(item);

        GamePanel.player.attack += item.attack;
        GamePanel.player.speed += item.speed;

      } else {
        UI.statement = "Inventory Full";
        inventoryFrameCount = 0;
        pickUpSuccess = false;
      }
    }
    return pickUpSuccess;
  }

  public void dropItem(InventoryItem item) {
    if(item.keyItem) {
      UI.statement = "Cannot Throw That Away";
      inventoryFrameCount = 0;
    } else {
      forceRemoveItem(item);
    }
  }

  public void forceRemoveItem(InventoryItem item) {
    inventory.remove(item);
    GamePanel.player.speed -= item.speed;
    GamePanel.player.attack -= item.attack;
  }

  public void useItem(InventoryItem item) {
    if(item.HP > 0 && HP < MAX_HP) {
      inventory.remove(item);
      GamePanel.player.HP += item.HP;
      GamePanel.player.HP = Math.min(GamePanel.player.HP, MAX_HP);
    } else {
      inventoryFrameCount = 0;
      if(item.HP <= 0) {
        UI.statement = "Cannot Use That";
      } else {
        UI.statement = "Too Full To Eat That";
      }
    }
  }

  public void talkNPC(int i) {

    if (i != 999) {
      if (keyH.zPressed) {
        System.out.println(GamePanel.npc[i].name);
        if (GamePanel.npc[i].name.equals("Old Hermit")
            && this.inventory.stream().anyMatch(o -> o.name.equals("Sword of a Thousand Truths"))) {
          GamePanel.npc[i].npcKeyDialogueComplete = true;
        }
        if (GamePanel.npc[i].name.equals("Sister") && !GamePanel.npc[i].firstChat) {
          GamePanel.npc[i].npcKeyDialogueComplete = true;
        }
        if (GamePanel.npc[i].name.equals("Potion Seller")) {
          for (InventoryItem item : GamePanel.player.inventory) {
            if (item.name.equals("Elixir of Life")) {
              GamePanel.npc[i].npcKeyDialogueComplete = true;
              break;
            }
          }
        }
        if (GamePanel.npc[i].name.equals("Potion Seller's Brother")) {
          for (InventoryItem item : GamePanel.player.inventory) {
            if (item.name.equals("Attuned Gemstone")) {
              GamePanel.npc[i].npcKeyDialogueComplete = true;
              break;
            }
          }
        }
        if (!GamePanel.npc[i].npcKeyDialogueComplete) {
          GamePanel.gameState = GamePanel.dialogueState;
          haveTalkedToOnceAlready = chatCheck(GamePanel.npc[i]);
          GamePanel.ui.commandNum = 0;
          GamePanel.npc[i].talk();
        }
      }
    }
    keyH.zPressed = false;
  }

  public boolean chatCheck(Entity entity) {
    return !entity.firstChat;
  }

  public void draw(Graphics2D g2D) {
    BufferedImage image = null;

    int xAmends = 0;
    int yAmends = 0;

    switch (direction) {
      case "up":
        if (isAttacking) {
          xAmends -= GamePanel.tileSize / 2;
          image = fightUp[spriteNumAttack - 1];
        } else {
          image = goUp[spriteNum - 1];
        }
        break;
      case "down":
        if (isAttacking) {
          xAmends -= GamePanel.tileSize / 3 * 2;
          yAmends += GamePanel.tileSize / 3;
          image = fightDown[spriteNumAttack - 1];
        } else {
          image = goDown[spriteNum - 1];
        }
        break;
      case "left":
        if (isAttacking) {
          xAmends -= GamePanel.tileSize / 4 * 3;
          yAmends += GamePanel.tileSize / 3;
          image = fightLeft[spriteNumAttack - 1];
        } else {
          image = goLeft[spriteNum - 1];
        }
        break;
      case "right":
        if (isAttacking) {
          xAmends -= GamePanel.tileSize / 4;
          yAmends += GamePanel.tileSize / 6;
          image = fightRight[spriteNumAttack - 1];
        } else {
          image = goRight[spriteNum - 1];
        }
        break;
    }

    if (invincible) {
      g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
    }

    if (isAttacking) {
      g2D.drawImage(image, screenX + xAmends, screenY + yAmends, null);
    } else {
      g2D.drawImage(image, screenX, screenY, null);
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


