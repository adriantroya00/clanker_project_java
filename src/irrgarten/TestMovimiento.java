package irrgarten;

import irrgarten.Game;
import irrgarten.UI.TextUI;
import irrgarten.controller.Controller;

/**
 * @authors Alvaro Perez Sanchez y Adrian Hernando Leal 
 */
public class TestMovimiento {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game(2);  
        TextUI vista=new TextUI();
        
        vista.showGame(game.getGameState());

        game.getLabyrinth().putPlayer2D(game.getCurrentPlayer().getRow(), game.getCurrentPlayer().getCol() , 2, 0,
        game.getCurrentPlayer());
        vista.showGame(game.getGameState());
        game.nextStep(Directions.UP); // Seguid probando cosas…
        vista.showGame(game.getGameState());


    
    }
    
}
