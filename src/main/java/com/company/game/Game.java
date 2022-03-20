package com.company.game;

import com.company.jackson.JacksonWriter;
import com.company.stax.StaxWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final String RATING_FILE_NAME = "rating.txt";


    public final Player PLAYER_1;
    public final Player PLAYER_2;
    private Gameboard gameBoard;
    private boolean isGameOn;
    private List<Step> steps = new ArrayList<>();

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public Game(String firstPlayerName, String secondPlayerName) {
        PLAYER_1 = new Player(firstPlayerName, 'X');
        PLAYER_2 = new Player(secondPlayerName,'0');
    }

    public void startGame() throws IOException {
        isGameOn = true;
        while (isGameOn) {
            gameBoard = new Gameboard();
            PLAYER_1.setCurrentWinner(false);
            PLAYER_2.setCurrentWinner(false);
            while (true) {
                playGameRound(PLAYER_1);
                if (gameBoard.isWinnerFound(PLAYER_1)) {
                    PLAYER_1.setCurrentWinner(true);
                    PLAYER_1.addWin();
                    PLAYER_2.addDefeat();
                    break;
                }
                if (gameBoard.isDraw()) {
                    PLAYER_1.addDraw();
                    PLAYER_2.addDraw();
                    break;
                }
                playGameRound(PLAYER_2);
                if (gameBoard.isWinnerFound(PLAYER_2)) {
                    PLAYER_2.setCurrentWinner(true);
                    PLAYER_2.addWin();
                    PLAYER_1.addDefeat();
                    break;
                }
            }
            gameBoard.printGameBoard();
            System.out.println("Продолжить игру? (Если закончили, введите 'НЕТ')");
            String response = in.readLine();
            if (response.equalsIgnoreCase("нет") || response.equalsIgnoreCase("no")) {
                endGame();
            };
        }
    }

    private void playGameRound(Player player) throws IOException {;
        while (true) {
            gameBoard.printGameBoard();
            System.out.println("Игрок " + player.getName() +". Выберите ряд (1-3)");
            int row = Integer.parseInt(in.readLine());
            System.out.println("Игрок " + player.getName() +". Выберите ячейку (1-3)");
            int column = Integer.parseInt(in.readLine());
            if (gameBoard.isCorrectCoords(row, column)) {
                gameBoard.registerTurn(player, row, column);
                steps.add(new Step(player.getId(), row, column));
                break;
            } else {
                System.out.println("Неверно указаны координаты. Повторяю запрос");
            }
        }
    }

    public void endGame() throws IOException {
        isGameOn = false;
        in.close();
        writePlayersRating();
        writeInXml();
        writeInJson();
        System.out.println("Всем спасибо за игру! Рейтинг игроков можно посмотреть в текстовом файле.");
    }


    public void writePlayersRating() {
        clearRatingFIle();
        try(FileWriter writer = new FileWriter(RATING_FILE_NAME, true))
        {
            writer.write(PLAYER_1.getRating());
            writer.write(PLAYER_2.getRating());
            writer.flush();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    public void clearRatingFIle() {
        try {
            FileWriter writer = new FileWriter(RATING_FILE_NAME);
            writer.write("");
            writer.flush();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void writeInXml() {
        StaxWriter sw = new StaxWriter(PLAYER_1, PLAYER_2, steps);
        sw.write();
    }

    private void writeInJson() {
        JacksonWriter jw = new JacksonWriter(PLAYER_1, PLAYER_2, steps);
        jw.write();
    }
}
