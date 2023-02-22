package com.potionquest.gui.gamecontrol;

import com.potionquest.game.Game;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {

  private Graphics2D g2D;

  private int arcWidth = 10;
  private int arcHeight = 10;
  private Font arial_24, arial_40, arial_80B;
  private GamePanel gp;

//  public UI() {
//    arial_24 = new Font("Arial", Font.PLAIN, 24);
//    arial_40 = new Font("Arial", Font.PLAIN, 40);
//    arial_80B = new Font("Arial", Font.BOLD, 80);
//  }

  public UI(GamePanel gp) {
    this.gp = gp;
    arial_24 = new Font("Arial", Font.PLAIN, 24);
    arial_40 = new Font("Arial", Font.PLAIN, 40);
    arial_80B = new Font("Arial", Font.BOLD, 80);
  }

  public String currentDialogue = "";
  public int commandNum = 0;
  
  public int pauseScreenState = 0; // 0 is main pause screen, 1 is settings
  public int dialogueScreenState = 0; // 0 is main dialogue, 1 is subdialogues

  public UI() {
    arial_24 = new Font("Arial", Font.PLAIN, 24);
    arial_40 = new Font("Comic Sans MS", Font.PLAIN, 40);
    arial_80B = new Font("Comic Sans MS", Font.BOLD, 80);


  }

  public void draw(Graphics2D g2D) {

    this.g2D = g2D;
    g2D.setColor(Color.white);

    //display game time;
    g2D.setFont(arial_24);
    g2D.drawString(String.valueOf(GamePanel.getGameTime()), 24, 24);

    //after displaying time, set the font back to default
    g2D.setFont(arial_40);


    // TITLE STATE
    if (GamePanel.gameState == GamePanel.titleState) {
      drawTitleScreen();
    } else {
      drawInventory();
      drawPlayerHP();
      // PLAY STATE
      if (GamePanel.gameState == GamePanel.playState) {
        // Play state stuff
      }
      // PAUSE STATE
      else if (GamePanel.gameState == GamePanel.pauseState) {
        drawPauseScreen();
      }
      // DIALOGUE STATE
      else if (GamePanel.gameState == GamePanel.dialogueState) {
        drawDialogueScreen();
      }
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

  public void drawTitleScreen() {

    //TITLE NAME
    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 96F));
    String text = "Potion Quest";

    int x = findCenterOfTextString(text);
    int y = GamePanel.tileSize * 3;

    // SHADOW TEXT
    g2D.setColor(new Color(70, 120, 80));
    g2D.drawString(text, x+5, y+5);

    //MAIN COLOR
    g2D.setColor(Color.white);
    g2D.drawString(text, x, y);

    // PLAYER IMAGE
    x = GamePanel.screenWidth/2 - (GamePanel.tileSize*2)/2;
    y += GamePanel.tileSize * 2;
    g2D.drawImage(GamePanel.player.goDown[0], x, y, GamePanel.tileSize*2, GamePanel.tileSize*2, null);

    // MENU
    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 48F));

    text = "NEW GAME";
    x = findCenterOfTextString(text);
    y += GamePanel.tileSize * 3.5;
    //SHADOW NEW GAME
    g2D.setColor(new Color(70, 120, 80));
    g2D.drawString(text, x+5, y+5);
    //NEW GAME
    g2D.setColor(Color.white);
    g2D.drawString(text, x, y);
    if (commandNum == 0) {
      g2D.drawString(">", x - GamePanel.tileSize, y);
    }

    text = "LOAD GAME";
    x = findCenterOfTextString(text);
    y += GamePanel.tileSize;
    //SHADOW LOAD GAME
    g2D.setColor(new Color(70, 120, 80));
    g2D.drawString(text, x+5, y+5);
    //LOAD GAME
    g2D.setColor(Color.white);
    g2D.drawString(text, x, y);
    if (commandNum == 1) {
      g2D.drawString(">", x - GamePanel.tileSize, y);
    }

    text = "QUIT";
    x = findCenterOfTextString(text);
    y += GamePanel.tileSize;
    //SHADOW QUIT
    g2D.setColor(new Color(70, 120, 80));
    g2D.drawString(text, x+5, y+5);
    //QUIT
    g2D.setColor(Color.white);
    g2D.drawString(text, x, y);
    if (commandNum == 2) {
      g2D.drawString(">", x - GamePanel.tileSize, y);
    }
  }

  public void drawPauseScreen() {
    if (pauseScreenState == 0) {

      // SUB WINDOW
      int x1 = GamePanel.tileSize * 3;
      int y1 = GamePanel.tileSize * 3;
      int width = GamePanel.screenWidth - 2*x1;
      int height = GamePanel.tileSize * 6;
      drawSubWindow(x1, y1, width, height);

      String text = "PAUSED";
      int x = findCenterOfTextString(text);
      int y = GamePanel.tileSize*4;
      //SHADOW PAUSED
      g2D.setColor(new Color(70, 120, 80));
      g2D.drawString(text, x+3, y+3);
      //PAUSED
      g2D.setColor(Color.white);
      g2D.drawString(text, x, y);

      g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 24F));
      text = "Resume";
      x = findCenterOfTextString(text);
      y += GamePanel.tileSize*2;
      // SHADOW RESUME
      g2D.setColor(new Color(70, 120, 80));
      g2D.drawString(text, x+3, y+3);
      // RESUME
      g2D.setColor(Color.white);
      g2D.drawString(text, x, y);
      if (commandNum == 0) {
        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
      }

      text = "Settings";
      x = findCenterOfTextString(text);
      y += GamePanel.tileSize / 1.5;
      // SHADOW SETTINGS
      g2D.setColor(new Color(70, 120, 80));
      g2D.drawString(text, x+3, y+3);
      // SETTINGS
      g2D.setColor(Color.white);
      g2D.drawString(text, x, y);
      if (commandNum == 1) {
        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
      }

      text = "Quit";
      x = findCenterOfTextString(text);
      y += GamePanel.tileSize / 1.5;
      // SHADOW QUIT
      g2D.setColor(new Color(70, 120, 80));
      g2D.drawString(text, x+3, y+3);
      // QUIT
      g2D.setColor(Color.white);
      g2D.drawString(text, x, y);
      if (commandNum == 2) {
        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
      }

    } else if (pauseScreenState == 1) {

      // SUB WINDOW
      int x1 = GamePanel.tileSize * 3;
      int y1 = GamePanel.tileSize * 3;
      int width = GamePanel.screenWidth - 2*x1;
      int height = GamePanel.tileSize * 6;
      drawSubWindow(x1, y1, width, height);

      String text = "Settings Menu";
      int x = findCenterOfTextString(text);
      int y = GamePanel.tileSize * 4;
      g2D.drawString(text, x, y);

      g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 24F));

      text = "Placeholder1";
      x = findCenterOfTextString(text);
      y += GamePanel.tileSize*2;
      // SHADOW P1
      g2D.setColor(new Color(70, 120, 80));
      g2D.drawString(text, x+3, y+3);
      // P1
      g2D.setColor(Color.white);
      g2D.drawString(text, x, y);
      if (commandNum == 0) {
        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
      }

      text = "Placeholder2";
      x = findCenterOfTextString(text);
      y += GamePanel.tileSize/1.5;
      // SHADOW P2
      g2D.setColor(new Color(70, 120, 80));
      g2D.drawString(text, x+3, y+3);
      // P2
      g2D.setColor(Color.white);
      g2D.drawString(text, x, y);
      if (commandNum == 1) {
        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
      }

      text = "Placeholder3";
      x = findCenterOfTextString(text);
      y += GamePanel.tileSize/1.5;
      // SHADOW P3
      g2D.setColor(new Color(70, 120, 80));
      g2D.drawString(text, x+3, y+3);
      // P3
      g2D.setColor(Color.white);
      g2D.drawString(text, x, y);
      if (commandNum == 2) {
        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
      }

      text = "Back";
      x = findCenterOfTextString(text);
      y += GamePanel.tileSize;
      // SHADOW BACK
      g2D.setColor(new Color(70, 120, 80));
      g2D.drawString(text, x+3, y+3);
      // BACK
      g2D.setColor(Color.white);
      g2D.drawString(text, x, y);
      if (commandNum == 3) {
        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
      }
    }
  }

  public void drawDialogueScreen() {
    if (dialogueScreenState == 0) {
      // WINDOW
      int x = GamePanel.tileSize * 2;
      int y = GamePanel.tileSize / 2;
      int width = GamePanel.screenWidth - (2 * x);
      int height = GamePanel.tileSize * 4;
      drawSubWindow(x, y, width, height);

      g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 24F));
      x += GamePanel.tileSize;
      y += GamePanel.tileSize;

      for (String line : currentDialogue.split("\n")) {
        g2D.drawString(line, x, y);
        y += 32;
      }

    } else if (dialogueScreenState == 1) {
      // WINDOW1
      int x1 = GamePanel.tileSize * 2;
      int y1 = GamePanel.tileSize / 2;
      int width1 = GamePanel.screenWidth - (2 * x1);
      int height1 = GamePanel.tileSize * 4;
      drawSubWindow(x1, y1, width1, height1);


      g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 24F));
      x1 += GamePanel.tileSize;
      y1 += GamePanel.tileSize;

      for (String line : currentDialogue.split("\n")) {
        g2D.drawString(line, x1, y1);
        y1 += 32;
      }

      // WINDOW2
      int x2 = GamePanel.tileSize * 11;
      int y2 = (int) (GamePanel.tileSize * 4.5);
      int width2 = GamePanel.tileSize * 3;
      int height2 = GamePanel.tileSize * 3;
      drawSubWindow(x2, y2, width2, height2);

      x2 += GamePanel.tileSize;
      y2 += GamePanel.tileSize;
      g2D.drawString("Yes", x2, y2);
      if (commandNum == 0) {
        g2D.drawString(">", x2 - GamePanel.tileSize / 2, y2);
      }
      y2 += GamePanel.tileSize;
      g2D.drawString("No", x2, y2);
      if (commandNum == 1) {
        g2D.drawString(">", x2 - GamePanel.tileSize / 2, y2);
      }
    }
  }

  public void drawSubWindow(int x, int y, int width, int height) {

    Color c = new Color(0, 0, 0, 200);
    g2D.setColor(c);
    g2D.fillRoundRect(x, y, width, height, 35, 35);

    c = new Color(255, 255, 255);
    g2D.setColor(c);
    g2D.setStroke(new BasicStroke(5));
    g2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
  }

  private void drawSubWindow(int x, int y, int width, int height, Color c) {
    g2D.setColor(c);
    arcWidth = 28;
    arcHeight = 28;
    g2D.fillRoundRect(x,y,width,height, arcWidth, arcHeight);
  }

  private void drawPlayerHP() {

  }

  public int findCenterOfTextString(String text) {
    int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
    return GamePanel.screenWidth / 2 - length / 2;
  }
}