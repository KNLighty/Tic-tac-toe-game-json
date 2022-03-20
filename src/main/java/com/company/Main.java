package com.company;

import com.company.game.Game;
import com.company.data_read.JacksonReader;
import com.company.data_read.StaxReader;

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
        System.out.println("-----Конец игры-----");
        //readAndPrintGameDataFromXml();
        readAndPrintGameDataFromJson();
    }

    public static void readAndPrintGameDataFromXml() {
        StaxReader staxReader = new StaxReader();
        try {
            staxReader.read();
            staxReader.printGameInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readAndPrintGameDataFromJson() {
        JacksonReader jacksonReader = new JacksonReader();
        try {
            jacksonReader.read();
            jacksonReader.printGameInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
