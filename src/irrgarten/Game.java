package irrgarten;
import java.util.ArrayList;

public class Game {
    private static final int MAX_ROUNDS = 10 ;
    private int currentPlayerIndex ;
    private String log ;
    private ArrayList<Player> players;
    private ArrayList<Monster> monsters; 
    private Labyrinth labyrinth;
    private Player currentPlayer;




    public Game(int nplayers, int nmonsters ){
        this.players = new ArrayList<>();
        this.monsters = new ArrayList<>();
        labyrinth = new Labyrinth(5, 5, 4, 4);
        this.log = "";

        for(int i = 0 ; i < nplayers ; i++){
            char a = 0;
            this.players.add(new Player(a));
            a++;
        }
        this.currentPlayerIndex = Dice.whoStarts(nplayers);
        this.currentPlayer = this.players.get(this.currentPlayerIndex);

        this.configureLabyrinth();
        //this.labyrinth.spreadPlayers(players); // este metodo es el que reparte los players (se queda así ya que no esta creado todavia)
    }

    public boolean finished(){
        return labyrinth.haveAWinner();
    }

    public boolean nextStep( Directions preferredDirection){
        throw new UnsupportedOperationException();
    }

    public GameState getGameState(){
        String labyrinthStr = labyrinth.toString();
        String playersStr = players.toString();
        String monstersStr = monsters.toString();

        return new GameState(monstersStr, labyrinthStr, playersStr, currentPlayerIndex, finished(), log);
    }

    private void configureLabyrinth(){
        
        labyrinth.addBlock(Orientation.VERTICAL , 1 , 1 , 2 );
        labyrinth.addBlock(Orientation.HORIZONTAL , 3 , 2 , 2 );

        Monster m1 = new Monster("Adri" , 2.0f , 3.0f );
        Monster m2 = new Monster("Alvaro" , 2.0f , 3.0f );

        labyrinth.addMonster(0, 2, m1);
        labyrinth.addMonster(4, 3, m2);

        monsters.add(m1);
        monsters.add(m2);

    }

    private void nextPlayer(){
        currentPlayerIndex++;
        currentPlayer = players.get(currentPlayerIndex);
    }

    private Directions actualDirection(Directions preferredDirection){
        throw new UnsupportedOperationException();
    }

    private GameCharacter combat(Monster monster){
        throw new UnsupportedOperationException();
    }

    private void manageReward(GameCharacter winner){
        throw new UnsupportedOperationException();
    }

    private void manageResurrection(){
        throw new UnsupportedOperationException();
    }

    private void logPlayerWon(){
        this.log += "El jugador ha ganado el combate.\n";
    }

    private void logMonsterWon(){
        this.log += "El monstruo ha ganado el combate.\n";
    }

    private void logResurrected(){
        this.log += "El jugador ha resucitado.\n";
    }

    private void logPlayerSkipTurn(){
        this.log += "El jugador ha perdido el turno al estar muerto.\n";
    }

    private void logPlayerNoOrders(){
        this.log += "El jugador no ha seguido las instrucciones (no fue posible).\n";
    }

    private void logNoMonster(){
        this.log += "El jugador se ha movido a una celda vacía o no le fue posible moverse.\n";
    }

    private void logRounds(int rounds, int max){
        this.log += "Se han producido " + rounds + " de " + max + " rondas de combate.\n";
    }


}
