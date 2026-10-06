package irrgarten;

/**
 * @authors Alvaro Perez Sanchez y Adrian Hernando Leal 
 */
public class TestP1 {
    public static void main(String[] args) {
    
        // ======================================================
        // 1. Prueba Weapon
        // ======================================================

        System.out.println("===== PRUEBAS ESPADA =====");

        Weapon espada = new Weapon(5,5);

        boolean usable1 = true;

        do {
            System.out.println("La espada ataca con " + espada.attack());

            if(espada.discard()){
                System.out.println("NO se ha descartado la espada");
                System.out.println(espada.toString());

            } else {
                System.out.println("SI se ha descartado la espada");
                usable1 = false;
            }
        } while(usable1);

        // ======================================================
        // 2. Prueba Shield
        // ======================================================

        System.out.println("===== PRUEBAS ESCUDO =====");

        Shield escudo = new Shield(3,4);

        boolean usable2 = true;

        do {
            System.out.println("El escudo defiende con " + escudo.protect());

            if(escudo.discard()){
                
                System.out.println("NO se ha descartado el escudo");
                System.out.println(escudo.toString());

            } else {
                System.out.println("SI se ha descartado el escudo");
                usable2 = false;
            }
        } while(usable2);

        // ======================================================
        // 3. Prueba GameState
        // ======================================================

        System.out.println("===== PRUEBAS GAMESTATE =====");

        GameState gs = new GameState("lab1", "jugadores", "monstruos", 0, false, "log inicial");

        System.out.println("El laberinto es: " + gs.getLabyrinth());
        System.out.println("Los jugadores son: " + gs.getPlayers());
        System.out.println("Los monstruos son: " + gs.getMonsters());
        System.out.println("El jugador actual es: " + gs.getCurrentPlayer());
        System.out.println("Ganador " + gs.isWinner());
        System.out.println("El log es:" + gs.getLog());

        // ======================================================
        // 4. Prueba Enumerados
        // ======================================================

        System.out.println("===== PRUEBAS ENUMERADOS =====");

        Directions dir = Directions.LEFT;
        Orientation ori = Orientation.HORIZONTAL;
        GameCharacter gc = GameCharacter.MONSTER;

        System.out.println("La direccion es: " + dir);
        System.out.println("La orientacion es: " + ori);
        System.out.println("El personaje es un: " + gc);

        System.out.println("Todas las direcciones: ");
        for (Directions d : Directions.values()){
            System.out.println("La direccion es: " + d);
        }

        System.out.println("Todas las orientaciones: ");
        for (Orientation o : Orientation.values()){
            System.out.println("La direccion es: " + o);
        }

        System.out.println("Todos los tipos de personajes: ");
        for (GameCharacter c : GameCharacter.values()){
            System.out.println("La direccion es: " + c);
        }


        // ======================================================
        // 5. Pruebas Dice
        // ======================================================

        



        

    }
}
