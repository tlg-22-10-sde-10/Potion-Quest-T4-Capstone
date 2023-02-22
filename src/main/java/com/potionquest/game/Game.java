package com.potionquest.game;

import com.potionquest.client.GameClient;
import com.potionquest.client.GameClientUtil;

import java.io.IOException;
import java.util.*;

public class Game {
    private static Game gameInstance = null;
    private Player player;
    private Map<String, Item> items;
    private Map<String, Location> locations;
    private Map<String, Monster> monsters;
    private Map<String, Characters> characters;

    private Sound sound;

    private Game(){};

    private Game(Player player, Map<String, Monster> monsters, Map<String, Item> items, Map<String, Location> locations,
                 Map<String, Characters> characters, Sound sound) {
        setPlayer(player);
        setMonsters(monsters);
        setCharacters(characters);
        setItems(items);
        setLocations(locations);
        setSound(sound);
    }

    public static Game createGameInstance() throws IOException {
        if (gameInstance == null) {
            int playerHealth = 100;
            Map<String, Integer> playerStats = Map.of(
                    "Strength", 10,
                    "Defense", 10,
                    "Agility", 10,
                    "Intelligence", 10,
                    "Charisma", 10,
                    "Luck", 10);
            Map<String, Location> mapOfAllLocations = Location.locationJsonParser();
            Map<String, Item> mapOfAllItems = Item.itemJsonParser();
            Map<String, Monster> mapOfAllMonsters = Monster.monsterJsonParser();
            Map<String, Characters> mapOfAllCharacters = Characters.characterJsonParser();

            Location startingVillage = mapOfAllLocations.get("Whitby Village");
            List<Item> itemsToAddStartingVillage = new ArrayList<>();
            Item trinket = mapOfAllItems.get("Trinket");
            Item bread = mapOfAllItems.get("Bread");
            itemsToAddStartingVillage.add(trinket);
            itemsToAddStartingVillage.add(bread);
            startingVillage.setItems(itemsToAddStartingVillage);

            Location forest = mapOfAllLocations.get("Forest");
//            com.postionquest.game.Item rope1 = mapOfAllItems.get("Rope");
            List<Item> itemsToAddForest = new ArrayList<>();
//            itemsToAddForest.add(rope1);
            forest.setItems(itemsToAddForest);
            Monster wolf = mapOfAllMonsters.get("Wolf");
            List<Monster> monstersToAdd = new ArrayList<>();
            monstersToAdd.add(wolf);
            forest.setMonsters(monstersToAdd);


            Location mountainPass = mapOfAllLocations.get("Mountain Pass");
            Item rope2 = mapOfAllItems.get("Rope");
            List<Item> itemsToAddMountains = new ArrayList<>();
            itemsToAddMountains.add(rope2);
            mountainPass.setItems(itemsToAddMountains);
            Characters hermit = mapOfAllCharacters.get("Hermit");
            List<Characters> charactersToAdd = new ArrayList<>();
            charactersToAdd.add(hermit);
            mountainPass.setCharacters(charactersToAdd);

            Location riverNorth = mapOfAllLocations.get("River North");
            Item fish = mapOfAllItems.get("Fish");
            List<Item> itemsToAddRiverNorth = new ArrayList<>();
            itemsToAddRiverNorth.add(fish);
            riverNorth.setItems(itemsToAddRiverNorth);

            Location riverSouth = mapOfAllLocations.get("River South");
            Item waterBottle = mapOfAllItems.get("Ale");
            List<Item> itemsToAddRiverSouth = new ArrayList<>();
            itemsToAddRiverSouth.add(waterBottle);
            riverSouth.setItems(itemsToAddRiverSouth);

            Location village2 = mapOfAllLocations.get("Langtoft Village");
            Item potion = mapOfAllItems.get("Potion");
            Item sword = mapOfAllItems.get("Sword");
            List<Item> itemsToAddVillage2 = new ArrayList<>();
            itemsToAddVillage2.add(potion);
            itemsToAddVillage2.add(sword);
            village2.setItems(itemsToAddVillage2);

            Player cindy = new Player("Cindy", playerHealth, new ArrayList<>(5), playerStats, startingVillage);

            startingVillage.addAdjacentLocation("NORTH", forest);
            startingVillage.addAdjacentLocation("SOUTH", mountainPass);

            forest.addAdjacentLocation("SOUTH", startingVillage);
            forest.addAdjacentLocation("EAST", riverNorth);

            mountainPass.addAdjacentLocation("NORTH", startingVillage);
            mountainPass.addAdjacentLocation("EAST", riverSouth);

            riverNorth.addAdjacentLocation("SOUTH", village2);
            riverNorth.addAdjacentLocation("WEST", forest);

            riverSouth.addAdjacentLocation("WEST", mountainPass);
            riverSouth.addAdjacentLocation("NORTH", village2);

            village2.addAdjacentLocation("NORTH", riverNorth);
            village2.addAdjacentLocation("SOUTH", riverSouth);

            Sound sound = new Sound();
            gameInstance = new Game(cindy, mapOfAllMonsters, mapOfAllItems, mapOfAllLocations, mapOfAllCharacters, sound);
        }
        return gameInstance;
    }

    public static void checkWin(List<Item> inventory, Location location, GameClient gameClient) throws IOException {
        //Map<String, Item> mapOfAllItems = Item.itemJsonParser();
        if (inventory.contains(Game.gameInstance.getItems().get("Potion"))) {
            if (location.getName().equalsIgnoreCase("Whitby Village")) {
                //TODO: Add win confirmation statement
//                gameClient.setQuitGame(true);
                System.out.println("You were able to save your sister in the nick of time!" +
                        "\nThough she will take days to fully recover, her life is no longer in jeopardy!" +
                        "\nYou go back to your regular life, content with what it brings.");
                System.out.println("\n");
                GameClientUtil.endGameSequence();
            }
        }
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public void setItems(Map<String, Item> items) {
        this.items = items;
    }

    public Map<String, Location> getLocations() {
        return locations;
    }

    public void setLocations(Map<String, Location> locations) {
        this.locations = locations;
    }

    public Map<String, Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(Map<String, Monster> monsters) {
        this.monsters = monsters;
    }

    public Map<String, Characters> getCharacters() {
        return characters;
    }

    public void setCharacters(Map<String, Characters> characters) {
        this.characters = characters;
    }

    public static void destroyGameInstance() {
        gameInstance = null;
    }

    public static Game getGameInstance() {
        return gameInstance;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }
}
