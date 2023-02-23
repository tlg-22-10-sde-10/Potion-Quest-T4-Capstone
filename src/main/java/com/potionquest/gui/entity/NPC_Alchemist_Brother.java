package com.potionquest.gui.entity;

import com.potionquest.gui.gamecontrol.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class NPC_Alchemist_Brother extends Entity {

  public NPC_Alchemist_Brother() {
    direction = "right";
    speed = 0;

    solidArea = new Rectangle();
    solidArea.x = 0;
    solidArea.y = 0;
    solidArea.width = 48;
    solidArea.height = 60;

    getNPCImage();
  }

  public void getNPCImage() {

    BufferedImage npcImage = imageFetch("/npc/potionseller60.png");

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

  @Override
  public void draw(Graphics2D g2D) {

    BufferedImage image = null;

    int screenX = worldX - GamePanel.player.worldX + GamePanel.player.screenX;
    int screenY = worldY - GamePanel.player.worldY + GamePanel.player.screenY;

    if (worldX + GamePanel.tileSize > GamePanel.player.worldX - GamePanel.player.screenX
        && worldX - GamePanel.tileSize < GamePanel.player.worldX + GamePanel.player.screenX
        && worldY + GamePanel.tileSize * 2 > GamePanel.player.worldY - GamePanel.player.screenY
        && worldY - GamePanel.tileSize * 2 < GamePanel.player.worldY + GamePanel.player.screenY) {

      image = goLeft[0];
      g2D.drawImage(image, screenX, screenY, null);
    }
  }
}
