package irrgarten;
import java.util.ArrayList;

public class Game {
    private static final int MAX_ROUNDS = 10 ;
    private int currentPlayerIndex ;
    private String log ;
    private ArrayList<Player> jugadores;
    private ArrayList<Monster> monstruos;
    private Labyrinth laberinto;




    public Game(int nplayers, int nmonstruos ){
        this.jugadores = new ArrayList<>();
        this.monstruos = new ArrayList<>();
        this.laberinto = new Labyrinth();
        this.log = "";

        for(int i = 0 ; i < nplayers ; i++){
            char a = 0;
            this.jugadores.add(new Player(a));
            a++;
        }
        this.currentPlayerIndex = Dice.whoStarts(nplayers);
        //this.currentPlayer = this.jugadores(get(this.currentPlayerIndex)) 

        this.configureLabyrinth();
        //this.laberinto.spreadPlayers(jugadores); // este metodo es el que reparte los jugadores (se queda así ya que no esta creado todavia)
    }

    public boolean finished(){
        return laberinto.haveAWinner();
    }

    public boolean nextStep( Directions preferredDirection){

    }

    public GameState getGameState(){
        String laberintoStr = laberinto.toString();
        String jugadoresStr = jugadores.toString();
        String monstruosStr = monstruos.toString();

        return new GameState(monstruosStr, laberintoStr, jugadoresStr, currentPlayerIndex, finished(), log);
        
        //GameState uno = new GameState(log, log, log, currentPlayerIndex, false, log)
    }

    private void configureLabyrinth(){

    }

    private void nextPlayer(){

    }

    private Directions actualDirection(Directions preferredDirection){

    }

    private GameCharacter combat(Monster monster){

    }

    private void manageReward(GameCharacter winner){

    }

    private void manageResurrection(){

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
