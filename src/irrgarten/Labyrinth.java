package irrgarten;

import java.util.ArrayList;

public class Labyrinth {
    private static final char 
            BLOCK_CHAR = 'X',  // obstaculo
            EMPTY_CHAR = '-',  // casilla vacia
            MONSTER_CHAR = 'M', // monstruo
            COMBAT_CHAR = 'C', //tiene a la vez monstruo y jugador
            EXIT_CHAR = 'E'; // casilla salida

    private static final int 
            ROW = 0,
            COL = 1;

    private int 
            nRows,
            nCols,
            exitRow,
            exitCol;

    private char[][] labyrinth; // simbolo de cada casilla
    private Monster[][] monsters; // referencia al monstruo
    private Player[][] players; // referencia al jugador

    public Labyrinth(int _nRows, int _nCols, int _exitRow, int _exitCol) { 
        this.nRows = _nRows;
        this.nCols = _nCols;
        this.exitRow = _exitRow;
        this.exitCol = _exitCol;

        this.labyrinth = new char[nRows][nCols];
        this.monsters = new Monster[nRows][nCols];
        this.players = new Player[nRows][nCols];

        for( int i = 0 ; i < nRows ; i++){ // rellena el mapa de '-'
            for( int j = 0; j < nCols ; j++){
                labyrinth[i][j] = EMPTY_CHAR;
            }
        }
        
        labyrinth[exitRow][exitCol] = EXIT_CHAR; // pone la casilla de salida
    }

    public void spreadPlayers(ArrayList<Player> players) {
        for (Player p : players) { // para cada jugador
            int[] pos = randomEmptyPos(); // pide una posición vacia aleatoria
            putPlayer2D(-1, -1, pos[ROW], pos[COL], p); // coloca al jugador 
        }
    }

    public boolean haveAWinner() {
        return (players[exitRow][exitCol] != null); // si hay un jugador en la casilla de salida, es que ha ganado
    }

    public String toString() {  // creación mapa en terminal
        String s = " Laberinto {\n";
        for(int i = 0 ; i < nRows ; i++){
            for(int j = 0 ; j <nCols ; j++){
                s += labyrinth[i][j];
            }
            s += "\n";
        }

        return s + "}\n";
    }

    public void addMonster(int row, int col, Monster monster) {
        if (posOK(row, col) && emptyPos(row, col)) { // si la posición esta dentro y vacía
            labyrinth[row][col] = MONSTER_CHAR; // anota 'M' en el mapa
            monsters[row][col] = monster; // guarda la referencia
            monster.setPos(row, col); // le dice al monstruo donde está
        }
    }

    public Monster putPlayer(Directions direction, Player player) {
        // se lee la posición actual del jugador
        int oldRow = player.getRow();
        int oldCol = player.getCol();

        // se calcula la nueva posición con dir2Pos
        int[] newPos = dir2Pos(oldRow, oldCol, direction);
        // se delega en putPlayer2D 
        Monster monster = putPlayer2D(oldRow, oldCol, newPos[ROW], newPos[COL], player);

        return monster; // devuelve el monstruo con el que ha chocado
    }

    public void addBlock(Orientation orientation, int startRow, int startCol, int length) { // se encarga de dibujar una línea de obstaculos 
        int incRow, incCol;

        if (orientation == Orientation.VERTICAL) { // se avanza en filas
            incRow = 1;
            incCol = 0;
        } else {
            incRow = 0;
            incCol = 1;  // se avanza en columnas
        }

        int row = startRow;
        int col = startCol;

        while (posOK(row,col) && (emptyPos(row, col)) && (length > 0)) { // mientras que la casilla de inicio sea correcta y vacia, y la longitud del bloque sea mayor que 0
            labyrinth[row][col] = BLOCK_CHAR;                            // colocamos el bloque

            length -= 1;                                                 // disminuimos el tamaño restante del bloque
            row += incRow;                                               // nos movemos en la direccion necesaria
            col += incCol;
        }
    }

    public ArrayList<Directions> validMoves(int row, int col) { // prueba las cuatro direcciones y devuelve una lista con las posibles (Player move coge la primera de la lista si la direccion pedida no es valida)
        ArrayList<Directions> output = new ArrayList<>();

        if (canStepOn(row + 1, col)) {
            output.add(Directions.DOWN);
        }

        if (canStepOn(row - 1, col)) {
            output.add(Directions.UP);
        }

        if (canStepOn(row, col + 1)) {
            output.add(Directions.RIGHT);
        }

        if (canStepOn(row, col - 1)) {
            output.add(Directions.LEFT);
        }

        return output;
    }

    private boolean posOK(int row, int col) { // mientras que la posicion proporcionada este dentro del tablero, esto devolvera true
        return (0 <= row && row < this.nRows &&
                0 <= col && col < this.nCols);
    }

    private boolean emptyPos(int row, int col) { // devuelve true si la posición en el tablero está vacia
        return (labyrinth[row][col] == EMPTY_CHAR);
    }

    private boolean monsterPos(int row, int col) {
        return (labyrinth[row][col] == MONSTER_CHAR); // devuelve true si la posición en el tablero hay un monstruo
    }

    private boolean exitPos(int row, int col) {
        return (labyrinth[row][col] == EXIT_CHAR); // devuelve true si la posición en el tablero hay una casilla de salida
    }

    private boolean combatPos(int row, int col) { // devuelve true si la posición en el tablero hay un combate
        return (labyrinth[row][col] == COMBAT_CHAR);
    }

    private boolean canStepOn(int row, int col) { // se encarga de indicar si la posición suministrada es: vacía, habita un monstruo o salida
        return (posOK(row, col) &&
                (monsterPos(row, col) ||
                exitPos(row, col) ||
                emptyPos(row, col)));
    }

    private void updateOldPos(int row, int col) {
        if (posOK(row, col)) {                          // si la posición esta en el tablero
            if (combatPos(row, col)) {                  // si habia un combate
                labyrinth[row][col] = MONSTER_CHAR;     // pasa a indicar que hay un mosntruo
            } else {                                    // si solo estaba el jugador
                labyrinth[row][col] = EMPTY_CHAR;       // pasa a indicar que queda vacia
            }
        }
    }

    private int[] dir2Pos(int row , int col, Directions direction) { // se encarga de calcular la posición a la que se llegaría desde la posición en la que está
        int[] pos = new int[2];
        pos[ROW] = row;
        pos[COL] = col;

        switch (direction) {
            case UP:
                pos[ROW]--;
                break;
            case DOWN:
                pos[ROW]++;
                break;
            case LEFT:
                pos[COL]--;
                break;
            case RIGHT:
                pos[COL]++;
                break;
        }
        return pos;
    }

    // Cambiamos la visibilidad de esta funcion ya que la necesita Game para poder colocar monstruos en posiciones vacias
    public int[] randomEmptyPos() {
        int[] pos = new int[2];

        do {
            pos[ROW] = Dice.randomPos(nRows); // se genera una posición aleatoria en el laberinto
            pos[COL] = Dice.randomPos(nCols);
        } while(labyrinth[pos[ROW]] [pos[COL]] != EMPTY_CHAR); // se generan posiciones hasta cumplirse la restricción.
    
        return pos;

        /**
         * si no quisieramos que se hiciera un bucle infinito tendriamos
         * que crear una funcion que nos diga si existe alguna posicion vacia
         * y si existe entocnes empezar a generar posiciones
         */
    }

    Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player) {
        Monster output = null;                              // suponemos que en la direccion final no habra un monstruo

        if (canStepOn(row, col)) {                          // si puede colocarse en la posicion nueva
            if (posOK(oldRow, oldCol)) {                    // si existia la posicion de origen
                Player p = players[oldRow][oldCol];         // obtenemos al jugador que habia en la casilla vieja
                
                if (p == player) {                          // si es el jugador que le pasamos
                    updateOldPos(oldRow, oldCol);           // actualizamos la posicion antigua
                    players[oldRow][oldCol] = null;         // eliminamos el jugador en la posicion antigua
                }
            }
        
            boolean monster_pos = monsterPos(row, col);     // comprobamos si hay un monstruo en la posicion nueva

            if (monster_pos) {                              // si hay monstruo
                labyrinth[row][col] = COMBAT_CHAR;          // se pasa a combate
                output = monsters[row][col];                // devuelve al monstruo en la posicion nueva
            } else {                                        // si no hay monstruo
                labyrinth[row][col] = player.getNumber();   // se escribe el numero del jugador en el mapa
            }

            players[row][col] = player;                     // colocamos al jugador en la posicion nueva
            player.setPos(row, col);                        // le damos al jugador su posicion
        }   

        return output;
    }
}
