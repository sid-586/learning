package Game;

import javax.swing.*;

public class Game {

    private GameBoard board;
    private GamePlayers[] player = new GamePlayers[2];
    private int playersTurn = 0;

    public Game() {
        this.board = new GameBoard(this);
    }

    public void initGame() {
        player[0] = new GamePlayers(true, 'X');
        player[1] = new GamePlayers(false, 'O');
    }

    void passTurn() {
        if (playersTurn == 0)
            playersTurn = 1;
        else playersTurn = 0;
    }
/*
//Метод для установки реального игрока в начале новой игры
 */
    void setRealPlayer() {
        playersTurn = 0;
    }

    GamePlayers getCurrentPlayer() { return player[playersTurn];}

    void showMessage(String message) {
        JOptionPane.showMessageDialog(board, message);
    }
}
