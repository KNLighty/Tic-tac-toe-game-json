package com.company.data_write;

import com.company.data_write.DataWriter;
import com.company.game.Player;
import com.company.game.Step;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.util.List;

public class StaxWriter extends DataWriter {

    private static final String XML_FILE_NAME = "gameplay.xml";

    private XMLStreamWriter writer;

    public StaxWriter(Player player1, Player player2, List<Step> stepList) {
        super(player1, player2, stepList);
    }

    @Override
    public void write() {
        try {
            FileOutputStream out = new FileOutputStream(XML_FILE_NAME);
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            writer = output.createXMLStreamWriter(out);

            writer.writeStartDocument("utf-8", "1.0");
            writer.writeDTD("\n");
            writer.writeStartElement("Gameplay");
            writer.writeDTD("\n");

            createPlayerNode(player1, player2);

            writer.writeDTD("\t");
            writer.writeStartElement("Game");
            writer.writeDTD("\n");

            createStepNode();

            writer.writeDTD("\t");
            writer.writeEndElement();
            writer.writeDTD("\n");

            createGameResultNode();

            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void createPlayerNode(Player player1, Player player2) {
        try {
            writer.writeDTD("\t");
            writer.writeEmptyElement("Player");
            writer.writeAttribute("id", String.valueOf(player1.getId()));
            writer.writeAttribute("name", player1.getName());
            writer.writeAttribute("symbol", String.valueOf(player1.getSymbolType()));
            writer.writeDTD("\n");

            writer.writeDTD("\t");
            writer.writeEmptyElement("Player");
            writer.writeAttribute("id", String.valueOf(player2.getId()));
            writer.writeAttribute("name", player2.getName());
            writer.writeAttribute("symbol", String.valueOf(player2.getSymbolType()));
            writer.writeDTD("\n");
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createStepNode() {
        int numOfStep = 0;
        for (Step step : stepList) {
            numOfStep++;
            try {
                writer.writeDTD("\t");
                writer.writeDTD("\t");
                writer.writeStartElement("Step");
                writer.writeAttribute("num", String.valueOf(numOfStep));
                writer.writeAttribute("playerId", String.valueOf(step.getPlayerId()));
                writer.writeCharacters(step.getRow() + " " + step.getColumn());
                writer.writeEndElement();
                writer.writeDTD("\n");
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createGameResultNode() {
        Player winner;
        if (player1.isCurrentWinner()) winner = player1;
        else if (player2.isCurrentWinner()) winner = player2;
        else winner = null;

        try {
            writer.writeDTD("\t");

            writer.writeStartElement("GameResult");
            if (winner != null) {
                writer.writeEmptyElement("Player");
                writer.writeAttribute("id", String.valueOf(winner.getId()));
                writer.writeAttribute("name", winner.getName());
                writer.writeAttribute("symbol", String.valueOf(winner.getSymbolType()));
            } else {
                writer.writeCharacters("Draw!");
            }
            writer.writeEndElement();
            writer.writeDTD("\n");
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
