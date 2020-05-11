package Game;

import javax.swing.*;


public class GameButtons extends JButton {

    private int indexButton;
    private GameBoard board;

    public GameButtons(int gameIndexButton, GameBoard currentGameBoard) {
        indexButton = gameIndexButton;
        board = currentGameBoard;

        int x = indexButton / GameBoard.dimension;
        int y = indexButton % GameBoard.dimension;
        setSize(GameBoard.cellSize - 5, GameBoard.cellSize - 5);
        addActionListener(new GameActionListener(x, y, this));

    }

    public GameBoard getBoard() { return board; }
}
