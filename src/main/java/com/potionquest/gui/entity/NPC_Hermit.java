package com.potionquest.gui.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import com.potionquest.gui.gamecontrol.*;

public class NPC_Hermit extends Entity {

  public NPC_Hermit() {
    direction = "down";
    speed = 1;

    solidArea = new Rectangle();
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.x = -10;
    solidArea.y = 0;
    solidArea.width = 60;
    solidArea.height = 60;

    getNPCImage();
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

  public void setBehavior() {

    actionTimeOut++;
    if (actionTimeOut == 60) {
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
}
