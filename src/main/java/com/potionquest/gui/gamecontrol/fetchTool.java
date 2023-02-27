package com.potionquest.gui.gamecontrol;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class fetchTool {
  public static BufferedImage fetchImage(String path) {
    try(InputStream inputStream = fetchTool.class.getResourceAsStream(path)) {
      assert inputStream != null;
      return ImageIO.read(inputStream);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
