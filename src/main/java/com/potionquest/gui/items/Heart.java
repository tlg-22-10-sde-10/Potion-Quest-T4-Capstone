package com.potionquest.gui.items;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Heart extends SuperObjects {

    public Heart() {
      name = "Heart";
      objectHeight = 16;
      objectWidth = 16;

      BufferedImage image;

      try {
        image = ImageIO.read(getClass().getResourceAsStream("/Heart.png"));

        var heart4 = image.getSubimage( 4 *objectWidth, 0*objectHeight, objectWidth, objectHeight);
        var heart3 = image.getSubimage( 5 *objectWidth, 0*objectHeight, objectWidth, objectHeight);
        var heart2 = image.getSubimage( 6 *objectWidth, 0*objectHeight, objectWidth, objectHeight);
        var heart1 = image.getSubimage( 7 *objectWidth, 0*objectHeight, objectWidth, objectHeight);
        var heart0 = image.getSubimage( 8 *objectWidth, 0*objectHeight, objectWidth, objectHeight);

        images.add(heart4);
        images.add(heart3);
        images.add(heart2);
        images.add(heart1);
        images.add(heart0);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
}
