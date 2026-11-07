package irrgarten;

import java.util.ArrayList;
import java.util.Iterator;

public class Player {

    private static final int MAX_WEAPONS = 2;
    private static final int MAX_SHIELDS = 3;
    private static final int INITIAL_HEALTH = 10;
    private static final int HITS2LOSE = 3;
    private static final int NO_POS = -1;
    private String name;
    private char number;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    private int consecutiveHits = 0;

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
        this.weapons.clear();
        this.shields.clear();
        this.health = INITIAL_HEALTH;
        this.consecutiveHits = 0;
    }

    public void setPos(int row, int col) {
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

    public boolean dead() {
        return(this.health <= 0);
    }
                                               
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        int size = validMoves.size(); 
        boolean contained = validMoves.contains(direction);

        if (size > 0 && !contained) {
            return validMoves.get(0);
        } else {
            return direction;
        }
    }

    public float attack() {
        return this.strength + this.sumWeapons();
    }

    public boolean defend( float receivedAttack ) {
        return this.manageHit(receivedAttack);
    }

    public void receiveReward(){
        int wReward = Dice.weaponsReward();
        int sReward = Dice.shieldsReward();

        for (int i = 1; i <= wReward; i++) {
            Weapon wnew = this.newWeapon();
            this.receiveWeapon(wnew);
        }

        for (int i = 1; i <= sReward; i++) {
            Shield snew = this.newShield();
            this.receiveShield(snew);
        }

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

    private void receiveWeapon(Weapon w){
        Iterator<Weapon> it = weapons.iterator();
        while (it.hasNext()) {
            Weapon wi = it.next();
            if (wi.discard()) {
                it.remove();   // borra el elemento actual de forma segura
            }
        }

        if (weapons.size() < MAX_WEAPONS) {
            weapons.add(w);
        }
    }

    private void receiveShield(Shield s){ 
        Iterator<Shield> it = shields.iterator();
        while (it.hasNext()) {
            Shield si = it.next();
            if (si.discard()) {
                it.remove();
            }
        }

        if (shields.size() < MAX_SHIELDS) {
            shields.add(s);
        }
    }

    private Weapon newWeapon() {
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }

    private Shield newShield() {
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }

    private float defensiveEnergy() {
        return this.intelligence + this.sumShields();
    }
    
    private boolean manageHit(float receivedAttack){
        float defense = this.defensiveEnergy();

        if (defense < receivedAttack) {
            this.gotWounded();
            this.incConsecutiveHits();
        } else {
            this.resetHits();
        }

        boolean lose;
        
        if ((consecutiveHits == HITS2LOSE) || (dead())) {
            this.resetHits();
            lose = true;
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
        for (Weapon weapon : this.weapons) {
            if (weapon != null) {
                sumTotal += weapon.attack();
            }
        }
        return sumTotal;
    }

    private float sumShields() {
        float sumTotal = 0.0f;
        for (Shield shield : this.shields) {
            if (shield != null) {
                sumTotal += shield.protect();
            }
        }
        return sumTotal;
    }


}


