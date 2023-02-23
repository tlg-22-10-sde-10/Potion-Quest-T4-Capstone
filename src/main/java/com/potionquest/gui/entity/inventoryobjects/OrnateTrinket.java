package com.potionquest.gui.entity.inventoryobjects;

import com.potionquest.gui.gamecontrol.GamePanel;
import com.potionquest.gui.items.SuperObjects;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class OrnateTrinket extends inventoryItem {

  public BufferedImage defaultImage;

  public OrnateTrinket() {
    name = "Ornate Trinket";
    objectHeight = 48;
    objectWidth = 48;
    collisionOn = false;
    speed = 0;
    direction = "down";

    BufferedImage image;

    try (InputStream is = getClass().getResourceAsStream("/sword.png")) {

      //noinspection ConstantConditions
      image = ImageIO.read(is);

      var trinket = image.getSubimage(objectWidth * 11, objectHeight, objectWidth, objectHeight);

      defaultImage = trinket;
      images.add(trinket);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void draw(Graphics2D g2D) {

//    BufferedImage image = null;

    int screenX = worldX - GamePanel.player.worldX + GamePanel.player.screenX;
    int screenY = worldY - GamePanel.player.worldY + GamePanel.player.screenY;

    g2D.drawImage(defaultImage, screenX, screenY, null);
  }

}
