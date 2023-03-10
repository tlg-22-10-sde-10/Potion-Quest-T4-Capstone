package com.potionquest.gui.gamecontrol;

import com.potionquest.client.GameClientUtil;
import com.potionquest.gui.gamecontrol.playerhp.Heart;
import com.potionquest.gui.gamecontrol.playerhp.SuperObjects;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import java.awt.FontFormatException;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class UI {
  private Graphics2D g2D;

  private Font comicSans;

  private final SuperObjects heart = new Heart();

  public String currentDialogue = "";
  public boolean keyDialogueComplete = false;
  public String[] responsesArray;
  public String[] dialogueArray;
  public int arrayIndex = 0;
  public int commandNum = 0;

  public int pauseScreenState = 0; // 0 is main pause screen, 1 is controls, 2 is settings
  public int dialogueScreenState = 0; // 0 is main dialogue, 1 is subdialogues
  public int titleScreenState = 0; // 0 is main title screen, 1 is controls menu

  public static String statement = "";

  public UI() {
    try (InputStream input = getClass().getResourceAsStream("/font/COMIC.TTF")) {
      //noinspection ConstantConditions
      comicSans = Font.createFont(Font.TRUETYPE_FONT, input);
    } catch (IOException | FontFormatException e) {
      e.printStackTrace();
    }
  }

  public void draw(Graphics2D g2D) {
    this.g2D = g2D;
    g2D.setColor(Color.white);

    //after displaying time, set the font back to default
    g2D.setFont(comicSans);
    g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 40F));

    // TITLE STATE
    if (GamePanel.gameState == GamePanel.titleState) {
      drawTitleScreen();
    } else if (GamePanel.gameState == GamePanel.creditState) {
      drawCredits();
    } else {
      //if not title state
      drawInventory();
      drawPlayerHP();
      drawCoin();
      drawTime();

      g2D.setColor(Color.white);
      g2D.setFont(comicSans);
      drawStatement();
      // PLAY STATE
      if (GamePanel.gameState == GamePanel.playState) {
        // Play state stuff above
      }
      // PAUSE STATE
      else if (GamePanel.gameState == GamePanel.pauseState) {
        drawPauseScreen();
      }
      // DIALOGUE STATE
      else if (GamePanel.gameState == GamePanel.dialogueState) {
        drawDialogueScreen();
      }
      // INVENTORY STATE
      else if (GamePanel.gameState == GamePanel.inventoryState) {
        drawInventoryScreen();
      }
      // GAME OVER STATE
      else if (GamePanel.gameState == GamePanel.gameOverState) {
        drawGameOverScreen();
      }
      // WIN STATE
      else if (GamePanel.gameState == GamePanel.winState) {
        drawWinScreen();
      }
    }
  }

  private void drawStatement() {

    g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 24F));
    int x = findCenterOfTextString(statement);
    int y = GamePanel.tileSize * 11;
    if (!statement.equals("")) {
      drawSubWindowForDrawInventory(x - 30, (int) (y - GamePanel.tileSize/1.5),
          (int) g2D.getFontMetrics().getStringBounds(statement, g2D).getWidth()+60, GamePanel.tileSize, new Color(0, 0, 0, 200));
    }
    drawString(statement, x + 2, y + 2, new Color(70, 120, 80));
    g2D.setColor(Color.white);
    g2D.drawString(statement, x, y);
  }


  private void drawTitleScreen() {
    if (titleScreenState == 0) {
      drawTitleScreen2();
    } else if (titleScreenState == 1) {
      drawIntroduction();
    } else if (titleScreenState == 2) {
      drawSettingScreen();
    } else if (titleScreenState == 3) {
      drawControl();
    }
  }

  private void drawGameOverScreen() {
    String[] texts = new String[] {"Retry", "Quit"};
    int[] yy = new int[] { GamePanel.tileSize * 4, 55};
    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 110F));

    int x;
    int y = GamePanel.tileSize * 5;
    
    String gameOver;
    g2D.setColor(Color.BLACK);
    if(GamePanel.gameTime >= GamePanel.gameTimeLimit) {
      gameOver = "Time Is Up";
      x = findCenterOfTextString(gameOver);
      g2D.drawString(gameOver, x, y);
      g2D.setColor(Color.WHITE);
    } else {
      gameOver = "You Died";
      x = findCenterOfTextString(gameOver);
      g2D.drawString(gameOver, x, y);
      g2D.setColor(new Color(210,60,30));
    }
    g2D.drawString(gameOver, x-4, y-4);

    g2D.setColor(Color.white);
    g2D.setFont(g2D.getFont().deriveFont(50f));
    for (int i=0; i< texts.length; i++) {
      x = findCenterOfTextString(texts[i]);
      y+=yy[i];
      // SHADOW TEXT
      drawString(texts[i], x + 5, y + 5, new Color(70, 120, 80));
      //MAIN COLOR
      drawString(texts[i], x, y, Color.white);

      if (commandNum == i) {
        g2D.drawString(">", x - GamePanel.tileSize, y);
      }
    }
  }
 
  public void drawTitleScreen2() {
    //TITLE NAME
    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 96F));
    String text = "Potion Quest";

    int x = findCenterOfTextString(text);
    int y = GamePanel.tileSize * 3;

    // SHADOW TEXT
    drawString(text, x + 3, y + 3, new Color(70, 120, 80));
    //MAIN COLOR
    drawString(text, x, y, Color.white);

    // PLAYER IMAGE
    x = GamePanel.screenWidth / 2 - (GamePanel.tileSize * 2) / 2 - GamePanel.tileSize *4 / 4;
    y += GamePanel.tileSize * 3/4;
    g2D.drawImage(GamePanel.player.goDown[0], x, y, GamePanel.tileSize * 3 /2, GamePanel.tileSize * 3,
        null);
    x += GamePanel.tileSize * 5 / 2;
    g2D.drawImage(GamePanel.npc[1].goDown[0], x, y + GamePanel.tileSize / 2, GamePanel.tileSize * 3 /2, GamePanel.tileSize * 2,
        null);
    // MENU
    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 36F));

    y += GamePanel.tileSize * 3;

    String[] texts = new String[] {"NEW GAME", "SETTINGS","CONTROLS", "QUIT"};

    for (int i = 0; i < texts.length; i++) {
      x = findCenterOfTextString(texts[i]);
      y += GamePanel.tileSize ;
      //SHADOW NEW GAME
      drawString(texts[i], x + 3, y + 3, new Color(70, 120, 80));
      //NEW GAME
      drawString(texts[i], x, y, Color.white);
      if (commandNum == i) {
        g2D.drawString(">", x - GamePanel.tileSize, y);
      }
    }
  }

  public void drawIntroduction() {
    try {
      int x;
      int y = GamePanel.tileSize;
      for (String line : GameClientUtil.retrieveMessage("welcomeMessage").split("\n")) {
        g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 20F));
        x = findCenterOfTextString(line);
        y += 24;
        drawString(line, x+2, y+2, new Color(70, 120, 80));
        drawString(line, x, y, Color.WHITE);
      }

      String text = "Press 'Z' to continue";
      x = findCenterOfTextString(text);
      y += GamePanel.tileSize * 3 / 2;
      // SHADOW BACK
      drawString(text, x + 2, y + 2, new Color(70, 120, 80));
      // BACK
      drawString(text, x, y, Color.white);

      if (commandNum == 0) {
        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void drawSettingScreen() {
    int x1 = GamePanel.tileSize * 3;
    int y1 = GamePanel.tileSize * 2;
    int width = GamePanel.screenWidth - 2 * x1;
    int height = GamePanel.tileSize * 8;
    drawSubWindowForDialogue(x1, y1, width, height);

    String text = "Settings Volume";
    int x = findCenterOfTextString(text);
    int y = GamePanel.tileSize * 4;
    g2D.drawString(text, x, y);

    g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 24F));

    text = "Current Volume: " + GamePanel.sound.getVolumePercentage() + "%";
    x = findCenterOfTextString(text);
    y += GamePanel.tileSize * 3 / 2;
    // SHADOW P1
    drawString(text, x + 2, y + 2, new Color(70, 120, 80));
    // P1
    drawString(text, x, y, new Color(210, 180, 30));

    String[] texts = new String[] {
        "Turn Up Volume", "Turn Down Volume",
        "Volume On/Off", "Back"
    };

    for(int i=0; i<texts.length; i++) {
      x = findCenterOfTextString(texts[i]);
      if(i == 0 || i == 3) {
        y += GamePanel.tileSize;
      } else {
        y += GamePanel.tileSize * 2 / 3;
      }

      // SHADOW P1
      drawString(texts[i], x + 2, y + 2, new Color(70, 120, 80));
      // P1
      drawString(texts[i], x, y, Color.white);
      if (commandNum == i) {
        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
      }
    }
  }

  private void drawControl() {
    // SUB WINDOW
    int x1 = 0;
    int y1 = 0;
    int width = GamePanel.screenWidth;
    int height = GamePanel.screenHeight;
    drawSubWindowForDialogue(x1, y1, width, height);

    String text = "Controls Menu";
    int x = findCenterOfTextString(text);
    int y = GamePanel.tileSize * 3 / 2;
    drawString(text, x + 2, y + 2, new Color(70, 120, 80));
    drawString(text, x, y, Color.white);

    g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 18F));

    String[] texts = new String[] {"Movement","Talk/Select/Use Item","Attack/Drop Item","Open Inventory (To Use/Drop Item)","Pause Game/Menu"};
    int[] xx = new int[] {
        GamePanel.tileSize * 7 / 2,
        GamePanel.tileSize * 7 / 2,
        GamePanel.tileSize * 7 / 2,
        GamePanel.tileSize * 7 / 2,
        GamePanel.tileSize * 7 / 2};
    int[] yy = new int[] {
        GamePanel.tileSize * 6 / 2,
        GamePanel.tileSize * 5 / 4,
        GamePanel.tileSize * 5 / 4,
        GamePanel.tileSize * 5 / 4,
        GamePanel.tileSize * 5 / 4
    };
    int[] xAmends = new int[]{
        GamePanel.tileSize * 7,
        GamePanel.tileSize * 8,
        GamePanel.tileSize * 13 / 2,
        GamePanel.tileSize * 8,
        GamePanel.tileSize * 15/2
    };
    int[] yAmends = new int[] {
        GamePanel.tileSize * 4/2,
        GamePanel.tileSize * 2/3,
        GamePanel.tileSize * 2/3,
        GamePanel.tileSize * 2/3,
        GamePanel.tileSize * 5/8
    };
    String[] paths = new String[] { "/controlsIcons/arrowsKeys48.png",
        "/controlsIcons/zKey48.png","/controlsIcons/spaceKey188.png","/controlsIcons/bKey48.png",
        "/controlsIcons/enterKey48.png"};

    for(int i = 0; i < texts.length; i++) {
      x = xx[i];
      y += yy[i];
      drawString(texts[i], x + 2, y + 2, new Color(70, 120, 80));
      drawString(texts[i], x, y, Color.white);
      g2D.drawImage(fetchImage(paths[i]),
          x + xAmends[i], y - yAmends[i], null);
    }

    text = "Back";
    x = findCenterOfTextString(text);
    y += GamePanel.tileSize * 3 / 2;
    // SHADOW BACK
    drawString(text, x + 2, y + 2, new Color(70, 120, 80));
    // BACK
    drawString(text, x, y, Color.white);

    if (commandNum == 0) {
      g2D.drawString(">", x - GamePanel.tileSize / 2, y);
    }
  }

  private void drawPauseScreen() {

    if (pauseScreenState == 0) {
      // SUB WINDOW
      int x1 = GamePanel.tileSize * 3;
      int y1 = GamePanel.tileSize * 3;
      int width = GamePanel.screenWidth - 2 * x1;
      int height = GamePanel.tileSize * 6;
      drawSubWindowForDialogue(x1, y1, width, height);

      String text = "PAUSED";
      int x = findCenterOfTextString(text);
      int y = GamePanel.tileSize * 4;
      //SHADOW PAUSED
      drawString(text, x + 2, y + 2, new Color(70, 120, 80));
      //PAUSED
      drawString(text, x, y, Color.white);

      String[] texts = new String[]{"Resume","Map", "Controls", "Settings", "Quit"};
      y += GamePanel.tileSize * 2/2;
      g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 24F));

      for (int i = 0; i < texts.length; i++) {
        x = findCenterOfTextString(texts[i]);
        y += GamePanel.tileSize * 2/3;
        // SHADOW RESUME
        drawString(texts[i], x + 2, y + 2, new Color(70, 120, 80));
        // RESUME
        drawString(texts[i], x, y, Color.white);

        if (commandNum == i) {
          g2D.drawString(">", x - GamePanel.tileSize / 2, y);
        }
      }

    } else if(pauseScreenState == 1) {
      int x1 = 0;
      int y1 = 0;
      int width = GamePanel.screenWidth;
      int height = GamePanel.screenHeight;
      drawSubWindowForDialogue(x1, y1, width, height);
      g2D.drawImage(fetchImage("/world01V2.png"), GamePanel.screenWidth/2 - 216, GamePanel.tileSize, null);
      String text = "Back";
      int x = findCenterOfTextString(text);
      int y = GamePanel.screenHeight - GamePanel.tileSize;
      g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 24F));
      // SHADOW BACK
      drawString(text, x + 2, y + 2, new Color(70, 120, 80));
      // BACK
      drawString(text, x, y, Color.white);
      if (commandNum == 0) {
        g2D.drawString(">", x - GamePanel.tileSize / 2, y);
      }

    } else if (pauseScreenState == 2) {
      drawControl();
    } else if (pauseScreenState == 3) {
      drawSettingScreen();
    }
  }

  private void drawDialogueScreen() {
    if (dialogueScreenState == 0) {
      // WINDOW
      int x = GamePanel.tileSize * 2;
      int y = GamePanel.tileSize / 2;
      int width = GamePanel.screenWidth - (2 * x);
      int height = GamePanel.tileSize * 4;
      drawSubWindowForDialogue(x, y, width, height);

      g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 18F));
      x += GamePanel.tileSize;
      y += GamePanel.tileSize;

      if (GamePanel.ui.currentDialogue == null) {
        if (!GamePanel.player.haveTalkedToOnceAlready) {
          for (String chunk : dialogueArray[arrayIndex].split("\n")) {
            g2D.drawString(chunk, x, y);
            y += 20;
          }
        }
        if (keyDialogueComplete) {
          for (String line : responsesArray[1].split("\n")) {
            g2D.drawString(line, x, y);
            y += 20;
          }
        } else if (GamePanel.player.haveTalkedToOnceAlready) {
          for (String line : responsesArray[2].split("\n")) {
            g2D.drawString(line, x, y);
            y += 20;
          }
        }
      } else {
        for (String line : currentDialogue.split("\n")) {
          g2D.drawString(line, x, y);
        }
      }

    } else if (dialogueScreenState == 1) {
      // WINDOW1
      int x1 = GamePanel.tileSize * 2;
      int y1 = GamePanel.tileSize / 2;
      int width1 = GamePanel.screenWidth - (2 * x1);
      int height1 = GamePanel.tileSize * 4;
      drawSubWindowForDialogue(x1, y1, width1, height1);

      g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 18F));
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
          y1 += 18;
        }
      }

      // WINDOW2
      if (GamePanel.ui.currentDialogue == null) {
        int x2 = GamePanel.tileSize * 11;
        int y2 = (int) (GamePanel.tileSize * 4.5);
        int width2 = GamePanel.tileSize * 3;
        int height2 = GamePanel.tileSize * 3;
        drawSubWindowForDialogue(x2, y2, width2, height2);

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

  private void drawInventory() {
    for (int i = 0; i < 5; i++) {
      int frameX = GamePanel.tileSize * (11 + i);
      int frameY = 0;
      int frameWidth = GamePanel.tileSize;
      int frameHeight = GamePanel.tileSize;

      Color c = new Color(0, 0, 0, 140);

      drawSubWindowForDrawInventory(frameX, frameY, frameWidth, frameHeight, c);

      if (i < GamePanel.player.inventory.size()) {
        var item = GamePanel.player.inventory.get(i);
        g2D.drawImage(item.portrait, frameX, 0, null);
      } else {
        drawString(String.valueOf(i + 1), frameX + 14, 38, new Color(255, 255, 255, 180));
      }
    }
  }

  private void drawInventoryScreen() {
    drawSubWindowForDrawInventory(0, 0, GamePanel.screenWidth, GamePanel.screenHeight, new Color(0, 0, 0, 200));

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
        g2D.drawImage(item.portrait, x, 0, null);
        g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 10F));
        drawString(item.name, x - (((int) g2D.getFontMetrics().getStringBounds(item.name, g2D).getWidth()-GamePanel.tileSize)/2), 56, Color.WHITE);
      }
    } else {
      g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 40F));
      g2D.drawString(text, x + 14, 38);
      g2D.setStroke(new BasicStroke(3));
      g2D.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }
  }

  private void drawTime() {
    int frameX = GamePanel.tileSize * 56 / 8;
    int frameY = GamePanel.tileSize * 7 / 4;

    long remainingTime = GamePanel.gameTimeLimit - GamePanel.gameTime;
    long min = (remainingTime % 3600) / 60;
    long sec = remainingTime % 60;
    String time = String.format("%02d:%02d", min, sec);

    g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 40F));
    if(remainingTime < 180) g2D.setColor(new Color(210,60,30));
    g2D.drawString(time, frameX, frameY);
  }

  private void drawWinScreen() {
    drawSubWindowForDrawInventory(0, 0, GamePanel.screenWidth, GamePanel.screenHeight, new Color(0, 0, 0, 200));
    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 96F));
    String text = "Potion Quest";

    int x = findCenterOfTextString(text);
    int y = GamePanel.tileSize * 3;
    // SHADOW TEXT
    drawString(text, x + 5, y + 5, new Color(70, 120, 80));
    //MAIN COLOR
    drawString(text, x, y, Color.white);

    try {
      y += GamePanel.tileSize * 3/2;
      for (String line : GameClientUtil.retrieveMessage("winGameMessage").split("\n")) {
        g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN, 20F));
        x = findCenterOfTextString(line);
        y += 24;
        drawString(line, x+3, y+3, new Color(70, 120, 80));
        drawString(line, x, y, Color.WHITE);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    // PLAYER IMAGE
    x = GamePanel.screenWidth / 2 - (GamePanel.tileSize * 2) / 2 - GamePanel.tileSize *4 / 4;
    y += GamePanel.tileSize;
    g2D.drawImage(GamePanel.player.goDown[0], x, y, GamePanel.tileSize * 3 /2, GamePanel.tileSize * 3,
        null);
    x += GamePanel.tileSize * 5 / 2;
    g2D.drawImage(GamePanel.npc[1].goDown[0], x, y + GamePanel.tileSize / 2, GamePanel.tileSize * 3 /2, GamePanel.tileSize * 2,
        null);

    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 30F));
    text = "Press Z To Continue...";
    x = findCenterOfTextString(text);
    y += GamePanel.tileSize * 4;

    drawString(text, x + 5, y + 5, new Color(70, 120, 80));
    //MAIN COLOR
    drawString(text, x, y, Color.white);
  }

  private int creditsY = GamePanel.tileSize;

  private void drawCredits() {
    if(GamePanel.creditsFrameCount < GamePanel.FPS * 16) {
      GamePanel.creditsFrameCount++;
      if(GamePanel.creditsFrameCount > GamePanel.FPS) {
        creditsY--;
      }
    } else {
      //GamePanel.creditsFrameCount = 0;
      GamePanel.creditStateDisplayed = true;
    }

    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 40F));
    String text = "Team 4 Staff";
    int x = findCenterOfTextString(text);
    int y = creditsY;

    y += GamePanel.tileSize * 7 / 2;

    // SHADOW BACK
    drawString(text, x + 2, y + 2, new Color(70, 120, 80));
    // BACK
    drawString(text, x, y, Color.white);

    String texts[] = new String[] {"Cameron Davis", "Chongwei Ma", "Ethan Wang"};
    y += GamePanel.tileSize / 2;
    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 30F));

    for(int i = 0; i < texts.length; i++) {
      x = findCenterOfTextString(texts[i]);
      y += GamePanel.tileSize;

      drawString(texts[i], x + 2, y + 2, new Color(70, 120, 80));
      // BACK
      drawString(texts[i], x, y, Color.white);
    }

    text = "Initial Game Concept: Team 1";
    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 40F));
    x = findCenterOfTextString(text);
    y += GamePanel.tileSize * 6;

    // SHADOW BACK
    drawString(text, x + 2, y + 2, new Color(70, 120, 80));
    // BACK
    drawString(text, x, y, Color.white);

    String teamsOne[] = new String[] {"Bryce Meadors", "Cynthia Pottin", "Joe Savella"};
    y += GamePanel.tileSize / 4;
    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 30F));

    for(int i = 0; i < texts.length; i++) {
      x = findCenterOfTextString(texts[i]);
      y += GamePanel.tileSize;

      drawString(teamsOne[i], x + 2, y + 2, new Color(70, 120, 80));
      // BACK
      drawString(teamsOne[i], x, y, Color.white);
    }

    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 40F));
    text = "Special Thanks";
    x = findCenterOfTextString(text);
    y += GamePanel.tileSize * 4;

    // SHADOW BACK
    drawString(text, x + 2, y + 2, new Color(70, 120, 80));
    // BACK
    drawString(text, x, y, Color.white);

    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 40F));
    text = "RyiSnow's Youtube Channel";
    x = findCenterOfTextString(text) ;
    y += GamePanel.tileSize * 2;

    // SHADOW BACK
    drawString(text, x + 2, y + 2, new Color(70, 120, 80));
    // BACK
    drawString(text, x, y, Color.white);

    g2D.setFont(g2D.getFont().deriveFont(Font.BOLD, 30F));
    text = "Press Any Key To Continue...";
    x = findCenterOfTextString(text);
    y += GamePanel.tileSize * 5;

    // SHADOW BACK
    drawString(text, x + 2, y + 2, new Color(70, 120, 80));
    // BACK
    drawString(text, x, y, Color.white);
  }


  public void drawCoin() {
    int frameX = GamePanel.tileSize * 7;
    g2D.drawImage(GamePanel.player.coin.portrait, frameX, 4, null);

    drawString("x "+ GamePanel.player.coinInPocket, frameX + 48, 38, new Color(255, 255, 255, 180));
  }

  private void drawPlayerHP() {
    int x = GamePanel.tileSize / 2;
    int y = 0;

    for (int i = 0; i < (GamePanel.player.HP - 1) / (heart.images.size() - 1); i++) {
      g2D.drawImage(heart.images.get(0), x, y, null);
      x += GamePanel.tileSize;
    }

    if (GamePanel.player.HP > 0) {
      int index = GamePanel.player.HP % (heart.images.size() - 1);

      g2D.drawImage(heart.images.get(index), x, y, null);
      x += GamePanel.tileSize;
    }

    for (int i = 0; i < (GamePanel.player.MAX_HP - GamePanel.player.HP) / (heart.images.size() - 1);
        i++) {
      g2D.drawImage(heart.images.get(4), x, y, null);
      x += GamePanel.tileSize;
    }
  }

  public void drawString(String text, int x, int y, Color color) {
    g2D.setColor(color);
    g2D.drawString(text, x, y);
  }

  private void drawSubWindowForDrawInventory(int x, int y, int width, int height, Color c) {
    g2D.setColor(c);
    int arcWidth = 28;
    int arcHeight = 28;
    g2D.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
  }

  private void drawSubWindowForDialogue(int x, int y, int width, int height) {

    Color c = new Color(0, 0, 0, 200);
    g2D.setColor(c);
    g2D.fillRoundRect(x, y, width, height, 35, 35);

    c = new Color(255, 255, 255);
    g2D.setColor(c);
    g2D.setStroke(new BasicStroke(5));
    g2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
  }

  private int findCenterOfTextString(String text) {
    int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
    return GamePanel.screenWidth / 2 - length / 2;
  }

  private BufferedImage fetchImage(String filePath) {
    BufferedImage image = null;
    try (InputStream input = getClass().getResourceAsStream(filePath)) {
      assert input != null;
      image = ImageIO.read(input);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return image;
  }
}
