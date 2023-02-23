package com.potionquest.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Characters {
    private String name;
    private Map<String, String> dialogue;
    private Map<String, String> responses;

    public Characters() {

    }
    //reads talkingnpc.json, creates map of monster from the file
    public static Map<String, Characters> characterJsonParser() throws IOException {
        Map<String, Characters> charactersMap;
        try (InputStream inputStream = Characters.class.getClassLoader().getResourceAsStream("talkingnpc.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<Characters> characters = objectMapper.readValue(inputStream, new TypeReference<List<Characters>>() {
            });
            charactersMap = characters
                    .stream()
                    .collect(Collectors.toMap(Characters::getName, Function.identity()));

        }
        return charactersMap;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getDialogue() {
        return dialogue;
    }

//    public void setDialogue(String dialogue) {
//        this.dialogue = dialogue;
//    }

    public Map<String, String> getResponses() {
        return responses;
    }

    public void setResponses(Map<String, String> responses) {
        this.responses = responses;
    }
}
