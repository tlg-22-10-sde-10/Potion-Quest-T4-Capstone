package com.potionquest.gui.entity.inventoryobjects;

import com.potionquest.gui.entity.Entity;
import com.potionquest.gui.gamecontrol.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class InventoryItem extends Entity {
  protected int objectWidth;
  protected int objectHeight;
  public int qty = 0;

  public boolean keyItem = false;
  //public boolean collisionOn = false;
  //public List<BufferedImage> images = new ArrayList<>();

  public InventoryItem() {
    objectWidth = GamePanel.tileSize;
    objectHeight = GamePanel.tileSize;

    //solidAreaDefaultX = objectWidth;
    //solidAreaDefaultY = objectHeight;

    solidArea = new Rectangle(0,0,objectWidth,objectHeight);
  }

  public void update() {}

  public void draw(Graphics2D g2D) {
    int screenX = worldX - GamePanel.player.worldX + GamePanel.player.screenX;
    int screenY = worldY - GamePanel.player.worldY + GamePanel.player.screenY;

    g2D.drawImage(portrait, screenX, screenY,null);
  }
}
