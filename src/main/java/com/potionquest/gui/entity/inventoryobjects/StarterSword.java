package com.potionquest.gui.entity.inventoryobjects;

import com.potionquest.gui.items.SuperObjects;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class StarterSword extends inventoryItem {

  public StarterSword() {
    name = "Father's Sword";
    objectHeight = 48;
    objectWidth = 48;
    attackValue = 1;
    collisionOn = false;


    BufferedImage image;

    try (InputStream is = getClass().getResourceAsStream("/sword.png")) {

      //noinspection ConstantConditions
      image = ImageIO.read(is);

        var sword = image.getSubimage(0, 7 * objectHeight, objectWidth, objectHeight);

        images.add(sword);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
