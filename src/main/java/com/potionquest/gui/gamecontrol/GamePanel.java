package com.potionquest.gui.gamecontrol;

import com.potionquest.gui.entity.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import com.potionquest.gui.tile.*;

public class GamePanel extends JPanel implements Runnable {

  //SCREEN SETTINGS
//  public final int tileSize  = ORIGINAL_TILE_SIZE * SCALE_FACTOR; // 48x48 tile
  public final int tileSize  = 48; // 48x48 tile
  public final int maxScreenCol = 16; // 16 columns of 48x48
  public final int maxScreenRow = 12; // 12 columns of 48x48
  public final int screenWidth = tileSize * maxScreenCol; // 16 * 48 = 768 px
  public final int screenHeight = tileSize * maxScreenRow; // 12 * 48 = 576 px

  //WORLD SETTINGS - change these to change world map dimensions
  public final int maxWorldCol = 90;
  public final int maxWorldRow = 90;
//  public final int worldWidth = tileSize * maxWorldCol;
//  public final int worldHeight = tileSize * maxWorldRow;

  //FPS
  public static final double FPS = 60;

  // SYSTEM
  TileLayer01 tileMLayer1 = new TileLayer01(this);
  TileLayer02 tileMLayer2 = new TileLayer02(this);
  TileLayer03 tileMLayer3 = new TileLayer03(this);
  KeyHandler keyH = new KeyHandler(this);
  public CollisionChecker collider = new CollisionChecker(this);
  public AssetPlacer aPlacer = new AssetPlacer(this);
  public UI ui = new UI(this);
  Thread gameThread;

  // ENTITIES AND OBJECTS
  public Player player = new Player(this, keyH);

  // GAME STATE
  public int gameState;
  public final int pauseState = 0;
  public final int playState = 1;

  public GamePanel() {

    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.BLACK);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyH);
    this.setFocusable(true);
  }

  //place setUpGame method here where NPC/objects/monsters are placed
  public void setUpWorld() {

    gameState = playState;
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
        System.out.println("FPS: " + drawCount);
        drawCount = 0;
        timer = 0;
      }
    }
  }

  public void update() {

    if (gameState == playState) {
      player.update();
    } else if (gameState == pauseState) {
      // nothing for now
    }
  }

  public void paintComponent(Graphics g) {

    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D) g;

    tileMLayer1.draw(g2D);
    tileMLayer2.draw(g2D);
    tileMLayer3.draw(g2D);
    player.draw(g2D);
    ui.draw(g2D);

    g2D.dispose();
  }
}
