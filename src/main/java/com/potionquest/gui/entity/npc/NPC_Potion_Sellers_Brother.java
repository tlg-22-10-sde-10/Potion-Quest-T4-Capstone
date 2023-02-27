package com.potionquest.gui.entity.npc;

import com.potionquest.gui.entity.Entity;
import com.potionquest.game.Characters;
import com.potionquest.gui.gamecontrol.GamePanel;
import com.potionquest.gui.gamecontrol.TileSheets;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class NPC_Potion_Sellers_Brother extends Entity {

  Characters potionSellerBrother = getCharacter();

  public NPC_Potion_Sellers_Brother() throws IOException {
    direction = "left";
    speed = 0;
    name = "Potion Seller's Brother";

    solidArea = new Rectangle();
    solidArea.x = 0;
    solidArea.y = 0;
    solidArea.width = 48;
    solidArea.height = 60;

    getNPCImage();
    setDialogue();
  }

  public void getNPCImage() {

    BufferedImage npcImage = TileSheets.npcPotionSellerTileSheet;

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

  public Characters getCharacter() throws IOException {
    Map<String, Characters> charactersMap = Characters.characterJsonParser();
    return charactersMap.get("Potion Seller's Brother");
  }

  public void setDialogue() throws IOException {

    for (int i = 0; i < potionSellerBrother.getDialogue().size(); i++) {
      this.dialogues[i] = potionSellerBrother.getDialogue().get(Integer.toString(i + 1));
    }

    for (int i = 0; i < potionSellerBrother.getResponses().size(); i++) {
      this.responses[i] = potionSellerBrother.getResponses().get(Integer.toString(i + 1));
    }
  }

  public void talk() {

    GamePanel.ui.dialogueArray = this.dialogues.clone();
    GamePanel.ui.responsesArray = this.responses.clone();
    GamePanel.ui.currentDialogue = null;
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
      GamePanel.ui.dialogueScreenState = 1;
      super.talk();
    }
  }

  @Override
  public void draw(Graphics2D g2D) {

    BufferedImage image = null;

    int screenX = worldX - GamePanel.player.worldX + GamePanel.player.screenX;
    int screenY = worldY - GamePanel.player.worldY + GamePanel.player.screenY;

    if (worldX + GamePanel.tileSize > GamePanel.player.worldX - GamePanel.player.screenX
        && worldX - GamePanel.tileSize < GamePanel.player.worldX + GamePanel.player.screenX
        && worldY + GamePanel.tileSize * 2 > GamePanel.player.worldY - GamePanel.player.screenY
        && worldY - GamePanel.tileSize * 2 < GamePanel.player.worldY + GamePanel.player.screenY) {

      switch (direction) {
        case "up":
          image = goUp[0];
          break;
        case "down":
          image = goDown[0];
          break;
        case "left":
          image = goLeft[0];
          break;
        case "right":
          image = goRight[0];
          break;
      }
    }
    g2D.drawImage(image, screenX, screenY, null);
  }
}
