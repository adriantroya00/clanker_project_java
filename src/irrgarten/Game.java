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

    
    /** Mantiene la compatibilidad con el constructor original. */
    public Game(int nplayers) {
        this(nplayers, false);
    }

    public Game(int nplayers, boolean debug){
        this.players = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.log = "";

        if (debug) {
            for (int i = 0; i < nplayers; i++) {
                players.add(new Player((char) ('0' + i), 1.0f, 1.0f));
            }

            currentPlayerIndex = 0;
            currentPlayer = players.get(currentPlayerIndex);
            labyrinth = new Labyrinth(3, 3, 2, 2);

            configureLabyrinthDebug();

            if (players.size() > 0) {
                labyrinth.putPlayer2D(-1, -1, 0, 0, players.get(0));
            }
            if (players.size() > 1) {
                labyrinth.putPlayer2D(-1, -1, 0, 1, players.get(1));
            }
        } else {
            for(int i = 0 ; i < nplayers ; i++){
                players.add(new Player(
                    (char) ('0' + i),
                    Dice.randomIntelligence(),
                    Dice.randomStrength()
                ));
            }

            currentPlayerIndex = Dice.whoStarts(nplayers);
            currentPlayer = players.get(currentPlayerIndex);
            labyrinth = new Labyrinth(5, 5, 4, 4);

            configureLabyrinth();
            labyrinth.spreadPlayers(players);
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Labyrinth getLabyrinth() {
        return labyrinth;
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
        StringBuilder playersStr = new StringBuilder();
        for (Player player : players) {
            playersStr.append(player);
        }

        StringBuilder monstersStr = new StringBuilder();
        for (Monster monster : monsters) {
            monstersStr.append(monster);
        }

        return new GameState(labyrinthStr,
                            playersStr.toString(),
                            monstersStr.toString(),
                            currentPlayerIndex, 
                            finished(), 
                            log
        );
    }

    private void configureLabyrinth(){
        
        labyrinth.addBlock(Orientation.VERTICAL , 1 , 1 , 2 );
        labyrinth.addBlock(Orientation.HORIZONTAL , 3 , 2 , 2 );

        int[] pos1 = labyrinth.randomEmptyPos();
        Monster m1 = new Monster("Adri", Dice.randomIntelligence(), Dice.randomStrength() );
        labyrinth.addMonster(pos1[0], pos1[1], m1);
        monsters.add(m1);
        
        int[] pos2 = labyrinth.randomEmptyPos();
        Monster m2 = new Monster("Alvaro", Dice.randomIntelligence(), Dice.randomStrength() );
        labyrinth.addMonster(pos2[0], pos2[1], m2);
        monsters.add(m2);

        

    }

    private void configureLabyrinthDebug() {
        labyrinth.addBlock(Orientation.VERTICAL, 1, 1, 1);

        Monster m1 = new Monster("Adri", 2.0f, 3.0f);
        Monster m2 = new Monster("Alvaro", 1.5f, 2.0f);

        labyrinth.addMonster(1, 0, m1);
        labyrinth.addMonster(2, 1, m2);

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

        if (resurrect) {
            currentPlayer.resurrect();
            logResurrected();
        } else {
            logPlayerSkipTurn();
        }
    }

    private void logPlayerWon(){
        this.log += "El jugador ha ganado el combate\n";
    }

    private void logMonsterWon(){
        this.log += "El monstruo ha ganado el combate\n";
    }

    private void logResurrected(){
        this.log += "El jugador ha resucitado\n";
    }

    private void logPlayerSkipTurn(){
        this.log += "El jugador ha perdido el turno al estar muerto\n";
    }

    private void logPlayerNoOrders(){
        this.log += "El jugador no ha seguido las instrucciones (no fue posible)\n";
    }

    private void logNoMonster(){
        this.log += "El jugador se ha movido a una celda vacia o no le fue posible moverse\n";
    }

    private void logRounds(int rounds, int max){
        this.log += "Se han producido " + rounds + "  de " + max + " rondas de combate\n";
    }


}
