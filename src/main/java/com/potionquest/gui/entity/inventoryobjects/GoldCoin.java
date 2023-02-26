package com.potionquest.gui.entity.inventoryobjects;

import com.potionquest.gui.gamecontrol.GamePanel;
import com.potionquest.gui.gamecontrol.TileSheets;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;


public class GoldCoin extends InventoryItem {
  private int frameCount = 0;

  private final BufferedImage[] coins = new BufferedImage[4];

  public GoldCoin() {
    Random rd = new Random();
    qty = rd.nextInt(4) + 1;

    name = "Gold Coin";

    collisionOn = true;

    int row = 4;

    for(int i=0; i< coins.length; i++) {
      coins[i] = TileSheets.heartsTileSheet.getSubimage(GamePanel.tileSize*i, GamePanel.tileSize* row, GamePanel.tileSize, GamePanel.tileSize);
    }

    portrait = coins[0];
  }

  public void update() {
    collisionOn = false;

    if(frameCount < 60) {
      frameCount++;
    } else {
      frameCount = 0;
    }
//    GamePanel.collider.checkTile(this);
//    GamePanel.collider.checkTargetsCollision(this);
//    GamePanel.collider.checkEntity(this, GamePanel.npc);
//    GamePanel.collider.checkEntity(this, GamePanel.monsters);
  }

  public void draw(Graphics2D g2D) {
    int screenX = worldX - GamePanel.player.worldX + GamePanel.player.screenX;
    int screenY = worldY - GamePanel.player.worldY + GamePanel.player.screenY;

    g2D.drawImage(coins[frameCount/15 % 4], screenX, screenY,null);
  }

}
