package com.potionquest.gui.gamecontrol;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

  public boolean upPressed, downPressed, leftPressed, rightPressed, zPressed;


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
  }

  public void titleState(int code) {
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
    if (code == KeyEvent.VK_ENTER) {
      switch (GamePanel.ui.commandNum) {
        case 0:
          GamePanel.gameState = GamePanel.playState;
          break;
        case 1:
          break;
        case 2:
          System.exit(0);
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
    if (code == KeyEvent.VK_Z) {
      zPressed = true;
    }
  }

  public void pauseState(int code) {
    if (GamePanel.ui.pauseScreenState == 0) {
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
            GamePanel.ui.commandNum = 0;
            GamePanel.ui.pauseScreenState = 1;
            break;
          case 2:
            System.exit(0);
        }
      }
    }
    if (GamePanel.ui.pauseScreenState == 1) {
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

        GamePanel.gameState = GamePanel.playState;
      }
    } else if (GamePanel.ui.dialogueScreenState == 1) {
      System.out.println("you are here");
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
            System.out.println("hello from case 0");
            //Add check in inventory for key item here
            break;
          case 1:
            System.out.println("hello from case 1");
            GamePanel.gameState = GamePanel.playState;
            break;
        }
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

  }
}