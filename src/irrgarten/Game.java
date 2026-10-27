package irrgarten;

public class Game {
    private static final int MAX_ROUNDS = 10 ;
    private int currentPlayerIndex ;
    private String log ;

    public Game(int nplayers){

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
