package com.company;

import java.io.*;

public class Game {
    public final Player PLAYER_1;
    public final Player PLAYER_2;
    private GameBoard gameBoard;
    private String RATING_FILE_NAME = "rating.txt";
    private boolean isGameOn;

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public Game(String firstPlayerName, String secondPlayerName) {
        PLAYER_1 = new Player(firstPlayerName, 'X');
        PLAYER_2 = new Player(secondPlayerName,'0');
    }

    public void startGame() throws IOException {
        isGameOn = true;
        while (isGameOn) {
            gameBoard = new GameBoard();
            while (true) {
                playGameRound(PLAYER_1);
                if (gameBoard.isWinnerFound(PLAYER_1)) {
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
                    PLAYER_2.addWin();
                    PLAYER_1.addDefeat();
                    break;
                }
            }
            gameBoard.printGameBoard();
            System.out.println("Продолжить игру? (Если закончили, введите 'НЕТ')");
            String response = in.readLine();
            if (response.equalsIgnoreCase("нет")) {
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
}
