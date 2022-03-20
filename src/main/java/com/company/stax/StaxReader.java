package com.company.stax;

import com.company.DataReader;
import com.company.game.Gameboard;
import com.company.game.Player;
import com.company.game.Step;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;

public class StaxReader extends DataReader {
    private static final String XML_FILE_NAME = "gameplay.xml";

    @Override
    public void read() {
        try {
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
                            playerList.add(player);
                            break;
                        case "Step":
                            nextEvent = reader.nextEvent();
                            String stepPlayerId = startElement.getAttributeByName(new QName("playerId")).getValue();
                            String data = nextEvent.asCharacters().getData();
                            step.setPlayerId(Integer.parseInt(stepPlayerId));
                            step.setRow(getRowFromData(data));
                            step.setColumn(getColumnFromData(data));
                            stepList.add(step);
                            break;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
