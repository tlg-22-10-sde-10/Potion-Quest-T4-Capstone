package com.potionquest.gui.gamecontrol;


import com.potionquest.gui.entity.inventoryobjects.BootsOfSpeed;
import com.potionquest.gui.entity.inventoryobjects.DeliciousMushroom;
import com.potionquest.gui.entity.monsters.MonsterSkeleton;

import com.potionquest.gui.entity.npc.NPC_Potion_Seller;
import com.potionquest.gui.entity.npc.NPC_Potion_Sellers_Brother;
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

    GamePanel.items[2] = new BootsOfSpeed();
    GamePanel.items[2].worldX = GamePanel.tileSize * 24;
    GamePanel.items[2].worldY = GamePanel.tileSize * 3;

    GamePanel.items[3] = new DeliciousMushroom();
    GamePanel.items[3].worldX = GamePanel.tileSize * 7;
    GamePanel.items[3].worldY = GamePanel.tileSize * 5;

    GamePanel.items[4] = new DeliciousMushroom();
    GamePanel.items[4].worldX = GamePanel.tileSize * 37;
    GamePanel.items[4].worldY = GamePanel.tileSize * 25;

    //easter egg
    GamePanel.items[5] = new GoldCoin();
    GamePanel.items[5].worldX = GamePanel.tileSize * 84;
    GamePanel.items[5].worldY = GamePanel.tileSize * 84;

    GamePanel.items[6] = new GoldCoin();
    GamePanel.items[6].worldX = GamePanel.tileSize * 84;
    GamePanel.items[6].worldY = GamePanel.tileSize * 85;

    GamePanel.items[7] = new GoldCoin();
    GamePanel.items[7].worldX = GamePanel.tileSize * 84;
    GamePanel.items[7].worldY = GamePanel.tileSize * 86;

    GamePanel.items[8] = new GoldCoin();
    GamePanel.items[8].worldX = GamePanel.tileSize * 85;
    GamePanel.items[8].worldY = GamePanel.tileSize * 86;

    GamePanel.items[9] = new GoldCoin();
    GamePanel.items[9].worldX = GamePanel.tileSize * 86;
    GamePanel.items[9].worldY = GamePanel.tileSize * 86;

    GamePanel.items[10] = new GoldCoin();
    GamePanel.items[10].worldX = GamePanel.tileSize * 87;
    GamePanel.items[10].worldY = GamePanel.tileSize * 86;

    GamePanel.items[11] = new GoldCoin();
    GamePanel.items[11].worldX = GamePanel.tileSize * 86;
    GamePanel.items[11].worldY = GamePanel.tileSize * 84;

    GamePanel.items[12] = new GoldCoin();
    GamePanel.items[12].worldX = GamePanel.tileSize * 86;
    GamePanel.items[12].worldY = GamePanel.tileSize * 85;

    GamePanel.items[13] = new GoldCoin();
    GamePanel.items[13].worldX = GamePanel.tileSize * 86;
    GamePanel.items[13].worldY = GamePanel.tileSize * 87;

    GamePanel.items[14] = new GoldCoin();
    GamePanel.items[14].worldX = GamePanel.tileSize * 86;
    GamePanel.items[14].worldY = GamePanel.tileSize * 88;
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

    try {
      GamePanel.npc[3] = new NPC_Potion_Sellers_Brother();
    } catch (IOException e) {
      e.printStackTrace();
    }
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
    orc1.worldX = GamePanel.tileSize * 7;
    orc1.worldY = GamePanel.tileSize * 7;

    MonsterPrototype orc2 = new MonsterOrc();
    orc2.worldX = GamePanel.tileSize * 25;
    orc2.worldY = GamePanel.tileSize * 22;

    MonsterPrototype orc3 = new MonsterOrc();
    orc3.worldX = GamePanel.tileSize * 41;
    orc3.worldY = GamePanel.tileSize * 24;

    MonsterPrototype skeleton1 = new MonsterSkeleton();
    skeleton1.worldX = GamePanel.tileSize * 63;
    skeleton1.worldY = GamePanel.tileSize * 88;

    MonsterPrototype skeleton2 = new MonsterSkeleton();
    skeleton2.worldX = GamePanel.tileSize * 76;
    skeleton2.worldY = GamePanel.tileSize * 74;

    setMonster(orc1);
    setMonster(orc2);
    setMonster(orc3);
    setMonster(skeleton1);
    setMonster(skeleton2);

    MonsterPrototype orc4 = new MonsterOrc();
    orc4.worldX = GamePanel.tileSize * 13;
    orc4.worldY = GamePanel.tileSize * 76;

    MonsterPrototype orc5 = new MonsterOrc();
    orc5.worldX = GamePanel.tileSize * 32;
    orc5.worldY = GamePanel.tileSize * 83;

    MonsterPrototype skeleton3 = new MonsterSkeleton();
    skeleton3.worldX = GamePanel.tileSize * 34;
    skeleton3.worldY = GamePanel.tileSize * 73;

    setMonster(orc4);
    setMonster(orc5);
    setMonster(skeleton3);
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
