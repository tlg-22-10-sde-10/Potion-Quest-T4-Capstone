package com.potionquest.gui.gamecontrol;

import com.potionquest.game.Sound;
import com.potionquest.game.Timer;
import com.potionquest.gui.entity.*;
import com.potionquest.gui.entity.monsters.MonsterPrototype;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;


//66 18
import java.awt.event.KeyEvent;

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
//  public final int worldWidth = tileSize * maxWorldCol;
//  public final int worldHeight = tileSize * maxWorldRow;

  //FPS
  public static final double FPS = 60;

  // SYSTEM
  public static TileLayer01 tileMLayer1 = new TileLayer01();
  public static TileLayer02 tileMLayer2 = new TileLayer02();
  public static TileLayer03 tileMLayer3 = new TileLayer03();

  public static KeyHandler keyH = new KeyHandler();
  public static CollisionChecker collider = new CollisionChecker();
  public static AssetPlacer aPlacer = new AssetPlacer();

  //public UI ui = new UI(this);

  public static UI ui = new UI();
  public static EventHandler eHandler = new EventHandler();

  private static Thread gameThread;

  // ENTITIES AND OBJECTS
  public static Entity[] npc = new Entity[10];
  public static Player player = new Player();
  public static Entity[] monsters = new MonsterPrototype[10];

  // GAME STATE
  public static int gameState;
  public static final int titleState = -1;
  public static final int pauseState = 0;
  public static final int playState = 1;
  public static final int dialogueState = 2;
  public static final int inventoryState = 3;

  //self defined
  private Sound sound = new Sound();
  private static long gameTime = 0;


  public GamePanel() {

    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.BLACK);
    this.setDoubleBuffered(true);

    this.addKeyListener(keyH);
    this.setFocusable(true);
  }

  //place setUpGame method here where NPC/objects/monsters are placed
  public void setUpWorld() {

    aPlacer.setNPC();
    aPlacer.setMonster();

    gameState = titleState;
//    aPlacer.setStuff
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
    int drawCount = 0;

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
        drawCount++;
      }

      if (timer >= 1000000000) {
        //System.out.println("FPS: " + drawCount);
        if(gameState == playState) gameTime++;

//        System.out.println(gameTime);
//        if(gameTime > 15) {
//          System.out.println("game over");
//        }

        drawCount = 0;
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

      // NPC
      for (int i = 0; i < npc.length; i++) {
        if (npc[i] != null) {
          npc[i].update();
        }
      }

      for (int i = 0; i< monsters.length; i++) {
        if(monsters[i] != null) {

          monsters[i].update();
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

      // NPC
      for (int i = 0; i < npc.length; i++) {
        if (npc[i] != null) {
          npc[i].draw(g2D);
        }
      }

      for (int i = 0; i < monsters.length; i++) {
        if (monsters[i] != null) {
          monsters[i].draw(g2D);
        }
      }

      //PLAYER
      player.draw(g2D);
      //UI
    }
    ui.draw(g2D);

    g2D.dispose();
  }

  public static long getGameTime() {
    return gameTime;
  }

}