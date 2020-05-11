package Game;

public class GamePlayers {

    private char playerSymbol;
    private boolean realPlayer = true;

    public GamePlayers(boolean isRealPlayer, char playerSymbol) {
        this.playerSymbol = playerSymbol;
        this.realPlayer = isRealPlayer;
    }

    public boolean isRealPlayer() {
        return realPlayer;
    }

    public char getPlayerSymbol() {
        return playerSymbol;
    }
}
