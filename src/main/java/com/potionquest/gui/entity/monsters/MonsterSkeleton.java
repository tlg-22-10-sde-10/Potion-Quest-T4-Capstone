package com.potionquest.gui.entity.monsters;

import com.potionquest.game.Monster;
import com.potionquest.gui.gamecontrol.GamePanel;

public class MonsterSkeleton extends MonsterPrototype {

  private int defaultSpeed;

  public MonsterSkeleton() {
    String monsterName = "skeleton";

    Monster m = GamePanel.monsterLibrary.get(monsterName);

    super.name = m.getName();
    super.MAX_HP = m.getHealth();

    super.HP = super.MAX_HP;
    super.monsterSizeX = 48;
    super.monsterSizeY = 48;
    super.speed = 1;

    defaultSpeed = super.speed;

    super.setDefaultStatus();

    int row = 13;

    super.getImage("/CharactersV3.png", super.monsterSizeX, super.monsterSizeY, row-1);
  }

  public MonsterSkeleton(int ID) {
    new MonsterSkeleton();
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
