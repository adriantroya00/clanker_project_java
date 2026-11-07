/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package irrgarten;

import irrgarten.Game;
import irrgarten.UI.TextUI;
import irrgarten.controller.Controller;

/**
 * @authors Alvaro Perez Sanchez y Adrian Hernando Leal 
 */
public class Irrgarten {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TextUI view = new TextUI();
        Game game = new Game(2,2);
        Controller controller = new Controller(game, view);
        controller.play();    
    }
    
}
