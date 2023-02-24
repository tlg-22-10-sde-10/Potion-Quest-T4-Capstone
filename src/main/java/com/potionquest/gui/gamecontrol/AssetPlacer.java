package com.potionquest.gui.gamecontrol;


import com.potionquest.gui.entity.npc.NPC_Potion_Seller;
import com.potionquest.gui.entity.npc.NPC_Alchemist_Brother;
import com.potionquest.gui.entity.npc.NPC_Doctor;
import com.potionquest.gui.entity.npc.NPC_Hermit;
import com.potionquest.gui.entity.npc.NPC_Sister;

import com.potionquest.gui.entity.monsters.MonsterOrc;

import com.potionquest.gui.entity.inventoryobjects.OrnateTrinket;

import com.potionquest.gui.entity.monsters.MonsterPrototype;
import com.potionquest.gui.entity.inventoryobjects.GoldCoin;
import java.io.IOException;

public class AssetPlacer {

  public AssetPlacer() {}

  public void setObjects() {
    GamePanel.items[0] = new OrnateTrinket();
    GamePanel.items[0].worldX = GamePanel.tileSize * 21;
    GamePanel.items[0].worldY = GamePanel.tileSize * 39;

    GamePanel.items[1] = new GoldCoin();
    GamePanel.items[1].worldX = GamePanel.tileSize * 6;
    GamePanel.items[1].worldY = GamePanel.tileSize * 40;
  }

  public void setNPC() {

    try {
      GamePanel.npc[0] = new NPC_Doctor();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GamePanel.npc[0].worldX = GamePanel.tileSize * 9;
    GamePanel.npc[0].worldY = GamePanel.tileSize * 40;

    GamePanel.npc[1] = new NPC_Sister();
    GamePanel.npc[1].worldX = GamePanel.tileSize * 8;
    GamePanel.npc[1].worldY = GamePanel.tileSize * 40;

    try {
      GamePanel.npc[2] = new NPC_Hermit();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GamePanel.npc[2].worldX = GamePanel.tileSize * 23;
    GamePanel.npc[2].worldY = GamePanel.tileSize * 78;

    GamePanel.npc[3] = new NPC_Alchemist_Brother();
    GamePanel.npc[3].worldX = GamePanel.tileSize * 49;
    GamePanel.npc[3].worldY = GamePanel.tileSize * 72;

    try {
      GamePanel.npc[4] = new NPC_Potion_Seller();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GamePanel.npc[4].worldX = GamePanel.tileSize * 66;
    GamePanel.npc[4].worldY = GamePanel.tileSize * 47;
  }

  public void setMonster() {
    MonsterPrototype orc1 = new MonsterOrc();
    orc1.worldX = GamePanel.tileSize * 12;
    orc1.worldY = GamePanel.tileSize * 40;

    MonsterPrototype orc2 = new MonsterOrc();
    orc2.worldX = GamePanel.tileSize * 16;
    orc2.worldY = GamePanel.tileSize * 40;

    setMonster(orc1);
    setMonster(orc2);
  }

  public void setMonster(MonsterPrototype monster) {
    for(int i = 0; i<GamePanel.monsters.length; i++) {
      if(GamePanel.monsters[i] == null) {
        GamePanel.monsters[i] = monster;
        monster.entityID = i;
        break;
      }
    }
  }
}
