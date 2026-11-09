package irrgarten;

import java.util.ArrayList;
import java.util.Iterator;

public class Player {

    private static final int MAX_WEAPONS = 2; // maximo numero de armas
    private static final int MAX_SHIELDS = 3; // maximo numero de escudos
    private static final int INITIAL_HEALTH = 10; // maximos puntos de salud
    private static final int HITS2LOSE = 3; // golpes consecutivos para perder un combate
    private static final int NO_POS = -1;
    private String name; // nombre
    private char number; // numero jugador
    private float intelligence; // defensa
    private float strength; // fuerza
    private float health; // vida
    private int row; // posicion en el tablero
    private int col;
    private int consecutiveHits = 0; // golpes recibidos seguidos sin pararlos

    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;
    
    //el nombre se debe inicializar como Player#numero
    public Player( char _number, float _intelligence, float _strength){
        this.number = _number;
        this.name = "Player#"+this.number;
        this.intelligence = _intelligence;
        this.strength = _strength;
        this.health = INITIAL_HEALTH;
        this.row = NO_POS;
        this.col = NO_POS;
        this.consecutiveHits = 0;
        this.weapons = new ArrayList<>();
        this.shields = new ArrayList<>();
    }

    public void resurrect() {
        // vacia armas y escudos
        this.weapons.clear();
        this.shields.clear();
        // restaura salud inicial
        this.health = INITIAL_HEALTH;
        // pone a cero los golpes consecutivos
        this.consecutiveHits = 0;
    }

    // sirven para saber de donde parte un jugador y que caracter dibujar en la casilla
    public void setPos(int row, int col) { // 
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public char getNumber() {
        return this.number; 
    }

    public boolean dead() { // muerte jugador
        return(this.health <= 0);
    }
                                            
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        int size = validMoves.size(); 
        boolean contained = validMoves.contains(direction);

        if (size > 0 && !contained) { // si la dirección pedida es válida
            return validMoves.get(0); // se usa
        } else {
            return direction; // devuelve la pedida (aunque no se mueva)
        }
    }

    public float attack() {
        return this.strength + this.sumWeapons(); // fuerza base mas lo que aporta cada arma
    }

    public boolean defend( float receivedAttack ) {
        return this.manageHit(receivedAttack); // accion defensa. Delega en manageHit
    }

    public void receiveReward(){
        // se pide al dado cuantas armas o escudos le tocan
        int wReward = Dice.weaponsReward();
        int sReward = Dice.shieldsReward();

        // para cada uno, se crea el objeto y lo recibe
        for (int i = 1; i <= wReward; i++) { 
            Weapon wnew = this.newWeapon();
            this.receiveWeapon(wnew);
        }

        for (int i = 1; i <= sReward; i++) {
            Shield snew = this.newShield();
            this.receiveShield(snew);
        }

        // se suma una cantidad aleatoria de salud
        int extraHealth = Dice.healthReward();
        this.health += extraHealth;
    }

    public String toString() {
        String armas = "";
        for (Weapon w : weapons) {
            armas += w + " ";
        }

        String escudos = "";
        for (Shield sh : shields) {
            escudos += sh + " ";
        }

        return "Jugador {" + 
                "\n Nombre = " + this.name +
                "\n Numero = " + this.number +
                "\n Inteligencia = " + this.intelligence + 
                "\n Fuerza = " + this.strength + 
                "\n Vida = " + this.health + 
                "\n Fila = " + this.row + 
                "\n Columna = " + this.col +
                "\n Hits Consecutivos = " + this.consecutiveHits +
                "\n Armas = " + armas +
                "\n Escudos = " + escudos +
                "\n}\n";
    }

    public void setHealth(float health) { 
        this.health = health;
    }

    private void receiveWeapon(Weapon w){
        Iterator<Weapon> it = weapons.iterator(); // se limpia el inventario de armas
        while (it.hasNext()) {
            Weapon wi = it.next();
            if (wi.discard()) {
                it.remove();   // borra el elemento actual de forma segura
            }
        }

        if (weapons.size() < MAX_WEAPONS) { // si hay hueco
            weapons.add(w); // se añade un arma
        }
    }

    private void receiveShield(Shield s){ 
        Iterator<Shield> it = shields.iterator(); // se limpia el inventario de escudos
        while (it.hasNext()) {
            Shield si = it.next();
            if (si.discard()) {
                it.remove();
            }
        }

        if (shields.size() < MAX_SHIELDS) { // si hay hueco
            shields.add(s); // se añade un escudo
        }
    }

    private Weapon newWeapon() {
        return new Weapon(Dice.weaponPower(), Dice.usesLeft()); // se fabrica el objeto pidiendo al dado la potencia y usos
    }

    private Shield newShield() {
        return new Shield(Dice.shieldPower(), Dice.usesLeft()); // se fabrica el objeto pidiendo al dado la defensa y usos
    }

    private float defensiveEnergy() {
        return this.intelligence + this.sumShields(); // equivalente defensivo de attack
    }
    
    private boolean manageHit(float receivedAttack){
        float defense = this.defensiveEnergy();

        if (defense < receivedAttack) { // si la defensa no alcanza el ataque
            this.gotWounded(); // -1 de salud
            this.incConsecutiveHits(); // suma 1 a la racha de golpes seguidos
        } else {
            this.resetHits(); // si para, se reinicia la racha
        }

        boolean lose;
        
        if ((consecutiveHits == HITS2LOSE) || (dead())) { // si los golpes seguidos llegan a 3 o muere
            this.resetHits(); // resetea la racha
            lose = true; // pierde
        } else {
            lose = false;
        }

        return lose;
    }
    
    private void resetHits() {
        this.consecutiveHits = 0;
    }
    
    private void gotWounded() {
        this.health = this.health - 1;
    }

    private void incConsecutiveHits() {
       this.consecutiveHits = this.consecutiveHits + 1; 
    }

    private float sumWeapons() {
        float sumTotal = 0.0f;
        for (Weapon weapon : this.weapons) { // recorren lista
            if (weapon != null) {
                sumTotal += weapon.attack(); // acumulan ataque
            }
        }
        return sumTotal;
    }

    private float sumShields() {
        float sumTotal = 0.0f;
        for (Shield shield : this.shields) { // recorren lista
            if (shield != null) {
                sumTotal += shield.protect(); // acumulan defensa
            }
        }
        return sumTotal;
    }

    


}


