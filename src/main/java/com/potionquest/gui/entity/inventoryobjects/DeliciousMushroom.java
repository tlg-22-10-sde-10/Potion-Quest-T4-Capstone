package com.potionquest.gui.entity.inventoryobjects;

import com.potionquest.gui.gamecontrol.TileSheets;

public class DeliciousMushroom extends InventoryItem {

  public DeliciousMushroom() {
    name = "Delicious Mushroom";
    int objectHeight = 48;
    int objectWidth = 48;

    HP = 4;

    portrait = TileSheets.gameItemsTileSheet.getSubimage(objectWidth * 4, objectHeight * 13, objectWidth, objectHeight);
  }
}
