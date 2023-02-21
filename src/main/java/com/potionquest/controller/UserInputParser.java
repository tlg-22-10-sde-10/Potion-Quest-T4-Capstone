package com.potionquest.controller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.potionquest.client.GameClient;
import com.potionquest.client.GameClientUtil;
import com.potionquest.game.*;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

/**
 * com.potionquest.controller.UserInputParser accepts user input from the keyboard and handles situations from command combinations.
 */
public class UserInputParser {
    Map<String, Item> itemsMap = Game.getGameInstance().getItems();
    Map<String, Characters> charactersMap = Game.getGameInstance().getCharacters();
    Map<String, Monster> monsterMap = Game.getGameInstance().getMonsters();

    private final ArrayList<String> movementVerbs = new ArrayList<>() {
        {
            add("go");
            add("move");
        }
    };
    private final ArrayList<String> interactionVerbs = new ArrayList<>() {
        {
            add("take");
            add("get");
            add("drop");
            add("talk");
            add("look");
            add("heal");
        }
    };

    //need to update to consume JSON items instead
    private final ArrayList<String> nouns = new ArrayList<>(itemsMap.keySet());

    private final ArrayList<String> monsters = new ArrayList<>(monsterMap.keySet());

    private final ArrayList<String> characters = new ArrayList<>(charactersMap.keySet());

    private final List<String> directions = Stream.of(Direction.values())
            .map(Enum::name)
            .collect(Collectors.toList());

    /**
     * parseCommand takes a list of Strings called wordList and parses it based on a verb and direction/noun combination.
     * If 'go' or 'move' are the first word in the list, parseCommand expects a direction. Otherwise, it will check the
     * list of interactions verbs with the list of nouns.
     *
     * @param wordlist - a List of String from the user input.
     */
    public static void parseCommand(List<String> wordlist) {
        UserInputParser inputParser = new UserInputParser();
        Characters hermit = inputParser.charactersMap.get("Hermit");
        Player player = Game.getGameInstance().getPlayer();

        String firstArgumentOfUserInput;
        String secondArgumentOfUserInput;

        firstArgumentOfUserInput = wordlist.get(0).toLowerCase();
        secondArgumentOfUserInput = wordlist.get(1).toLowerCase();
        secondArgumentOfUserInput = secondArgumentOfUserInput.substring(0, 1)
                .toUpperCase() + secondArgumentOfUserInput.substring(1);

        //only input of 2 words allowed
        if (wordlist.size() > 2) {
            System.out.println("Only 2 word commands allowed.");
        } else if (inputParser.getMovementVerbs().contains(firstArgumentOfUserInput)) {
            if (inputParser.getDirections().contains(secondArgumentOfUserInput.toUpperCase())) {
                // movement and direction are valid, move player
                updatePlayerLocation(secondArgumentOfUserInput, player);
            } else {
                System.out.println("Invalid direction. Try 'NORTH', 'EAST', 'SOUTH', or 'WEST' after 'Go' or 'Move'");
            }
        } else if (firstArgumentOfUserInput.equalsIgnoreCase("attack")) {
            if (inputParser.getMonsters().contains(secondArgumentOfUserInput)) {
                System.out.println(handleCombatEncounter());
            } else {
                System.out.println("Invalid command, please pair 'attack' with a monster name.");
            }
        } else if (firstArgumentOfUserInput.equalsIgnoreCase("heal")) {
            if (secondArgumentOfUserInput.equalsIgnoreCase("character")) {
                player.useHealingItem();
            } else {
                System.out.println("Invalid command, please pair 'heal' with 'character'.");
            }
        } else if (inputParser.getInteractionVerbs().contains(firstArgumentOfUserInput)) {
            if (inputParser.getDirections().contains(secondArgumentOfUserInput.toUpperCase())) {
                System.out.println("Invalid command. Please pair 'go' and 'move' with directions only.");
            } else if (inputParser.getNouns()
                    .contains(secondArgumentOfUserInput)) {
                // interact with nouns here
                Item item = player.convertInputNounToTargetObject(secondArgumentOfUserInput.toLowerCase());
                if (firstArgumentOfUserInput.equalsIgnoreCase("take")
                        || firstArgumentOfUserInput.equalsIgnoreCase("get")) {
                    player.takeItem(item);
                }
                if (firstArgumentOfUserInput.equalsIgnoreCase("drop")) {
                    player.dropItem(item);
                }
                if (firstArgumentOfUserInput.equalsIgnoreCase("look")) {
                    player.lookAtItem(item);
                }
            } else if (inputParser.getCharacters()
                    .contains(secondArgumentOfUserInput.substring(0, 1)
                            .toUpperCase() + secondArgumentOfUserInput.substring(1))) {
                if (firstArgumentOfUserInput.equalsIgnoreCase("talk")) {
                    player.talkToCharacters(hermit);
                }
            } else {
                System.out.println("Invalid noun.");
            }
        } else {
            System.out.println("Invalid interaction verb. For interacting with items, try 'take', 'grab', 'drop', or " +
                    "'use'. For NPCs try 'talk'");
        }
    }

    public static void updatePlayerLocation(String secondArgumentOfUserInput, Player player) {
        var direction = Direction.valueOf(secondArgumentOfUserInput.toUpperCase());
        player.move(direction);
    }

    private static String handleCombatEncounter() {
        String combatReport = "";
        Player player = Game.getGameInstance().getPlayer();
        Location currentLocation = player.getCurrentLocation();
        Item rope = Game.getGameInstance().getItems().get("Rope");
        List<Item> itemsToAddForest = new ArrayList<>();
        itemsToAddForest.add(rope);

        //need to fix, this makes it so only combat is occurring in Forest
        //need to fix null pointer exception for fight monster and removing from monster list
        if(Game.getGameInstance().getPlayer().getCurrentLocation().getName().equals(
                Game.getGameInstance().getLocations().get("Forest").getName())) {

            int playerHealth = player.getHealth();
            Monster monster = Game.getGameInstance().getMonsters().get("Wolf");
            int monsterHealth = monster.getHealth();
            int totalMonsterDamageTaken = 0;
            int totalPlayerDamageTaken = 0;
            String monsterName = monster.getName();

            while (monsterHealth > 0 && playerHealth > 0) {
                int monsterDamageTaken = Combat.monsterTakeDamage(player, monster);
                totalMonsterDamageTaken += monsterDamageTaken;
                int playerDamageTaken = Combat.playerTakeDamage(player, monster);
                totalPlayerDamageTaken += playerDamageTaken;
                monsterHealth -= monsterDamageTaken;

                playerHealth -= playerDamageTaken;
                player.setHealth(playerHealth);

                combatReport = "After a hard fought battle, you did " + totalMonsterDamageTaken + " total damage to the " +
                    monsterName +
                    "\nYou took " + totalPlayerDamageTaken + " damage." +
                    "\nYour current hp is " + playerHealth + ".";
            }

            if (monsterHealth <= 0) {
                combatReport += "\nThe " + monsterName + " is no longer a concern. You should continue your journey.";
                combatReport += "\nThe monster dropped a " + rope.getName() + ".";
                currentLocation.setItems(itemsToAddForest);
                player.getCurrentLocation().getMonsters().remove(monster);
            }

            if (playerHealth <= 0) {
                combatReport += "\ncom.postionquest.game.Game Over. You died.";
            }
        }
        return combatReport;
    }

    public static String displayMap() {
        String startingVillage = Game.getGameInstance().getLocations().get("Whitby Village").getName();
        String forest = Game.getGameInstance().getLocations().get("Forest").getName();
        String riverNorth = Game.getGameInstance().getLocations().get("River North").getName();
        String riverSouth = Game.getGameInstance().getLocations().get("River South").getName();
        String village2 = Game.getGameInstance().getLocations().get("Langtoft Village").getName();
        String mountainPass = Game.getGameInstance().getLocations().get("Mountain Pass").getName();
        String green = "\u001B[32m";
        String reset = "\u001B[0m";
        String playerCurrentLocation = Game.getGameInstance().getPlayer().getCurrentLocation().getName();
        switch (playerCurrentLocation) {
            case "Whitby Village":
                startingVillage = green + startingVillage + reset;
                break;
            case "Forest":
                forest = green + forest + reset;
                break;
            case "River North":
                riverNorth = green + riverNorth + reset;
                break;
            case "River South":
                riverSouth = green + riverSouth + reset;
                break;
            case "Langtoft Village":
                village2 = green + village2 + reset;
                break;
            case "Mountain Pass":
                mountainPass = green + mountainPass + reset;
                break;
            default:
                // do nothing
                break;
        }

        return "\nTip: Your current location is in green." +
                "\n                       " + forest + "    ---West/East--- " + riverNorth +
                "\n                        /                              \\" +
                "\n                      North/South                       North/South" +
                "\n                      /                                   \\" +
                "\n       " + startingVillage + "                                   " + village2 +
                "\n                      \\                                   /" +
                "\n                    North/South                           North/South" +
                "\n                       \\                                 /" +
                "\n                      " + mountainPass + "---West/East---" + riverSouth +
                "\n";
    }

    public static String displayInventory() {
        StringBuilder detailedInventory = new StringBuilder();
        List<Item> playerInventory = Game.getGameInstance().getPlayer().getInventory();
        int inventorySize = playerInventory.size();
        switch (inventorySize) {
            case (1):
                String item1 = String.format(StringUtils.center(playerInventory.get(0).getName(), 10));
                detailedInventory.append("------------ ------------ ------------ ------------ ------------ ");
                detailedInventory.append("\n|").append(item1)
                        .append("| |          | |          | |          | |");
                detailedInventory.append("\n____________ ____________ ____________ ____________ ____________ ");
                break;
            case (2):
                item1 = String.format(StringUtils.center(playerInventory.get(0).getName(), 10));
                String item2 = String.format(StringUtils.center(playerInventory.get(1).getName(), 10));
                detailedInventory.append("------------ ------------ ------------ ------------ ------------ ");
                detailedInventory.append("\n|").append(item1)
                        .append("| |")
                        .append(item2)
                        .append("| |          | |          | |          |");
                detailedInventory.append("\n____________ ____________ ____________ ____________ ____________ ");
                break;
            case (3):
                item1 = String.format(StringUtils.center(playerInventory.get(0).getName(), 10));
                item2 = String.format(StringUtils.center(playerInventory.get(1).getName(), 10));
                String item3 = String.format(StringUtils.center(playerInventory.get(2).getName(), 10));
                detailedInventory.append("------------ ------------ ------------ ------------ ------------ ");
                detailedInventory.append("\n|").append(item1)
                        .append("| |")
                        .append(item2)
                        .append("| |")
                        .append(item3)
                        .append("| |          | |          |");
                detailedInventory.append("\n____________ ____________ ____________ ____________ ____________ ");
                break;
            case (4):
                item1 = String.format(StringUtils.center(playerInventory.get(0).getName(), 10));
                item2 = String.format(StringUtils.center(playerInventory.get(1).getName(), 10));
                item3 = String.format(StringUtils.center(playerInventory.get(2).getName(), 10));
                String item4 = String.format(StringUtils.center(playerInventory.get(3).getName(), 10));
                detailedInventory.append("------------ ------------ ------------ ------------ ------------ ");
                detailedInventory.append("\n|").append(item1)
                        .append("| |")
                        .append(item2)
                        .append("| |")
                        .append(item3)
                        .append("| |")
                        .append(item4)
                        .append("| |          |");
                detailedInventory.append("\n____________ ____________ ____________ ____________ ____________ ");
                break;
            case (5):
                item1 = String.format(StringUtils.center(playerInventory.get(0).getName(), 10));
                item2 = String.format(StringUtils.center(playerInventory.get(1).getName(), 10));
                item3 = String.format(StringUtils.center(playerInventory.get(2).getName(), 10));
                item4 = String.format(StringUtils.center(playerInventory.get(3).getName(), 10));
                String item5 = String.format(StringUtils.center(playerInventory.get(4).getName(), 10));
                detailedInventory.append("------------ ------------ ------------ ------------ ------------ ");
                detailedInventory.append("\n|").append(item1)
                        .append("| |")
                        .append(item2)
                        .append("| |")
                        .append(item3)
                        .append("| |")
                        .append(item4)
                        .append("| |")
                        .append(item5)
                        .append("|");
                detailedInventory.append("\n____________ ____________ ____________ ____________ ____________ ");
                break;
            default:
                detailedInventory.append("------------ ------------ ------------ ------------ ------------ ");
                detailedInventory.append("\n|          | |          | |          | |          | |          |");
                detailedInventory.append("\n____________ ____________ ____________ ____________ ____________ ");
        }
        return detailedInventory.toString();
    }

    private static List<String> trimUserInput(String userInput) {
        String punctuation = " \t,.:;?!\"'";
        List<String> listOfStrings = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(userInput, punctuation);
        String token;
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            listOfStrings.add(token);
        }
        return listOfStrings;
    }

    /**
     * handleUserInput works direction with user input from the com.client.GameClient main method. If the user enters 'inventory',
     * 'quit' or 'help' a menu is shown. Any other one word command is not recognized. An empty String is also handled.
     * If the user enters in 2 Strings, the input is sent to trimUserInput to trim the input before sending it to
     * parseCommand.
     *
     * @param userInput  - input from the user
     * @param gameClient - instance of the com.client.GameClient
     * @throws IOException
     * @throws InterruptedException
     */
    public static void handleUserInput(String userInput, GameClient gameClient) throws IOException, InterruptedException {
        // Scanner object for accepting user keyboard input
        Scanner input = new Scanner(System.in);
        List<String> listOfTrimmedInput;
        if (userInput.equalsIgnoreCase("quit")) {
            System.out.println("Are you sure you want to quit?");
            boolean invalidInput = true;
            while (invalidInput) {
                System.out.println("Please enter yes or no.");
                userInput = input.next();
                if (userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("y")) {
                    invalidInput = false;
                    gameClient.setQuitGame(true);
                } else if (userInput.equalsIgnoreCase("no") || userInput.equalsIgnoreCase("n")) {
                    invalidInput = false;
                    System.out.println("Exiting quit menu...");
                    Thread.sleep(2000);
                }
            }
        }
        if (userInput.equalsIgnoreCase("map")) {
            System.out.println(UserInputParser.displayMap());
        }
        if (userInput.equalsIgnoreCase("help")) {
            // display help message
            GameClientUtil.playerHelpCall();
        }
        if (userInput.equalsIgnoreCase("")) {
            System.out.println("You must enter a command!");
        }

        if (userInput.equalsIgnoreCase("inventory")) {
            System.out.println(displayInventory());
        }
        if (userInput.equalsIgnoreCase("mute")) {
//            SoundUtil.toggleMusicMute();
        } else {
            listOfTrimmedInput = trimUserInput(userInput);
            if (listOfTrimmedInput.size() > 1) {
                parseCommand(listOfTrimmedInput);
            } else {
                System.out.println("Try entering commands in a [verb] [direction/noun] format such as:" +
                        " 'go north' or 'take sword'");
            }
        }
    }

    public ArrayList<String> getMovementVerbs() {
        return movementVerbs;
    }

    public ArrayList<String> getInteractionVerbs() {
        return interactionVerbs;
    }

    public ArrayList<String> getNouns() {
        return nouns;
    }

    public ArrayList<String> getMonsters() {
        return monsters;
    }

    public ArrayList<String> getCharacters() {
        return characters;
    }

    public List<String> getDirections() {
        return directions;
    }
}
