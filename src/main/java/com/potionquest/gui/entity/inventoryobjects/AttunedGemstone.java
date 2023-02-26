package com.potionquest.gui.entity.inventoryobjects;

import com.potionquest.gui.gamecontrol.TileSheets;

public class AttunedGemstone extends InventoryItem {

  public AttunedGemstone() {
    name = "Attuned Gemstone";
    int objectHeight = 48;
    int objectWidth = 48;
    collisionOn = false;

    portrait = TileSheets.gameItemsTileSheet.getSubimage(objectWidth * 4, objectHeight * 3,
        objectWidth, objectHeight);

  }
}
