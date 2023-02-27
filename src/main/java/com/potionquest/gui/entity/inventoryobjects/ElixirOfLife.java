package com.potionquest.gui.entity.inventoryobjects;

import com.potionquest.gui.gamecontrol.TileSheets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class ElixirOfLife extends InventoryItem {

  public ElixirOfLife() {
    name = "Elixir of Life";
    int objectHeight = 48;
    int objectWidth = 48;

    keyItem = true;

    portrait = TileSheets.gameItemsTileSheet.getSubimage(objectWidth* 12, objectHeight*4, objectWidth, objectHeight);
  }
}
