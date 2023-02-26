package com.potionquest.gui.entity.inventoryobjects;

import com.potionquest.gui.gamecontrol.TileSheets;

public class SwordOfAThousandTruths extends InventoryItem {

  public SwordOfAThousandTruths() {
    name = "Sword of a Thousand Truths";
    int objectHeight = 48;
    int objectWidth = 48;

    attack = 7;

    keyItem = true;

    portrait = TileSheets.gameItemsTileSheet.getSubimage(objectWidth*12, objectHeight*7, objectWidth, objectHeight);
  }
}
