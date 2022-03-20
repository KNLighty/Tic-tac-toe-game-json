package com.company.data_write;

import com.company.data_write.DataWriter;
import com.company.game.Player;
import com.company.game.Step;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.nio.file.Paths;
import java.util.List;


public class JacksonWriter extends DataWriter {

  private static final String JSON_FILE_NAME = "gameplay.json";

  private ObjectMapper mapper;
  private ObjectNode mainNode;
  private ObjectNode gameplayNode;
  private ObjectNode gameNode;

  public JacksonWriter(Player player1, Player player2, List<Step> stepList) {
    super(player1, player2, stepList);
  }

  @Override
  public void write() {
    try {
      mapper = new ObjectMapper();
      ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

      mainNode = mapper.createObjectNode();
      gameplayNode = mapper.createObjectNode();

      createPlayerNode(player1, player2);
      mainNode.set("GamePlay", gameplayNode);

      gameNode = mapper.createObjectNode();
      createStepNode();
      gameplayNode.set("Game", gameNode);

      createGameResultNode();

      writer.writeValue(Paths.get(JSON_FILE_NAME).toFile(), mainNode);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void createPlayerNode(Player player1, Player player2) {
    ArrayNode playersNode = mapper.createArrayNode();

    ObjectNode player1Node = mapper.createObjectNode();
    player1Node.put("id", String.valueOf(player1.getId()));
    player1Node.put("name", String.valueOf(player1.getName()));
    player1Node.put("symbol", String.valueOf(player1.getSymbolType()));
    playersNode.add(player1Node);

    ObjectNode player2Node = mapper.createObjectNode();
    player2Node.put("id", String.valueOf(player2.getId()));
    player2Node.put("name", String.valueOf(player2.getName()));
    player2Node.put("symbol", String.valueOf(player2.getSymbolType()));
    playersNode.add(player2Node);

    gameplayNode.set("Player", playersNode);
  }

  @Override
  public void createStepNode() {
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
    gameNode.set("Step", stepsNode);
  }

  @Override
  public void createGameResultNode() {
    ObjectNode gameResultNode = mapper.createObjectNode();
    Player winner;
    if (player1.isCurrentWinner()) winner = player1;
    else if (player2.isCurrentWinner()) winner = player2;
    else winner = null;

    if (winner != null) {
      ObjectNode winnerPlayerNode = mapper.createObjectNode();
      winnerPlayerNode.put("id", String.valueOf(player1.getId()));
      winnerPlayerNode.put("name", String.valueOf(player1.getName()));
      winnerPlayerNode.put("symbol", String.valueOf(player1.getSymbolType()));
      gameResultNode.set("Player", winnerPlayerNode);
    }
    gameplayNode.set("GameResult", gameResultNode);
  }
}
