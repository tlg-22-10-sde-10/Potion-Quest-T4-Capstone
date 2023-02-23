package com.potionquest.gui.entity.inventoryobjects;

import com.potionquest.gui.items.SuperObjects;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class SwordOfAThousandTruths extends inventoryItem {

  public SwordOfAThousandTruths() {
    name = "Sword of a Thousand Truths";
    objectHeight = 48;
    objectWidth = 48;
    attackValue = 5;
    collisionOn = false;


    BufferedImage image;

    try (InputStream is = getClass().getResourceAsStream("/sword.png")) {

      //noinspection ConstantConditions
      image = ImageIO.read(is);

      var sword = image.getSubimage(objectWidth*12, objectHeight*7, objectWidth, objectHeight);

      images.add(sword);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
