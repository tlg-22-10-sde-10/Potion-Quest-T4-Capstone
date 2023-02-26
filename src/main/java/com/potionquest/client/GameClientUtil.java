package com.potionquest.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.potionquest.game.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameClientUtil {

    private String messageName;
    private String message;

    private static int width = 130;
    private static int height = 15;
    private static int fontSize = 15;
    private static int x_offset = 10;
    private static int y_offset = 12;

    private static String titleGame = "POTION QUEST";

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static void printGameLogo() {
        BufferedImage bufferedImage = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB);

        Graphics logoGraphics = bufferedImage.getGraphics();
        logoGraphics.setFont(new Font("Helvetica", Font.ITALIC, fontSize));

        Graphics2D logo = (Graphics2D) logoGraphics;
        logo.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        logoGraphics.drawString(titleGame, x_offset, y_offset);

        for (int y = 0; y < height; y++) {
            StringBuilder logoStringBuilder = new StringBuilder();
            for (int x = 0; x < width; x++) {
                logoStringBuilder.append(bufferedImage.getRGB(x, y) == -16777216 ? " " : "L");
            }
            if (logoStringBuilder.toString().trim().isEmpty()) {
                continue;
            }
            System.out.println(ANSI_PURPLE + logoStringBuilder + ANSI_RESET);
        }
    }

    public static String retrieveMessage(String requestedMessage) throws InterruptedException, IOException {
        Map<String, GameClientUtil> messageMap;
        try (
                InputStream inputStream = GameClientUtil.class.getClassLoader().getResourceAsStream("messages.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<GameClientUtil> gameVillageMessage = objectMapper.readValue(inputStream, new
                    TypeReference<List<GameClientUtil>>() {
                    });
            messageMap = gameVillageMessage
                    .stream()
                    .collect(Collectors.toMap(GameClientUtil::getMessageName, Function.identity()));

        }
        return messageMap.get(requestedMessage).getMessage();
    }

    public static void availableCommands() {
        System.out.println();
        System.out.println(ANSI_PURPLE + "=====================================================================================================");
        System.out.println("Available commands: go [direction], get [item], attack [creature], map, inventory, help, quit, mute");
        System.out.println("=====================================================================================================" + ANSI_RESET);
        System.out.println();
    }


    public static void playerHelpCall() throws IOException {
        Map<String, GameClientUtil> messageMap;
        try (
                InputStream inputStream = GameClientUtil.class.getClassLoader().getResourceAsStream("messages.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<GameClientUtil> gameVillageMessage = objectMapper.readValue(inputStream, new
                    TypeReference<List<GameClientUtil>>() {
                    });
            messageMap = gameVillageMessage
                    .stream()
                    .collect(Collectors.toMap(GameClientUtil::getMessageName, Function.identity()));

        }
        String helpMessage = messageMap.get("helpMessage").getMessage();
        System.out.println(ANSI_GREEN + helpMessage + ANSI_RESET);
        System.out.println();
    }

    public static void startingVillageMessage() throws IOException {
        Map<String, GameClientUtil> messageMap;
        try (
                InputStream inputStream = GameClientUtil.class.getClassLoader().getResourceAsStream("messages.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<GameClientUtil> gameVillageMessage = objectMapper.readValue(inputStream, new
                    TypeReference<List<GameClientUtil>>() {
                    });
            messageMap = gameVillageMessage
                    .stream()
                    .collect(Collectors.toMap(GameClientUtil::getMessageName, Function.identity()));

        }

        String villageMessage = messageMap.get("villageStartMessage").getMessage();
        System.out.println(ANSI_CYAN + villageMessage + ANSI_RESET);
    }

    public static void endGameSequence() {
        Game.destroyGameInstance();
        gameExitMessage();
        System.exit(0);
    }

    public static void gameExitMessage() {
        System.out.println("Thank you for playing Potion Quest, have a nice day!");
    }

    public static void askPlayerIfTheyWantToStartGame() throws InterruptedException, IOException {
        // Prompt the user for input about starting the game
        System.out.println("\nWould you like to start Potion Quest?\n");
        Scanner input = new Scanner(System.in);
        String userInput = "";
        boolean invalidInput = true;
        while (invalidInput) {
            System.out.println("Please enter 'yes' or 'no'");
            userInput = input.nextLine();
            if (userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")) {
                invalidInput = false;
            } else if (userInput.equalsIgnoreCase("no") || userInput.equalsIgnoreCase("n")) {
                invalidInput = false;
                System.out.println("Why would you start up a game if you don't want to play?");
                endGameSequence();
            }
        }
        Game.createGameInstance();
        GameClientUtil.printGameLogo();
//        Game.getGameInstance().getSound().playSound();
        System.out.println();
    }

    public static String displayHUD() {
        String hud = "";
        Player player = Game.getGameInstance().getPlayer();
        String currentLocation = player.getCurrentLocation().getName();
        String timeRemaining = String.format("%02d:%02d", Timer.getTimeRemainingMin(), Timer.getTimeRemainingSec());
        hud += "Location: " + currentLocation;
        hud += " | Health " + player.getHealth() + " \\ 100";
        hud += " | Inventory: " + player.getInventory()
                .stream()
                .map(Item::getName)
                .collect(Collectors.toList());
        hud += " | Time left " + timeRemaining;
        return hud;
    }

    public static String displayLocationDescription() {
        String display = "";
        Player player = Game.getGameInstance().getPlayer();
        display = player.getCurrentLocation().description();
        return display;
    }
}
