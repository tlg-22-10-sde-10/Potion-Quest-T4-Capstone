package com.potionquest.gui.entity.inventoryobjects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class AttunedGemstone extends InventoryItem {

  public AttunedGemstone() {
    name = "Attuned Gemstone";
    int objectHeight = 48;
    int objectWidth = 48;
    collisionOn = false;

    BufferedImage image;

    try (InputStream is = getClass().getResourceAsStream("/sword.png")) {

      //noinspection ConstantConditions
      image = ImageIO.read(is);

      portrait = image.getSubimage(objectWidth* 4, objectHeight*3, objectWidth, objectHeight);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
