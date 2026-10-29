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
        this.configureLabyrinth();

        for(int i = 0 ; i < nplayers ; i++){
            char a = 0;
            this.jugadores.add(new Player(a));
            a++;
        }
        this.currentPlayerIndex = Dice.whoStarts(nplayers);
        //this.currentPlayer = this.jugadores(get(this.currentPlayerIndex)) 

        //no sabemos q es log
    }

    public boolean finished(){
        return Labyrinth.haveAWinner();
    }

    public boolean nextStep( Directions preferredDirection){

    }

    public Gamestate getGameState(){
        Gamestate uno = new GameState(log, log, log, currentPlayerIndex, false, log)
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

    }

    private void logMonsterWon(){
        
    }

    private void logResurrected(){
        
    }

    private void logPlayerSkipTurn(){
        
    }

    private void logPlayerNoOrders(){
        
    }

    private void logNoMonster(){
        
    }

    private void logRounds(int rounds, int max){
        
    }


}
