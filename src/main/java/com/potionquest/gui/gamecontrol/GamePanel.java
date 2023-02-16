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
  final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
  final int SCALE_FACTOR = 3; // scale

  public final int tileSize  = ORIGINAL_TILE_SIZE * SCALE_FACTOR; // 48x48 tile
  public final int maxScreenCol = 16; // 16 columns of 48x48
  public final int maxScreenRow = 12; // 12 columns of 48x48
  public final int screenWidth = tileSize * maxScreenCol; // 16 * 48 = 768 px
  public final int screenHeight = tileSize * maxScreenRow; // 12 * 48 = 576 px

  //WORLD SETTINGS - change these to change world map dimensions
  public final int maxWorldCol = 40;
  public final int maxWorldRow = 30;
  public final int worldWidth = tileSize * maxWorldCol;
  public final int worldHeight = tileSize * maxWorldRow;

  //FPS
  double FPS = 60;

  TileLayer01 tileMLayer1 = new TileLayer01(this);
  TileLayer02 tileMLayer2 = new TileLayer02(this);
  KeyHandler keyH = new KeyHandler();
  Thread gameThread;
  public CollisionChecker collider = new CollisionChecker(this);
  public Player player = new Player(this, keyH);

  public GamePanel() {

    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.BLACK);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyH);
    this.setFocusable(true);
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

    player.update();
  }

  public void paintComponent(Graphics g) {

    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D) g;

    tileMLayer1.draw(g2D);
    tileMLayer2.draw(g2D);
    player.draw(g2D);

    g2D.dispose();
  }
}
