package irrgarten;

import java.util.ArrayList;

public class Labyrinth {
    private static final char 
            BLOCK_CHAR      = 'X',
            EMPTY_CHAR      = '-',
            MONSTER_CHAR    = 'M',
            COMBAT_CHAR     = 'C',
            EXIT_CHAR       = 'E';
    private static final int 
            ROW = 0,
            COL = 1;

    private int 
            nRows,
            nCols,
            exitRow,
            exitCol;

    private ArrayList<Character> pos[][];

    public int Labyrinth(int nRows, int nCols, int exitRow, int exitCol) {

    }

    public void spreadPlayers(Player[] players) {

    }

    public boolean haveAWinner() {
        
        return exitPos(Player.row , Player.col )
        return (x == exitRow && y == exitCol)
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
        if(posOK(row,col) && emptyPos(row, col)){

        }
    }

    public Monster putPlayer(Directions direction, Player player) {

    }

    public void addBlock(Orientation orientation, int startRow, int startCol, int length) {

    }

    public Directions[] validMoves(int row, int col) {

    }

    private boolean posOK(int row, int col) {
        return (0 <= row && row <= this.nRows && 
                0 <= col && col <= this.nCols );
    }

    private boolean emptyPos(int row, int col) {

    }

    private boolean monsterPos(int row, int col) {

    }

    private boolean exitPos(int row, int col) {

    }

    private boolean combatPos(int row, int col) {

    }

    private boolean canStepOn(int row, int col) {

    }

    private void updateOldPos( int row , int col){

    }

    private int[] dir2Pos(int row , int col, Directions direction) {

    }

    private int[] randomEmptyPos(){

    }

    private Monster putPlayer2D( int oldRow , int oldCol , int row , int col , Player player){

    }
}
