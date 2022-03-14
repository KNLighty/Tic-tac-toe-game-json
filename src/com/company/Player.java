package com.company;


public class Player {
    private static final char[] acceptableSymbols = {'X', '0'};
    private char symbolType;
    private int id;
    private String name;
    private int wins;
    private int defeats;
    private int draws;
    private boolean isCurrentWinner;


    public Player(String name, char symbolType) {
        this.name = name;
        if (isCorrectSymbolType(symbolType)) this.symbolType = symbolType;
        else throw new IllegalArgumentException("Неправильно задан символ для игрока");
        if (symbolType == 'X') id = 1;
        else id = 2;
    }

    public Player(int id) {
        this.id = id;
        switch (id) {
            case 1 -> this.symbolType = 'X';
            case 2 -> this.symbolType = '0';
        }
    }

    public Player() {}

    private boolean isCorrectSymbolType(char symbolType) {
        for (char c : acceptableSymbols) {
            if (c == symbolType) return true;
        }
        return false;
    }

    public void addWin() {
        System.out.println("Победа для игрока " + name);
        wins += 1;
    }

    public void addDefeat() {
        System.out.println("Поражение для игрока " + name);
        defeats += 1;
    }

    public void addDraw() {
        System.out.println("Ничья для игрока " + name);
        draws += 1;
    }

    public char getSymbolType() {
        return symbolType;
    }

    public void setSymbolType(char symbolType) {
        this.symbolType = symbolType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCurrentWinner() {
        return isCurrentWinner;
    }

    public void setCurrentWinner(boolean currentWinner) {
        isCurrentWinner = currentWinner;
    }

    public String getRating() {
        return name + "\n" + "Число побед: " + wins + "\n" +
                "Число поражений: " + defeats + "\n" +
                "Число ничьих: " + draws + "\n";
    }

    @Override
    public String toString() {
        return name;
    }
}
