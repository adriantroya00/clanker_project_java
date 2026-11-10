package irrgarten;
import java.util.ArrayList;

public class Game {
    private static final int MAX_ROUNDS = 10 ;    // rondas maximas de un combate
    private int currentPlayerIndex ;              // que jugador tiene el turno actual
    private String log ;                          // texto sucesos turno
    private ArrayList<Player> players;            // lista jugadores: sirve para orden turnos
    private ArrayList<Monster> monsters;          // lista monstruos: sirve para mostrarlos
    private Labyrinth labyrinth;                  // tablero
    private Player currentPlayer;                 // que jugador tiene el turno actual


    public Game(int nplayers, boolean debug){ 
        this.players = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.log = "";

        if (debug) { // este if sirve para los test de casos sin aleatoriedad
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
                    (char) ('0' + i), // se crea nplayers numerados con este caracter
                    Dice.randomIntelligence(), // se les asigna una inteligencia aleatoria
                    Dice.randomStrength() // se les asigna una fuerza aleatoria
                ));
            }

            currentPlayerIndex = Dice.whoStarts(nplayers); // se elige al azar que jugador empieza
            currentPlayer = players.get(currentPlayerIndex); // se fija currentPlayer
            labyrinth = new Labyrinth(5, 5, 4, 4); // se crea el laberinto con ese tamaño

            configureLabyrinth(); // se llama primero a este método para que los jugadores solo caigan en casillas que no sean ni obstaculos ni monstruos
            labyrinth.spreadPlayers(players); // se reparten los jugadores tras hacer la comprobación anterior
        }
    }

    public Player getCurrentPlayer() { // es usado para ambos test, se obtiene el jugador actual
        return currentPlayer;
    }

    public Labyrinth getLabyrinth() { // es usado en el test del movimiento; devuelve el laberinto
        return labyrinth;
    }

    public boolean finished(){
        return labyrinth.haveAWinner();                                         // delega en este método ya que game no necesita saber como se gana
    }

    public boolean nextStep( Directions preferredDirection){                    // ejecuta un turno y devuelve bool si ha terminado el juego
        log = "";                                                               // se vacia el log
        boolean dead = currentPlayer.dead();                                    // comprobamos si el jugador esta muerto

        if (!dead) {                                                            // si no murio el jugador
            Directions direction = actualDirection(preferredDirection);         // se calcula la dirección real

            if (direction != preferredDirection) {
                logPlayerNoOrders();                                            // si difiere lo anota en el log
            }

            Monster monster = labyrinth.putPlayer(direction, currentPlayer);    // mueve al jugador y devuelve al monstruo en esa direccion

            if (monster == null) {                                              // si no hay monstruo
                logNoMonster();                                                 // registramos q se pudo mover sin monstruo
            } else {
                GameCharacter winner = combat(monster);                         // si lo hay, se produce un combate
                manageReward(winner);                                           // se reparten recompensas
            }
        } else {
            manageResurrection();                                               // si está muerto, se intenta resucitar
        }

        boolean endGame = finished();                                           // vemos si ha terminado el juego

        if (!endGame) {                                                         // Si no ha terminado el juego 
            nextPlayer();                                                       // se pasa al siguiente turno
        }

        return endGame;                                                         // devolvemos si ha terminado el juego
    }

    public GameState getGameState(){
        String labyrinthStr = labyrinth.toString();
        StringBuilder playersStr = new StringBuilder();                         // Construimos un string con los toString de los jugadores
        for (Player player : players) {
            playersStr.append(player);
        }

        /**
         * String playersStr = "";
         * for (Player player : players) {
         *      playersStr += player + " "
         * }
         */

        StringBuilder monstersStr = new StringBuilder();                        // Construimos un string con los toString de los monstruos
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
        
        // se crea el mapa del laberinto
        labyrinth.addBlock(Orientation.VERTICAL , 1 , 1 , 2 );
        labyrinth.addBlock(Orientation.HORIZONTAL , 3 , 2 , 2 );

        int[] pos1 = labyrinth.randomEmptyPos();                                                       // creamos un vector int para randomizar la posicion del monstruo
        Monster m1 = new Monster("Adri", Dice.randomIntelligence(), Dice.randomStrength() );    // se crea el monstruo 1
        labyrinth.addMonster(pos1[0], pos1[1], m1); // se añade el monstruo al laberinto 
        monsters.add(m1);
        
        int[] pos2 = labyrinth.randomEmptyPos();
        Monster m2 = new Monster("Alvaro", Dice.randomIntelligence(), Dice.randomStrength() );
        labyrinth.addMonster(pos2[0], pos2[1], m2);
        monsters.add(m2);

        

    }

    private void configureLabyrinthDebug() { // igual que antes, pero con parametros cambiados para el debug
        labyrinth.addBlock(Orientation.VERTICAL, 1, 1, 1);

        Monster m1 = new Monster("Adri", 2.0f, 3.0f);
        Monster m2 = new Monster("Alvaro", 1.5f, 2.0f);

        labyrinth.addMonster(1, 0, m1);
        labyrinth.addMonster(2, 1, m2);

        monsters.add(m1);
        monsters.add(m2);
    }

    private void nextPlayer(){ 
        currentPlayerIndex = (currentPlayerIndex+1)%players.size(); // se hace un recorrido circular: tras el ultimo jugador, se vuelve al primero
        currentPlayer = players.get(currentPlayerIndex);
    }

    private Directions actualDirection(Directions preferredDirection){ 
        int currentRow = currentPlayer.getRow(); // se inicializan para obtener la posición actual del jugador
        int currentCol = currentPlayer.getCol();

        ArrayList<Directions> validMoves = labyrinth.validMoves(currentRow, currentCol); // pregunta al laberinto los movimientos válidos desde la posición del jugador

        Directions output = currentPlayer.move(preferredDirection, validMoves); // se los pasa a Player.move para que decida

        return output;
    }

    private GameCharacter combat(Monster monster){              // combate contra monster, devuelve un tipo de personaje
        int rounds = 0;                                         // inicializamos las rondas de combate a 0
        GameCharacter winner = GameCharacter.PLAYER;            // empezara ganando el jugador

        // turno del jugador
        float playerAttack = currentPlayer.attack();            // jugador ataca 
        boolean lose = monster.defend(playerAttack);            // el monstruo se defiende, devuelve true si muere con ese golpe

        while (!lose && (rounds < MAX_ROUNDS)) {                // mientras nadie haya perdido y no se hayan agotado las rondas
            // turno del monstruo
            winner = GameCharacter.MONSTER;                     // la victoria pasa a monster
            rounds++;                                           // incremento ronda
            float monsterAttack = monster.attack();             // ataque monstruo
            lose = currentPlayer.defend(monsterAttack);         // defensa del jugador. Si el jugador no logra defenderse true

            if (!lose) {                                        // si el jugador ha logrado defenderse
            // turno del jugador
            playerAttack = currentPlayer.attack();              // ataque jugador
            winner = GameCharacter.PLAYER;                      // se le da la victoria al jugador
            lose = monster.defend(playerAttack);                // defensa del monstruo. Si el monsturo muere, lose es true
            }
        }
        logRounds(rounds, MAX_ROUNDS);                          // si se han alcanzado el maximo de rondas y no hay ganador, gana el player automaticamente
        return winner;
    }

    private void manageReward(GameCharacter winner){
        if (winner == GameCharacter.PLAYER) {       // si el ganador ha sido el player
            currentPlayer.receiveReward();          // se le da una recompensa al jugador
            logPlayerWon();                         // registramos la victoria del jugador
        } else {
            logMonsterWon();                        // registramos la victoria del monstruo
        }
    }

    private void manageResurrection(){
        boolean resurrect = Dice.resurrectPlayer(); // se pregunta al dado si el jugador revive

        if (resurrect) {                            // si sí 
            currentPlayer.resurrect();              // revive el jugador
            logResurrected();                       // registramos revivir
        } else {                                    // si no
            logPlayerSkipTurn();                    // se pasa el turno
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
