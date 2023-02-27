package com.potionquest.gui.entity.inventoryobjects;

import com.potionquest.gui.gamecontrol.TileSheets;

public class StarterSword extends InventoryItem {

  public StarterSword() {
    name = "Father's Sword";
    int objectHeight = 48;
    int objectWidth = 48;
    attack = 1;
    keyItem = true;

    portrait = TileSheets.gameItemsTileSheet.getSubimage(0, 7 * objectHeight, objectWidth, objectHeight);
  }
}
