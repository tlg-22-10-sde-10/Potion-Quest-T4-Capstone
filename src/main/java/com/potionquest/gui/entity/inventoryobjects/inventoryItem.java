package com.potionquest.gui.entity.inventoryobjects;

import com.potionquest.gui.entity.Entity;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class inventoryItem extends Entity {

  public int objectWidth;
  public int objectHeight;
  public int attackValue;

  public String name;
  public boolean collisionOn = false;
  public List<BufferedImage> images = new ArrayList<>();
}
