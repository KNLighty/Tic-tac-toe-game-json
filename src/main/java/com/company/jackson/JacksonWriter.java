package com.company.jackson;

import com.company.DataWriter;
import com.company.game.Player;
import com.company.game.Step;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


public class JacksonWriter extends DataWriter {

  private static final String JSON_FILE_NAME = "gameplay.json";

  public JacksonWriter(Player player1, Player player2, List<Step> stepList) {
    super(player1, player2, stepList);
  }

  @Override
  public void write() {
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

      ObjectNode mainNode = mapper.createObjectNode();
      ObjectNode gameplayNode = mapper.createObjectNode();

      ArrayNode playersNode = mapper.createArrayNode();
      ObjectNode player1Node = createPlayerNode(mapper, player1);
      ObjectNode player2Node = createPlayerNode(mapper, player2);

      playersNode.addAll(Arrays.asList(player1Node, player2Node));
      gameplayNode.set("Player", playersNode);
      mainNode.set("GamePlay", gameplayNode);

      ObjectNode gameNode = mapper.createObjectNode();
      ArrayNode stepsNode = createStepsNode(mapper);

      gameNode.set("Step", stepsNode);
      gameplayNode.set("Game", gameNode);

      ObjectNode gameResultNode = createGameResultNode(mapper);
      gameplayNode.set("GameResult", gameResultNode);

      writer.writeValue(Paths.get(JSON_FILE_NAME).toFile(), mainNode);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private ObjectNode createPlayerNode(ObjectMapper mapper, Player player) {
    ObjectNode playerNode = mapper.createObjectNode();
    playerNode.put("id", String.valueOf(player.getId()));
    playerNode.put("name", String.valueOf(player.getName()));
    playerNode.put("symbol", String.valueOf(player.getSymbolType()));

    return playerNode;
  }

  private ArrayNode createStepsNode(ObjectMapper mapper) {
    ArrayNode stepsNode = mapper.createArrayNode();
    int i = 1;
    for (Step step : stepList) {
      ObjectNode stepNode = mapper.createObjectNode();
      stepNode.put("num", String.valueOf(i));
      stepNode.put("playerId", String.valueOf(step.getPlayerId()));
      stepNode.put("text", step.getRow() + " " + step.getColumn());
      stepsNode.add(stepNode);
      i++;
    }
    return stepsNode;
  }

  private ObjectNode createGameResultNode(ObjectMapper mapper) {
    ObjectNode gameResultNode = mapper.createObjectNode();
    Player winner;
    if (player1.isCurrentWinner()) winner = player1;
    else if (player2.isCurrentWinner()) winner = player2;
    else winner = null;

    if (winner != null) {
      ObjectNode winnerPlayerNode = createPlayerNode(mapper, winner);
      gameResultNode.set("Player", winnerPlayerNode);
    }
    return gameResultNode;
  }
}
