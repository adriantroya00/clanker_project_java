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

        for(int i = 0 ; i < nplayers ; i++){
            char a = 0;
            this.players.add(new Player(a));
            a++;
        }
        
        this.currentPlayerIndex = Dice.whoStarts(nplayers);
        this.currentPlayer = this.players.get(this.currentPlayerIndex);

        this.monsters = new ArrayList<>();
        labyrinth = new Labyrinth(5, 5, 4, 4);
        this.log = "";

        

        this.configureLabyrinth();
        this.labyrinth.spreadPlayers(players);
    }

    public boolean finished(){
        return labyrinth.haveAWinner();
    }

    public boolean nextStep( Directions preferredDirection){
        log = "";
        boolean dead = currentPlayer.dead();

        if (!dead) {
            Directions direction = actualDirection(preferredDirection);

            if (direction != preferredDirection) {
                logPlayerNoOrders();
            }

            Monster monster = labyrinth.putPlayer(direction, currentPlayer);

            if (monster == null) {
                logNoMonster();
            } else {
                GameCharacter winner = combat(monster);
                manageReward(winner);
            }
        } else {
            manageResurrection();
        }

        boolean endGame = finished();

        if (!endGame) {
            nextPlayer();
        }

        return endGame;
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
        currentPlayerIndex = (currentPlayerIndex+1)%players.size();
        currentPlayer = players.get(currentPlayerIndex);
    }

    private Directions actualDirection(Directions preferredDirection){
        int currentRow = currentPlayer.getRow();
        int currentCol = currentPlayer.getCol();

        ArrayList<Directions> validMoves = labyrinth.validMoves(currentRow, currentCol);

        Directions output = currentPlayer.move(preferredDirection, validMoves);

        return output;
    }

    private GameCharacter combat(Monster monster){
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;

        float playerAttack = currentPlayer.attack();

        boolean lose = monster.defend(playerAttack);

        while (!lose && (rounds < MAX_ROUNDS)) {
            winner = GameCharacter.MONSTER;
            rounds++;

            float monsterAttack = monster.attack();

            lose = currentPlayer.defend(monsterAttack);

            if (!lose) {
            playerAttack = currentPlayer.attack();
            winner = GameCharacter.PLAYER;
            lose = monster.defend(playerAttack);
            }
        }

        logRounds(rounds, MAX_ROUNDS);

        return winner;
    }

    private void manageReward(GameCharacter winner){
        if (winner == GameCharacter.PLAYER) {
            currentPlayer.receiveReward();
            logPlayerWon();
        } else {
            logMonsterWon();
        }
    }

    private void manageResurrection(){
        boolean resurrect = Dice.resurrectPlayer();

        if (resurrect = true) {
            currentPlayer.resurrect();
            logResurrected();
        } else {
            logPlayerSkipTurn();
        }
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
