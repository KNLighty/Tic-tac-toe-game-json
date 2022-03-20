package com.company.stax;

import com.company.game.Gameboard;
import com.company.game.Player;
import com.company.game.Step;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class StaxReader {
    private static final String XML_FILE_NAME = "gameplay.xml";

    private List<Player> players = new ArrayList<>(); // последний из трёх элементов (если элементов три) - победитель
    private List<Step> steps = new ArrayList<>();

    public void readFromXml() throws Exception {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(XML_FILE_NAME));

        while (reader.hasNext()) {
            Player player = new Player();
            Step step = new Step();
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement startElement = nextEvent.asStartElement();
                switch (startElement.getName().getLocalPart()) {
                    case "Player":
                        String playerId = startElement.getAttributeByName(new QName("id")).getValue();
                        String name = startElement.getAttributeByName(new QName("name")).getValue();
                        String symbol = startElement.getAttributeByName(new QName("symbol")).getValue();

                        player.setId(Integer.parseInt(playerId));
                        player.setName(name);
                        player.setSymbolType(symbol.charAt(0));
                        players.add(player);
                        break;
                    case "Step":
                        nextEvent = reader.nextEvent();
                        String stepPlayerId = startElement.getAttributeByName(new QName("playerId")).getValue();
                        String data = nextEvent.asCharacters().getData();
                        step.setPlayerId(Integer.parseInt(stepPlayerId));
                        step.setRow(getRowFromData(data));
                        step.setColumn(getColumnFromData(data));
                        steps.add(step);
                        break;
                }
            }
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

    private Player getWinnerPlayer() {
        if (players.size() == 3) {
            return players.get(players.size()-1);
        } else {
            return null;
        }
    }

    public void printGameInfo() {
        Gameboard gameboard = new Gameboard(3, 3);
        Player winner = getWinnerPlayer();
        for (Step step : steps) {
            gameboard.printGameBoardFromStep(step);
        }
        if (getWinnerPlayer() != null) {
            System.out.println("Player " + winner.getId() + " -> "
                    + winner.getName() + " is winner as '" + winner.getSymbolType() + "'!");
        } else {
            System.out.println("Draw!");
        }
    }
}
