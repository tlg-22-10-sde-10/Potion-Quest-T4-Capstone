package com.potionquest.gui.gamecontrol;

import com.potionquest.game.Monster;
import com.potionquest.game.Sound;
import com.potionquest.gui.entity.*;
import com.potionquest.gui.entity.inventoryobjects.InventoryItem;
import com.potionquest.gui.entity.monsters.MonsterPrototype;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.io.IOException;
import java.util.Map;
import javax.swing.JPanel;
import com.potionquest.gui.tile.*;

public class GamePanel extends JPanel implements Runnable {

  //SCREEN SETTINGS
//  public final int tileSize  = ORIGINAL_TILE_SIZE * SCALE_FACTOR; // 48x48 tile
  public static final int tileSize  = 48; // 48x48 tile
  public static final int maxScreenCol = 16; // 16 columns of 48x48
  public static final int maxScreenRow = 12; // 12 columns of 48x48
  public static final int screenWidth = tileSize * maxScreenCol; // 16 * 48 = 768 px
  public static final int screenHeight = tileSize * maxScreenRow; // 12 * 48 = 576 px

  //WORLD SETTINGS - change these to change world map dimensions
  public static final int maxWorldCol = 90;
  public static final int maxWorldRow = 90;

  //FPS
  public static final double FPS = 60;

  // SYSTEM
  public static TileLayer01 tileMLayer1;
  public static TileLayer02 tileMLayer2;
  public static TileLayer03 tileMLayer3;

  public static KeyHandler keyH = new KeyHandler();
  public static CollisionChecker collider = new CollisionChecker();
  public static AssetPlacer aPlacer = new AssetPlacer();

  public static UI ui;
  public static EventHandler eHandler = new EventHandler();

  private static Thread gameThread;

  // ENTITIES AND OBJECTS
  public static Entity[] npc;
  public static InventoryItem[] items;
  public static Player player;
  public static Entity[] monsters;

  // GAME STATE
  public static int gameState;
  public static final int titleState = -1;
  public static final int SETTING_STATE = -2;

  public static final int pauseState = 0;
  public static final int playState = 1;
  public static final int dialogueState = 2;
  public static final int inventoryState = 3;
  
  public static final int gameOverState = 6;
  public static final int winState = 7;

  //self defined
  public static Sound sound = new Sound();

  public static long gameTime;
  public static final int gameTimeLimit = 420;

  //monster library json
  public static Map<String, Monster> monsterLibrary;

  public GamePanel() {
    this.setUpWorld();

    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.BLACK);
    this.setDoubleBuffered(true);

    this.addKeyListener(keyH);
    this.setFocusable(true);
  }

  //place setUpGame method here where NPC/objects/monsters are placed
  public void setUpWorld() {
    jsonInitialisation();

    TileSheets.gameTileSheetInitialization();

    tileMLayerInitialization();

    ui = new UI();

    gameInstanceInitialization();

    gameState = titleState;
    GamePanel.ui.titleScreenState = 0;
  }

  public void jsonInitialisation() {
    try {
      monsterLibrary = Monster.monsterJsonParser();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void gameInstanceInitialization() {
    npc = new Entity[10];
    items = new InventoryItem[20];
    player = new Player();
    monsters = new MonsterPrototype[10];

    gameTime = 0;

    aPlacer.setObjects();
    aPlacer.setNPC();
    aPlacer.setMonster();
  }

  public void tileMLayerInitialization() {
    tileMLayer1 = new TileLayer01();
    tileMLayer2 = new TileLayer02();
    tileMLayer3 = new TileLayer03();
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    double drawInterval = 1000000000/FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;

    sound.playSound();

    while (gameThread != null) {

      currentTime = System.nanoTime();

      delta += (currentTime - lastTime) / drawInterval;
      timer += (currentTime - lastTime);
      lastTime = currentTime;

      if (delta >= 1) {
        update();
        repaint();
        delta--;
      }

      if (timer >= 1000000000) {
        if(gameState == playState) gameTime++;
        timer = 0;
      }
    }
  }

  public void update() {
    if (gameState == playState) {
      //resume the music
      if(sound.getClip() != null && !sound.getClip().isRunning()) {
        sound.getClip().start();
      }
      // PLAYER
      player.update();
      if(player.HP<=0 || gameTime >= gameTimeLimit) {
        GamePanel.gameState = GamePanel.gameOverState;
      }

      //OBJECTS
      for (InventoryItem item : items) {
        if (item != null) {
          item.update();
        }
      }

      // NPC
      for (Entity entity : npc) {
        if (entity != null) {
          entity.update();
        }
      }

      for (Entity monster : monsters) {
        if (monster != null) {
          monster.update();
        }
      }

    } else if (gameState == pauseState) {
      // nothing for now
      if (sound.getClip() != null && sound.getClip().isRunning()) {
        sound.getClip().stop();
      }
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D) g;

    //TITLE SCREEN
    if (gameState != titleState) {
      // TILES
      tileMLayer1.draw(g2D);
      tileMLayer2.draw(g2D);
      tileMLayer3.draw(g2D);

      // OBJECTS
      for (InventoryItem item : items) {
        if (item != null) {
          item.draw(g2D);
        }
      }

      // NPC
      for (Entity entity : npc) {
        if (entity != null) {
          entity.draw(g2D);
        }
      }

      for (Entity monster : monsters) {
        if (monster != null) {
          monster.draw(g2D);
        }
      }

      //PLAYER
      player.draw(g2D);
    }

    //UI
    ui.draw(g2D);
    g2D.dispose();
  }

  public static long getGameTime() {
    return gameTime;
  }
}