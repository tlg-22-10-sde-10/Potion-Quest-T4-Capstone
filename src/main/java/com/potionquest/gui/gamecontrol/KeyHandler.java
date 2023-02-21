package com.potionquest.gui.gamecontrol;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

  public boolean upPressed, downPressed, leftPressed, rightPressed;
  private boolean interactOff = true;

  private GamePanel gp;

  public KeyHandler(GamePanel gp) {
    this.gp = gp;
  }

  public KeyHandler() {}

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP) {
      upPressed = true;
    }
    if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_KP_DOWN) {
      downPressed = true;
    }
    if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
      leftPressed = true;
    }
    if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
      rightPressed = true;
    }
    if (code == KeyEvent.VK_ESCAPE) {
      gp.gameState = gp.optionState;
    }

    if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
      if(interactOff) {
        System.out.println("command line");
        interactOff = false;
      }
    }

    if (code == KeyEvent.VK_P) {
      if (GamePanel.gameState == GamePanel.playState) {
        GamePanel.gameState = GamePanel.pauseState;
      } else if (GamePanel.gameState == GamePanel.pauseState) {
        GamePanel.gameState = GamePanel.playState;
      }
    }
  }




  @Override
  public void keyReleased(KeyEvent e) {

    int code = e.getKeyCode();

    if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP) {
      upPressed = false;
    }
    if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_KP_DOWN) {
      downPressed = false;
    }
    if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
      leftPressed = false;
    }
    if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
      rightPressed = false;
    }
    if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
      interactOff = true;
    }
  }
}