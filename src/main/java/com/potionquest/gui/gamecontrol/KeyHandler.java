package com.potionquest.gui.gamecontrol;

import static com.potionquest.gui.gamecontrol.GamePanel.winState;

import com.potionquest.gui.entity.Player;
import com.potionquest.gui.entity.inventoryobjects.AttunedGemstone;
import com.potionquest.gui.entity.inventoryobjects.ElixirOfLife;
import com.potionquest.gui.entity.inventoryobjects.GoldCoin;
import com.potionquest.gui.entity.inventoryobjects.InventoryItem;
import com.potionquest.gui.entity.inventoryobjects.SwordOfAThousandTruths;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
      // PLAY STATE
    } else if (GamePanel.gameState == GamePanel.playState) {
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
    else if(GamePanel.gameState == GamePanel.gameOverState) {
      gameOverState(code);
    }
  }

  public void titleState(int code) {
    if (GamePanel.ui.titleScreenState == 0) {
      if (code == KeyEvent.VK_UP) {
        GamePanel.ui.commandNum--;
        if (GamePanel.ui.commandNum < 0) {
          GamePanel.ui.commandNum = 2;
        }
      }
      if (code == KeyEvent.VK_DOWN) {
        GamePanel.ui.commandNum++;
        if (GamePanel.ui.commandNum > 2) {
          GamePanel.ui.commandNum = 0;
        }
      }
      if (code == KeyEvent.VK_Z) {
        switch (GamePanel.ui.commandNum) {
          case 0:
            GamePanel.gameState = GamePanel.playState;
            break;
          case 1:
            GamePanel.ui.titleScreenState = 1;
            break;
          case 2:
            System.exit(0);
        }
        GamePanel.ui.commandNum = 0;
      }
    } else if (GamePanel.ui.titleScreenState == 1) {
      if (code == KeyEvent.VK_UP) {
        GamePanel.ui.commandNum--;
        if (GamePanel.ui.commandNum < 0) {
          GamePanel.ui.commandNum = 4;
        }
      }
      if (code == KeyEvent.VK_DOWN) {
        GamePanel.ui.commandNum++;
        if (GamePanel.ui.commandNum > 4) {
          GamePanel.ui.commandNum = 0;
        }
      }
      if (code == KeyEvent.VK_Z) {
        switch (GamePanel.ui.commandNum) {
          case 0:
          case 1:
          case 2:
          case 3:
            break;
          case 4:
          default:
            GamePanel.ui.titleScreenState = 0;
            GamePanel.ui.commandNum = 0;
        }
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
//      if (code == KeyEvent.VK_ENTER) {
//        GamePanel.gameState = GamePanel.optionsState;
//      }
    if(code == KeyEvent.VK_SPACE) {
      spacePressed = true;
    }

    if (code == KeyEvent.VK_Z) {
      zPressed = true;
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
            GamePanel.gameState = GamePanel.playState;
            break;
          case 1:
            GamePanel.ui.commandNum = 0;
            GamePanel.ui.pauseScreenState = 1;
            break;
          case 2:
            GamePanel.ui.commandNum = 0;
            GamePanel.ui.pauseScreenState = 2;
            break;
          case 3:
            GamePanel.gameState = GamePanel.titleState;
            break;
        }
        GamePanel.ui.commandNum = 0;
      }
    }
    if (GamePanel.ui.pauseScreenState == 1) {
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
            GamePanel.ui.commandNum = 1;
            break;
          case 1:
            GamePanel.ui.pauseScreenState = 0;
            break;
        }
      }
    }
    if (GamePanel.ui.pauseScreenState == 2) {
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

            break;
          case 1:

            break;
          case 2:

            break;
          case 3:
            GamePanel.ui.commandNum = 0;
            GamePanel.ui.pauseScreenState = 0;
            break;
        }
      }
    }
  }

  public void dialogueState(int code) {
    if (GamePanel.ui.dialogueScreenState == 0) {
      if (code == KeyEvent.VK_Z) {
        GamePanel.ui.arrayIndex++;
        if (GamePanel.ui.dialogueArray[GamePanel.ui.arrayIndex] == null || GamePanel.player.haveTalkedToOnceAlready) {
          GamePanel.gameState = GamePanel.playState;
          GamePanel.ui.arrayIndex = 0;
        }
      }
    } else if (GamePanel.ui.dialogueScreenState == 1) {
      System.out.println("Diaglogue screen state = " + GamePanel.ui.dialogueScreenState);
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
                }
              }
            }
            if (GamePanel.player.npcIndex == 2) {
              if (GamePanel.player.currentWeapon.name.equals("Father's Sword")) {
                GamePanel.ui.keyDialogueComplete = true;
                GamePanel.player.inventory.remove(GamePanel.player.currentWeapon);
                GamePanel.player.currentWeapon = new SwordOfAThousandTruths();
                GamePanel.player.inventory.add(GamePanel.player.currentWeapon);
                GamePanel.ui.dialogueScreenState = 0;
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
              if (GamePanel.player.inventory.contains(GamePanel.player.currentWeapon)) {
                GamePanel.ui.keyDialogueComplete = true;
                GamePanel.player.inventory.add(new ElixirOfLife());
                GamePanel.ui.dialogueScreenState = 0;
              }
            }

            System.out.println("hello from case 0");
            //Add check in inventory for key item here
            break;
          case 1:
            System.out.println("hello from case 1");
            GamePanel.ui.dialogueScreenState = 0;
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
    }
    if (code == KeyEvent.VK_RIGHT) {
      GamePanel.ui.commandNum++;
      if (GamePanel.ui.commandNum > 5) {
        GamePanel.ui.commandNum = 1;
      }
    }
    if (code == KeyEvent.VK_Z) {
      switch (GamePanel.ui.commandNum) {
        case 1:
          System.out.println("using item 1");

          break;
        case 2:
          System.out.println("using item 2");

          break;
        case 3:
          System.out.println("using item 3");

          break;
        case 4:
          System.out.println("using item 4");

          break;
        case 5:
          System.out.println("using item  5");
          break;
      }
      GamePanel.gameState = GamePanel.playState;
    }
  }

  public void gameOverState(int code) {
    if(code == KeyEvent.VK_UP) {
      GamePanel.ui.commandNum--;
    }
    if(code == KeyEvent.VK_DOWN) {
      GamePanel.ui.commandNum++;
    }
    if(GamePanel.ui.commandNum < 0) GamePanel.ui.commandNum+=2;
    if(GamePanel.ui.commandNum > 1) GamePanel.ui.commandNum-=2;
    if(code == KeyEvent.VK_Z) {
      if(GamePanel.ui.commandNum == 0) {
        GamePanel.gameState = GamePanel.titleState;
      }
      if(GamePanel.ui.commandNum ==  1) {
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
    if(code == KeyEvent.VK_SPACE) {
      spacePressed = false;
    }
  }
}