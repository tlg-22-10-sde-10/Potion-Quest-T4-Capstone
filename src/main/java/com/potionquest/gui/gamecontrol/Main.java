package com.potionquest.gui.gamecontrol;

import javax.swing.JFrame;

public class Main {

  public static void main(String[] args) {
    GamePanel gamePanel = new GamePanel();

    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setTitle("2D Adventure Game");


    window.add(gamePanel);

    window.pack();

    window.setLocationRelativeTo(null);
    window.setVisible(true);

    gamePanel.setUpWorld();
    gamePanel.startGameThread();
  }

}
