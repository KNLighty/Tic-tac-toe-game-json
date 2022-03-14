package com.company;

public class Step {
    private int playerId;
    private int row;
    private int column;

    public Step(int playerId, int row, int column) {
        this.playerId = playerId;
        this.row = row;
        this.column = column;
    }

    public Step() {}


    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "Ход игрока: " + playerId + "\n" +
                "Ряд: " + row + "\n" +
                "Колонка: " + column;
    }
}
