package com.potionquest.gui.entity.npc;

import com.potionquest.gui.entity.Entity;
import com.potionquest.gui.gamecontrol.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class NPC_Sister extends Entity {

  public NPC_Sister() {
    direction = "down";
    speed = 0;
    name = "Sister";

    solidArea = new Rectangle();
    solidArea.x = 0;
    solidArea.y = 0;
    solidArea.width = 48;
    solidArea.height = 60;

    getNPCImage();
  }

  public void getNPCImage() {

    BufferedImage npcImage = TileSheets.npcSisterTileSheet;

    int imageIndexX = 0;
    for (int i = 0; i < 3; i++) {
      BufferedImage up = npcImage.getSubimage(imageIndexX, 60, 48, 60);
      goUp[i] = up;
      imageIndexX += 48;
    }

    imageIndexX = 0;
    for (int i = 0; i < 3; i++) {
      BufferedImage down = npcImage.getSubimage(imageIndexX, 0, 48, 60);
      goDown[i] = down;
      imageIndexX += 48;
    }

    imageIndexX = 0;
    for (int i = 0; i < 3; i++) {
      BufferedImage left = npcImage.getSubimage(imageIndexX, 180, 48, 60);
      goLeft[i] = left;
      imageIndexX += 48;
    }

    imageIndexX = 0;
    for (int i = 0; i < 3; i++) {
      BufferedImage right = npcImage.getSubimage(imageIndexX, 120, 48, 60);
      goRight[i] = right;
      imageIndexX += 48;
    }
  }

  public void setDialogue() {

    // for loop to iterate over character's dialogues from JSON file
    dialogues[0] = null;
    responses[0] = null;
  }

  public void talk() {

    GamePanel.ui.currentDialogue = "Your sister is too weak to talk right now.";
    GamePanel.ui.dialogueArray = this.dialogues.clone();
    GamePanel.ui.responsesArray = this.responses.clone();

    if (firstChat) {

      switch (GamePanel.player.direction) {
        case "up":
          this.direction = "down";
          break;
        case "down":
          this.direction = "up";
          break;
        case "left":
          this.direction = "right";
          break;
        case "right":
          this.direction = "left";
          break;
      }

      firstChat = false;

    } else if (!npcKeyDialogueComplete) {
      this.dialogues = null;
      this.responses = null;
      GamePanel.ui.dialogueScreenState = 1;
      super.talk();
    }
  }

  @Override
  public void draw(Graphics2D g2D) {
    int screenX = worldX - GamePanel.player.worldX + GamePanel.player.screenX;
    int screenY = worldY - GamePanel.player.worldY + GamePanel.player.screenY;

    if (worldX + GamePanel.tileSize > GamePanel.player.worldX - GamePanel.player.screenX
        && worldX - GamePanel.tileSize < GamePanel.player.worldX + GamePanel.player.screenX
        && worldY + GamePanel.tileSize * 2 > GamePanel.player.worldY - GamePanel.player.screenY
        && worldY - GamePanel.tileSize * 2 < GamePanel.player.worldY + GamePanel.player.screenY) {

      BufferedImage image = goDown[0];
      g2D.drawImage(image, screenX, screenY, null);
    }
  }
}
