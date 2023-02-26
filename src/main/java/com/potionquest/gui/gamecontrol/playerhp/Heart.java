package com.potionquest.gui.gamecontrol.playerhp;

import com.potionquest.gui.gamecontrol.TileSheets;
import java.awt.image.BufferedImage;

public class Heart extends SuperObjects {

    public Heart() {
      name = "Heart";
      objectHeight = 48;
      objectWidth = 48;

      BufferedImage image = TileSheets.heartsTileSheet;

      var heart4 = image.getSubimage( 4 * objectWidth, 0, objectWidth, objectHeight);
      var heart3 = image.getSubimage( 5 * objectWidth, 0, objectWidth, objectHeight);
      var heart2 = image.getSubimage( 6 * objectWidth, 0, objectWidth, objectHeight);
      var heart1 = image.getSubimage( 7 * objectWidth, 0, objectWidth, objectHeight);
      var heart0 = image.getSubimage( 8 * objectWidth, 0, objectWidth, objectHeight);

      images.add(heart4);
      images.add(heart1);
      images.add(heart2);
      images.add(heart3);
      images.add(heart0);
    }
}
