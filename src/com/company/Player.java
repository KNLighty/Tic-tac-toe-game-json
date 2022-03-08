package com.company;


public class Player {
    private static final char[] acceptableSymbols = {'X', '0'};
    private char symbolType;
    private String name;
    private int wins;
    private int defeats;
    private int draws;


    public Player(String name, char symbolType) {
        this.name = name;
        if (isCorrectSymbolType(symbolType)) this.symbolType = symbolType;
        else throw new IllegalArgumentException("Неправильно задан сисов для игрока");
    }

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

    public String getName() {
        return name;
    }

    public String getRating() {
        return name + "\n" + "Число побед: " + wins + "\n" +
                "Число поражений: " + defeats + "\n" +
                "Число ничьих: " + draws + "\n";
    }
}
