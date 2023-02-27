package com.potionquest.gui.entity.inventoryobjects;

import com.potionquest.gui.gamecontrol.TileSheets;

public class OrnateTrinket extends InventoryItem {

  public OrnateTrinket() {
    name = "Ornate Trinket";
    int objectHeight = 48;
    int objectWidth = 48;

    portrait = TileSheets.gameItemsTileSheet.getSubimage(objectWidth * 11, objectHeight, objectWidth, objectHeight);


  }
}
