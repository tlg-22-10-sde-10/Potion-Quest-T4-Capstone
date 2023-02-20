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

  public Tile[] tile;
  int tileSheetCol = 47;
  int tileSheetRow = 70;
  public int[][] mapTileNum;

  public TileLayer01() {

    tile = new Tile[3456];
    mapTileNum = new int[GamePanel.maxWorldCol][GamePanel.maxWorldRow];

    getTileImage("/tiles/CombinedTilesheet.png");
    loadMap("/maps/world01V2_TileLayer1.csv");
  }

  public void getTileImage(String filePath) {
    try (InputStream inputStream = getClass().getResourceAsStream(filePath)){

      //noinspection ConstantConditions
      BufferedImage tileSheet = ImageIO.read(inputStream);

      int colCount = 0;
      int rowCount = 0;
      int i = 0;

      while (rowCount <= tileSheetRow) {
        while (colCount <= tileSheetCol) {
          tile[i] = new Tile();
          tile[i].image = tileSheet.getSubimage(colCount*GamePanel.tileSize, rowCount*GamePanel.tileSize, GamePanel.tileSize, GamePanel.tileSize);
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

      while (col < GamePanel.maxWorldCol && row < GamePanel.maxWorldRow) {

        String line = bReader.readLine();

        while (col < GamePanel.maxWorldCol) {

          String[] numbers = line.split(",");
          int num = Integer.parseInt(numbers[col]);
          mapTileNum[col][row] = num;
          col++;
        }
        if (col == GamePanel.maxWorldCol) {
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

    while (worldCol < GamePanel.maxWorldCol && worldRow < GamePanel.maxWorldRow) {

      int tileNum = mapTileNum[worldCol][worldRow];
      int worldX = worldCol * GamePanel.tileSize;
      int worldY = worldRow * GamePanel.tileSize;
      int screenX = worldX - GamePanel.player.worldX + GamePanel.player.screenX;
      int screenY = worldY - GamePanel.player.worldY + GamePanel.player.screenY;

      if (worldX + GamePanel.tileSize > GamePanel.player.worldX - GamePanel.player.screenX
          && worldX - GamePanel.tileSize < GamePanel.player.worldX + GamePanel.player.screenX
          && worldY + GamePanel.tileSize*2 > GamePanel.player.worldY - GamePanel.player.screenY
          && worldY - GamePanel.tileSize*2 < GamePanel.player.worldY + GamePanel.player.screenY) {

        g2D.drawImage(tile[tileNum].image, screenX, screenY, null);
      }
      worldCol++;

      if (worldCol == GamePanel.maxWorldCol) {
        worldCol = 0;
        worldRow++;
      }
    }
  }
}
