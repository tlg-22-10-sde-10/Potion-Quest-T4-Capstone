package com.potionquest.gui.tile;

import com.potionquest.gui.gamecontrol.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import javax.imageio.ImageIO;

public class TileLayer02 {

  GamePanel gp;
  public Tile[] tile;
  int tileSheetCol = 39;
  int tileSheetRow = 35;
  public int[][] mapTileNum;

  public TileLayer02(GamePanel gp) {

    this.gp = gp;
    tile = new Tile[1440];
    mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

    getTileImage();
    loadMap("/maps/map01_layer02.csv");
  }

  public void getTileImage() {
    try {

      BufferedImage tileSheet = ImageIO.read(
          Objects.requireNonNull(getClass().getResourceAsStream("/tiles/overworld.png")));

      int colCount = 0;
      int rowCount = 0;
      int i = 0;

      while (rowCount <= tileSheetRow) {
        while (colCount <= tileSheetCol) {
          tile[i] = new Tile();
          tile[i].image = tileSheet.getSubimage(colCount*gp.tileSize, rowCount*gp.tileSize, gp.tileSize, gp.tileSize);
          tile[i].collision = true;

          //noinspection RedundantIfStatement
          if (colCount == 39 && rowCount == 0) {
            tile[i].collision = false;
          }
          i++;
          colCount++;
        }
        colCount = 0;
        rowCount++;
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void loadMap(String filePath) {
    try {

      InputStream is = getClass().getResourceAsStream(filePath);
      BufferedReader bReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

      int col = 0;
      int row = 0;

      while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

        String line = bReader.readLine();

        while (col < gp.maxWorldCol) {

          String[] numbers = line.split(",");
          int num = Integer.parseInt(numbers[col]);
          mapTileNum[col][row] = num;
          col++;
        }
        if (col == gp.maxWorldCol) {
          col = 0;
          row++;
        }
      }
      bReader.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void draw (Graphics2D g2D) {

    int worldCol = 0;
    int worldRow = 0;

    while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

      int tileNum = mapTileNum[worldCol][worldRow];

      if (tileNum == -1) {
        tileNum = 39;
      }

      int worldX = worldCol * gp.tileSize;
      int worldY = worldRow * gp.tileSize;
      int screenX = worldX - gp.player.worldX + gp.player.screenX;
      int screenY = worldY - gp.player.worldY + gp.player.screenY;

      if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
          && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
          && worldY + gp.tileSize*2 > gp.player.worldY - gp.player.screenY
          && worldY - gp.tileSize*2 < gp.player.worldY + gp.player.screenY) {

        g2D.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
      }
      worldCol++;

      if (worldCol == gp.maxWorldCol) {
        worldCol = 0;
        worldRow++;
      }
    }
  }
}