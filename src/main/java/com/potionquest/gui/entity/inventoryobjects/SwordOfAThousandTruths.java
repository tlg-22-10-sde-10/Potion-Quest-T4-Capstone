package com.potionquest.gui.entity.inventoryobjects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class SwordOfAThousandTruths extends InventoryItem {

  public SwordOfAThousandTruths() {
    name = "Sword of a Thousand Truths";
    int objectHeight = 48;
    int objectWidth = 48;
    attack = 5;
    collisionOn = false;
    keyItem = true;

    BufferedImage image;

    try (InputStream is = getClass().getResourceAsStream("/sword.png")) {

      //noinspection ConstantConditions
      image = ImageIO.read(is);

      portrait = image.getSubimage(objectWidth*12, objectHeight*7, objectWidth, objectHeight);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
