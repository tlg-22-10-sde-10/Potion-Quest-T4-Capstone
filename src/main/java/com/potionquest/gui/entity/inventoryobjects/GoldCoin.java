package com.potionquest.gui.entity.inventoryobjects;

import com.potionquest.gui.entity.Entity;
import com.potionquest.gui.gamecontrol.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.imageio.ImageIO;

public class GoldCoin extends Entity {

  private int qty = 0;
  private int col = 7;
  private int row = 3;

  public GoldCoin() {
    Random rd = new Random();
    setQty(rd.nextInt(4) + 1);

    try (InputStream is = getClass().getResourceAsStream("/sword.png")) {

      BufferedImage image = ImageIO.read(is);

      BufferedImage coin = image.getSubimage(GamePanel.tileSize*col, GamePanel.tileSize*row, GamePanel.tileSize, GamePanel.tileSize);

      itemPortrait = coin;

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void update() {
    collisionOn = false;

//    GamePanel.collider.checkTile(this);
//    GamePanel.collider.checkTargetsCollision(this);
//    GamePanel.collider.checkEntity(this, GamePanel.npc);
//    GamePanel.collider.checkEntity(this, GamePanel.monsters);
  }

  public void draw(Graphics2D g2D) {
    int screenX = worldX - GamePanel.player.worldX + GamePanel.player.screenX;
    int screenY = worldY - GamePanel.player.worldY + GamePanel.player.screenY;

    g2D.drawImage(itemPortrait, screenX, screenY,null);
  }


  public int getQty() {
    return qty;
  }

  public void setQty(int qty) {
    this.qty = qty;
  }
}
