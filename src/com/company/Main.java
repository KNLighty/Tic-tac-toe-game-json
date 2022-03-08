package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите имя первого игрока");
        String firstPlayerName = in.readLine();
        System.out.println("Введите имя второго игрока");
        String secondPlayerName = in.readLine();
	    Game game = new Game(firstPlayerName, secondPlayerName);
        game.startGame();
    }
}
