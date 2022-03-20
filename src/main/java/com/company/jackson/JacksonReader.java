package com.company.jackson;

import com.company.game.Gameboard;
import com.company.game.Player;
import com.company.game.Step;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JacksonReader {
    private static final String JSON_FILE_NAME = "gameplay.json";

    private List<Player> playerList = new ArrayList<>(); // последний из трёх элементов (если элементов три) - победитель
    private List<Step> stepList = new ArrayList<>();

    public void readFromXml() {
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

    private int getRowFromData(String data) {
        String[] arr = data.split(" ");
        if (arr.length == 2) return Integer.parseInt(arr[0]);
        else return Integer.parseInt(data.substring(0, 1));
    }

    private int getColumnFromData(String data) {
        String[] arr = data.split(" ");
        if (arr.length == 2) return Integer.parseInt(arr[1]);
        else return Integer.parseInt(data.substring(1));
    }

    public void printGameInfo() {
        Gameboard gameboard = new Gameboard(3, 3);
        Player winner = getWinnerPlayer();
        for (Step step : stepList) {
            gameboard.printGameBoardFromStep(step);
        }
        if (getWinnerPlayer() != null) {
            System.out.println("Player " + winner.getId() + " -> "
                    + winner.getName() + " is winner as '" + winner.getSymbolType() + "'!");
        } else {
            System.out.println("Draw!");
        }
    }

    private Player getWinnerPlayer() {
        if (playerList.size() == 3) {
            return playerList.get(playerList.size()-1);
        } else {
            return null;
        }
    }
}
