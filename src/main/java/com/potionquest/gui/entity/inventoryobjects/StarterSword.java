package com.potionquest.gui.entity.inventoryobjects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class StarterSword extends InventoryItem {

  public StarterSword() {
    name = "Father's Sword";
    int objectHeight = 48;
    int objectWidth = 48;
    attack = 1;
    collisionOn = false;

    BufferedImage image;

    try (InputStream is = getClass().getResourceAsStream("/sword.png")) {

      //noinspection ConstantConditions
      image = ImageIO.read(is);

        var sword = image.getSubimage(0, 7 * objectHeight, objectWidth, objectHeight);

      portrait = sword;

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
