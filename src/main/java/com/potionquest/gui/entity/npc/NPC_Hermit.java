package com.potionquest.gui.entity.npc;

import com.potionquest.game.Characters;
import com.potionquest.gui.entity.Entity;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import com.potionquest.gui.gamecontrol.*;

public class NPC_Hermit extends Entity {

  Characters hermit = getCharacter();

  public NPC_Hermit() throws IOException {
    direction = "left";
    speed = 1;
    name = "Old Hermit";

    super.entityType = 1;

    solidArea = new Rectangle();
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.x = -10;
    solidArea.y = 0;
    solidArea.width = 60;
    solidArea.height = 60;

    getNPCImage();
    setDialogue();
  }

  public void getNPCImage() {

    BufferedImage npcImage = imageFetch("/npc/hermit60.png");

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
    return charactersMap.get("Hermit");
  }

  public void setDialogue() throws IOException {

    for (int i = 0; i < hermit.getDialogue().size(); i++) {
      this.dialogues[i] = hermit.getDialogue().get(Integer.toString(i + 1));
    }

    for (int i = 0; i < hermit.getResponses().size(); i++) {
      this.responses[i] = hermit.getResponses().get(Integer.toString(i + 1));
    }

  }

  public void setBehavior() {

    actionTimeOut++;

    if (actionTimeOut == 90) {
      Random random = new Random();
      int i = random.nextInt(100) + 1; //pick random number from 1 to 4

      if (i <= 25) {
        direction = "left";
      } else if (i > 25 && i <= 50) {
        direction = "right";
      } else if (i > 50 && i <= 75) {
        direction = "right";
      } else if (i > 75) {
        direction = "left";
      }

      actionTimeOut = 0;
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
}
