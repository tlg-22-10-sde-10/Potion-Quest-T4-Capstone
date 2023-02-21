package com.potionquest.gui.entity;

import java.awt.image.BufferedImage;
import java.util.Random;
import com.potionquest.gui.gamecontrol.*;

public class NPC_Hermit extends Entity {

  public NPC_Hermit() {
    direction = "down";
    speed = 1;

    getNPCImage();
  }

  public void getNPCImage() {

    BufferedImage npcImage = imageFetch("/npc/hermit.png");

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

  public void setBehavior() {

    actionTimeOut++;

    if (actionTimeOut == 60) {
      Random random = new Random();
      int i = random.nextInt(100) + 1; //pick random number from 1 to 4

      if (i <= 25) {
        direction = "up";
      } else if (i > 25 && i <= 50) {
        direction = "down";
      } else if (i > 50 && i <= 75) {
        direction = "right";
      } else if (i > 75) {
        direction = "left";
      }

      actionTimeOut = 0;
    }
  }
}
