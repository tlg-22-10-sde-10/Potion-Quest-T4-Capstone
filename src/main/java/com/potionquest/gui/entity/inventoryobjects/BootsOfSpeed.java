package com.potionquest.gui.entity.inventoryobjects;

import com.potionquest.gui.gamecontrol.TileSheets;

public class BootsOfSpeed extends InventoryItem {

  public BootsOfSpeed() {
    name = "Boots of Speed";
    int objectHeight = 48;
    int objectWidth = 48;

    speed = 2;

    portrait = TileSheets.gameItemsTileSheet.getSubimage(objectWidth * 8, objectHeight * 10, objectWidth, objectHeight);
  }
}
