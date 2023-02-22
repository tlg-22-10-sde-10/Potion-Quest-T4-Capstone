package com.potionquest.controller;

import com.potionquest.game.Monster;
import com.potionquest.game.Player;

import java.util.Random;

public class Combat {
    private static final Random rng = new Random();

    public static int playerTakeDamage(Player player,
        Monster monster) {

        int monsterBaseAttack = monster.getStats().get("strength") + 1;
        int monsterAttack = rng.nextInt(monsterBaseAttack);

        int playerBaseDefend = player.getStats().get("Defense") + 1;
        int playerDefend = rng.nextInt(playerBaseDefend);

        return Math.max(0, monsterAttack - playerDefend);
    }

    public static int monsterTakeDamage(Player player,
        Monster monster) {
        int playerBaseAttack = monster.getStats().get("strength") + 1;
        int playerAttack = rng.nextInt(playerBaseAttack);

        int monsterBaseDefend = player.getStats().get("Defense") + 1;
        int monsterDefend = rng.nextInt(monsterBaseDefend);

        return Math.max(0, playerAttack -  monsterDefend);
    }
}
