package irrgarten;

/**
 * @authors Alvaro Perez Sanchez y Adrian Hernando Leal 
 */
public class GameState {
    private String labyrinth;
    private String players;
    private String monsters;
    private int currentPlayer;
    private boolean winner;
    private String log;

    /**
     * @brief Constructor para inicializar la clase con todos sus parametros
     * @param _labyrinth mapa
     * @param _players numero de jugadores
     * @param _monsters numero de monstruos
     * @param _currentPlayer indice jugador actual
     * @param _winner indice del ganador
     * @param _log registro de sucesos en el tablero
     */
    public GameState(String _labyrinth, String _players, String _monsters, int _currentPlayer, boolean _winner, String _log){
        this.labyrinth = _labyrinth;
        this.players = _players;
        this.monsters = _monsters;
        this.currentPlayer = _currentPlayer;
        this.winner = _winner;
        this.log = _log; 
    }

    /**
     * @return String de labyrinth
     */
    public String getLabyrinth() {
        return labyrinth;
    }

    /**
     * @return String de players
     */
    public String getPlayers() {
        return players;
    }

    /**
     * @return Getter de monsters
     */
    public String getMonsters() {
        return monsters;
    }

    /**
     * @return int indice de currentPlayer
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return boolean si el jugador es el ganador
     */
    public boolean isWinner() {
        return winner;
    }

    /**
     * @return String del log
     */
    public String getLog() {
        return log;
    }

}
