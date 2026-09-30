package irrgarten;

public class GameState {
    private static String labyrinth;
    private static String players;
    private static String monsters;
    private static int currentPlayer;
    private static boolean winner;
    private static String log;

    public GameState(String _labyrinth, String _players, String _monsters, int _currentPlayer, boolean _winner, String _log){
        this.labyrinth = _labyrinth;
        this.players = _players;
        this.monsters = _monsters;
        this.currentPlayer = _currentPlayer;
        this.winner = _winner;
        this.log = _log; 
    }

    public static String getLabyrinth() {
        return labyrinth;
    }

    public static String getPlayers() {
        return players;
    }

    public static String getMonsters() {
        return monsters;
    }

    public static int getCurrentPlayer() {
        return currentPlayer;
    }

    public static boolean isWinner() {
        return winner;
    }

    public static String getLog() {
        return log;
    }

}
