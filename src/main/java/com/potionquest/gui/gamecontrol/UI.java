package com.potionquest.gui.gamecontrol;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UI {

  private GamePanel gp;
  private Graphics2D g2D;
  private Font arial_40, arial_80B;

  public UI(GamePanel gp) {
    this.gp = gp;
    arial_40 = new Font("Arial", Font.PLAIN, 40);
    arial_80B = new Font("Arial", Font.BOLD, 80);
  }

  public void draw(Graphics2D g2D) {

    this.g2D = g2D;
    g2D.setFont(arial_40);
    g2D.setColor(Color.white);

    if (gp.gameState == gp.playState) {
      // Play state stuff
    } else if (gp.gameState == gp.pauseState) {
      drawPauseScreen();
    }
  }

  public void drawPauseScreen() {

    String text = "PAUSED";
    int x = findCenterOfTextString(text);
    int y = gp.screenHeight/2;

    g2D.drawString(text, x, y);
  }

  public int findCenterOfTextString(String text) {
    int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
    return gp.screenWidth/2 - length/2;
  }
}
