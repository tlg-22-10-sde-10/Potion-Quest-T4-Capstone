package com.potionquest.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Location {
    private String name;
    private List<Item> items;
    private Map<String, Location> exits;
    private String description;
    private List<Direction> availableDirections;
    private List<Monster> monsters;
    private List<Characters> characters;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public Location() {
    }

    public Location(String name, List<Location> adjacentLocations) {
        this.name = name;
    }

    public Location(String name) {
        this.name = name;
        this.exits = new HashMap<>();
    }

    public Location(String name, List<Item> items, Map<String, Location> exits) {
        this.name = name;
        this.items = items;
        this.exits = exits;
    }

    //method will iterate through printing out each location on file.
    //need to continue researching the creation of each location and accessing individual elements
    public static Map<String, Location> locationJsonParser() throws IOException {
        Map<String, Location> locationMap;
        try (InputStream inputStream = Location.class.getClassLoader().getResourceAsStream("locations.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<Location> locations = objectMapper.readValue(inputStream, new TypeReference<List<Location>>() {
            });
            locationMap = locations
                    .stream()
                    .collect(Collectors.toMap(Location::getName, Function.identity()));
        }
        return locationMap;
    }

    public void addAdjacentLocation(String direction, Location location) {
        exits.put(direction, location);
    }

    public Location getAdjacentLocation(Direction direction) {
//        switch (direction) {
//            case NORTH:
//                return exits.get("NORTH");
//            case SOUTH:
//                return exits.get("SOUTH");
//            case EAST:
//                return exits.get("EAST");
//            case WEST:
//                return exits.get("WEST");
//            default:
//                return null;
//        }
        return exits.get(direction.name());
    }

    public List<String> displayAdjacentLocations() {
        List<Location> adjacentLocations = new ArrayList<>();
        List<String> adjacentLocationNames = new ArrayList<>();
        String name;
        for (Location adjacentLocation : exits.values()) {
            adjacentLocations.add(adjacentLocation);
            name = adjacentLocation.getName();
            adjacentLocationNames.add(name);
        }
        return adjacentLocationNames;
    }

    public List<String> displayExits() {
//        List<String> exits = new ArrayList<>();

        List<String> exits = this.getExits().keySet().stream().collect(Collectors.toList());

//        for (String direction : this.getExits().keySet()) {
//            exits.add(direction);
//        }
        return exits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Map<String, Location> getExits() {
        return exits;
    }

    public void setExits(Map<String, Location> exits) {
        this.exits = exits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Direction> getAvailableDirections() {
        return availableDirections;
    }

    public void setAvailableDirections(List<Direction> availableDirections) {
        this.availableDirections = availableDirections;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monster) {
        this.monsters = monster;
    }

    public List<Characters> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Characters> characters) {
        this.characters = characters;
    }

    public String monsterDescription() throws NullPointerException, IndexOutOfBoundsException {
        String str = "";
        try {
            str = "There is a " + ANSI_RED + getMonsters().get(0).getName() + ANSI_RESET + " here.";
        } catch (Exception e) {
            System.out.println("");
        }
        return str;
    }

    public String characterDescription() throws NullPointerException, IndexOutOfBoundsException {
        String str = "";
        try {
            str = "A " + ANSI_BLUE + getCharacters().get(0).getName() + ANSI_RESET + " is here.";
        } catch (Exception e) {
            System.out.println("");
        }
        return str;
    }

    public String description() {
        String display = new StringBuilder()
                .append("\n").append(getDescription())
                .append("\n").append(characterDescription())
                .append("\n").append("You can find these items here: ").append(getItems()
                        .stream()
                        .map(p -> p.getName())
                        .collect(Collectors.toList()))
                .append("\n").append(monsterDescription()).toString();
        return display;
    }
}
