package com.company.stax;

import com.company.DataWriter;
import com.company.game.Player;
import com.company.game.Step;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.util.List;

public class StaxWriter extends DataWriter {

    private static final String XML_FILE_NAME = "gameplay.xml";

    public StaxWriter(Player player1, Player player2, List<Step> stepList) {
        super(player1, player2, stepList);
    }

    @Override
    public void write() {
        try {
            FileOutputStream out = new FileOutputStream(XML_FILE_NAME);
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = output.createXMLStreamWriter(out);
            writer.writeStartDocument("utf-8", "1.0");
            writer.writeDTD("\n");
            writer.writeStartElement("Gameplay");
            writer.writeDTD("\n");

            createPlayerNode(writer, player1);
            createPlayerNode(writer, player2);

            writer.writeDTD("\t");
            writer.writeStartElement("Game");
            writer.writeDTD("\n");

            int numOfStep = 0;
            for (Step step : stepList) {
                numOfStep++;
                createStepNode(writer, step, numOfStep);
            }

            writer.writeDTD("\t");
            writer.writeEndElement();
            writer.writeDTD("\n");

            createGameResultNode(writer);

            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createPlayerNode(XMLStreamWriter writer, Player player) throws XMLStreamException {
        writer.writeDTD("\t");
        writer.writeEmptyElement("Player");
        writer.writeAttribute("id", String.valueOf(player.getId()));
        writer.writeAttribute("name", player.getName());
        writer.writeAttribute("symbol", String.valueOf(player.getSymbolType()));
        writer.writeDTD("\n");
    }

    private void createStepNode(XMLStreamWriter writer, Step step, int numOfStep) throws XMLStreamException {
        writer.writeDTD("\t");
        writer.writeDTD("\t");
        writer.writeStartElement("Step");
        writer.writeAttribute("num", String.valueOf(numOfStep));
        writer.writeAttribute("playerId", String.valueOf(step.getPlayerId()));
        writer.writeCharacters(step.getRow() + " " + step.getColumn());
        writer.writeEndElement();
        writer.writeDTD("\n");
    }

    private void createGameResultNode(XMLStreamWriter writer) throws XMLStreamException {
        Player winner;
        if (player1.isCurrentWinner()) winner = player1;
        else if (player2.isCurrentWinner()) winner = player2;
        else winner = null;

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
    }
}
