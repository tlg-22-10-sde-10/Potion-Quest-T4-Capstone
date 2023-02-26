package com.potionquest.client;

import com.potionquest.game.*;
import com.potionquest.controller.*;
import com.potionquest.game.Timer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class GameClient {
    // flag to allow user to quickly exit the game
    public boolean quitGame = false;

    public static void main(String[] args)
        throws InterruptedException, IOException, NoSuchMethodException {
        // Scanner object for accepting user keyboard input

        Scanner input = new Scanner(System.in);
        GameClient gameClient = new GameClient();
        // Welcome the user to the game
        GameClientUtil.askPlayerIfTheyWantToStartGame();
        GameClientUtil.startingVillageMessage();

        // starts timer thread
        (new Thread(new Timer(System.currentTimeMillis(), 7L, 0L, 0L))).start();
        // game runs below:
        do {
            GameClientUtil.availableCommands();
            System.out.println(GameClientUtil.displayHUD());
            System.out.println(GameClientUtil.displayLocationDescription());

//            try {
//                Class<?> cls = Class.forName("com.potionquest.controller.UserInputParser");
//
//                Object obj = cls.getConstructor().newInstance();
//
//                var methods = cls.getMethods();
//
//                for (Method method : methods) {
//                    System.out.println(method.getName());
//                }
//
//                Method method = cls.getMethod("displayMap");
//
//                System.out.println(method.invoke(obj));
//
//
//            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
//                e.printStackTrace();
//            }

            String userInput = input.nextLine();
            UserInputParser.handleUserInput(userInput, gameClient);
            Game.checkWin(Game.getGameInstance().getPlayer().getInventory(),
                    Game.getGameInstance().getPlayer().getCurrentLocation(),
                    gameClient);
        }
        while (!gameClient.isQuitGame() && Game.getGameInstance().getPlayer().getHealth() > 0);
        GameClientUtil.endGameSequence();
    }

    public boolean isQuitGame() {
        return quitGame;
    }

    public void setQuitGame(boolean quitGame) {
        this.quitGame = quitGame;
    }
}
