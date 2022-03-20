package com.company.data_read;

import com.company.data_read.DataReader;
import com.company.game.Player;
import com.company.game.Step;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class JacksonReader extends DataReader {
    private static final String JSON_FILE_NAME = "gameplay.json";

    @Override
    public void read() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<?, ?> map = mapper.readValue(Paths.get(JSON_FILE_NAME).toFile(), Map.class);
            LinkedHashMap<?, ?> gameplayValuesMap = (LinkedHashMap<?, ?>) map.entrySet().stream().findFirst().get().getValue();

            for (Map.Entry<?, ?> entry : gameplayValuesMap.entrySet()) {
                if (entry.getKey().equals("Player")) {
                    parsePlayerFromEntry(entry);
                }
                else if (entry.getKey().equals("Game")) {
                    parseStepsFromEntry(entry);
                }
                else if (entry.getKey().equals("GameResult")) {
                    parseGameResultFromEntry(entry);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parsePlayerFromEntry(Map.Entry<?,?> entry) {
        for (var playerData : (ArrayList<?>) entry.getValue()) {
            LinkedHashMap<String, String>  playerDataMap = (LinkedHashMap<String, String>) playerData;
            Player player = new Player();
            player.setId(Integer.parseInt(playerDataMap.get("id")));
            player.setName(playerDataMap.get("name"));
            player.setSymbolType(playerDataMap.get("symbol").charAt(0));
            playerList.add(player);
        }
    }

    private void parseStepsFromEntry(Map.Entry<?,?> entry) {
        var steps = (ArrayList<?>) ((LinkedHashMap<?, ?>) entry.getValue()).get("Step");
        for (var stepData : steps) {
            LinkedHashMap<String, String> stepDataMap = (LinkedHashMap<String, String>) stepData;
            Step step = new Step();
            step.setPlayerId(Integer.parseInt(stepDataMap.get("playerId")));
            step.setRow(getRowFromData(stepDataMap.get("text")));
            step.setColumn(getColumnFromData(stepDataMap.get("text")));
            stepList.add(step);
        }
    }

    private void parseGameResultFromEntry(Map.Entry<?,?> entry) {
        var gameResultEntry = ((LinkedHashMap<?, ?>) entry.getValue());
        if (!gameResultEntry.isEmpty()) {
            LinkedHashMap<String, String>  playerDataMap = (LinkedHashMap<String, String>) gameResultEntry.get("Player");
            Player winner = new Player();
            winner.setId(Integer.parseInt(playerDataMap.get("id")));
            winner.setName(playerDataMap.get("name"));
            winner.setSymbolType(playerDataMap.get("symbol").charAt(0));
            playerList.add(winner);
        }
    }
}
