package com.potionquest.gui.entity.monsters;

import com.potionquest.game.Monster;
import com.potionquest.gui.gamecontrol.GamePanel;

public class MonsterOrc extends MonsterPrototype {

  private int defaultSpeed;

  public MonsterOrc() {
    String monsterName = "orc";

    Monster m = GamePanel.monsterLibrary.get(monsterName);

    super.name = m.getName();
    super.MAX_HP = m.getHealth();

    super.HP = super.MAX_HP;
    super.monsterSizeX = 48;
    super.monsterSizeY = 48;
    super.speed = 1;
    attack = 1;

    defaultSpeed = super.speed;

    super.setDefaultStatus();

    int row = 11;

    super.getImage("/CharactersV3.png", super.monsterSizeX, super.monsterSizeY, row-1);
  }

  public MonsterOrc(int ID) {
    new MonsterOrc();
    entityID = ID;
  }

  protected void handleDamageReaction() {
    actionTimeOut = 0;
    direction = GamePanel.player.direction;
  }

  public void update() {
    if(HP>0) {
      super.speed = defaultSpeed;

      super.handleEnemyBehaviour();
      // IF COLLISION IS FALSE, PLAYER CAN MOVE
      super.handleMonsterCollision();

      super.handleMonsterWalkAnimation();

      super.handleMonsterTouchPlayer();

      super.handleMonsterBeingHit();

      super.handleMonsterHeal();
    }
  }
}
