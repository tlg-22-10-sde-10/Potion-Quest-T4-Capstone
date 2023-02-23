package com.potionquest.gui.entity.inventoryobjects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class OrnateTrinket extends InventoryItem {


  public OrnateTrinket() {
    name = "Ornate Trinket";
    int objectHeight = 48;
    int objectWidth = 48;
    collisionOn = false;
    speed = 0;
    direction = "down";

    BufferedImage image;

    try (InputStream is = getClass().getResourceAsStream("/sword.png")) {

      //noinspection ConstantConditions
      image = ImageIO.read(is);

      var trinket = image.getSubimage(objectWidth * 11, objectHeight, objectWidth, objectHeight);

      portrait = trinket;

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
