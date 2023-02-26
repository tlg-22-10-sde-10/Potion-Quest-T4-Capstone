package com.potionquest.gui.gamecontrol;

import static com.potionquest.gui.gamecontrol.GamePanel.winState;

import com.potionquest.gui.entity.Player;
import com.potionquest.gui.entity.inventoryobjects.AttunedGemstone;
import com.potionquest.gui.entity.inventoryobjects.ElixirOfLife;
import com.potionquest.gui.entity.inventoryobjects.InventoryItem;
import com.potionquest.gui.entity.inventoryobjects.SwordOfAThousandTruths;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class KeyHandler implements KeyListener {

  public boolean upPressed, downPressed, leftPressed, rightPressed, zPressed, spacePressed;

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();

    // TITLE STATE
    if (GamePanel.gameState == GamePanel.titleState) {
      titleState(code);
    }
    // setting mode
    else if (GamePanel.gameState == GamePanel.SETTING_STATE) {
      settingState(code);
    }
    // PLAY STATE
    else if (GamePanel.gameState == GamePanel.playState) {
      playState(code);
    }
    // PAUSE STATE
    else if (GamePanel.gameState == GamePanel.pauseState) {
      pauseState(code);
    }
    // DIALOGUE STATE
    else if (GamePanel.gameState == GamePanel.dialogueState) {
      dialogueState(code);
    }
    // INVENTORY STATE
    else if (GamePanel.gameState == GamePanel.inventoryState) {
      inventoryState(code);
    }
    //Game over
    else if (GamePanel.gameState == GamePanel.gameOverState) {
      gameOverState(code);
    }
    else if (GamePanel.gameState == GamePanel.winState) {
      winState(code);
    }
    else if (GamePanel.gameState == GamePanel.creditState) {
      creditState(code);
    }
  }

  public void titleState(int code) {
    if (GamePanel.ui.titleScreenState == 0) {
      if (code == KeyEvent.VK_UP) {
        GamePanel.ui.commandNum--;
        if (GamePanel.ui.commandNum < 0) {
          GamePanel.ui.commandNum = 3;
        }
      }
      if (code == KeyEvent.VK_DOWN) {
        GamePanel.ui.commandNum++;
        if (GamePanel.ui.commandNum > 3) {
          GamePanel.ui.commandNum = 0;
        }
      }
      if (code == KeyEvent.VK_Z) {
        switch (GamePanel.ui.commandNum) {
          case 0:
            GamePanel.ui.titleScreenState = 1;
            break;
          case 1:
            //music control
            GamePanel.ui.titleScreenState = 2;
            break;
          case 2:
            GamePanel.ui.titleScreenState = 3;
            break;
          case 3:
            System.exit(0);
        }
        GamePanel.ui.commandNum = 0;
      }
    } else if (GamePanel.ui.titleScreenState == 1) {
      if (code == KeyEvent.VK_Z) {
        GamePanel.gameState = GamePanel.playState;
        GamePanel.ui.commandNum = 0;
      }
    } else if (GamePanel.ui.titleScreenState == 2) {
      settingState(code);
    } else if (GamePanel.ui.titleScreenState == 3) {
      if (code == KeyEvent.VK_Z) {
        GamePanel.ui.titleScreenState = 0;
        GamePanel.ui.commandNum = 0;
      }
    }
  }

  public void settingState(int code) {
    if (code == KeyEvent.VK_UP) {
      GamePanel.ui.commandNum--;
      if (GamePanel.ui.commandNum < 0) {
        GamePanel.ui.commandNum = 3;
      }
    }
    if (code == KeyEvent.VK_DOWN) {
      GamePanel.ui.commandNum++;
      if (GamePanel.ui.commandNum > 3) {
        GamePanel.ui.commandNum = 0;
      }
    }
    if (code == KeyEvent.VK_Z) {
      switch (GamePanel.ui.commandNum) {
        case 0:
          if (GamePanel.sound.getClip() != null) {
            GamePanel.sound.turnUpVolume();
          }
          break;
        case 1:
          if (GamePanel.sound.getClip() != null) {
            GamePanel.sound.turnDownVolume();
          }
          break;
        case 2:
          if (GamePanel.sound.getClip() != null) {
            GamePanel.sound.muteToggle();
          }
          break;
        case 3:
          GamePanel.ui.commandNum = 0;
          if (GamePanel.ui.pauseScreenState == 3) {
            GamePanel.ui.pauseScreenState = 0;
            GamePanel.gameState = GamePanel.pauseState;
          } else {
            GamePanel.ui.titleScreenState = 0;
          }
          break;
      }
    }
  }

  public void playState(int code) {
    // PLAY STATE
    if (code == KeyEvent.VK_UP) {
      upPressed = true;
    }
    if (code == KeyEvent.VK_DOWN) {
      downPressed = true;
    }
    if (code == KeyEvent.VK_LEFT) {
      leftPressed = true;
    }
    if (code == KeyEvent.VK_RIGHT) {
      rightPressed = true;
    }
    if (code == KeyEvent.VK_ENTER) {
      GamePanel.gameState = GamePanel.pauseState;
    }
    if (code == KeyEvent.VK_SPACE) {
      spacePressed = true;
    }
    if (code == KeyEvent.VK_Z) {
      zPressed = true;
      if (GamePanel.player.worldX < 13 * GamePanel.tileSize
          && GamePanel.player.worldX > 11 * GamePanel.tileSize
          && GamePanel.player.worldY > 60 * GamePanel.tileSize
          && GamePanel.player.worldY < 62 * GamePanel.tileSize) {
        UI.statement = "Go South";
        GamePanel.player.inventoryFrameCount = 0;
      } else if (GamePanel.player.worldX < 12 * GamePanel.tileSize
          && GamePanel.player.worldX > 10 * GamePanel.tileSize
          && GamePanel.player.worldY > 26 * GamePanel.tileSize
          && GamePanel.player.worldY < 29 * GamePanel.tileSize) {
        UI.statement = "Go North";
        GamePanel.player.inventoryFrameCount = 0;
      }
    }
    if (code == KeyEvent.VK_B) {
      GamePanel.ui.commandNum = 1;
      GamePanel.gameState = GamePanel.inventoryState;
    }
  }

  public void pauseState(int code) {
    if (GamePanel.ui.pauseScreenState == 0) {
      if (code == KeyEvent.VK_UP) {
        GamePanel.ui.commandNum--;
        if (GamePanel.ui.commandNum < 0) {
          GamePanel.ui.commandNum = 4;
        }
      } else if (code == KeyEvent.VK_DOWN) {
        GamePanel.ui.commandNum++;
        if (GamePanel.ui.commandNum > 4) {
          GamePanel.ui.commandNum = 0;
        }
      } else if (code == KeyEvent.VK_Z) {
        switch (GamePanel.ui.commandNum) {
          case 0:
            GamePanel.gameState = GamePanel.playState;
            break;
          case 1:
            GamePanel.ui.pauseScreenState = 1;
            break;
          case 2:
            GamePanel.ui.pauseScreenState = 2;
            break;
          case 3:
            GamePanel.ui.pauseScreenState = 3;
            break;
          case 4:
            GamePanel.gameInstanceInitialization();
            GamePanel.gameState = GamePanel.titleState;
            GamePanel.ui.titleScreenState = 0;
            break;
        }
        GamePanel.ui.commandNum = 0;
      } else if (code == KeyEvent.VK_ENTER) {
        GamePanel.ui.commandNum = 0;
        GamePanel.gameState = GamePanel.playState;
      }
    } else if (GamePanel.ui.pauseScreenState == 1) {
      if (code == KeyEvent.VK_Z) {
        GamePanel.ui.pauseScreenState = 0;
        GamePanel.ui.commandNum = 0;
      }
    } else if (GamePanel.ui.pauseScreenState == 2) {
      if (code == KeyEvent.VK_Z) {
        GamePanel.ui.pauseScreenState = 0;
        GamePanel.ui.commandNum = 0;
      }
    } else if (GamePanel.ui.pauseScreenState == 3) {
      settingState(code);
    }
  }

  public void winState(int code) {
    if(GamePanel.gameState == winState) {
      if(code == KeyEvent.VK_Z) {
        GamePanel.gameState = GamePanel.creditState;
      }
    }
  }

  private void creditState(int code) {
    if(GamePanel.creditStateDisplayed) {
      if(code == KeyEvent.VK_Z
          || code == KeyEvent.VK_ENTER
          || code == KeyEvent.VK_SPACE
          || code == KeyEvent.VK_B
          || code == KeyEvent.VK_UP
          || code == KeyEvent.VK_DOWN
          || code == KeyEvent.VK_LEFT
          || code == KeyEvent.VK_RIGHT)
      {
        GamePanel.gameState = GamePanel.titleState;
        GamePanel.gameInstanceInitialization();
      }
    }
  }

  public void dialogueState(int code) {
    if (GamePanel.ui.dialogueScreenState == 0) {
      if (code == KeyEvent.VK_Z) {
        GamePanel.ui.arrayIndex++;
        if (GamePanel.ui.dialogueArray[GamePanel.ui.arrayIndex] == null
            || GamePanel.player.haveTalkedToOnceAlready) {
          GamePanel.gameState = GamePanel.playState;
          GamePanel.ui.arrayIndex = 0;
        }
      }
    } else if (GamePanel.ui.dialogueScreenState == 1) {

      if (code == KeyEvent.VK_UP) {
        GamePanel.ui.commandNum--;
        if (GamePanel.ui.commandNum < 0) {
          GamePanel.ui.commandNum = 1;
        }
      }
      if (code == KeyEvent.VK_DOWN) {
        GamePanel.ui.commandNum++;
        if (GamePanel.ui.commandNum > 1) {
          GamePanel.ui.commandNum = 0;
        }
      }
      if (code == KeyEvent.VK_Z) {
        switch (GamePanel.ui.commandNum) {
          case 0:
            if (GamePanel.player.npcIndex == 0) {
              for (InventoryItem item : GamePanel.player.inventory) {
                if (item.name.equals("Elixir of Life")) {
                  GamePanel.gameState = winState;
                  break;
                }
              }
            }
            if (GamePanel.player.npcIndex == 2) {
              if (GamePanel.player.coinInPocket >= 5 &&
                  GamePanel.player.currentWeapon.name.equals("Father's Sword")) {
                if (GamePanel.player.inventory.size() <= Player.INVENTORY_SIZE) {
                  GamePanel.ui.keyDialogueComplete = true;
                  GamePanel.player.forceRemoveItem(GamePanel.player.currentWeapon);
                  GamePanel.player.currentWeapon = new SwordOfAThousandTruths();
                  GamePanel.player.pickUpObject(GamePanel.player.currentWeapon);
                  GamePanel.player.coinInPocket -= 5;
                  GamePanel.ui.dialogueScreenState = 0;
                } else {
                  GamePanel.ui.dialogueScreenState = 0;
                  GamePanel.ui.currentDialogue = "Your inventory is full!";
                }
              }
            }
            if (GamePanel.player.npcIndex == 3) {
              if (GamePanel.player.coinInPocket >= 3) {
                if (GamePanel.player.inventory.size() < Player.INVENTORY_SIZE) {
                  GamePanel.ui.keyDialogueComplete = true;
                  GamePanel.player.coinInPocket -= 3;
                  GamePanel.player.inventory.add(new AttunedGemstone());
                  GamePanel.ui.dialogueScreenState = 0;
                } else {
                  GamePanel.ui.dialogueScreenState = 0;
                  GamePanel.ui.currentDialogue = "Your inventory is full!";
                }
              }
            }
            if (GamePanel.player.npcIndex == 4) {
              if (GamePanel.player.inventory.size() < Player.INVENTORY_SIZE) {
                for (InventoryItem item : GamePanel.player.inventory) {
                  if (item.name.equals("Ornate Trinket")) {
                    GamePanel.ui.keyDialogueComplete = true;
                    GamePanel.player.inventory.remove(item);
                    GamePanel.player.inventory.add(new ElixirOfLife());
                    GamePanel.ui.dialogueScreenState = 0;
                    break;
                  }
                }
                if (GamePanel.player.coinInPocket >= 10 && !GamePanel.ui.keyDialogueComplete) {
                  GamePanel.ui.keyDialogueComplete = true;
                  GamePanel.player.coinInPocket -= 10;
                  GamePanel.player.inventory.add(new ElixirOfLife());
                  //GamePanel.ui.dialogueScreenState = 0;
                } else {
                  //GamePanel.ui.dialogueScreenState = 0;
                  GamePanel.ui.currentDialogue = "Your inventory is full!";
                }
              }
            }
          case 1:
            GamePanel.ui.dialogueScreenState = 0;
            GamePanel.ui.keyDialogueComplete = false;
            break;
        }
      }
    }
  }

  public void inventoryState(int code) {
    if (code == KeyEvent.VK_LEFT) {
      GamePanel.ui.commandNum--;
      if (GamePanel.ui.commandNum < 1) {
        GamePanel.ui.commandNum = 5;
      }
    } else if (code == KeyEvent.VK_RIGHT) {
      GamePanel.ui.commandNum++;
      if (GamePanel.ui.commandNum > 5) {
        GamePanel.ui.commandNum = 1;
      }
    } else if (code == KeyEvent.VK_Z) {
      InventoryItem item = null;
      if (GamePanel.player.inventory.size() >= GamePanel.ui.commandNum) {
        item = GamePanel.player.inventory.get(GamePanel.ui.commandNum - 1);
      }
      if (item == null) {
        GamePanel.player.inventoryFrameCount = 0;
        UI.statement = "This inventory slot is empty.";

      } else {
        GamePanel.player.useItem(item);
      }
      GamePanel.gameState = GamePanel.playState;
    } else if (code == KeyEvent.VK_SPACE) {
      InventoryItem item = null;
      if (GamePanel.player.inventory.size() >= GamePanel.ui.commandNum) {
        item = GamePanel.player.inventory.get(GamePanel.ui.commandNum - 1);
      }
      if(item != null) {
        GamePanel.player.dropItem(item);
      } else {
        GamePanel.player.inventoryFrameCount = 0;
        UI.statement = "This inventory slot is empty.";
      }

      GamePanel.gameState = GamePanel.playState;
    } else if(code == KeyEvent.VK_B) {
      GamePanel.gameState = GamePanel.playState;
      GamePanel.ui.commandNum = 0;
    }
  }

  public void gameOverState(int code) {
    if (code == KeyEvent.VK_UP) {
      GamePanel.ui.commandNum--;
    }
    if (code == KeyEvent.VK_DOWN) {
      GamePanel.ui.commandNum++;
    }
    if (GamePanel.ui.commandNum < 0) {
      GamePanel.ui.commandNum += 2;
    }
    if (GamePanel.ui.commandNum > 1) {
      GamePanel.ui.commandNum -= 2;
    }
    if (code == KeyEvent.VK_Z) {
      if (GamePanel.ui.commandNum == 0) {
        GamePanel.gameState = GamePanel.titleState;
        GamePanel.gameInstanceInitialization();
      }
      if (GamePanel.ui.commandNum == 1) {
        System.exit(0);
      }
    }
  }


  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_UP) {
      upPressed = false;
    }
    if (code == KeyEvent.VK_DOWN) {
      downPressed = false;
    }
    if (code == KeyEvent.VK_LEFT) {
      leftPressed = false;
    }
    if (code == KeyEvent.VK_RIGHT) {
      rightPressed = false;
    }
    if (code == KeyEvent.VK_SPACE) {
      spacePressed = false;
    }
  }
}