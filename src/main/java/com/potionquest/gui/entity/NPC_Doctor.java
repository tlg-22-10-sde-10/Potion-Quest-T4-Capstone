package com.potionquest.gui.entity;

import com.potionquest.game.Characters;
import com.potionquest.gui.gamecontrol.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class NPC_Doctor extends Entity {

  public boolean keyCharacter = true;
  Characters doctor = getCharacter();

  public NPC_Doctor() throws IOException {
    direction = "left";
    speed = 0;

    getNPCImage();
    setDialogue();
  }

  public void getNPCImage() {

    BufferedImage npcImage = imageFetch("/npc/doctor.png");

    int imageIndexX = 0;
    for (int i = 0; i < 3; i++) {
      BufferedImage up = npcImage.getSubimage(imageIndexX, 48, 48, 48);
      goUp[i] = up;
      imageIndexX += 48;
    }

    imageIndexX = 0;
    for (int i = 0; i < 3; i++) {
      BufferedImage down = npcImage.getSubimage(imageIndexX, 0, 48, 48);
      goDown[i] = down;
      imageIndexX += 48;
    }

    imageIndexX = 0;
    for (int i = 0; i < 3; i++) {
      BufferedImage left = npcImage.getSubimage(imageIndexX, 144, 48, 48);
      goLeft[i] = left;
      imageIndexX += 48;
    }

    imageIndexX = 0;
    for (int i = 0; i < 3; i++) {
      BufferedImage right = npcImage.getSubimage(imageIndexX, 96, 48, 48);
      goRight[i] = right;
      imageIndexX += 48;
    }
  }

  public Characters getCharacter() throws IOException {
    Map<String, Characters> charactersMap = Characters.characterJsonParser();
    return charactersMap.get("Doctor");
  }

  public void setDialogue() throws IOException {

    for (int i = 0; i < doctor.getResponses().size(); i++) {
      dialogues[i] = doctor.getResponses().get(Integer.toString(i+1));
    }

  }

  public void setBehavior() {

    actionTimeOut++;

    if (actionTimeOut == 60) {
      Random random = new Random();
      int i = random.nextInt(100) + 1; //pick random number from 1 to 4

      if (i <= 15) {
        direction = "up";
      } else if (i > 15 && i <= 30) {
        direction = "down";
      } else if (i > 30 && i <= 45) {
        direction = "right";
      } else if (i > 45) {
        direction = "left";
      }
      actionTimeOut = 0;
    }
  }

  public void talk() {

    if (firstChat) {
      try {
        Characters doctor = getCharacter();
        GamePanel.ui.currentDialogue = doctor.getDialogue();

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
      } catch (IOException e) {
        e.printStackTrace();
      }
      firstChat = false;

    } else {
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
