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

public class TileLayer01 {

  GamePanel gp;
  public Tile[] tile;
  int tileSheetCol = 47;
  int tileSheetRow = 70;
  public int[][] mapTileNum;

  public TileLayer01(GamePanel gp) {

    this.gp = gp;
    tile = new Tile[3456];
    mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

    getTileImage("/tiles/CombinedTilesheet.png");
    loadMap("/maps/world01V2_TileLayer1.csv");
  }

  public void getTileImage(String filePath) {
    try (InputStream inputStream = getClass().getResourceAsStream(filePath)) {

      //noinspection ConstantConditions
      BufferedImage tileSheet = ImageIO.read(inputStream);

      int colCount = 0;
      int rowCount = 0;
      int i = 0;

      while (rowCount <= tileSheetRow) {
        while (colCount <= tileSheetCol) {
          tile[i] = new Tile();
          tile[i].image = tileSheet.getSubimage(colCount*gp.tileSize, rowCount*gp.tileSize, gp.tileSize, gp.tileSize);
          tile[i].collision = false;
          if (i == 2067) {
            tile[i].collision = true;
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
    try (InputStream inputStream = getClass().getResourceAsStream(filePath)) {

      //noinspection ConstantConditions
      BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));

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
      int worldX = worldCol * gp.tileSize;
      int worldY = worldRow * gp.tileSize;
      int screenX = worldX - gp.player.worldX + gp.player.screenX;
      int screenY = worldY - gp.player.worldY + gp.player.screenY;

      if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
          && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
          && worldY + gp.tileSize*2 > gp.player.worldY - gp.player.screenY
          && worldY - gp.tileSize*2 < gp.player.worldY + gp.player.screenY) {

        g2D.drawImage(tile[tileNum].image, screenX, screenY, null);
      }
      worldCol++;

      if (worldCol == gp.maxWorldCol) {
        worldCol = 0;
        worldRow++;
      }
    }
  }
}