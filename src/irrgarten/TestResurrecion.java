package irrgarten;

import irrgarten.Game;
import irrgarten.UI.TextUI;
import irrgarten.controller.Controller;

/**
 * @authors Alvaro Perez Sanchez y Adrian Hernando Leal 
 */
public class TestResurrecion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game(2, true);
        
        TextUI vista=new TextUI();
        vista.showGame(game.getGameState());
        game.nextStep(Directions.DOWN);
        vista.showGame(game.getGameState());
        game.getCurrentPlayer().setHealth(0);
        for (int i=0; i<10; i++)
        {
            game.nextStep(Directions.LEFT);
            vista.showGame(game.getGameState());
        }

    
    }
    
}