package irrgarten;

import java.util.ArrayList;

public class Labyrinth {
    private static final char 
            BLOCK_CHAR = 'X',
            EMPTY_CHAR = '-',
            MONSTER_CHAR = 'M',
            COMBAT_CHAR = 'C', //tiene a la vez monstruo y jugador
            EXIT_CHAR = 'E';

    private static final int 
            ROW = 0,
            COL = 1;

    private int 
            nRows,
            nCols,
            exitRow,
            exitCol;

    private char[][] labyrinth = new char[nRows][nCols];
    private Monster[][] monsters = new Monster[nRows][nCols];
    private Player[][] players = new Player[nRows][nCols];

    public Labyrinth(int _nRows, int _nCols, int _exitRow, int _exitCol) {
        this.nRows = _nRows;
        this.nCols = _nCols;
        this.exitRow = _exitRow;
        this.exitCol = _exitCol;
    }

    public void spreadPlayers(Player[] players) {
        throw new UnsupportedOperationException();
    }

    public boolean haveAWinner() {
        return (players[exitRow][exitCol] != null);
    }

    public String toString() {
        return "Laberinto {" +
                "\n Numero de Filas = " + this.nRows +
                "\n Numero de Columnas = " + this.nCols +
                "\n Fila de salida = " + this.exitRow +
                "\n Columna de salida = " + this.exitCol +
                "\n}\n";
    }

    public void addMonster(int row, int col, Monster monster) {
        if (posOK(row, col) && emptyPos(row, col)) {
            monsters[row][col] = monster;
            labyrinth[row][col] = MONSTER_CHAR;
            monster.setPos(row, col);
        }
    }

    public Monster putPlayer(Directions direction, Player player) {
        throw new UnsupportedOperationException();
    }

    public void addBlock(Orientation orientation, int startRow, int startCol, int length) {
        throw new UnsupportedOperationException();
    }

    public Directions[] validMoves(int row, int col) {
        throw new UnsupportedOperationException();
    }

    private boolean posOK(int row, int col) {
        return (0 <= row && row <= this.nRows &&
                0 <= col && col <= this.nCols);
    }

    private boolean emptyPos(int row, int col) {
        return (labyrinth[row][col] == EMPTY_CHAR);
    }

    private boolean monsterPos(int row, int col) {
        return (labyrinth[row][col] == MONSTER_CHAR);
    }

    private boolean exitPos(int row, int col) {
        // return (row == exitRow && col == exitCol);
        return (labyrinth[row][col] == EXIT_CHAR);
    }

    private boolean combatPos(int row, int col) {
        return (labyrinth[row][col] == COMBAT_CHAR);
    }

    private boolean canStepOn(int row, int col) {
        return (posOK(row, col) &&
                monsterPos(row, col) ||
                exitPos(row, col) ||
                emptyPos(row, col));
    }

    private void updateOldPos(int row, int col) {
        if (posOK(row, col)) {
            if (labyrinth[row][col] == COMBAT_CHAR) {
                labyrinth[row][col] = MONSTER_CHAR;
            } else {
                labyrinth[row][col] = EMPTY_CHAR;
            }
        }
    }

    private int[] dir2Pos(int row , int col, Directions direction) {
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

    private int[] randomEmptyPos() {
        int[] pos = new int[2];

        do {
            pos[ROW] = Dice.randomPos(nRows);
            pos[COL] = Dice.randomPos(nCols);
        } while(labyrinth[pos[ROW]][pos[COL]] != EMPTY_CHAR);
    
        return pos;

        /**
         * si no quisieramos que se hiciera un bucle infinito tendriamos
         * que crear una funcion que nos diga si existe alguna posicion vacia
         * y si existe entocnes empezar a generar posiciones
         */
    }

    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player) {
        throw new UnsupportedOperationException();
    }
}
