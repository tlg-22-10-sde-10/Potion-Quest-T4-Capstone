package com.potionquest.gui.gamecontrol;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {

  private Graphics2D g2D;
  private Font arial_40, arial_80B;

  public UI() {
    arial_40 = new Font("Arial", Font.PLAIN, 40);
    arial_80B = new Font("Arial", Font.BOLD, 80);
  }

  public void draw(Graphics2D g2D) {

    this.g2D = g2D;
    g2D.setFont(arial_40);
    g2D.setColor(Color.white);

    if (GamePanel.gameState == GamePanel.playState) {
      // Play state stuff
    } else if (GamePanel.gameState == GamePanel.pauseState) {
      drawPauseScreen();
    }
  }

  public void drawPauseScreen() {

    String text = "PAUSED";
    int x = findCenterOfTextString(text);
    int y = GamePanel.screenHeight/2;

    g2D.drawString(text, x, y);
  }

  public int findCenterOfTextString(String text) {
    int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
    return GamePanel.screenWidth/2 - length/2;
  }
}
