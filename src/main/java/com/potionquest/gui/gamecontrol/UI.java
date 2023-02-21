package com.potionquest.gui.gamecontrol;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {

  private Graphics2D g2D;
  private int arcWidth = 10;
  private int arcHeight = 10;
  private Font arial_24, arial_40, arial_80B;
  private GamePanel gp;

  public UI() {
    arial_24 = new Font("Arial", Font.PLAIN, 24);
    arial_40 = new Font("Arial", Font.PLAIN, 40);
    arial_80B = new Font("Arial", Font.BOLD, 80);
  }

  public UI(GamePanel gp) {
    this.gp = gp;
    arial_24 = new Font("Arial", Font.PLAIN, 24);
    arial_40 = new Font("Arial", Font.PLAIN, 40);
    arial_80B = new Font("Arial", Font.BOLD, 80);
  }

  public void draw(Graphics2D g2D) {

    this.g2D = g2D;
    g2D.setColor(Color.white);

    //display game time;
    g2D.setFont(arial_24);
    g2D.drawString(String.valueOf(GamePanel.getGameTime()), 24, 24);

    //after displaying time, set the font back to default
    g2D.setFont(arial_40);

    drawInventory();

    if (gp.gameState == gp.playState) {
      // Play state stuff
    }
    //pause
    if (gp.gameState == gp.pauseState) {
      drawPauseScreen();
    }
  }

  private void drawInventory() {
    for(int i=0;i<5;i++) {
      int frameX = gp.tileSize * (11 + i);
      int frameY = gp.tileSize * 0;
      int frameWidth = gp.tileSize * 1;
      int frameHeight = gp.tileSize;

      Color c = new Color(0,0,0, 140);

      drawSubWindow(frameX, frameY, frameWidth, frameHeight, c);
      g2D.setColor(new Color(255,255,255, 180));
      g2D.drawString(String.valueOf(i+1), frameX + 14, 38);
    }
  }

  private void drawSubWindow(int x, int y, int width, int height) {
    Color c = new Color(255,255,255, 120);

    g2D.setColor(c);
    arcWidth = 28;
    arcHeight = 28;
    g2D.fillRoundRect(x,y,width,height, arcWidth, arcHeight);
  }

  private void drawSubWindow(int x, int y, int width, int height, Color c) {
    g2D.setColor(c);
    arcWidth = 28;
    arcHeight = 28;
    g2D.fillRoundRect(x,y,width,height, arcWidth, arcHeight);
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
