package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {



    static int dimension = 5;
    static int cellSize = 150;
    private char[][] gameField;

    public char[][] getGameField() {
        return gameField;
    }

    private GameButtons[] gameButton;
    final char NULLSYMBOL = '\u0000';        // оптимизация кода - объявление финальной переменной

    private Game game;

    public GameBoard(Game currentGame) {
        this.game = currentGame;
        initField();
    }

    private void initField() {
        setBounds(cellSize * dimension, cellSize * dimension, 400, 300);
        setTitle("Крестики-нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        JButton newGameButton = new JButton("Новая игра");
//        JButton setDimension = new JButton("Настройка игры...");   Для доработки на досуге

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyField();
            }
        });

        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(newGameButton);
        controlPanel.setSize(cellSize * dimension, 150);

        JPanel gameFieldPanel = new JPanel();
        gameFieldPanel.setLayout(new GridLayout(dimension, dimension));
        gameFieldPanel.setSize(cellSize * dimension, cellSize * dimension);

        gameField = new char[dimension][dimension];
        gameButton = new GameButtons[dimension * dimension];

        for (int i = 0; i < (dimension * dimension); i++) {
            GameButtons fieldButton = new GameButtons(i, this);
            gameFieldPanel.add(fieldButton);
            gameButton[i] = fieldButton;
        }

        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(gameFieldPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    void emptyField() {
        for (int i = 0; i < (dimension * dimension); i++) {
            gameButton[i].setText("");
            gameField[i / GameBoard.dimension][i % GameBoard.dimension] = NULLSYMBOL;       // оптимизация кода
        }
        game.setRealPlayer();                                   // устанавливаем реального игрока для начала новой игры
    }

    Game getGame() {
        return game;
    }

    boolean isCellEmpty(int x, int y) {
        boolean result = false;
        if(gameField[x][y] == NULLSYMBOL)
            result = true;
        return result;
        }

        void updateGameField(int x, int y) {
        gameField[x][y] = game.getCurrentPlayer().getPlayerSymbol();
        }

        boolean checkWin() {
        boolean result = false;

        char playerSymbol = getGame().getCurrentPlayer().getPlayerSymbol();

            if (checkDiagonals(playerSymbol) || checkLines(playerSymbol)){
                result = true;
            }
            return result;

        }

    private  boolean checkDiagonals(char playerSymbol) {
        boolean leftRight = true;
        boolean rightLeft = true;

        for (int i = 0; i < dimension; i ++) {
            leftRight &= (gameField[i][i] == playerSymbol);
            rightLeft &= (gameField[i][dimension - i - 1] == playerSymbol);
        }
        return leftRight || rightLeft;
    }

    private  boolean checkLines(char playerSymbol) {
        boolean row, col, result;

        for (int i = 0; i < dimension; i ++) {
            row = true;
            col = true;
            for (int j = 0; j < dimension; j ++) {
                row &= (gameField[i][j] == playerSymbol);
                col &= (gameField[j][i] == playerSymbol);
            }
            if (row || col) {
                result = true;
                return result;
            }
        }
        result = false;
        return result;
    }

    boolean isFull() {                                                                      // оптимизация кода

        for (int i = 0; i < (dimension * dimension); i++) {
            if (gameField[i / GameBoard.dimension][i % GameBoard.dimension] == NULLSYMBOL)
                return false;
        }
        return true;
    }

    public GameButtons getButton(int buttonIndex) {
        return gameButton[buttonIndex];
    }

}