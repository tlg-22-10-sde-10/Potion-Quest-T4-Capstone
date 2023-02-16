package com.potionquest.gui.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

  public int sizeX, sizeY;
  public int scaleFactor;
  public int worldX, worldY;
  public int speed;

  public BufferedImage[] goUp = new BufferedImage[4];
  public BufferedImage[] goDown = new BufferedImage[4];
  public BufferedImage[] goLeft = new BufferedImage[4];
  public BufferedImage[] goRight = new BufferedImage[4];
  public String direction;

  public int spriteCounter = 0;
  public int spriteNum = 1;

  public Rectangle solidArea;
  public boolean collisionOn = false;
}
