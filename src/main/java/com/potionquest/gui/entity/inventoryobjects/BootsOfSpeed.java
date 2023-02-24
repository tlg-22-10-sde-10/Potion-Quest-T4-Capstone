package com.potionquest.gui.entity.inventoryobjects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class BootsOfSpeed extends InventoryItem {

  public BootsOfSpeed() {
    name = "Boots of Speed";
    int objectHeight = 48;
    int objectWidth = 48;
    collisionOn = false;
    speed = 0;
    direction = "down";

    BufferedImage image;

    try (InputStream is = getClass().getResourceAsStream("/sword.png")) {

      //noinspection ConstantConditions
      image = ImageIO.read(is);

      portrait = image.getSubimage(objectWidth * 8, objectHeight * 10, objectWidth, objectHeight);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
