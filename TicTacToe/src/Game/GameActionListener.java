package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameActionListener implements ActionListener {

    private int x;
    private int y;
    private GameButtons button;

    public GameActionListener(int x, int y, GameButtons button) {
        this.x = x;
        this.y = y;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameBoard board = button.getBoard();
        if (board.isCellEmpty(x, y)) {
            updateByPlayersData(board);

            if (board.isFull()) {
                board.getGame().showMessage("Ничья");
                board.emptyField();
            } else {
                if (!board.getGame().getCurrentPlayer().isRealPlayer())         // устранение логической ошибки, при которой, после победы игрока, компьютер делает ещё ход
                    updateByCompData(board);
                if (board.isFull()) {                                           // устранение логической ошибки
                    board.getGame().showMessage("Ничья");
                    board.emptyField();
                }
            }
        } else {
            board.getGame().showMessage("Некорректный ход");
        }
    }

    private void updateByPlayersData(GameBoard board) {
        board.updateGameField(x, y);
        button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSymbol()));
        if (board.checkWin()) {
            board.getGame().showMessage("Вы выиграли!");
            board.emptyField();
        } else {
            board.getGame().passTurn();
        }
    }

    private void updateByCompData(GameBoard board) {
        Random rnd = new Random();
        int x;
        int y;
        int[] tempStatus = new int[3];

        for (int i = 0; i < GameBoard.dimension; i++) {
            for (int j = 0; j < GameBoard.dimension; j++) {
                if (board.isCellEmpty(i, j)) {
                    getRanking(i, j, tempStatus, board);
                }
            }
        }
        if (tempStatus[2] == 0) {
            do {
                x = rnd.nextInt(GameBoard.dimension);
                y = rnd.nextInt(GameBoard.dimension);
            } while (!board.isCellEmpty(x, y));
        } else {
            x = tempStatus[0];
            y = tempStatus[1];
        }
        board.updateGameField(x, y);
        int cellIndex = GameBoard.dimension * x + y;
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSymbol()));

        if (board.checkWin()) {
            board.getGame().showMessage("Выиграл компьютер(:");
            board.emptyField();
        } else {
            board.getGame().passTurn();
        }
    }

    private int[] getRanking(int i, int j, int[] tempStatus, GameBoard board) {
        int cellRanking = 0;
        for (int n = i - 1; n <= i + 1; n++) {
            for (int m = j - 1; m <= j + 1; m++) {
                if (n >= 0 && n < GameBoard.dimension && m >= 0 && m < GameBoard.dimension) {
                    if (board.getGameField()[n][m] == board.getGame().getCurrentPlayer().getPlayerSymbol()) {
                        cellRanking = cellRanking + 1;
                    }
                }
            }
        }
        if (tempStatus[2] < cellRanking) {
            tempStatus[0] = i;
            tempStatus[1] = j;
            tempStatus[2] = cellRanking;
        }
        return tempStatus;
    }
}
