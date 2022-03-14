package com.company;

public class Gameboard {
    private char[][] board;
    int numOfTurns;

    public Gameboard() {
        this.board = new char[3][3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = '-';
            }
        }
    }

    public Gameboard(int numberOfRows, int numberOfColumns) {
        this.board = new char[numberOfRows][numberOfColumns];
        int digit = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                char num = Character.forDigit(digit, 10);
                board[i][j] = num;
                digit++;
            }
        }
    }

    public boolean isWinnerFound(Player player) {
       return  (board[0][0] == board[0][1] && board[0][0] == board[0][2] && board[0][0] == player.getSymbolType()) ||
               (board[1][0] == board[1][1] && board[1][0] == board[1][2] && board[1][0] == player.getSymbolType()) ||
               (board[2][0] == board[2][1] && board[2][0] == board[2][2] && board[2][0] == player.getSymbolType()) ||
               (board[0][0] == board[1][0] && board[0][0] == board[2][0] && board[0][0] == player.getSymbolType()) ||
               (board[0][1] == board[1][1] && board[0][1] == board[2][1] && board[0][1] == player.getSymbolType()) ||
               (board[0][2] == board[1][2] && board[0][2] == board[2][2] && board[0][2] == player.getSymbolType()) ||
               (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == player.getSymbolType()) ||
               (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == player.getSymbolType());
    }

    public boolean isDraw() {
        return numOfTurns == 9;
    }

    public void printGameBoardFromStep(Step step) {
        char symbol = new Player(step.getPlayerId()).getSymbolType();
        board[step.getRow() - 1][step.getColumn() - 1] = symbol;
        printGameBoard();
        System.out.println();
    }

    public void printGameBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                System.out.print("|");
                if (j == 2) System.out.print("\n");
            }
        }
    }

    public boolean isCorrectCoords(int row, int column) {
        if (row >= 1 && row <= 3 && column >= 1 && column <=3) {
            return board[row - 1][column - 1] == '-';
        }
        return false;
    }

    public void registerTurn(Player player, int row, int column) {
        char symbol = player.getSymbolType();
        board[row-1][column-1] = symbol;
        numOfTurns++;
    }
}
