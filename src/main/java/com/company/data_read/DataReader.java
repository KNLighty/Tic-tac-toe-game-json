package com.company.data_read;

import com.company.game.Gameboard;
import com.company.game.Player;
import com.company.game.Step;

import java.util.ArrayList;
import java.util.List;

public abstract class DataReader {
    protected List<Player> playerList = new ArrayList<>(); // последний из трёх элементов (если элементов три) - победитель
    protected List<Step> stepList = new ArrayList<>();

    public abstract void read();

    protected int getRowFromData(String data) {
        String[] arr = data.split(" ");
        if (arr.length == 2) return Integer.parseInt(arr[0]);
        else return Integer.parseInt(data.substring(0, 1));
    }

    protected int getColumnFromData(String data) {
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

    protected Player getWinnerPlayer() {
        if (playerList.size() == 3) {
            return playerList.get(playerList.size()-1);
        } else {
            return null;
        }
    }
}
