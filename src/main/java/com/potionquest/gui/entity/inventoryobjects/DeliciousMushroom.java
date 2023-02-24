package com.potionquest.gui.entity.inventoryobjects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class DeliciousMushroom extends InventoryItem {

  public DeliciousMushroom() {
    name = "Delicious Mushroom";
    int objectHeight = 48;
    int objectWidth = 48;
    collisionOn = false;
    speed = 0;
    direction = "down";

    BufferedImage image;

    try (InputStream is = getClass().getResourceAsStream("/sword.png")) {

      //noinspection ConstantConditions
      image = ImageIO.read(is);

      portrait = image.getSubimage(objectWidth * 4, objectHeight * 13, objectWidth, objectHeight);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
