package com.potionquest.gui.gamecontrol;

import com.potionquest.gui.items.Heart;
import com.potionquest.gui.items.SuperObjects;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class UI {

  private Graphics2D g2D;

  private int arcWidth = 10;
  private int arcHeight = 10;

  private final Font arial_24, arial_40, arial_80B;

  private final SuperObjects heart = new Heart();

  public String currentDialogue = "";
  public boolean keyDialogueComplete = false;
  public String[] responsesArray;
  public String[] dialogueArray;
  public int arrayIndex = 0;
  public int commandNum = 0;

  public int pauseScreenState = 0; // 0 is main pause screen, 1 is controls, 2 is settings
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
      //if not title state
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
//        g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 18F));
//        int x = GamePanel.tileSize*3;
//        int y = (GamePanel.tileSize/2) + GamePanel.tileSize;
//
//        for (String line : currentDialogue.split("\n")) {
//          g2D.drawString(line, x, y);
//          y += 18;
//        }

      }
      // INVENTORY STATE
      else if (GamePanel.gameState == GamePanel.inventoryState) {
        drawInventoryScreen();
      }
    }
  }

  private void drawInventory() {
    for (int i = 0; i < 5; i++) {
      int frameX = GamePanel.tileSize * (11 + i);
      int frameY = 0;
      int frameWidth = GamePanel.tileSize;
      int frameHeight = GamePanel.tileSize;

      Color c = new Color(0, 0, 0, 140);

      drawSubWindow(frameX, frameY, frameWidth, frameHeight, c);

      if (i < GamePanel.player.inventory.size()) {
        var item = GamePanel.player.inventory.get(i);
        g2D.drawImage(item.images.get(0), frameX, 0, null);
      } else {
        drawString(String.valueOf(i + 1), frameX + 14, 38, new Color(255, 255, 255, 180));
      }
    }
  }

  private void drawSubWindow(int x, int y, int width, int height, Color c) {
    g2D.setColor(c);
    arcWidth = 28;
    arcHeight = 28;
    g2D.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
  }

  public void drawTitleScreen() {
    //TITLE NAME
    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 96F));
    String text = "Potion Quest";

    int x = findCenterOfTextString(text);
    int y = GamePanel.tileSize * 3;

    // SHADOW TEXT
    drawString(text, x + 5, y + 5, new Color(70, 120, 80));
    //MAIN COLOR
    drawString(text, x, y, Color.white);

    // PLAYER IMAGE
    x = GamePanel.screenWidth / 2 - (GamePanel.tileSize * 2) / 2;
    y += GamePanel.tileSize * 2;
    g2D.drawImage(GamePanel.player.goDown[0], x, y, GamePanel.tileSize * 2, GamePanel.tileSize * 2,
        null);
    // MENU
    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 48F));

    y += GamePanel.tileSize * 2.5;

    String[] texts = new String[]{"NEW GAME", "LOAD GAME", "QUIT"};

    for (int i = 0; i < texts.length; i++) {
      x = findCenterOfTextString(texts[i]);
      y += GamePanel.tileSize;
      //SHADOW NEW GAME
      drawString(texts[i], x + 5, y + 5, new Color(70, 120, 80));
      //NEW GAME
      drawString(texts[i], x, y, Color.white);
      if (commandNum == i) {
        g2D.drawString(">", x - GamePanel.tileSize, y);
      }
    }
  }

  public void drawString(String text, int x, int y, Color color) {
    g2D.setColor(color);
    g2D.drawString(text, x, y);
  }

  public void drawPauseScreen() {
    if (pauseScreenState == 0) {
      // SUB WINDOW
      int x1 = GamePanel.tileSize * 3;
      int y1 = GamePanel.tileSize * 3;
      int width = GamePanel.screenWidth - 2 * x1;
      int height = GamePanel.tileSize * 6;
      drawSubWindow(x1, y1, width, height);

      String text = "PAUSED";
      int x = findCenterOfTextString(text);
      int y = GamePanel.tileSize * 4;
      //SHADOW PAUSED
      drawString(text, x + 3, y + 3, new Color(70, 120, 80));
      //PAUSED
      drawString(text, x, y, Color.white);

      String[] texts = new String[]{"Resume", "Controls", "Settings", "Quit"};
      y += GamePanel.tileSize * 1.5;
      g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 24F));

      for (int i = 0; i < texts.length; i++) {
        x = findCenterOfTextString(texts[i]);
        y += GamePanel.tileSize / 1.5;
        // SHADOW RESUME
        drawString(texts[i], x + 3, y + 3, new Color(70, 120, 80));
        // RESUME
        drawString(texts[i], x, y, Color.white);

        if (commandNum == i) {
          g2D.drawString(">", x - GamePanel.tileSize / 2, y);
        }
      }
    } else if (pauseScreenState == 1) {
      // SUB WINDOW
      int x1 = 0;
      int y1 = 0;
      int width = GamePanel.screenWidth;
      int height = GamePanel.screenHeight;
      drawSubWindow(x1, y1, width, height);

      String text = "Controls Menu";
      int x = findCenterOfTextString(text);
      int y = GamePanel.tileSize;
      g2D.drawString(text, x, y);

      g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 24F));

      text = "Movement";
      x = (int) (GamePanel.tileSize * 3.5);
      y += GamePanel.tileSize * 3;
      // SHADOW P1
      drawString(text, x + 3, y + 3, new Color(70, 120, 80));
      // P1
      drawString(text, x, y, Color.white);
      g2D.drawImage(fetchImage("/controlsIcons/arrowsKeys48.png"),
          x + GamePanel.tileSize * 7, (int) (y - GamePanel.tileSize * 1.5), null);
//      if (commandNum == 0) {
//        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
//      }

      text = "Talk/Interact";
      x = (int) (GamePanel.tileSize * 3.5);
      y += GamePanel.tileSize * 2;
      // SHADOW P2
      drawString(text, x + 3, y + 3, new Color(70, 120, 80));
      // P2
      drawString(text, x, y, Color.white);
      g2D.drawImage(fetchImage("/controlsIcons/zKey48.png"),
          x + GamePanel.tileSize * 8, (int) (y - GamePanel.tileSize / 1.5), null);
//      if (commandNum == 1) {
//        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
//      }

      text = "Open Inventory";
      x = (int) (GamePanel.tileSize * 3.5);
      y += GamePanel.tileSize * 1.5;
      // SHADOW P3
      drawString(text, x + 3, y + 3, new Color(70, 120, 80));
      // P3
      drawString(text, x, y, Color.white);
      g2D.drawImage(fetchImage("/controlsIcons/bKey48.png"),
          x + GamePanel.tileSize * 8, (int) (y - GamePanel.tileSize / 1.5), null);
//      if (commandNum == 2) {
//        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
//      }

      text = "Pause Game/Menu";
      x = GamePanel.tileSize * 3;
      y += GamePanel.tileSize * 3 / 2;
      // SHADOW P3
      drawString(text, x + 3, y + 3, new Color(70, 120, 80));
      // P3
      drawString(text, x, y, Color.white);
      g2D.drawImage(fetchImage("/controlsIcons/enterKey48.png"),
          x + GamePanel.tileSize * 8, (int) (y - GamePanel.tileSize / 1.5), null);
//      if (commandNum == 2) {
//        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
//      }

      text = "Back";
      x = findCenterOfTextString(text);
      y += GamePanel.tileSize * 1.5;
      // SHADOW BACK
      drawString(text, x + 3, y + 3, new Color(70, 120, 80));
      // BACK
      drawString(text, x, y, Color.white);
      if (commandNum == 1) {
        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
      }

    } else if (pauseScreenState == 2) {
      // SUB WINDOW
      int x1 = GamePanel.tileSize * 3;
      int y1 = GamePanel.tileSize * 3;
      int width = GamePanel.screenWidth - 2 * x1;
      int height = GamePanel.tileSize * 6;
      drawSubWindow(x1, y1, width, height);

      String text = "Settings Menu";
      int x = findCenterOfTextString(text);
      int y = GamePanel.tileSize * 4;
      g2D.drawString(text, x, y);

      g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 24F));

      text = "Placeholder1";
      x = findCenterOfTextString(text);
      y += GamePanel.tileSize * 2;
      // SHADOW P1
      drawString(text, x + 3, y + 3, new Color(70, 120, 80));
      // P1
      drawString(text, x, y, Color.white);
      if (commandNum == 0) {
        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
      }

      text = "Placeholder2";
      x = findCenterOfTextString(text);
      y += GamePanel.tileSize / 1.5;
      // SHADOW P2
      drawString(text, x + 3, y + 3, new Color(70, 120, 80));
      // P2
      drawString(text, x, y, Color.white);
      if (commandNum == 1) {
        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
      }

      text = "Placeholder3";
      x = findCenterOfTextString(text);
      y += GamePanel.tileSize / 1.5;
      // SHADOW P3
      drawString(text, x + 3, y + 3, new Color(70, 120, 80));
      // P3
      drawString(text, x, y, Color.white);
      if (commandNum == 2) {
        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
      }

      text = "Back";
      x = findCenterOfTextString(text);
      y += GamePanel.tileSize;
      // SHADOW BACK
      drawString(text, x + 3, y + 3, new Color(70, 120, 80));
      // BACK
      drawString(text, x, y, Color.white);
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

      g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 18F));
      x += GamePanel.tileSize;
      y += GamePanel.tileSize;

//      if (dialogueArray[arrayIndex] != null) {
      if (GamePanel.ui.currentDialogue == null) {
        if (!GamePanel.player.haveTalkedToOnceAlready) {
          for (String chunk : dialogueArray[arrayIndex].split("\n")) {
            g2D.drawString(chunk, x, y);
            y += 20;
          }
        }
        if (keyDialogueComplete) {
          g2D.drawString(responsesArray[1], x, y);
        } else if (GamePanel.player.haveTalkedToOnceAlready) {
          g2D.drawString(responsesArray[2], x, y);
        }
      } else {
        for (String line : currentDialogue.split("\n")) {
          g2D.drawString(line, x, y);
        }
      }


    } else if (dialogueScreenState == 1) {
      // WINDOW1
//      arrayIndex = 0;

        int x1 = GamePanel.tileSize * 2;
        int y1 = GamePanel.tileSize / 2;
        int width1 = GamePanel.screenWidth - (2 * x1);
        int height1 = GamePanel.tileSize * 4;
        drawSubWindow(x1, y1, width1, height1);

        g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 24F));
        x1 += GamePanel.tileSize;
        y1 += GamePanel.tileSize;
      if (GamePanel.ui.currentDialogue == null) {
        for (String chunk : responsesArray[0].split("\n")) {
          g2D.drawString(chunk, x1, y1);
          y1 += 20;
        }
      } else {
        for (String line : currentDialogue.split("\n")) {
          g2D.drawString(line, x1, y1);
        }
      }

      // WINDOW2
      if (GamePanel.ui.currentDialogue == null) {
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
  }

  public void drawInventoryScreen() {
    drawSubWindow(0, 0, GamePanel.screenWidth, GamePanel.screenHeight, new Color(0, 0, 0, 200));

    Color c = new Color(255, 255, 255);
    g2D.setColor(c);

    int width = GamePanel.tileSize;
    int height = GamePanel.tileSize;
    int arcWidth = 28;
    int arcHeight = 28;

    int x = GamePanel.tileSize * (10 + commandNum);
    int y = 0;
    String text = String.valueOf(commandNum);

    if ((commandNum - 1) < GamePanel.player.inventory.size()) {
      if (GamePanel.player.inventory.get(commandNum - 1) != null) {
        var item = GamePanel.player.inventory.get(commandNum - 1);
        g2D.drawImage(item.images.get(0), x, 0, null);
      }
    } else {
      g2D.drawString(text, x + 14, 38);
      g2D.setStroke(new BasicStroke(3));
      g2D.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
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

  private void drawPlayerHP() {
    int x = GamePanel.tileSize / 2;
    //int y = GamePanel.tileSize / 2;
    int y = 0;

    int i = 0;

    for (i = 0; i < (GamePanel.player.HP - 1) / (heart.images.size() - 1); i++) {
      g2D.drawImage(heart.images.get(0), x, y, null);
      x += GamePanel.tileSize;
    }

    if (GamePanel.player.HP > 0) {
      int index = GamePanel.player.HP % (heart.images.size() - 1);
      g2D.drawImage(heart.images.get(index), x, y, null);
      x += GamePanel.tileSize;
    }

    for (i = 0; i < (GamePanel.player.MAX_HP - GamePanel.player.HP) / (heart.images.size() - 1);
        i++) {
      g2D.drawImage(heart.images.get(4), x, y, null);
      x += GamePanel.tileSize;
    }
  }

  public int findCenterOfTextString(String text) {
    int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
    return GamePanel.screenWidth / 2 - length / 2;
  }

  public BufferedImage fetchImage(String filePath) {
    BufferedImage image = null;
    try (InputStream input = getClass().getResourceAsStream(filePath)) {
      image = ImageIO.read(input);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return image;
  }
}
