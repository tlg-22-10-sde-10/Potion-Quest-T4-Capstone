package com.potionquest.gui.entity.inventoryobjects;

import com.potionquest.gui.items.SuperObjects;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class ElixirOfLife extends inventoryItem {

  public ElixirOfLife() {
    name = "Salve of Life";
    objectHeight = 48;
    objectWidth = 48;
    collisionOn = false;

    BufferedImage image;

    try (InputStream is = getClass().getResourceAsStream("/sword.png")) {

      //noinspection ConstantConditions
      image = ImageIO.read(is);

      var potion = image.getSubimage(objectWidth* 12, objectHeight*4, objectWidth, objectHeight);

      images.add(potion);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
