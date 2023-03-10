package com.potionquest.gui.gamecontrol;

import com.potionquest.gui.entity.inventoryobjects.AttunedGemstone;
import com.potionquest.gui.entity.inventoryobjects.InventoryItem;
import java.awt.Rectangle;

public class EventHandler {

  Rectangle eventRectangle;
  int eventRectangleDefaultX, eventRectangleDefaultY;

  public EventHandler() {

    eventRectangle = new Rectangle();
    eventRectangle.x = 23;
    eventRectangle.y = 23;
    eventRectangle.width = 2;
    eventRectangle.height = 2;
    eventRectangleDefaultX = eventRectangle.x;
    eventRectangleDefaultY = eventRectangle.y;
  }

  public void checkEvent() {

    if (hit(51, 69, "right")) {
      for (InventoryItem item : GamePanel.player.inventory) {
        if (item.name.equals("Attuned Gemstone")) {
          teleport1(GamePanel.playState);
        }
      }
    }
    if (hit(59, 69, "left")) {
      for (InventoryItem item : GamePanel.player.inventory) {
        if (item.name.equals("Attuned Gemstone")) {
          teleport2((GamePanel.playState));
        }
      }
    }
  }

  public boolean hit(int eventCol, int eventRow, String reqDirection) {

    boolean hit = false;

    GamePanel.player.solidArea.x = GamePanel.player.worldX + GamePanel.player.solidArea.x;
    GamePanel.player.solidArea.y = GamePanel.player.worldY + GamePanel.player.solidArea.y;
    eventRectangle.x = eventCol * GamePanel.tileSize + eventRectangle.x;
    eventRectangle.y = eventRow * GamePanel.tileSize + eventRectangle.y;

    if (GamePanel.player.solidArea.intersects(eventRectangle)) {
      if (GamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals(
          "any")) {
        hit = true;
      }
    }

    GamePanel.player.solidArea.x = GamePanel.player.solidAreaDefaultX;
    GamePanel.player.solidArea.y = GamePanel.player.solidAreaDefaultY;
    eventRectangle.x = eventRectangleDefaultX;
    eventRectangle.y = eventRectangleDefaultY;

    return hit;
  }

  public void teleport1(int gameState) {

    GamePanel.gameState = gameState;
    GamePanel.ui.currentDialogue = "Teleport!";
    GamePanel.ui.dialogueScreenState = 0;
    GamePanel.player.worldX = GamePanel.tileSize * 59;
    GamePanel.player.worldY = GamePanel.tileSize * 69;
  }

  public void teleport2(int gameState) {

    GamePanel.gameState = gameState;
    GamePanel.ui.currentDialogue = "Teleport!";
    GamePanel.ui.dialogueScreenState = 0;
    GamePanel.player.worldX = GamePanel.tileSize * 51;
    GamePanel.player.worldY = GamePanel.tileSize * 69;
  }
}
